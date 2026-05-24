import { useEffect, useRef, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import api from '../api/axios'
import LoadingSpinner from '../components/LoadingSpinner'

const POLL_INTERVAL_MS = 10000
const RETRY_INTERVAL_MS = 5000

export default function OrderTracking() {
  const { orderId } = useParams()
  const [order, setOrder] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [connectionState, setConnectionState] = useState('connecting')
  const lastStatusVersionRef = useRef(0)
  const activeRequestRef = useRef(0)
  const timerRef = useRef(null)
  const stoppedRef = useRef(false)

  const fetchTracking = async () => {
    const requestId = activeRequestRef.current + 1
    activeRequestRef.current = requestId

    try {
      const response = await api.get(`/api/orders/${orderId}/tracking`)
      const nextOrder = response.data.data

      if (requestId !== activeRequestRef.current) {
        return
      }

      setOrder((prev) => {
        if (!prev || nextOrder.statusVersion >= lastStatusVersionRef.current) {
          lastStatusVersionRef.current = nextOrder.statusVersion
          return nextOrder
        }
        return prev
      })
      setConnectionState('connected')
      setError('')
      return true
    } catch (err) {
      setConnectionState('reconnecting')
      setError(err.response?.data?.message || 'Unable to fetch order status right now.')
      return false
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    stoppedRef.current = false

    const poll = async () => {
      if (stoppedRef.current) return

      const isConnected = await fetchTracking()

      if (stoppedRef.current) return
      timerRef.current = setTimeout(poll, isConnected ? POLL_INTERVAL_MS : RETRY_INTERVAL_MS)
    }

    poll()

    return () => {
      stoppedRef.current = true
      if (timerRef.current) {
        clearTimeout(timerRef.current)
      }
    }
  }, [orderId])

  if (loading) return <LoadingSpinner />

  if (!order) {
    return (
      <div className="max-w-3xl mx-auto px-4 py-8">
        <h1 className="text-2xl font-bold mb-3">Order Tracking</h1>
        <p className="text-red-600 mb-4">{error || 'Order not found.'}</p>
        <Link to="/restaurants" className="btn-primary inline-block">
          Browse Restaurants
        </Link>
      </div>
    )
  }

  return (
    <div className="max-w-3xl mx-auto px-4 py-8">
      <div className="flex flex-wrap justify-between items-start gap-3 mb-6">
        <div>
          <h1 className="text-3xl font-bold text-text-dark">Track Order #{order.id}</h1>
          <p className="text-text-muted mt-1">{order.restaurantName}</p>
        </div>
        <span
          className={`px-3 py-1 rounded-full text-sm font-medium ${
            connectionState === 'connected' ? 'bg-green-100 text-green-700' : 'bg-amber-100 text-amber-700'
          }`}
        >
          {connectionState === 'connected' ? 'Live updates' : 'Reconnecting...'}
        </span>
      </div>

      <div className="card p-5 mb-4">
        <p className="text-sm text-text-muted">Current status</p>
        <p className="text-xl font-semibold mt-1">{order.status.replaceAll('_', ' ')}</p>
        <p className="text-sm text-text-muted mt-2">Status version: {order.statusVersion}</p>
        <p className="text-sm text-text-muted">Last update: {new Date(order.updatedAt).toLocaleString()}</p>
      </div>

      <div className="card p-5 mb-4">
        <p className="text-sm text-text-muted">Delivery address</p>
        <p className="mt-1 font-medium">{order.deliveryAddress}</p>
        <div className="flex justify-between mt-4">
          <span className="text-text-muted">Total</span>
          <span className="font-semibold">₹{Number(order.total).toFixed(0)}</span>
        </div>
      </div>

      <div className="card p-5">
        <h2 className="font-semibold mb-3">Items</h2>
        <div className="space-y-2">
          {order.items.map((item) => (
            <div key={item.id} className="flex justify-between text-sm">
              <span>
                {item.name} x {item.quantity}
              </span>
              <span>₹{Number(item.lineTotal).toFixed(0)}</span>
            </div>
          ))}
        </div>
      </div>

      {error && <p className="text-amber-600 text-sm mt-4">{error}</p>}
    </div>
  )
}

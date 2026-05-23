import { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import api from '../api/axios'
import useCartStore from '../store/cartStore'
import LoadingSpinner from '../components/LoadingSpinner'

export default function Cart() {
  const navigate = useNavigate()
  const { items, totalPrice, loading, fetchCart, updateItem, removeItem, clearCart } = useCartStore()
  const [deliveryAddress, setDeliveryAddress] = useState('')
  const [placingOrder, setPlacingOrder] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    fetchCart()
  }, [fetchCart])

  const handleCheckout = async () => {
    if (!deliveryAddress.trim()) {
      setError('Please enter a delivery address.')
      return
    }

    try {
      setPlacingOrder(true)
      setError('')
      const response = await api.post('/api/orders', {
        deliveryAddress: deliveryAddress.trim(),
      })

      const order = response.data.data
      await fetchCart()
      navigate(`/orders/${order.id}/track`)
    } catch (err) {
      setError(err.response?.data?.message || 'Unable to place order. Please try again.')
    } finally {
      setPlacingOrder(false)
    }
  }

  if (loading) return <LoadingSpinner />

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold text-text-dark mb-8">Your Cart</h1>

      {items.length === 0 ? (
        <div className="text-center py-16">
          <p className="text-text-muted text-lg">Your cart is empty</p>
          {/* TODO: ISSUE-029 - Empty state illustration */}
          <Link to="/restaurants" className="btn-primary inline-block mt-6">
            Browse Restaurants
          </Link>
        </div>
      ) : (
        <>
          <div className="space-y-4">
            {items.map((item) => (
              <div key={item.menuItemId} className="card flex gap-4 p-4">
                <img
                  src={item.imageUrl || 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=200'}
                  alt={item.name}
                  className="w-24 h-24 object-cover rounded-lg"
                />
                <div className="flex-1">
                  <h3 className="font-semibold">{item.name}</h3>
                  <p className="text-text-muted text-sm mt-1">₹{Number(item.price).toFixed(0)} each</p>
                  <div className="flex items-center gap-3 mt-3">
                    <button
                      onClick={() => updateItem(item.menuItemId, item.quantity - 1)}
                      disabled={item.quantity <= 1}
                      className="w-8 h-8 rounded border flex items-center justify-center hover:border-tomato disabled:opacity-50"
                    >
                      -
                    </button>
                    <span className="font-medium w-6 text-center">{item.quantity}</span>
                    <button
                      onClick={() => updateItem(item.menuItemId, item.quantity + 1)}
                      className="w-8 h-8 rounded border flex items-center justify-center hover:border-tomato"
                    >
                      +
                    </button>
                    <button
                      onClick={() => removeItem(item.menuItemId)}
                      className="ml-auto text-red-500 text-sm hover:underline"
                    >
                      Remove
                    </button>
                  </div>
                </div>
                <div className="text-right">
                  <p className="font-semibold text-lg">₹{Number(item.lineTotal).toFixed(0)}</p>
                </div>
              </div>
            ))}
          </div>

          <div className="mt-8 p-6 bg-bg-light rounded-xl">
            <div className="flex justify-between items-center mb-4">
              <span className="text-lg font-medium">Subtotal</span>
              <span className="text-2xl font-bold">₹{Number(totalPrice).toFixed(0)}</span>
            </div>
            <div className="mb-3">
              <label className="block text-sm font-medium text-text-dark mb-1">Delivery Address</label>
              <textarea
                value={deliveryAddress}
                onChange={(e) => setDeliveryAddress(e.target.value)}
                rows={3}
                className="input-field resize-none"
                placeholder="Enter complete delivery address"
              />
            </div>
            {error && <p className="text-sm text-red-600 mb-3">{error}</p>}
            <button
              onClick={handleCheckout}
              disabled={placingOrder}
              className="btn-primary w-full py-3 disabled:opacity-70"
            >
              {placingOrder ? 'Placing order...' : 'Proceed to Checkout'}
            </button>
            <button
              onClick={clearCart}
              className="w-full mt-3 text-red-500 text-sm hover:underline"
            >
              Clear Cart
            </button>
          </div>
        </>
      )}
    </div>
  )
}

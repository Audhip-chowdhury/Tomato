import { useState } from 'react'
import useAuthStore from '../store/authStore'
import useCartStore from '../store/cartStore'

export default function MenuItemCard({ item, restaurantOpen = true }) {
  const [adding, setAdding] = useState(false)
  const token = useAuthStore((state) => state.token)
  const addItem = useCartStore((state) => state.addItem)

  const handleAdd = async () => {
    if (!token) {
      window.location.href = '/login'
      return
    }
    if (!restaurantOpen) return

    setAdding(true)
    try {
      await addItem(item.id, 1)
    } finally {
      setAdding(false)
    }
  }

  return (
    <div className="card flex gap-4 p-4">
      <img
        src={item.imageUrl || 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=300'}
        alt={item.name}
        className="w-28 h-28 object-cover rounded-lg flex-shrink-0"
      />
      <div className="flex-1 flex flex-col justify-between">
        <div>
          <h4 className="font-semibold text-text-dark">{item.name}</h4>
          <p className="text-text-muted text-sm mt-1 line-clamp-2">{item.description}</p>
        </div>
        <div className="flex items-center justify-between mt-3">
          <span className="font-semibold text-text-dark">
            ₹{Number(item.price).toFixed(0)}
          </span>
          <button
            onClick={handleAdd}
            disabled={adding || !restaurantOpen}
            className="btn-primary text-sm px-4 py-1.5 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {adding ? 'Adding...' : restaurantOpen ? 'ADD' : 'Closed'}
          </button>
        </div>
      </div>
    </div>
  )
}

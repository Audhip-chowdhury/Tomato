import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import api from '../api/axios'
import MenuItemCard from '../components/MenuItemCard'
import LoadingSpinner from '../components/LoadingSpinner'

export default function RestaurantDetail() {
  const { id } = useParams()
  const [restaurant, setRestaurant] = useState(null)
  const [menu, setMenu] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [restRes, menuRes] = await Promise.all([
          api.get(`/api/restaurants/${id}`),
          api.get(`/api/restaurants/${id}/menu`),
        ])
        setRestaurant(restRes.data.data)
        setMenu(menuRes.data.data || [])
      } catch (error) {
        console.error('Failed to fetch restaurant:', error)
      } finally {
        setLoading(false)
      }
    }
    fetchData()
  }, [id])

  const groupedMenu = menu.reduce((acc, item) => {
    const category = item.category || 'Other'
    if (!acc[category]) acc[category] = []
    acc[category].push(item)
    return acc
  }, {})

  if (loading) return <LoadingSpinner />
  if (!restaurant) {
    return (
      <div className="text-center py-20">
        <p className="text-text-muted">Restaurant not found.</p>
      </div>
    )
  }

  return (
    <div>
      <div className="relative h-64 md:h-80">
        <img
          src={restaurant.imageUrl || 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800'}
          alt={restaurant.name}
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-t from-black/70 to-transparent" />
        <div className="absolute bottom-0 left-0 right-0 p-6 md:p-8 text-white">
          <div className="max-w-7xl mx-auto">
            <span
              className={`inline-block px-3 py-1 rounded text-sm font-semibold mb-3 ${
                restaurant.isOpen ? 'bg-green-500' : 'bg-red-500'
              }`}
            >
              {restaurant.isOpen ? 'Open Now' : 'Closed'}
            </span>
            <h1 className="text-3xl md:text-4xl font-bold">{restaurant.name}</h1>
            <p className="text-white/80 mt-2">{restaurant.cuisine} · {restaurant.city}</p>
            <div className="flex items-center gap-1 mt-2">
              <svg className="w-5 h-5 text-yellow-400 fill-current" viewBox="0 0 20 20">
                <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
              </svg>
              <span className="font-medium">{restaurant.rating}</span>
            </div>
          </div>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 py-8">
        {Object.keys(groupedMenu).length === 0 ? (
          <p className="text-text-muted text-center py-12">No menu items available.</p>
        ) : (
          Object.entries(groupedMenu).map(([category, items]) => (
            <div key={category} className="mb-10">
              <h2 className="text-xl font-bold text-text-dark mb-4 border-b pb-2">{category}</h2>
              <div className="grid grid-cols-1 lg:grid-cols-2 gap-4">
                {items.map((item) => (
                  <MenuItemCard
                    key={item.id}
                    item={item}
                    restaurantOpen={restaurant.isOpen}
                  />
                ))}
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  )
}

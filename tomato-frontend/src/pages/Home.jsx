import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import api from '../api/axios'
import RestaurantCard from '../components/RestaurantCard'
import LoadingSpinner from '../components/LoadingSpinner'

const FEATURED_RESTAURANTS = [
  { id: 1, name: 'Punjab Grill', cuisine: 'North Indian', city: 'Mumbai', rating: 4.5, imageUrl: 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=400', isOpen: true },
  { id: 2, name: 'Saravana Bhavan', cuisine: 'South Indian', city: 'Mumbai', rating: 4.3, imageUrl: 'https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=400', isOpen: true },
  { id: 4, name: "Karim's", cuisine: 'North Indian', city: 'Delhi', rating: 4.6, imageUrl: 'https://images.unsplash.com/photo-1552566626-52f8b828add9?w=400', isOpen: true },
  { id: 5, name: 'Biryani Blues', cuisine: 'Biryani', city: 'Delhi', rating: 4.4, imageUrl: 'https://images.unsplash.com/photo-1563379091339-03246963d96c?w=400', isOpen: true },
  { id: 7, name: 'Truffles', cuisine: 'Burgers', city: 'Bangalore', rating: 4.7, imageUrl: 'https://images.unsplash.com/photo-1571091718767-18b5b1457a45?w=400', isOpen: true },
  { id: 9, name: 'Corner House', cuisine: 'Desserts', city: 'Bangalore', rating: 4.8, imageUrl: 'https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=400', isOpen: true },
]

export default function Home() {
  const [restaurants, setRestaurants] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const fetchFeatured = async () => {
      try {
        const response = await api.get('/api/restaurants?page=0&size=6')
        const data = response.data.data?.content
        if (data && data.length > 0) {
          setRestaurants(data.slice(0, 6))
        } else {
          setRestaurants(FEATURED_RESTAURANTS)
        }
      } catch {
        setRestaurants(FEATURED_RESTAURANTS)
      } finally {
        setLoading(false)
      }
    }
    fetchFeatured()
  }, [])

  return (
    <div>
      <section className="bg-gradient-to-r from-tomato to-tomato-dark text-white">
        <div className="max-w-7xl mx-auto px-4 py-20 md:py-28">
          <h1 className="text-4xl md:text-5xl font-bold leading-tight">
            Order food you love
          </h1>
          <p className="text-lg md:text-xl mt-4 text-white/90 max-w-xl">
            Discover the best restaurants in Mumbai, Delhi & Bangalore — delivered to your doorstep.
          </p>
          <Link
            to="/restaurants"
            className="inline-block mt-8 bg-white text-tomato font-semibold px-8 py-3 rounded-lg hover:bg-gray-100 transition-colors"
          >
            Explore Restaurants
          </Link>
          {/* TODO: ISSUE-001 - Search bar with live autocomplete */}
        </div>
      </section>

      <section className="max-w-7xl mx-auto px-4 py-12">
        <div className="flex items-center justify-between mb-8">
          <h2 className="text-2xl font-bold text-text-dark">Featured Restaurants</h2>
          <Link to="/restaurants" className="text-tomato font-medium hover:underline">
            View All
          </Link>
        </div>

        {loading ? (
          <LoadingSpinner />
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            {restaurants.map((restaurant) => (
              <RestaurantCard key={restaurant.id} restaurant={restaurant} />
            ))}
          </div>
        )}
      </section>
    </div>
  )
}

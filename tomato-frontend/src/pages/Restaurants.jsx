import { useEffect, useState } from 'react'
import api from '../api/axios'
import RestaurantCard from '../components/RestaurantCard'
import LoadingSpinner from '../components/LoadingSpinner'

const TABS = ['All', 'Veg', 'Non-Veg']

const VEG_CUISINES = ['South Indian', 'Desserts']
const NON_VEG_CUISINES = ['North Indian', 'Chinese', 'Pizza', 'Burgers', 'Biryani']

export default function Restaurants() {
  const [restaurants, setRestaurants] = useState([])
  const [filtered, setFiltered] = useState([])
  const [activeTab, setActiveTab] = useState('All')
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const fetchRestaurants = async () => {
      try {
        const response = await api.get('/api/restaurants?page=0&size=50')
        const data = response.data.data?.content || []
        setRestaurants(data)
        setFiltered(data)
      } catch (error) {
        console.error('Failed to fetch restaurants:', error)
      } finally {
        setLoading(false)
      }
    }
    fetchRestaurants()
  }, [])

  useEffect(() => {
    if (activeTab === 'All') {
      setFiltered(restaurants)
    } else if (activeTab === 'Veg') {
      setFiltered(restaurants.filter((r) => VEG_CUISINES.includes(r.cuisine)))
    } else {
      setFiltered(restaurants.filter((r) => NON_VEG_CUISINES.includes(r.cuisine)))
    }
    // TODO: ISSUE-005 - Vegetarian-only toggle filter (proper backend support)
  }, [activeTab, restaurants])

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold text-text-dark mb-2">All Restaurants</h1>
      <p className="text-text-muted mb-8">Browse restaurants near you</p>

      <div className="flex gap-2 mb-8 overflow-x-auto">
        {TABS.map((tab) => (
          <button
            key={tab}
            onClick={() => setActiveTab(tab)}
            className={`px-5 py-2 rounded-full font-medium whitespace-nowrap transition-colors ${
              activeTab === tab
                ? 'bg-tomato text-white'
                : 'bg-gray-100 text-text-dark hover:bg-gray-200'
            }`}
          >
            {tab}
          </button>
        ))}
      </div>

      {/* TODO: ISSUE-001 to ISSUE-006 - Search, filters, sort */}

      {loading ? (
        <LoadingSpinner />
      ) : filtered.length === 0 ? (
        <p className="text-center text-text-muted py-12">No restaurants found.</p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {filtered.map((restaurant) => (
            <RestaurantCard key={restaurant.id} restaurant={restaurant} />
          ))}
        </div>
      )}
    </div>
  )
}

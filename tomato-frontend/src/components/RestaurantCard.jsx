import { Link } from 'react-router-dom'

export default function RestaurantCard({ restaurant }) {
  return (
    <Link to={`/restaurants/${restaurant.id}`} className="card block group">
      <div className="relative h-48 overflow-hidden">
        <img
          src={restaurant.imageUrl || 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=400'}
          alt={restaurant.name}
          className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
        />
        <span
          className={`absolute top-3 right-3 px-2 py-1 rounded text-xs font-semibold ${
            restaurant.isOpen
              ? 'bg-green-500 text-white'
              : 'bg-red-500 text-white'
          }`}
        >
          {restaurant.isOpen ? 'Open' : 'Closed'}
        </span>
      </div>
      <div className="p-4">
        <h3 className="font-semibold text-lg text-text-dark group-hover:text-tomato transition-colors">
          {restaurant.name}
        </h3>
        <p className="text-text-muted text-sm mt-1">{restaurant.cuisine}</p>
        <div className="flex items-center justify-between mt-3">
          <span className="text-sm text-text-muted">{restaurant.city}</span>
          <div className="flex items-center gap-1">
            <svg className="w-4 h-4 text-yellow-400 fill-current" viewBox="0 0 20 20">
              <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
            </svg>
            <span className="text-sm font-medium">{restaurant.rating}</span>
          </div>
        </div>
      </div>
    </Link>
  )
}

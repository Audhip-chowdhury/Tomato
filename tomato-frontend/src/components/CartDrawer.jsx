import { Link } from 'react-router-dom'
import useCartStore from '../store/cartStore'
import useAuthStore from '../store/authStore'

export default function CartDrawer({ isOpen, onClose }) {
  const items = useCartStore((state) => state.items)
  const totalPrice = useCartStore((state) => state.totalPrice)
  const updateItem = useCartStore((state) => state.updateItem)
  const removeItem = useCartStore((state) => state.removeItem)
  const token = useAuthStore((state) => state.token)

  if (!isOpen) return null

  return (
    <>
      <div className="fixed inset-0 bg-black/50 z-40" onClick={onClose} />
      <div className="fixed right-0 top-0 h-full w-full max-w-md bg-white shadow-xl z-50 flex flex-col">
        <div className="flex items-center justify-between p-4 border-b">
          <h2 className="text-lg font-semibold">Your Cart</h2>
          <button onClick={onClose} className="p-2 hover:bg-gray-100 rounded-lg">
            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>

        <div className="flex-1 overflow-y-auto p-4">
          {!token ? (
            <div className="text-center py-12">
              <p className="text-text-muted">Please login to view your cart</p>
              <Link to="/login" className="btn-primary inline-block mt-4" onClick={onClose}>
                Login
              </Link>
            </div>
          ) : items.length === 0 ? (
            <div className="text-center py-12">
              <p className="text-text-muted">Your cart is empty</p>
              {/* TODO: ISSUE-029 - Add empty state illustration */}
            </div>
          ) : (
            <div className="space-y-4">
              {items.map((item) => (
                <div key={item.menuItemId} className="flex gap-3 border-b pb-4">
                  <img
                    src={item.imageUrl || 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=100'}
                    alt={item.name}
                    className="w-16 h-16 object-cover rounded-lg"
                  />
                  <div className="flex-1">
                    <h4 className="font-medium text-sm">{item.name}</h4>
                    <p className="text-tomato font-semibold text-sm mt-1">
                      ₹{Number(item.lineTotal).toFixed(0)}
                    </p>
                    <div className="flex items-center gap-2 mt-2">
                      <button
                        onClick={() =>
                          item.quantity <= 1
                            ? removeItem(item.menuItemId)
                            : updateItem(item.menuItemId, item.quantity - 1)
                        }
                        className="w-7 h-7 rounded border flex items-center justify-center hover:border-tomato"
                      >
                        -
                      </button>
                      <span className="text-sm font-medium w-6 text-center">{item.quantity}</span>
                      <button
                        onClick={() => updateItem(item.menuItemId, item.quantity + 1)}
                        className="w-7 h-7 rounded border flex items-center justify-center hover:border-tomato"
                      >
                        +
                      </button>
                      <button
                        onClick={() => removeItem(item.menuItemId)}
                        className="ml-auto text-red-500 text-xs hover:underline"
                      >
                        Remove
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {token && items.length > 0 && (
          <div className="p-4 border-t">
            <div className="flex justify-between mb-4">
              <span className="font-medium">Subtotal</span>
              <span className="font-semibold">₹{Number(totalPrice).toFixed(0)}</span>
            </div>
            <Link
              to="/cart"
              onClick={onClose}
              className="btn-primary w-full text-center block"
            >
              View Cart
            </Link>
          </div>
        )}
      </div>
    </>
  )
}

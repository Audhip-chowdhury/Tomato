import { create } from 'zustand'
import api from '../api/axios'
import useAuthStore from './authStore'

const useCartStore = create((set, get) => ({
  items: [],
  totalItems: 0,
  totalPrice: 0,
  loading: false,

  setCart: (cartData) => {
    const items = cartData?.items || []
    set({
      items,
      totalItems: cartData?.totalItems || 0,
      totalPrice: cartData?.subtotal || 0,
    })
  },

  fetchCart: async () => {
    const token = useAuthStore.getState().token
    if (!token) return

    try {
      set({ loading: true })
      const response = await api.get('/api/cart')
      get().setCart(response.data.data)
    } catch (error) {
      console.error('Failed to fetch cart:', error)
    } finally {
      set({ loading: false })
    }
  },

  addItem: async (menuItemId, quantity = 1) => {
    const token = useAuthStore.getState().token
    if (!token) {
      window.location.href = '/login'
      return
    }

    try {
      const response = await api.post('/api/cart/add', { menuItemId, quantity })
      get().setCart(response.data.data)
    } catch (error) {
      console.error('Failed to add item:', error)
      throw error
    }
  },

  updateItem: async (menuItemId, quantity) => {
    try {
      const response = await api.put('/api/cart/update', { menuItemId, quantity })
      get().setCart(response.data.data)
    } catch (error) {
      console.error('Failed to update item:', error)
    }
  },

  removeItem: async (menuItemId) => {
    try {
      const response = await api.delete(`/api/cart/remove/${menuItemId}`)
      get().setCart(response.data.data)
    } catch (error) {
      console.error('Failed to remove item:', error)
    }
  },

  clearCart: async () => {
    try {
      const response = await api.delete('/api/cart/clear')
      get().setCart(response.data.data)
    } catch (error) {
      console.error('Failed to clear cart:', error)
    }
  },
}))

export default useCartStore

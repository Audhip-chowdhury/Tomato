import { create } from 'zustand'
import { persist } from 'zustand/middleware'

const useAuthStore = create(
  persist(
    (set) => ({
      user: null,
      token: null,

      login: (data) => {
        set({
          token: data.token,
          user: {
            id: data.id,
            name: data.name,
            email: data.email,
            role: data.role,
            phone: data.phone,
          },
        })
      },

      logout: () => {
        set({ user: null, token: null })
      },

      initFromStorage: () => {
        // Handled automatically by persist middleware
      },
    }),
    {
      name: 'tomato-auth',
      partialize: (state) => ({ user: state.user, token: state.token }),
    }
  )
)

export default useAuthStore

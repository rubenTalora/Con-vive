import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import authService from '@/services/authService'

export const useAuthStore = defineStore('auth', () => {
  // State
  const user = ref(null)
  const token = ref(localStorage.getItem('token') || null)
  const refreshToken = ref(localStorage.getItem('refreshToken') || null)
  const loading = ref(false)
  const error = ref(null)

  // Getters
  const isAuthenticated = computed(() => !!token.value)
  const currentUser = computed(() => user.value)

  // Actions
  async function login(email, password) {
    loading.value = true
    error.value = null
    
    try {
      const response = await authService.login(email, password)
      
      user.value = response.user
      token.value = response.token
      refreshToken.value = response.refreshToken
      
      // Guardar en localStorage
      localStorage.setItem('token', response.token)
      localStorage.setItem('refreshToken', response.refreshToken)
      localStorage.setItem('user', JSON.stringify(response.user))
      
      return response
    } catch (err) {
      error.value = err.message || 'Error al iniciar sesión'
      throw err
    } finally {
      loading.value = false
    }
  }

  async function logout() {
    try {
      // Intentar revocar el refresh token en el servidor
      if (refreshToken.value) {
        await authService.logout(refreshToken.value)
      }
    } catch (err) {
      console.error('Error al hacer logout:', err)
    } finally {
      // Limpiar estado local
      user.value = null
      token.value = null
      refreshToken.value = null
      localStorage.removeItem('token')
      localStorage.removeItem('refreshToken')
      localStorage.removeItem('user')
    }
  }

  async function refresh() {
    if (!refreshToken.value) {
      throw new Error('No hay refresh token disponible')
    }

    try {
      const response = await authService.refreshToken(refreshToken.value)
      
      token.value = response.token
      refreshToken.value = response.refreshToken
      
      localStorage.setItem('token', response.token)
      localStorage.setItem('refreshToken', response.refreshToken)
      
      return response
    } catch (err) {
      // Si falla el refresh, hacer logout
      await logout()
      throw err
    }
  }

  function initAuth() {
    const savedToken = localStorage.getItem('token')
    const savedRefreshToken = localStorage.getItem('refreshToken')
    const savedUser = localStorage.getItem('user')
    
    if (savedToken && savedRefreshToken && savedUser) {
      token.value = savedToken
      refreshToken.value = savedRefreshToken
      user.value = JSON.parse(savedUser)
    }
  }

  return {
    user,
    token,
    refreshToken,
    loading,
    error,
    isAuthenticated,
    currentUser,
    login,
    logout,
    refresh,
    initAuth
  }
})

/**
 * Auth Service - Integración con Odoo
 * Servicio de autenticación con JWT usando la API de Odoo
 */

// Configuración de la API
const API_URL = 'http://localhost:8069/api/convive'

const authService = {
  /**
   * Registro de nuevo usuario
   * @param {Object} userData - { name, login, password, birthdate? }
   * @returns {Promise<Object>} - Confirmación del registro
   */
  async register(userData) {
    try {
      const response = await fetch(`${API_URL}/auth/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData)
      })

      if (!response.ok) {
        const error = await response.json()
        throw new Error(error.error || 'Error al registrarse')
      }

      return await response.json()
    } catch (error) {
      console.error('Error en registro:', error)
      throw error
    }
  },

  /**
   * Login con Odoo
   * @param {string} login - Email o username del usuario
   * @param {string} password - Contraseña
   * @returns {Promise<Object>} - { access_token, refresh_token, user }
   */
  async login(login, password) {
    try {
      const response = await fetch(`${API_URL}/auth/token`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ login, password })
      })

      if (!response.ok) {
        const error = await response.json()
        throw new Error(error.error || 'Error al iniciar sesión')
      }

      const data = await response.json()
      
      // La API de Odoo retorna:
      // {
      //   access_token: "...",
      //   refresh_token: "...",
      //   token_type: "Bearer",
      //   expires_in: 3600,
      //   user: { id, name, login, role }
      // }
      
      return {
        token: data.access_token,
        refreshToken: data.refresh_token,
        expiresIn: data.expires_in,
        user: data.user
      }
    } catch (error) {
      console.error('Error en login:', error)
      throw error
    }
  },

  /**
   * Refresh del access token
   * @param {string} refreshToken - Refresh token
   * @returns {Promise<Object>} - { access_token, refresh_token }
   */
  async refreshToken(refreshToken) {
    try {
      const response = await fetch(`${API_URL}/auth/refresh`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ refresh_token: refreshToken })
      })

      if (!response.ok) {
        const error = await response.json()
        throw new Error(error.error || 'Error al refrescar el token')
      }

      const data = await response.json()
      
      return {
        token: data.access_token,
        refreshToken: data.refresh_token,
        expiresIn: data.expires_in
      }
    } catch (error) {
      console.error('Error al refrescar token:', error)
      throw error
    }
  },

  /**
   * Logout - Revoca el refresh token
   * @param {string} refreshToken - Refresh token a revocar
   * @returns {Promise<boolean>}
   */
  async logout(refreshToken) {
    try {
      const response = await fetch(`${API_URL}/auth/logout`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ refresh_token: refreshToken })
      })

      if (!response.ok) {
        console.warn('Error al hacer logout en el servidor')
      }

      return true
    } catch (error) {
      console.error('Error en logout:', error)
      // Retornamos true de todos modos para permitir el logout local
      return true
    }
  },

  /**
   * Obtener información del usuario actual
   * @param {string} accessToken - Access token
   * @returns {Promise<Object>} - Información del usuario
   */
  async getMe(accessToken) {
    try {
      const response = await fetch(`${API_URL}/users/me`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${accessToken}`,
          'Content-Type': 'application/json',
        }
      })

      if (!response.ok) {
        const error = await response.json()
        throw new Error(error.error || 'Error al obtener datos del usuario')
      }

      return await response.json()
    } catch (error) {
      console.error('Error al obtener usuario:', error)
      throw error
    }
  }
}

export default authService

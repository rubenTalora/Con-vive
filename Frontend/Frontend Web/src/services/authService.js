/**
 * Mock Auth Service
 * Servicio de autenticación provisional con datos mockeados
 * TODO: Reemplazar con integración real de Odoo cuando esté disponible
 */

// Credenciales mock
const MOCK_USERS = [
  {
    id: 1,
    email: 'user@convive.com',
    password: 'password123',
    name: 'Usuario Demo',
    role: 'user'
  },
  {
    id: 2,
    email: 'admin@convive.com',
    password: 'admin123',
    name: 'Admin Demo',
    role: 'admin'
  }
]

// Simular delay de red
const delay = (ms = 800) => new Promise(resolve => setTimeout(resolve, ms))

const authService = {
  /**
   * Login mock
   * @param {string} email 
   * @param {string} password 
   * @returns {Promise<Object>}
   */
  async login(email, password) {
    await delay()
    
    const user = MOCK_USERS.find(
      u => u.email === email && u.password === password
    )
    
    if (!user) {
      throw new Error('Credenciales inválidas')
    }
    
    // Generar token mock (en producción vendrá de Odoo)
    const token = `mock_token_${user.id}_${Date.now()}`
    
    return {
      token,
      user: {
        id: user.id,
        email: user.email,
        name: user.name,
        role: user.role
      }
    }
  },

  /**
   * Verificar token (mock)
   * @param {string} token 
   * @returns {Promise<boolean>}
   */
  async verifyToken(token) {
    await delay(300)
    return token && token.startsWith('mock_token_')
  },

  /**
   * Logout (mock)
   * @returns {Promise<void>}
   */
  async logout() {
    await delay(300)
    return true
  }
}

export default authService

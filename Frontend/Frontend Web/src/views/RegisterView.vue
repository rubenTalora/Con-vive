<template>
  <div class="register-container">
    <div class="register-card">
      <div class="register-header">
        <h1 class="logo">Con!Vive</h1>
        <p class="tagline">Únete a la comunidad</p>
      </div>

      <form @submit.prevent="handleRegister" class="register-form">
        <div class="form-group">
          <label for="name">Nombre completo *</label>
          <input
            id="name"
            v-model="formData.name"
            type="text"
            placeholder="Tu nombre completo"
            required
            :disabled="loading"
          />
        </div>

        <div class="form-group">
          <label for="email">Correo electrónico *</label>
          <input
            id="email"
            v-model="formData.email"
            type="email"
            placeholder="tu@email.com"
            required
            :disabled="loading"
          />
        </div>

        <div class="form-group">
          <label for="password">Contraseña *</label>
          <input
            id="password"
            v-model="formData.password"
            type="password"
            placeholder="Mínimo 8 caracteres"
            required
            minlength="8"
            :disabled="loading"
          />
        </div>

        <div class="form-group">
          <label for="confirmPassword">Repetir contraseña *</label>
          <input
            id="confirmPassword"
            v-model="formData.confirmPassword"
            type="password"
            placeholder="Repite tu contraseña"
            required
            :disabled="loading"
          />
          <span v-if="passwordMismatch" class="field-error">
            Las contraseñas no coinciden
          </span>
        </div>

        <div class="form-group">
          <label for="birthdate">Fecha de nacimiento</label>
          <input
            id="birthdate"
            v-model="formData.birthdate"
            type="date"
            :disabled="loading"
            :max="maxDate"
          />
          <span class="field-hint">Opcional</span>
        </div>

        <div v-if="error" class="error-message">
          {{ error }}
        </div>

        <button 
          type="submit" 
          class="btn-register"
          :disabled="loading || passwordMismatch"
        >
          {{ loading ? 'Registrando...' : 'Registrarse' }}
        </button>
      </form>

      <div class="login-link">
        <p>
          ¿Ya tienes cuenta? 
          <router-link to="/login">Inicia sesión</router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import authService from '@/services/authService'

const router = useRouter()
const loading = ref(false)
const error = ref(null)

const formData = reactive({
  name: '',
  email: '',
  password: '',
  confirmPassword: '',
  birthdate: ''
})

// Fecha máxima (hoy)
const maxDate = new Date().toISOString().split('T')[0]

// Validación de contraseñas
const passwordMismatch = computed(() => {
  if (!formData.confirmPassword) return false
  return formData.password !== formData.confirmPassword
})

const handleRegister = async () => {
  // Validación final
  if (formData.password !== formData.confirmPassword) {
    error.value = 'Las contraseñas no coinciden'
    return
  }

  loading.value = true
  error.value = null

  try {
    // Preparar datos para el registro
    const registerData = {
      name: formData.name,
      login: formData.email,
      password: formData.password
    }

    // Añadir fecha de nacimiento si está presente
    if (formData.birthdate) {
      registerData.birthdate = formData.birthdate
    }

    await authService.register(registerData)
    
    // Registro exitoso, redirigir al login
    await router.push({ 
      name: 'login',
      query: { registered: 'true' }
    })
  } catch (err) {
    error.value = err.message || 'Error al registrarse. Inténtalo de nuevo.'
    console.error('Error en registro:', err)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 1rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-card {
  background: white;
  border-radius: 1rem;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  padding: 2.5rem;
  width: 100%;
  max-width: 440px;
}

.register-header {
  text-align: center;
  margin-bottom: 2rem;
}

.logo {
  font-size: 2.5rem;
  font-weight: 700;
  color: #667eea;
  margin-bottom: 0.5rem;
}

.tagline {
  color: #64748b;
  font-size: 1rem;
}

.register-form {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group label {
  font-weight: 500;
  color: #334155;
  font-size: 0.95rem;
}

.form-group input {
  padding: 0.75rem 1rem;
  border: 2px solid #e2e8f0;
  border-radius: 0.5rem;
  font-size: 1rem;
  transition: all 0.2s;
}

.form-group input:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.form-group input:disabled {
  background-color: #f1f5f9;
  cursor: not-allowed;
}

.field-error {
  color: #ef4444;
  font-size: 0.85rem;
  margin-top: -0.25rem;
}

.field-hint {
  color: #94a3b8;
  font-size: 0.85rem;
  margin-top: -0.25rem;
}

.error-message {
  background-color: #fee2e2;
  color: #dc2626;
  padding: 0.75rem;
  border-radius: 0.5rem;
  font-size: 0.9rem;
  border: 1px solid #fecaca;
}

.btn-register {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 0.875rem;
  border: none;
  border-radius: 0.5rem;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
  margin-top: 0.5rem;
}

.btn-register:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
}

.btn-register:active:not(:disabled) {
  transform: translateY(0);
}

.btn-register:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.login-link {
  text-align: center;
  margin-top: 1.5rem;
  padding-top: 1.5rem;
  border-top: 1px solid #e2e8f0;
}

.login-link p {
  color: #64748b;
  font-size: 0.95rem;
}

.login-link a {
  color: #667eea;
  font-weight: 600;
  text-decoration: none;
  transition: color 0.2s;
}

.login-link a:hover {
  color: #764ba2;
  text-decoration: underline;
}

@media (max-width: 480px) {
  .register-card {
    padding: 1.5rem;
  }

  .logo {
    font-size: 2rem;
  }
}
</style>

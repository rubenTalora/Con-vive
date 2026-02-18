<template>
  <div class="home-container">
    <header class="header">
      <div class="header-content">
        <h1 class="logo">Con!Vive</h1>
        <div class="user-menu">
          <span class="user-name">👤 {{ authStore.currentUser?.name }}</span>
          <button @click="handleLogout" class="btn-logout">Cerrar sesión</button>
        </div>
      </div>
    </header>

    <main class="main-content">
      <div class="welcome-section">
        <h2>¡Bienvenido a Con!Vive!</h2>
        <p class="subtitle">Encuentra tu piso ideal y los mejores compañeros</p>
        
        <div class="info-cards">
          <div class="info-card">
            <div class="card-icon">🏠</div>
            <h3>Busca pisos</h3>
            <p>Encuentra el hogar perfecto según tus necesidades</p>
          </div>
          
          <div class="info-card">
            <div class="card-icon">👥</div>
            <h3>Conoce gente</h3>
            <p>Conecta con compañeros compatibles</p>
          </div>
          
          <div class="info-card">
            <div class="card-icon">✨</div>
            <h3>Vive mejor</h3>
            <p>Disfruta de una experiencia de convivencia única</p>
          </div>
        </div>

        <div class="dev-notice">
          <h4>🚧 En desarrollo</h4>
          <p>Esta es una versión preliminar del frontend.</p>
          <p>Las funcionalidades completas estarán disponibles próximamente.</p>
          <div class="user-info">
            <strong>Usuario actual:</strong>
            <pre>{{ JSON.stringify(authStore.currentUser, null, 2) }}</pre>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const handleLogout = () => {
  authStore.logout()
  router.push({ name: 'login' })
}
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  background-color: #f9fafb;
}

.header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 1rem 0;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  font-size: 1.75rem;
  font-weight: 700;
  margin: 0;
}

.user-menu {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.user-name {
  font-weight: 500;
}

.btn-logout {
  background-color: rgba(255, 255, 255, 0.2);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.3);
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s;
}

.btn-logout:hover {
  background-color: rgba(255, 255, 255, 0.3);
  transform: translateY(-1px);
}

.main-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem 1rem;
}

.welcome-section {
  text-align: center;
}

.welcome-section h2 {
  font-size: 2.5rem;
  color: #1f2937;
  margin-bottom: 0.5rem;
}

.subtitle {
  font-size: 1.25rem;
  color: #6b7280;
  margin-bottom: 3rem;
}

.info-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 2rem;
  margin-bottom: 3rem;
}

.info-card {
  background: white;
  padding: 2rem;
  border-radius: 1rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
}

.info-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
}

.card-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.info-card h3 {
  font-size: 1.5rem;
  color: #667eea;
  margin-bottom: 0.5rem;
}

.info-card p {
  color: #6b7280;
  line-height: 1.6;
}

.dev-notice {
  background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
  border: 2px solid #fbbf24;
  border-radius: 1rem;
  padding: 2rem;
  margin-top: 2rem;
}

.dev-notice h4 {
  color: #92400e;
  margin-bottom: 1rem;
  font-size: 1.25rem;
}

.dev-notice p {
  color: #78350f;
  margin-bottom: 0.5rem;
}

.user-info {
  margin-top: 1.5rem;
  text-align: left;
}

.user-info strong {
  color: #92400e;
  display: block;
  margin-bottom: 0.5rem;
}

.user-info pre {
  background-color: rgba(255, 255, 255, 0.7);
  padding: 1rem;
  border-radius: 0.5rem;
  overflow-x: auto;
  font-size: 0.9rem;
  color: #1f2937;
}

@media (max-width: 768px) {
  .header-content {
    flex-direction: column;
    gap: 1rem;
  }

  .user-menu {
    flex-direction: column;
    gap: 0.5rem;
  }

  .welcome-section h2 {
    font-size: 2rem;
  }

  .subtitle {
    font-size: 1rem;
  }

  .info-cards {
    grid-template-columns: 1fr;
  }
}
</style>
<div class=""></div>
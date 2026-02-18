<template>
  <div class="menu-view">
    <header class="page-header">
      <h1>☰ Menú</h1>
      <p class="page-subtitle">Configuración y opciones</p>
    </header>

    <div class="menu-container">
      <div class="menu-section">
        <h3 class="section-title">Mi cuenta</h3>
        <div class="menu-items">
          <div class="menu-item">
            <div class="item-icon">👤</div>
            <div class="item-content">
              <h4>Perfil</h4>
              <p>Edita tu información personal</p>
            </div>
            <div class="item-action">▶</div>
          </div>

          <div class="menu-item">
            <div class="item-icon">⚙️</div>
            <div class="item-content">
              <h4>Configuración</h4>
              <p>Ajusta tus preferencias</p>
            </div>
            <div class="item-action">▶</div>
          </div>

          <div class="menu-item">
            <div class="item-icon">🔔</div>
            <div class="item-content">
              <h4>Notificaciones</h4>
              <p>Gestiona tus notificaciones</p>
            </div>
            <div class="item-action">▶</div>
          </div>
        </div>
      </div>

      <div class="menu-section">
        <h3 class="section-title">Mis publicaciones</h3>
        <div class="menu-items">
          <div class="menu-item">
            <div class="item-icon">🏠</div>
            <div class="item-content">
              <h4>Mis pisos</h4>
              <p>Administra tus anuncios de pisos</p>
            </div>
            <div class="item-action">▶</div>
          </div>

          <div class="menu-item">
            <div class="item-icon">⭐</div>
            <div class="item-content">
              <h4>Favoritos</h4>
              <p>Pisos y compañeros guardados</p>
            </div>
            <div class="item-action">▶</div>
          </div>
        </div>
      </div>

      <div class="menu-section">
        <h3 class="section-title">Soporte</h3>
        <div class="menu-items">
          <div class="menu-item">
            <div class="item-icon">❓</div>
            <div class="item-content">
              <h4>Ayuda</h4>
              <p>Centro de ayuda y preguntas frecuentes</p>
            </div>
            <div class="item-action">▶</div>
          </div>

          <div class="menu-item">
            <div class="item-icon">📧</div>
            <div class="item-content">
              <h4>Contacto</h4>
              <p>Envíanos tus dudas o sugerencias</p>
            </div>
            <div class="item-action">▶</div>
          </div>

          <div class="menu-item">
            <div class="item-icon">📄</div>
            <div class="item-content">
              <h4>Términos y condiciones</h4>
              <p>Lee nuestros términos de uso</p>
            </div>
            <div class="item-action">▶</div>
          </div>
        </div>
      </div>

      <div class="menu-section">
        <h3 class="section-title">Información</h3>
        <div class="info-card">
          <div class="info-row">
            <span class="info-label">Versión:</span>
            <span class="info-value">1.0.0 (Beta)</span>
          </div>
          <div class="info-row">
            <span class="info-label">Usuario:</span>
            <span class="info-value">{{ authStore.currentUser?.email }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">Tipo de cuenta:</span>
            <span class="info-value">{{ authStore.currentUser?.role }}</span>
          </div>
        </div>
      </div>

      <div class="dev-notice">
        <h4>🚧 Sección en desarrollo</h4>
        <p>Las funcionalidades de este menú estarán disponibles próximamente.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
</script>

<style scoped>
.menu-view {
  padding: 2rem;
  max-width: 900px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 2rem;
}

.page-header h1 {
  font-size: 2rem;
  color: #1f2937;
  margin-bottom: 0.5rem;
}

.page-subtitle {
  color: #6b7280;
  font-size: 1.1rem;
}

.menu-container {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.menu-section {
  background: white;
  border-radius: 1rem;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.section-title {
  padding: 1.25rem 1.5rem;
  margin: 0;
  background: linear-gradient(135deg, #f9fafb 0%, #e5e7eb 100%);
  color: #1f2937;
  font-size: 1.1rem;
  border-bottom: 1px solid #e5e7eb;
}

.menu-items {
  display: flex;
  flex-direction: column;
}

.menu-item {
  padding: 1.25rem 1.5rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  cursor: pointer;
  transition: all 0.3s;
  border-bottom: 1px solid #f3f4f6;
}

.menu-item:last-child {
  border-bottom: none;
}

.menu-item:hover {
  background: #f9fafb;
  padding-left: 2rem;
}

.item-icon {
  width: 48px;
  height: 48px;
  background: linear-gradient(135deg, #ede9fe 0%, #ddd6fe 100%);
  border-radius: 0.75rem;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  flex-shrink: 0;
}

.item-content {
  flex: 1;
}

.item-content h4 {
  margin: 0 0 0.25rem 0;
  color: #1f2937;
  font-size: 1rem;
}

.item-content p {
  margin: 0;
  color: #6b7280;
  font-size: 0.875rem;
}

.item-action {
  color: #9ca3af;
  font-size: 1.25rem;
  transition: all 0.3s;
}

.menu-item:hover .item-action {
  color: #667eea;
  transform: translateX(5px);
}

/* Info card */
.info-card {
  padding: 1.5rem;
  background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
  border-radius: 0.75rem;
  margin: 1rem;
}

.info-row {
  display: flex;
  justify-content: space-between;
  padding: 0.75rem 0;
  border-bottom: 1px solid rgba(180, 140, 50, 0.2);
}

.info-row:last-child {
  border-bottom: none;
}

.info-label {
  font-weight: 600;
  color: #92400e;
}

.info-value {
  color: #78350f;
  font-family: 'Courier New', monospace;
}

/* Dev notice */
.dev-notice {
  background: linear-gradient(135deg, #dbeafe 0%, #bfdbfe 100%);
  border: 2px solid #60a5fa;
  border-radius: 1rem;
  padding: 1.5rem;
  text-align: center;
}

.dev-notice h4 {
  color: #1e40af;
  margin-bottom: 0.5rem;
  font-size: 1.1rem;
}

.dev-notice p {
  color: #1e3a8a;
  margin: 0;
}

/* Responsive */
@media (max-width: 768px) {
  .menu-view {
    padding: 1rem;
  }

  .page-header h1 {
    font-size: 1.5rem;
  }

  .menu-item {
    padding: 1rem;
  }

  .item-icon {
    width: 40px;
    height: 40px;
    font-size: 1.25rem;
  }
}
</style>

<template>
  <div class="companeros-view">
    <header class="page-header">
      <h1>👥 Compañeros de piso</h1>
      <p class="page-subtitle">Encuentra a tu compañero ideal</p>
    </header>

    <div class="filters-section">
      <div class="search-bar">
        <input 
          type="text" 
          v-model="searchQuery"
          placeholder="Buscar por nombre, intereses..."
          class="search-input"
        />
        <button class="btn-search">🔍 Buscar</button>
      </div>

      <div class="filter-tags">
        <button class="filter-tag active">Todos</button>
        <button class="filter-tag">Estudiantes</button>
        <button class="filter-tag">Trabajadores</button>
        <button class="filter-tag">No fumadores</button>
      </div>
    </div>

    <div class="companeros-grid">
      <div 
        v-for="companero in mockCompaneros" 
        :key="companero.id"
        class="companero-card"
      >
        <div class="companero-header">
          <div class="avatar" :style="{ background: companero.color }">
            {{ companero.iniciales }}
          </div>
          <div class="companero-info">
            <h3 class="companero-name">{{ companero.nombre }}</h3>
            <p class="companero-age">{{ companero.edad }} años</p>
          </div>
          <span class="status-badge" :class="companero.activo ? 'active' : ''">
            {{ companero.activo ? 'Activo' : 'Inactivo' }}
          </span>
        </div>

        <div class="companero-content">
          <p class="companero-bio">{{ companero.bio }}</p>
          
          <div class="companero-details">
            <div class="detail-item">
              <span class="detail-icon">💼</span>
              <span>{{ companero.ocupacion }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-icon">📍</span>
              <span>{{ companero.zona }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-icon">💰</span>
              <span>{{ companero.presupuesto }}€/mes</span>
            </div>
          </div>

          <div class="companero-tags">
            <span 
              v-for="interes in companero.intereses" 
              :key="interes"
              class="tag"
            >
              {{ interes }}
            </span>
          </div>
        </div>

        <div class="companero-footer">
          <button class="btn-perfil">Ver perfil</button>
          <button class="btn-chat">💬 Chat</button>
        </div>
      </div>
    </div>

    <div class="empty-state" v-if="mockCompaneros.length === 0">
      <div class="empty-icon">👥</div>
      <h3>No hay compañeros disponibles</h3>
      <p>Prueba a cambiar los filtros de búsqueda</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const searchQuery = ref('')

// Mock data - Datos de ejemplo
const mockCompaneros = ref([
  {
    id: 1,
    nombre: 'Ana García',
    iniciales: 'AG',
    edad: 23,
    color: '#667eea',
    ocupacion: 'Estudiante',
    zona: 'Valencia Centro',
    presupuesto: 400,
    bio: 'Estudiante de Arquitectura buscando compañeros tranquilos y responsables.',
    intereses: ['📚 Lectura', '🎵 Música', '🧘 Yoga'],
    activo: true
  },
  {
    id: 2,
    nombre: 'Carlos Martínez',
    iniciales: 'CM',
    edad: 27,
    color: '#f59e0b',
    ocupacion: 'Diseñador',
    zona: 'Ruzafa',
    presupuesto: 500,
    bio: 'Diseñador gráfico, creativo y amante de la cocina. Busco buen rollo.',
    intereses: ['🎨 Arte', '🍳 Cocinar', '🎮 Gaming'],
    activo: true
  },
  {
    id: 3,
    nombre: 'Laura Sánchez',
    iniciales: 'LS',
    edad: 25,
    color: '#10b981',
    ocupacion: 'Ingeniera',
    zona: 'Benimaclet',
    presupuesto: 450,
    bio: 'Ingeniera informática, ordenada y respetuosa. Me gusta el deporte.',
    intereses: ['💻 Tecnología', '🏃 Running', '🎬 Cine'],
    activo: false
  },
  {
    id: 4,
    nombre: 'Miguel Torres',
    iniciales: 'MT',
    edad: 22,
    color: '#ef4444',
    ocupacion: 'Estudiante',
    zona: 'Vera',
    presupuesto: 350,
    bio: 'Estudiante de Ingeniería en la UPV. Busco piso cerca del campus.',
    intereses: ['⚽ Fútbol', '🎸 Música', '🍕 Pizza'],
    activo: true
  },
  {
    id: 5,
    nombre: 'Sara López',
    iniciales: 'SL',
    edad: 26,
    color: '#8b5cf6',
    ocupacion: 'Profesora',
    zona: 'El Carmen',
    presupuesto: 480,
    bio: 'Profesora de inglés, tranquila y limpia. No fumadora.',
    intereses: ['📖 Idiomas', '✈️ Viajar', '☕ Café'],
    activo: true
  },
  {
    id: 6,
    nombre: 'David Ruiz',
    iniciales: 'DR',
    edad: 29,
    color: '#06b6d4',
    ocupacion: 'Programador',
    zona: 'Mestalla',
    presupuesto: 550,
    bio: 'Desarrollador web, trabajo en remoto. Busco ambiente tranquilo.',
    intereses: ['💻 Programación', '🎧 Podcast', '🏋️ Gym'],
    activo: true
  }
])
</script>

<style scoped>
.companeros-view {
  padding: 2rem;
  max-width: 1400px;
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

/* Filters */
.filters-section {
  background: white;
  padding: 1.5rem;
  border-radius: 1rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  margin-bottom: 2rem;
}

.search-bar {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
}

.search-input {
  flex: 1;
  padding: 0.75rem 1rem;
  border: 2px solid #e5e7eb;
  border-radius: 0.5rem;
  font-size: 1rem;
  transition: border-color 0.3s;
}

.search-input:focus {
  outline: none;
  border-color: #667eea;
}

.btn-search {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 0.75rem 2rem;
  border-radius: 0.5rem;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.2s;
}

.btn-search:hover {
  transform: translateY(-2px);
}

.filter-tags {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
}

.filter-tag {
  padding: 0.5rem 1rem;
  border: 2px solid #e5e7eb;
  background: white;
  border-radius: 2rem;
  cursor: pointer;
  transition: all 0.3s;
  font-size: 0.9rem;
}

.filter-tag:hover {
  border-color: #667eea;
  color: #667eea;
}

.filter-tag.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-color: transparent;
}

/* Compañeros Grid */
.companeros-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 1.5rem;
}

.companero-card {
  background: white;
  border-radius: 1rem;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s;
  display: flex;
  flex-direction: column;
}

.companero-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
}

.companero-header {
  padding: 1.5rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  background: linear-gradient(135deg, #f9fafb 0%, #e5e7eb 100%);
}

.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 700;
  font-size: 1.25rem;
  flex-shrink: 0;
}

.companero-info {
  flex: 1;
}

.companero-name {
  font-size: 1.25rem;
  color: #1f2937;
  margin: 0 0 0.25rem 0;
}

.companero-age {
  color: #6b7280;
  margin: 0;
  font-size: 0.9rem;
}

.status-badge {
  padding: 0.375rem 0.75rem;
  border-radius: 2rem;
  font-size: 0.75rem;
  font-weight: 600;
  background: #e5e7eb;
  color: #6b7280;
}

.status-badge.active {
  background: #d1fae5;
  color: #065f46;
}

.companero-content {
  padding: 1.5rem;
  flex: 1;
}

.companero-bio {
  color: #4b5563;
  line-height: 1.6;
  margin-bottom: 1rem;
  font-size: 0.95rem;
}

.companero-details {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #e5e7eb;
}

.detail-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: #6b7280;
  font-size: 0.9rem;
}

.detail-icon {
  font-size: 1.1rem;
}

.companero-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.tag {
  background: #ede9fe;
  color: #6d28d9;
  padding: 0.375rem 0.75rem;
  border-radius: 2rem;
  font-size: 0.8rem;
  font-weight: 500;
}

.companero-footer {
  padding: 1rem 1.5rem;
  background: #f9fafb;
  display: flex;
  gap: 0.75rem;
}

.btn-perfil {
  flex: 1;
  padding: 0.75rem;
  background: white;
  border: 2px solid #667eea;
  color: #667eea;
  border-radius: 0.5rem;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s;
}

.btn-perfil:hover {
  background: #667eea;
  color: white;
}

.btn-chat {
  flex: 1;
  padding: 0.75rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s;
}

.btn-chat:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(102, 126, 234, 0.3);
}

/* Empty State */
.empty-state {
  text-align: center;
  padding: 4rem 2rem;
}

.empty-icon {
  font-size: 4rem;
  margin-bottom: 1rem;
}

.empty-state h3 {
  color: #1f2937;
  margin-bottom: 0.5rem;
}

.empty-state p {
  color: #6b7280;
}

/* Responsive */
@media (max-width: 768px) {
  .companeros-view {
    padding: 1rem;
  }

  .page-header h1 {
    font-size: 1.5rem;
  }

  .companeros-grid {
    grid-template-columns: 1fr;
  }

  .search-bar {
    flex-direction: column;
  }
}
</style>

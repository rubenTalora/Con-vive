<template>
  <div class="pisos-view">
    <header class="page-header">
      <h1>🏠 Pisos disponibles</h1>
      <p class="page-subtitle">Encuentra tu hogar ideal</p>
    </header>

    <div class="filters-section">
      <div class="search-bar">
        <input 
          type="text" 
          v-model="searchQuery"
          placeholder="Buscar por ubicación, precio..."
          class="search-input"
        />
        <button class="btn-search">🔍 Buscar</button>
      </div>

      <div class="filter-tags">
        <button class="filter-tag active">Todos</button>
        <button class="filter-tag">Habitaciones</button>
        <button class="filter-tag">Pisos completos</button>
        <button class="filter-tag">Precio bajo</button>
      </div>
    </div>

    <div class="pisos-grid">
      <div 
        v-for="piso in mockPisos" 
        :key="piso.id"
        class="piso-card"
      >
        <div class="piso-image" :style="{ backgroundImage: `url(${piso.image})` }">
          <span class="piso-badge">{{ piso.tipo }}</span>
        </div>
        
        <div class="piso-content">
          <h3 class="piso-title">{{ piso.titulo }}</h3>
          <p class="piso-location">📍 {{ piso.ubicacion }}</p>
          
          <div class="piso-features">
            <span class="feature">🛏️ {{ piso.habitaciones }} hab.</span>
            <span class="feature">🚿 {{ piso.banos }} baños</span>
            <span class="feature">📐 {{ piso.metros }}m²</span>
          </div>

          <div class="piso-footer">
            <div class="piso-price">
              <span class="price-amount">{{ piso.precio }}€</span>
              <span class="price-period">/mes</span>
            </div>
            <button class="btn-ver-mas">Ver más</button>
          </div>
        </div>
      </div>
    </div>

    <div class="empty-state" v-if="mockPisos.length === 0">
      <div class="empty-icon">🏠</div>
      <h3>No hay pisos disponibles</h3>
      <p>Prueba a cambiar los filtros de búsqueda</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const searchQuery = ref('')

// Mock data - Datos de ejemplo
const mockPisos = ref([
  {
    id: 1,
    titulo: 'Piso luminoso en el centro',
    ubicacion: 'Valencia Centro',
    precio: 450,
    habitaciones: 3,
    banos: 2,
    metros: 85,
    tipo: 'Habitación',
    image: 'https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=400'
  },
  {
    id: 2,
    titulo: 'Apartamento moderno',
    ubicacion: 'Ruzafa, Valencia',
    precio: 650,
    habitaciones: 2,
    banos: 1,
    metros: 60,
    tipo: 'Piso completo',
    image: 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=400'
  },
  {
    id: 3,
    titulo: 'Habitación con vistas',
    ubicacion: 'Benimaclet',
    precio: 350,
    habitaciones: 4,
    banos: 2,
    metros: 95,
    tipo: 'Habitación',
    image: 'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=400'
  },
  {
    id: 4,
    titulo: 'Piso compartido cerca UPV',
    ubicacion: 'Vera, Valencia',
    precio: 320,
    habitaciones: 3,
    banos: 1,
    metros: 75,
    tipo: 'Habitación',
    image: 'https://images.unsplash.com/photo-1484154218962-a197022b5858?w=400'
  },
  {
    id: 5,
    titulo: 'Estudio céntrico',
    ubicacion: 'Gran Vía',
    precio: 550,
    habitaciones: 1,
    banos: 1,
    metros: 40,
    tipo: 'Piso completo',
    image: 'https://images.unsplash.com/photo-1502672023488-70e25813eb80?w=400'
  },
  {
    id: 6,
    titulo: 'Ático con terraza',
    ubicacion: 'Malvarrosa',
    precio: 800,
    habitaciones: 3,
    banos: 2,
    metros: 100,
    tipo: 'Piso completo',
    image: 'https://images.unsplash.com/photo-1493809842364-78817add7ffb?w=400'
  }
])
</script>

<style scoped>
.pisos-view {
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

/* Pisos Grid */
.pisos-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 1.5rem;
}

.piso-card {
  background: white;
  border-radius: 1rem;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s;
  cursor: pointer;
}

.piso-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
}

.piso-image {
  height: 200px;
  background-size: cover;
  background-position: center;
  position: relative;
}

.piso-badge {
  position: absolute;
  top: 1rem;
  right: 1rem;
  background: rgba(102, 126, 234, 0.9);
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 2rem;
  font-size: 0.85rem;
  font-weight: 600;
}

.piso-content {
  padding: 1.25rem;
}

.piso-title {
  font-size: 1.25rem;
  color: #1f2937;
  margin-bottom: 0.5rem;
}

.piso-location {
  color: #6b7280;
  font-size: 0.9rem;
  margin-bottom: 1rem;
}

.piso-features {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #e5e7eb;
}

.feature {
  color: #6b7280;
  font-size: 0.85rem;
}

.piso-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.piso-price {
  display: flex;
  align-items: baseline;
  gap: 0.25rem;
}

.price-amount {
  font-size: 1.5rem;
  font-weight: 700;
  color: #667eea;
}

.price-period {
  color: #6b7280;
  font-size: 0.9rem;
}

.btn-ver-mas {
  background: #667eea;
  color: white;
  border: none;
  padding: 0.5rem 1.25rem;
  border-radius: 0.5rem;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s;
}

.btn-ver-mas:hover {
  background: #5568d3;
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
  .pisos-view {
    padding: 1rem;
  }

  .page-header h1 {
    font-size: 1.5rem;
  }

  .pisos-grid {
    grid-template-columns: 1fr;
  }

  .search-bar {
    flex-direction: column;
  }
}
</style>

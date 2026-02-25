# Arquitectura del Frontend - Con!Vive

## 📋 Índice
- [Visión General](#visión-general)
- [Estructura de Carpetas](#estructura-de-carpetas)
- [Arquitectura Component-Based](#arquitectura-component-based)
- [Separación de Responsabilidades](#separación-de-responsabilidades)
- [Flujo de Datos](#flujo-de-datos)
- [Principios de Diseño](#principios-de-diseño)
- [Estado Actual vs Futuro](#estado-actual-vs-futuro)
- [Tecnologías](#tecnologías)

---

## 🎯 Visión General

El frontend de Con!Vive está desarrollado con **Vue 3** siguiendo una **arquitectura Component-Based** que facilita:

- ✅ Mantenibilidad del código
- ✅ Escalabilidad del proyecto
- ✅ Reutilización de componentes
- ✅ Separación de responsabilidades
- ✅ Gestión clara del estado

---

## 📁 Estructura de Carpetas

```
Frontend Web/
├── src/
│   ├── assets/          # Recursos estáticos
│   │   └── main.css     # Estilos globales, variables CSS
│   │
│   ├── layouts/         # Plantillas de página
│   │   └── DashboardLayout.vue  # Layout principal con sidebar
│   │
│   ├── views/           # PÁGINAS (componentes de ruta)
│   │   ├── LoginView.vue        # Pantalla de inicio de sesión
│   │   ├── PisosView.vue        # Vista de pisos disponibles
│   │   ├── CompanerosView.vue   # Vista de compañeros de piso
│   │   ├── ChatsView.vue        # Sistema de mensajería
│   │   └── MenuView.vue         # Configuración y opciones
│   │
│   ├── router/          # ENRUTAMIENTO
│   │   └── index.js     # Configuración de rutas + guards
│   │
│   ├── stores/          # ESTADO GLOBAL (Pinia)
│   │   └── auth.js      # Store de autenticación
│   │
│   ├── services/        # SERVICIOS DE API
│   │   └── authService.js  # Comunicación con backend (mock)
│   │
│   ├── App.vue          # Componente raíz
│   └── main.js          # Punto de entrada
│
├── index.html           # HTML principal
├── package.json         # Dependencias del proyecto
└── vite.config.js       # Configuración de Vite
```

> **Nota:** La carpeta `components/` no existe aún porque todas las vistas son auto-contenidas. Se creará cuando sea necesario extraer componentes reutilizables.

---

## 🏗️ Arquitectura Component-Based

Vue utiliza una arquitectura basada en componentes donde cada carpeta tiene una responsabilidad específica:

```
┌─────────────────────────────────────┐
│         Usuario (Navegador)         │
└─────────────┬───────────────────────┘
              │
┌─────────────▼───────────────────────┐
│          VIEWS (Páginas)            │
│  LoginView, PisosView, ChatsView    │
└─────────────┬───────────────────────┘
              │
┌─────────────▼───────────────────────┐
│       LAYOUTS (Estructura)          │
│      DashboardLayout (sidebar)      │
└─────────────┬───────────────────────┘
              │
     ┌────────┴────────┐
     │                 │
┌────▼─────┐    ┌──────▼──────┐
│  STORES  │    │  SERVICES   │
│  (Pinia) │    │   (API)     │
└──────────┘    └─────────────┘
     │                 │
     └────────┬────────┘
              │
        (Estado + Datos)
```

---

## 📂 Separación de Responsabilidades

| Carpeta | Responsabilidad | Estado Actual |
|---------|----------------|---------------|
| **views/** | Páginas completas (rutas) | ✅ `LoginView.vue`, `PisosView.vue`, etc. |
| **layouts/** | Estructuras de página | ✅ `DashboardLayout.vue` - Layout con sidebar |
| **services/** | Lógica de API | ✅ `authService.js` - Login mock |
| **stores/** | Estado compartido | ✅ `auth.js` - Usuario actual, token |
| **router/** | Navegación | ✅ `index.js` - Rutas + protección |
| **components/** | Piezas reutilizables | 🔜 *Pendiente* - Se creará cuando sea necesario |

### Explicación de cada carpeta:

#### **views/** - Páginas
Son componentes Vue que representan rutas/páginas completas de la aplicación:
- Auto-contenidas (HTML, CSS, JS en un archivo)
- Se cargan a través del router
- Ejemplo: `PisosView.vue` muestra la página completa de pisos

#### **layouts/** - Estructuras
Envolturas que definen la estructura común de varias páginas:
- `DashboardLayout.vue` → Sidebar + área de contenido
- Envuelve a las vistas mediante `<router-view />`

#### **services/** - Comunicación API
Funciones que se comunican con el backend:
- Llamadas HTTP (fetch, axios)
- Actualmente con datos mock
- Ejemplo: `authService.login(email, password)`

#### **stores/** - Estado Global
Almacenamiento centralizado de datos (Pinia):
- Usuario autenticado
- Token de sesión
- Cualquier dato que necesite compartirse entre vistas

#### **router/** - Navegación
Configuración de rutas de la SPA:
- Mapeo URL → Vista
- Protección de rutas (guards)
- Redirecciones

---

## 🔄 Flujo de Datos

### Ejemplo: Inicio de sesión de usuario

```
1. Usuario ingresa credenciales en LoginView.vue
                    │
                    ▼
2. LoginView llama a authStore.login(email, password)
                    │
                    ▼
3. authStore usa authService.login()
                    │
                    ▼
4. authService hace POST al backend (o mock)
                    │
                    ▼
5. authService retorna { user, token }
                    │
                    ▼
6. authStore guarda el estado (user, token)
                    │
                    ▼
7. authStore guarda en localStorage
                    │
                    ▼
8. router.push('/pisos') → Redirección
                    │
                    ▼
9. DashboardLayout muestra sidebar con usuario
```

### Flujo de navegación protegida:

```
Usuario intenta acceder a /pisos
            │
            ▼
Router Guard (beforeEach)
            │
    ┌───────┴───────┐
    │               │
    ▼               ▼
¿Autenticado?   ¿Autenticado?
   SÍ              NO
    │               │
    ▼               ▼
Permite      Redirige a
 acceso         /login
```

---

## 🎯 Principios de Diseño

### 1. **Single Responsibility Principle (SRP)**

Cada archivo/carpeta tiene UNA responsabilidad:

- **views/**: Solo páginas completas
- **layouts/**: Solo estructuras de página
- **services/**: Solo comunicación con API
- **stores/**: Solo gestión de estado global
- **router/**: Solo configuración de rutas

### 2. **Separation of Concerns (SoC)**

```javascript
// ❌ MAL - Todo mezclado en la vista
<script>
export default {
  methods: {
    async login() {
      const response = await fetch('/api/login', {...})
      const data = await response.json()
      this.user = data.user
      localStorage.setItem('token', data.token)
    }
  }
}
</script>

// ✅ BIEN - Responsabilidades separadas
<script>
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const login = () => authStore.login(email, password)
</script>
```

### 3. **Don't Repeat Yourself (DRY)**

Si ves código duplicado en varias vistas:
- Extrae a un **componente** (si es UI)
- Extrae a un **service** (si es lógica)
- Extrae a **utils/** (si es una función helper)

### 4. **State Management (Pinia)**

```javascript
// Estado global en stores/
const authStore = useAuthStore()
console.log(authStore.user)  // Disponible en toda la app

// vs.

// Estado local en componente
const localData = ref([])  // Solo disponible aquí
```

**Regla:** Usa Pinia cuando necesitas compartir estado entre componentes.

### 5. **Reactive Data Flow**

```
Estado cambia (store)
        ↓
Vue detecta cambio
        ↓
Re-renderiza vista automáticamente
```

---

## 📦 Estado Actual vs Futuro

### **Actualmente (Febrero 2026):**

✅ **MVP funcional** con:
- Login con mock server
- 4 vistas principales (Pisos, Compañeros, Chats, Menú)
- Sidebar colapsable
- Datos mock integrados
- Navegación protegida con router guards

✅ **Estructura simple**:
- Vistas auto-contenidas
- No hay componentes reutilizables aún
- Todo el código CSS está scoped en cada vista
- Funciona perfectamente para fase inicial

### **Próximos pasos (cuando escalar):**

🔜 **Crear carpeta `components/`** cuando:
- Una tarjeta de piso se repite → `components/PisoCard.vue`
- Un botón se usa en varias vistas → `components/Button.vue`
- Un modal es común → `components/Modal.vue`
- Un formulario se reutiliza → `components/FormInput.vue`

🔜 **Integración con backend real**:
- Reemplazar `authService.js` mock por llamadas reales a Odoo
- Crear `services/pisosService.js` para comunicación con Spring Boot
- Gestión real de tokens JWT
- Implementar interceptors de Axios para tokens

🔜 **Mejoras de UX**:
- Loading states en peticiones
- Manejo de errores global
- Notificaciones toast
- Validación de formularios
- Paginación en listas

🔜 **Optimizaciones**:
- Lazy loading de rutas
- Optimización de imágenes
- Caché de datos
- Service Workers (PWA)

---

## 🚀 Ventajas de esta Arquitectura

| Ventaja | Descripción |
|---------|-------------|
| **📁 Organización** | Cada archivo en su lugar lógico, fácil de encontrar |
| **🔄 Mantenibilidad** | Cambios localizados, sin efectos colaterales |
| **⚡ Escalabilidad** | Agregar features sin romper lo existente |
| **🧪 Testeable** | Cada parte se puede testear independientemente |
| **👥 Colaboración** | Varios devs pueden trabajar sin conflictos |
| **📚 Aprendizaje** | Estructura clara para nuevos desarrolladores |
| **🎯 Enfoque** | Cada carpeta tiene un propósito específico |

---

## 🛠️ Tecnologías

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Vue** | 3.4 | Framework reactivo principal |
| **Vite** | 5.0 | Build tool y dev server rápido |
| **Vue Router** | 4.2 | Enrutamiento SPA (Single Page App) |
| **Pinia** | 2.1 | State management (reemplazo de Vuex) |
| **Axios** | 1.6 | Cliente HTTP (futuro, para API real) |

### ¿Por qué estas tecnologías?

**Vue 3**
- Composition API más limpia
- Mejor rendimiento
- TypeScript opcional
- Gran ecosistema

**Vite**
- Dev server instantáneo (HMR rápido)
- Build optimizado
- Configuración mínima

**Vue Router**
- Estándar para SPAs en Vue
- Guards de navegación
- Lazy loading de rutas

**Pinia**
- Más simple que Vuex
- TypeScript nativo
- DevTools integrado
- API intuitiva

---

## 📚 Referencias y Buenas Prácticas

- [Vue 3 Official Guide](https://vuejs.org/guide/) - Documentación oficial
- [Vue Router Documentation](https://router.vuejs.org/) - Guía de rutas
- [Pinia Documentation](https://pinia.vuejs.org/) - State management
- [Vue Style Guide](https://vuejs.org/style-guide/) - Convenciones de código
- [Component-Based Architecture](https://www.patterns.dev/posts/component-based-architecture) - Patrones

---

## 💡 Preguntas Frecuentes

### ¿Por qué no hay carpeta `components/`?
Porque actualmente no hay necesidad. Cada vista es única y auto-contenida. Se creará cuando haya código UI repetido que valga la pena extraer.

### ¿Por qué las vistas tienen todo el CSS incluido?
Por simplicidad en la fase MVP. Cuando crezca, se pueden extraer estilos comunes a `assets/` o crear un sistema de diseño.

### ¿Cuándo usar un componente vs. una vista?
- **Vista**: Es una página completa (tiene una ruta)
- **Componente**: Es una pieza reutilizable de UI (sin ruta propia)

### ¿Por qué Pinia y no Vuex?
Pinia es el state management oficial recomendado para Vue 3. Es más simple, tiene mejor TypeScript y menos boilerplate.

---

**Última actualización**: Febrero 2026  
**Proyecto**: Con!Vive - Frontend Web  
**Stack**: Vue 3 + Vite + Vue Router + Pinia

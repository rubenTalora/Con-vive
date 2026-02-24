# Con!Vive - Frontend Web

Frontend web de la aplicación Con!Vive para buscar pisos y compañeros de piso.

## Tecnologías

- Vue 3
- Vite
- Vue Router
- Pinia (State Management)
- Axios

## Instalación

```bash
npm install
```

## Desarrollo

```bash
npm run dev
```

La aplicación estará disponible en `http://localhost:3000`

## Credenciales de prueba (Mock)

- **Email:** user@convive.com
- **Password:** password123

## Build para producción

```bash
npm run build
```

## Estructura del proyecto

```
src/
  ├── assets/         # Recursos estáticos (CSS, imágenes)
  ├── components/     # Componentes reutilizables
  ├── router/         # Configuración de rutas
  ├── stores/         # Stores de Pinia
  ├── views/          # Vistas/Páginas
  ├── services/       # Servicios (API, Mock)
  ├── App.vue         # Componente principal
  └── main.js         # Punto de entrada
```

## Notas

El sistema de autenticación actual usa un **mock server** provisional. Una vez que el backend de Odoo esté completo, se integrará con el sistema real de tokens JWT.

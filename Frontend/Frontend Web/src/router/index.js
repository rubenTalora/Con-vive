import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import DashboardLayout from '@/layouts/DashboardLayout.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue'),
      meta: { requiresGuest: true }
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/RegisterView.vue'),
      meta: { requiresGuest: true }
    },
    {
      path: '/',
      component: DashboardLayout,
      meta: { requiresAuth: true },
      children: [
        {
          path: '',
          redirect: '/pisos'
        },
        {
          path: 'pisos',
          name: 'pisos',
          component: () => import('@/views/PisosView.vue')
        },
        {
          path: 'companeros',
          name: 'companeros',
          component: () => import('@/views/CompanerosView.vue')
        },
        {
          path: 'chats',
          name: 'chats',
          component: () => import('@/views/ChatsView.vue')
        },
        {
          path: 'menu',
          name: 'menu',
          component: () => import('@/views/MenuView.vue')
        }
      ]
    },
    {
      path: '/:pathMatch(.*)*',
      redirect: '/pisos'
    }
  ]
})

// Guard de navegación
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next({ name: 'login' })
  } else if (to.meta.requiresGuest && authStore.isAuthenticated) {
    next({ name: 'pisos' })
  } else {
    next()
  }
})

export default router

import { createRouter, createWebHistory } from 'vue-router'

import LoginView from '../views/login/LoginView.vue'
import ShopView from '../views/shop/ShopView.vue'
import CartView from '../views/cart/CartView.vue'
import MyOrdersView from '../views/order/MyOrdersView.vue'

import AdminHomeView from '../views/admin/AdminHomeView.vue'
import ProductManageView from '../views/admin/ProductManageView.vue'
import CategoryManageView from '../views/admin/CategoryManageView.vue'
import OrderManageView from '../views/admin/OrderManageView.vue'

const routes = [
  {
    path: '/',
    redirect: '/shop'
  },
  {
    path: '/login',
    component: LoginView
  },
  {
    path: '/shop',
    component: ShopView
  },
  {
    path: '/cart',
    component: CartView
  },
  {
    path: '/my-orders',
    component: MyOrdersView
  },
  {
    path: '/admin',
    component: AdminHomeView,
    meta: { requiresAuth: true, role: 'admin' }
  },
  {
    path: '/admin/products',
    component: ProductManageView,
    meta: { requiresAuth: true, role: 'admin' }
  },
  {
    path: '/admin/categories',
    component: CategoryManageView,
    meta: { requiresAuth: true, role: 'admin' }
  },
  {
    path: '/admin/orders',
    component: OrderManageView,
    meta: { requiresAuth: true, role: 'admin' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to) => {
  const token = localStorage.getItem('token')
  const role = localStorage.getItem('role')

  if (to.path === '/login' && token) {
    return role === 'admin' ? '/admin' : '/shop'
  }

  if (to.meta.requiresAuth && !token) {
    return '/login'
  }

  if (to.meta.role && to.meta.role !== role) {
    return '/shop'
  }

  return true
})

export default router
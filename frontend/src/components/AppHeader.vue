<template>
  <header class="app-header">
    <div class="brand" @click="goHome">
      校园小卖部
    </div>

    <nav class="nav-links">
      <el-button link @click="goShop">商品</el-button>
      <el-button link @click="goCart">购物车</el-button>
      <el-button link @click="goOrders">我的订单</el-button>
      <el-button v-if="role === 'admin'" link @click="goAdmin">后台管理</el-button>
    </nav>

    <div class="user-area">
      <span v-if="username">当前用户：{{ username }}</span>
      <el-button v-if="username" type="danger" plain size="small" @click="logout">
        退出
      </el-button>
      <el-button v-else type="primary" size="small" @click="goLogin">
        登录
      </el-button>
    </div>
  </header>
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()

const username = computed(() => localStorage.getItem('username'))
const role = computed(() => localStorage.getItem('role'))

function goHome() {
  router.push('/shop')
}

function goShop() {
  router.push('/shop')
}

function goCart() {
  router.push('/cart')
}

function goOrders() {
  router.push('/my-orders')
}

function goAdmin() {
  router.push('/admin')
}

function goLogin() {
  router.push('/login')
}

function logout() {
  localStorage.clear()
  ElMessage.success('已退出登录')
  router.push('/login')
}
</script>

<style scoped>
.app-header {
  height: 64px;
  padding: 0 24px;
  display: flex;
  align-items: center;
  gap: 24px;
  background: #ffffff;
  border-bottom: 1px solid #e5e7eb;
}

.brand {
  font-size: 20px;
  font-weight: 700;
  cursor: pointer;
  color: #1f2937;
}

.nav-links {
  flex: 1;
  display: flex;
  gap: 8px;
}

.user-area {
  display: flex;
  align-items: center;
  gap: 12px;
  color: #6b7280;
  font-size: 14px;
}
</style>
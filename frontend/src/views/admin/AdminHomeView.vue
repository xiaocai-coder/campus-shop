<template>
  <div>
    <AppHeader />

    <main class="admin-home">
      <section class="admin-hero">
        <div>
          <h1>管理后台</h1>
          <p>管理校园小卖部的商品、分类和订单</p>
        </div>

        <el-button @click="goShop">
          返回店铺
        </el-button>
      </section>

      <section class="admin-grid">
        <el-card
          v-for="item in adminMenus"
          :key="item.path"
          class="admin-card"
          shadow="hover"
          @click="goPage(item.path)"
        >
          <div class="card-icon">{{ item.icon }}</div>
          <h3>{{ item.title }}</h3>
          <p>{{ item.desc }}</p>
        </el-card>
      </section>
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import AppHeader from '../../components/AppHeader.vue'

const router = useRouter()

const adminMenus = [
  {
    title: '商品管理',
    desc: '新增、编辑、下架商品',
    path: '/admin/products',
    icon: '🛒'
  },
  {
    title: '分类管理',
    desc: '维护饮料、零食、文具等分类',
    path: '/admin/categories',
    icon: '📦'
  },
  {
    title: '订单管理',
    desc: '查看订单并处理订单状态',
    path: '/admin/orders',
    icon: '📋'
  }
]

function goPage(path) {
  router.push(path)
}

function goShop() {
  router.push('/shop')
}
</script>

<style scoped>
.admin-home {
  min-height: calc(100vh - 64px);
  padding: 24px;
  background: #f5f7fb;
}

.admin-hero {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 24px;
  padding: 24px;
  background: #ffffff;
  border-radius: 10px;
}

.admin-hero h1 {
  margin: 0;
  font-size: 28px;
}

.admin-hero p {
  margin: 8px 0 0;
  color: #6b7280;
}

.admin-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 18px;
}

.admin-card {
  cursor: pointer;
  border-radius: 10px;
}

.admin-card :deep(.el-card__body) {
  min-height: 170px;
}

.card-icon {
  margin-bottom: 16px;
  font-size: 42px;
}

.admin-card h3 {
  margin: 0 0 8px;
}

.admin-card p {
  margin: 0;
  color: #6b7280;
}
</style>
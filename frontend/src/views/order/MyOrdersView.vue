<template>
  <div>
    <AppHeader />

    <main class="orders-page">
      <h1>我的订单</h1>

      <el-empty v-if="orders.length === 0" description="暂无订单" />

      <div v-else class="order-list">
        <el-card
          v-for="order in orders"
          :key="order.id"
          class="order-card"
        >
          <div class="order-header">
            <div>
              <h3>订单号：{{ order.id }}</h3>
              <p>下单时间：{{ order.createTime }}</p>
            </div>

            <el-tag type="warning">{{ order.status }}</el-tag>
          </div>

          <div class="order-items">
            <div v-for="item in order.items" :key="item.id" class="order-item">
              <span>{{ item.productName }}</span>
              <span>
                  ￥{{ item.productPrice }} × {{ item.quantity }}
                    = ￥{{ item.subtotal }}
              </span>
            </div>
          </div>

          <div class="order-footer">
            <span>共 {{ order.totalQuantity }} 件商品</span>
            <strong>合计：￥{{ order.totalPrice }}</strong>
          </div>
        </el-card>
      </div>
    </main>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../../utils/request'
import AppHeader from '../../components/AppHeader.vue'

const orders = ref([])

async function loadOrders() {
  const userId = localStorage.getItem('userId')

  if (!userId) {
    ElMessage.warning('请先登录')
    return
  }

  try {
    const res = await request.get(`/orders/user/${userId}`)
    orders.value = res.data
  } catch (error) {
    console.error('订单加载失败：', error)
    ElMessage.error('订单加载失败')
  }
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped>
.orders-page {
  min-height: calc(100vh - 64px);
  padding: 24px;
  background: #f5f7fb;
}

.orders-page h1 {
  margin: 0 0 24px;
}

.order-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.order-card {
  border-radius: 10px;
}

.order-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 16px;
}

.order-header h3 {
  margin: 0 0 6px;
}

.order-header p {
  margin: 0;
  color: #6b7280;
}

.order-items {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 16px 0;
  border-top: 1px solid #e5e7eb;
  border-bottom: 1px solid #e5e7eb;
}

.order-item {
  display: flex;
  justify-content: space-between;
  color: #374151;
}

.order-footer {
  display: flex;
  justify-content: space-between;
  margin-top: 16px;
}

.order-footer strong {
  color: #ef4444;
}
</style>
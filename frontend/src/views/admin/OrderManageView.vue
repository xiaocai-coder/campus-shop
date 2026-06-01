<template>
  <div>
    <AppHeader />

    <main class="admin-page">
      <h1>订单管理</h1>

      <el-empty v-if="orders.length === 0" description="暂无订单" />

      <el-table v-else :data="orders" border class="order-table">
        <el-table-column prop="id" label="订单号" width="180" />
        <el-table-column prop="createTime" label="下单时间" width="220" />
        <el-table-column prop="totalQuantity" label="商品数量" width="100" />
        <el-table-column prop="totalPrice" label="订单金额" width="120">
          <template #default="{ row }">
            ￥{{ row.totalPrice }}
          </template>
        </el-table-column>

        <el-table-column label="商品明细">
          <template #default="{ row }">
            <div class="order-items">
              <div
                v-for="item in row.items"
                :key="item.id"
                class="order-item"
              >
                <span>{{ item.productName }}</span>
                <span>￥{{ item.productPrice }} × {{ item.quantity }} = ￥{{ item.subtotal }}</span>
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="row.status === '已完成' ? 'success' : 'warning'">
              {{ row.status }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="140">
          <template #default="{ row }">
            <el-button
              type="primary"
              size="small"
              :disabled="row.status === '已完成'"
              @click="completeOrder(row.id)"
            >
              完成订单
            </el-button>
          </template>
        </el-table-column>
      </el-table>
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
  try {
    const res = await request.get('/admin/orders')
    console.log('后台订单数据：', res.data)
    orders.value = res.data
  } catch (error) {
    console.error('订单加载失败：', error)
    ElMessage.error('订单加载失败')
  }
}

async function completeOrder(id) {
  try {
    await request.put(`/admin/orders/${id}/complete`)
    ElMessage.success('订单已完成')
    loadOrders()
  } catch (error) {
    console.error('订单操作失败：', error)
    ElMessage.error('订单操作失败')
  }
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped>
.admin-page {
  min-height: calc(100vh - 64px);
  padding: 24px;
  background: #f5f7fb;
}

.admin-page h1 {
  margin: 0 0 24px;
}

.order-table {
  background: #ffffff;
}

.items-text {
  display: flex;
  flex-direction: column;
  gap: 6px;
  max-width: 420px;
}

.items-text span {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  padding: 6px 10px;
  background: #f3f4f6;
  border-radius: 6px;
  font-size: 13px;
}

.items-text em {
  font-style: normal;
  color: #6b7280;
}
</style>
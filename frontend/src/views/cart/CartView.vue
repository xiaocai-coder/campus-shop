<template>
  <div>
    <AppHeader />

    <main class="cart-page">
      <h1>购物车</h1>

      <el-empty v-if="cartItems.length === 0" description="购物车还是空的" />

      <div v-else class="cart-layout">
        <section class="cart-list">
          <el-card
            v-for="item in cartItems"
            :key="item.id"
            class="cart-item"
          >
            <div class="item-icon">{{ item.icon }}</div>

            <div class="item-info">
              <h3>{{ item.name }}</h3>
              <p>单价：￥{{ item.price }}</p>
            </div>

            <div class="item-actions">
              <el-button size="small" @click="decreaseQuantity(item.id)">-</el-button>
              <span>{{ item.quantity }}</span>
              <el-button size="small" @click="increaseQuantity(item.id)">+</el-button>
              <el-button type="danger" size="small" @click="removeItem(item.id)">
                删除
              </el-button>
            </div>
          </el-card>
        </section>

        <aside class="cart-summary">
          <h2>订单汇总</h2>
          <p>商品数量：{{ totalQuantity }}</p>
          <p>合计金额：￥{{ totalPrice }}</p>

          <el-button type="primary" class="submit-button"@click="submitOrder">
            去结算
          </el-button>
        </aside>
      </div>
    </main>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import request from '../../utils/request'
import AppHeader from '../../components/AppHeader.vue'

const router = useRouter()

const cartItems = ref(loadCart())

function loadCart() {
  return JSON.parse(localStorage.getItem('cart') || '[]')
}

function saveCart() {
  localStorage.setItem('cart', JSON.stringify(cartItems.value))
}

function increaseQuantity(id) {
  const item = cartItems.value.find(item => item.id === id)

  if (item) {
    item.quantity += 1
    saveCart()
  }
}

function decreaseQuantity(id) {
  const item = cartItems.value.find(item => item.id === id)

  if (!item) {
    return
  }

  if (item.quantity > 1) {
    item.quantity -= 1
  } else {
    cartItems.value = cartItems.value.filter(item => item.id !== id)
  }

  saveCart()
}

function removeItem(id) {
  cartItems.value = cartItems.value.filter(item => item.id !== id)
  saveCart()
  ElMessage.success('已删除商品')
}

async function submitOrder() {
  if (cartItems.value.length === 0) {
    ElMessage.warning('购物车为空')
    return
  }

  const userId = Number(localStorage.getItem('userId'))

  if (!userId) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }

  const items = cartItems.value.map(item => {
    return {
      productId: item.id,
      quantity: item.quantity
    }
  })

  try {
    await request.post('/orders', {
      userId,
      items
    })

    localStorage.removeItem('cart')
    cartItems.value = []

    ElMessage.success('下单成功')
    router.push('/my-orders')
  } catch (error) {
    ElMessage.error('下单失败')
  }
}
const totalQuantity = computed(() => {
  return cartItems.value.reduce((sum, item) => sum + item.quantity, 0)
})

const totalPrice = computed(() => {
  return cartItems.value
    .reduce((sum, item) => sum + item.price * item.quantity, 0)
    .toFixed(2)
})
</script>

<style scoped>
.cart-page {
  min-height: calc(100vh - 64px);
  padding: 24px;
  background: #f5f7fb;
}

.cart-page h1 {
  margin: 0 0 24px;
}

.cart-layout {
  display: grid;
  grid-template-columns: 1fr 300px;
  gap: 24px;
}

.cart-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.cart-item :deep(.el-card__body) {
  display: flex;
  align-items: center;
  gap: 20px;
}

.item-icon {
  width: 64px;
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 36px;
  background: #f3f4f6;
  border-radius: 8px;
}

.item-info {
  flex: 1;
}

.item-info h3 {
  margin: 0 0 8px;
}

.item-info p {
  margin: 0;
  color: #6b7280;
}

.item-actions {
  display: flex;
  align-items: center;
  gap: 10px;
}

.cart-summary {
  height: fit-content;
  padding: 24px;
  background: #ffffff;
  border-radius: 10px;
}

.cart-summary h2 {
  margin: 0 0 20px;
}

.submit-button {
  width: 100%;
  margin-top: 16px;
}
</style>
<template>
  <div class="shop-page">
    <AppHeader />
    
    <section class="category-bar">
      <el-button
        v-for="category in categories"
        :key="category"
        :type="activeCategory === category ? 'primary' : 'default'"
        @click="activeCategory = category"
      >
        {{ category }}
      </el-button>
    </section>

    <section class="product-grid">
      <el-card
        v-for="product in filteredProducts"
        :key="product.id"
        class="product-card"
        shadow="hover"
      >
        <div class="product-image">
          {{ product.icon }}
        </div>

        <div class="product-info">
          <h3>{{ product.name }}</h3>
          <p>{{ product.description }}</p>

          <div class="product-bottom">
            <span class="price">￥{{ product.price }}</span>
            <el-button type="primary" size="small"@click="addToCart(product)">加入购物车</el-button>
          </div>
        </div>
      </el-card>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import request from '../../utils/request'
import AppHeader from '../../components/AppHeader.vue'

const router = useRouter()

const categories = ref(['全部'])
const products = ref([])
const activeCategory = ref('全部')

const filteredProducts = computed(() => {
  if (activeCategory.value === '全部') {
    return products.value
  }

  return products.value.filter(product => product.categoryName === activeCategory.value)
})

function addToCart(product) {
  const cart = JSON.parse(localStorage.getItem('cart') || '[]')
  const existProduct = cart.find(item => item.id === product.id)

  if (existProduct) {
    existProduct.quantity += 1
  } else {
    cart.push({
      id: product.id,
      name: product.name,
      price: product.price,
      icon: product.icon,
      quantity: 1
    })
  }

  localStorage.setItem('cart', JSON.stringify(cart))
  ElMessage.success('已加入购物车')
}

function goCart() {
  router.push('/cart')
}

function goOrders() {
  router.push('/my-orders')
}

async function loadShopData() {
  try {
    console.log('开始请求店铺数据')

    const categoryRes = await request.get('/categories')
    console.log('分类数据：', categoryRes.data)

    categories.value = ['全部', ...categoryRes.data.map(category => category.name)]

    const productRes = await request.get('/products')
    console.log('商品数据：', productRes.data)

    products.value = productRes.data.filter(product => product.status === '上架')
  } catch (error) {
    console.error('店铺数据加载失败：', error)
    ElMessage.error('店铺数据加载失败')
  }
}

onMounted(() => {
  loadShopData()
})
</script>


<style scoped>
.shop-page {
  min-height: 100vh;
  padding: 24px;
  background: #f5f7fb;
}

.shop-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 24px;
  padding: 24px;
  background: #ffffff;
  border-radius: 10px;
}

.shop-header h1 {
  margin: 0;
  font-size: 28px;
}

.shop-header p {
  margin: 8px 0 0;
  color: #6b7280;
}

.header-actions {
  display: flex;
  gap: 12px;
}

.category-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 24px;
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 18px;
}

.product-card {
  border-radius: 10px;
}

.product-image {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 120px;
  margin-bottom: 16px;
  font-size: 48px;
  background: #f3f4f6;
  border-radius: 8px;
}

.product-info h3 {
  margin: 0 0 8px;
}

.product-info p {
  margin: 0 0 18px;
  color: #6b7280;
}

.product-bottom {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.price {
  font-size: 20px;
  font-weight: 700;
  color: #ef4444;
}
</style>
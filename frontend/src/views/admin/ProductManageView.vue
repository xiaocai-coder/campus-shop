<template>
  <div>
    <AppHeader />

    <main class="admin-page">
      <div class="page-header">
        <h1>商品管理</h1>
        <el-button type="primary" @click="openAdd">新增商品</el-button>
      </div>

      <el-table :data="products" border>
        <el-table-column prop="name" label="商品名称" />
        <el-table-column prop="categoryName" label="分类" width="120" />
        <el-table-column prop="price" label="价格" width="100" />
        <el-table-column prop="stock" label="库存" width="100" />
        <el-table-column prop="status" label="状态" width="100" />

        <el-table-column label="操作" width="260">
          <template #default="{ row }">
            <el-button size="small" @click="openEdit(row)">编辑</el-button>
            <el-button size="small" @click="toggleStatus(row)">
              {{ row.status === '上架' ? '下架' : '上架' }}
            </el-button>
            <el-button type="danger" size="small" @click="deleteProduct(row.id)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑商品' : '新增商品'" width="420px">
        <el-form label-width="80px">
          <el-form-item label="名称">
            <el-input v-model="form.name" />
          </el-form-item>

          <el-form-item label="分类">
            <el-select v-model="form.categoryId" placeholder="请选择分类">
              <el-option
                v-for="category in categories"
                :key="category.id"
                :label="category.name"
                :value="category.id"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="价格">
            <el-input-number v-model="form.price" :min="0" />
          </el-form-item>

          <el-form-item label="库存">
            <el-input-number v-model="form.stock" :min="0" />
          </el-form-item>

          <el-form-item label="图标">
            <el-input v-model="form.icon" />
          </el-form-item>

          <el-form-item label="描述">
            <el-input v-model="form.description" />
          </el-form-item>
        </el-form>

        <template #footer>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveProduct">保存</el-button>
        </template>
      </el-dialog>
    </main>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../../utils/request'
import AppHeader from '../../components/AppHeader.vue'

const products = ref([])
const categories = ref([])

const dialogVisible = ref(false)
const isEdit = ref(false)

const form = ref({
  id: null,
  name: '',
  categoryId: null,
  price: 0,
  stock: 0,
  icon: '',
  description: '',
  status: '上架'
})

async function loadProducts() {
  try {
    const res = await request.get('/products')
    products.value = res.data
  } catch (error) {
    console.error('商品加载失败：', error)
    ElMessage.error('商品加载失败')
  }
}

async function loadCategories() {
  try {
    const res = await request.get('/categories')
    categories.value = res.data
  } catch (error) {
    console.error('分类加载失败：', error)
    ElMessage.error('分类加载失败')
  }
}

function resetForm() {
  form.value = {
    id: null,
    name: '',
    categoryId: null,
    price: 0,
    stock: 0,
    icon: '',
    description: '',
    status: '上架'
  }
}

function openAdd() {
  isEdit.value = false
  resetForm()
  dialogVisible.value = true
}

function openEdit(product) {
  isEdit.value = true
  form.value = {
    id: product.id,
    name: product.name,
    categoryId: product.categoryId,
    price: product.price,
    stock: product.stock,
    icon: product.icon,
    description: product.description,
    status: product.status
  }
  dialogVisible.value = true
}

async function saveProduct() {
  if (!form.value.name || !form.value.categoryId) {
    ElMessage.warning('请填写商品名称和分类')
    return
  }

  const data = {
    categoryId: form.value.categoryId,
    name: form.value.name,
    price: form.value.price,
    stock: form.value.stock,
    icon: form.value.icon,
    description: form.value.description,
    status: form.value.status
  }

  try {
    if (isEdit.value) {
      await request.put(`/admin/products/${form.value.id}`, data)
    } else {
      await request.post('/admin/products', data)
    }

    ElMessage.success('保存成功')
    dialogVisible.value = false
    loadProducts()
  } catch (error) {
    console.error('商品保存失败：', error)
    ElMessage.error('商品保存失败')
  }
}

async function toggleStatus(product) {
  const status = product.status === '上架' ? '下架' : '上架'

  try {
    await request.put(`/admin/products/${product.id}/status`, { status })
    ElMessage.success('状态修改成功')
    loadProducts()
  } catch (error) {
    console.error('状态修改失败：', error)
    ElMessage.error('状态修改失败')
  }
}

async function deleteProduct(id) {
  try {
    await request.delete(`/admin/products/${id}`)
    ElMessage.success('删除成功')
    loadProducts()
  } catch (error) {
    console.error('商品删除失败：', error)
    ElMessage.error('商品删除失败')
  }
}

onMounted(() => {
  loadProducts()
  loadCategories()
})
</script>

<style scoped>
.admin-page {
  min-height: calc(100vh - 64px);
  padding: 24px;
  background: #f5f7fb;
}

.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}
</style>
<template>
  <div>
    <AppHeader />

    <main class="admin-page">
      <h1>分类管理</h1>

      <div class="toolbar">
        <el-input v-model="categoryName" placeholder="请输入分类名称" />
        <el-input-number v-model="sort" :min="0" />
        <el-button type="primary" @click="addCategory">新增分类</el-button>
      </div>

      <el-table :data="categories" border>
        <el-table-column prop="name" label="分类名称" />
        <el-table-column prop="sort" label="排序" width="120" />

        <el-table-column label="操作" width="140">
          <template #default="{ row }">
            <el-button type="danger" size="small" @click="removeCategory(row.id)">
              删除
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

const categories = ref([])
const categoryName = ref('')
const sort = ref(0)

async function loadCategories() {
  try {
    const res = await request.get('/categories')
    categories.value = res.data
  } catch (error) {
    console.error('分类加载失败：', error)
    ElMessage.error('分类加载失败')
  }
}

async function addCategory() {
  const name = categoryName.value.trim()

  if (!name) {
    ElMessage.warning('请输入分类名称')
    return
  }

  try {
    await request.post('/admin/categories', {
      name,
      sort: sort.value
    })

    ElMessage.success('分类新增成功')
    categoryName.value = ''
    sort.value = 0
    loadCategories()
  } catch (error) {
    console.error('分类新增失败：', error)
    ElMessage.error('分类新增失败，可能分类已存在')
  }
}

async function removeCategory(id) {
  try {
    await request.delete(`/admin/categories/${id}`)
    ElMessage.success('分类删除成功')
    loadCategories()
  } catch (error) {
    console.error('分类删除失败：', error)
    ElMessage.error('分类删除失败')
  }
}

onMounted(() => {
  loadCategories()
})
</script>

<style scoped>
.admin-page {
  min-height: calc(100vh - 64px);
  padding: 24px;
  background: #f5f7fb;
}

.toolbar {
  display: flex;
  gap: 12px;
  max-width: 420px;
  margin-bottom: 20px;
}
</style>
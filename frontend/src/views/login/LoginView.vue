<template>
  <div class="login-page">
    <div class="login-card">
      <h1>校园小卖部</h1>
      <p>请登录后继续使用</p>

      <el-form class="login-form">
        <el-form-item>
          <el-input
            v-model="loginForm.username"
            placeholder="请输入账号"
            size="large"
          />
        </el-form-item>

        <el-form-item>
          <el-input
            v-model="loginForm.password"
            placeholder="请输入密码"
            size="large"
            show-password
          />
        </el-form-item>

        <el-button
          type="primary"
          size="large"
          class="login-button"
          @click="handleLogin"
        >
          登录
        </el-button>
      </el-form>

      <div class="login-tips">
        <p>学生账号：student / 123456</p>
        <p>管理员账号：admin / 123456</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import request from '../../utils/request'


const router = useRouter()

const loginForm = ref({
  username: '',
  password: ''
})

async function handleLogin() {
  if (!loginForm.value.username || !loginForm.value.password) {
    ElMessage.warning('请输入账号和密码')
    return
  }

  try {
    const res = await request.post('/login', loginForm.value)
    const user = res.data

    localStorage.setItem('token', user.token)
    localStorage.setItem('role', user.role)
    localStorage.setItem('username', user.username)
    localStorage.setItem('nickname', user.nickname)
    localStorage.setItem('userId', user.id)


    ElMessage.success('登录成功')

    if (user.role === 'admin') {
      router.push('/admin')
    } else {
      router.push('/shop')
    }
  } catch (error) {
    ElMessage.error('账号或密码错误')
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
  background: #eef2f7;
}

.login-card {
  width: 380px;
  padding: 32px;
  background: #ffffff;
  border-radius: 10px;
  box-shadow: 0 12px 30px rgba(15, 23, 42, 0.12);
}

.login-card h1 {
  margin: 0;
  text-align: center;
  font-size: 28px;
}

.login-card > p {
  margin: 10px 0 28px;
  text-align: center;
  color: #6b7280;
}

.login-form {
  width: 100%;
}

.login-button {
  width: 100%;
}

.login-tips {
  margin-top: 20px;
  padding: 12px;
  color: #6b7280;
  font-size: 13px;
  background: #f9fafb;
  border-radius: 8px;
}

.login-tips p {
  margin: 4px 0;
}
</style>
# 校园小卖部管理系统

一个基于 Vue 3 + Spring Boot + MySQL 的校园小卖部全栈项目，用于练习前后端分离开发、接口联调、数据库设计和基础后台管理功能。

## 技术栈

前端：

- Vue 3
- Vue Router
- Axios
- Element Plus
- Vite

后端：

- Spring Boot
- MyBatis
- MySQL
- Maven

## 功能模块

学生端：

- 登录
- 浏览商品
- 按分类筛选商品
- 加入购物车
- 提交订单
- 查看我的订单

管理员端：

- 管理商品
- 管理分类
- 查看订单
- 完成订单

## 项目结构

```text
mini-admin
├─ frontend   前端项目
├─ backend    后端项目
├─ sql        数据库脚本
└─ docs       项目文档
数据库初始化
先创建并导入数据库脚本：

source C:/Users/33205/Desktop/mini-admin/sql/campus_shop.sql;
数据库名：

campus_shop
核心数据表：

user
category
product
orders
order_item
后端启动
进入后端目录：

cd C:\Users\33205\Desktop\mini-admin\backend
启动后端：

mvn spring-boot:run
后端默认地址：

http://localhost:8080
后端配置示例文件：

backend/src/main/resources/application.example.yml
实际运行时需要创建：

backend/src/main/resources/application.yml
或：

backend/src/main/resources/application.yaml
并配置自己的 MySQL 账号和密码。

前端启动
进入前端目录：

cd C:\Users\33205\Desktop\mini-admin\frontend
安装依赖：

npm install
启动前端：

npm run dev
前端默认地址：

http://localhost:5173
默认账号
管理员：

admin / 123456
学生：

student / 123456
接口示例
登录：

POST /api/login
查询商品：

GET /api/products
查询分类：

GET /api/categories
创建订单：

POST /api/orders
查询我的订单：

GET /api/orders/user/{userId}
管理员查询订单：

GET /api/admin/orders
当前项目状态
已完成基础前后端联动：

Vue 前端
 -> Axios
 -> Spring Boot
 -> MyBatis
 -> MySQL
后续计划：

密码加密
JWT 登录认证
后端权限校验
参数校验
库存扣减
部署上线
CREATE DATABASE IF NOT EXISTS campus_shop
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE campus_shop;

-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
  username VARCHAR(50) NOT NULL UNIQUE COMMENT '账号',
  password VARCHAR(100) NOT NULL COMMENT '密码',
  nickname VARCHAR(50) NOT NULL COMMENT '昵称',
  role VARCHAR(20) NOT NULL COMMENT '角色：admin/student',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '用户表';

INSERT INTO user (username, password, nickname, role)
VALUES
('admin', '123456', '管理员', 'admin'),
('student', '123456', '学生用户', 'student');


-- 创建商品分类表
CREATE TABLE IF NOT EXISTS category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
  name VARCHAR(50) NOT NULL UNIQUE COMMENT '分类名称',
  sort INT DEFAULT 0 COMMENT '排序值，数字越小越靠前',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '商品分类表';

-- 初始化商品分类数据
INSERT INTO category (name, sort)
VALUES
('饮料', 1),
('零食', 2),
('文具', 3),
('日用品', 4);

-- 创建商品表
CREATE TABLE IF NOT EXISTS product (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '商品ID',
  category_id BIGINT NOT NULL COMMENT '分类ID',
  name VARCHAR(100) NOT NULL COMMENT '商品名称',
  price DECIMAL(10, 2) NOT NULL COMMENT '商品价格',
  stock INT NOT NULL DEFAULT 0 COMMENT '库存数量',
  icon VARCHAR(20) DEFAULT NULL COMMENT '商品图标',
  description VARCHAR(255) DEFAULT NULL COMMENT '商品描述',
  status VARCHAR(20) NOT NULL DEFAULT '上架' COMMENT '状态：上架/下架',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '商品表';

-- 初始化商品数据
INSERT INTO product (category_id, name, price, stock, icon, description, status)
VALUES
(1, '矿泉水', 2.00, 50, '💧', '550ml 清爽解渴', '上架'),
(1, '可乐', 3.50, 30, '🥤', '冰镇更好喝', '上架'),
(2, '薯片', 6.00, 20, '🍟', '香脆休闲零食', '上架'),
(3, '中性笔', 2.50, 100, '🖊️', '黑色 0.5mm', '上架');

-- 创建订单表
CREATE TABLE IF NOT EXISTS orders (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
  user_id BIGINT NOT NULL COMMENT '下单用户ID',
  total_quantity INT NOT NULL COMMENT '商品总数量',
  total_price DECIMAL(10, 2) NOT NULL COMMENT '订单总金额',
  status VARCHAR(20) NOT NULL DEFAULT '待处理' COMMENT '订单状态：待处理/已完成',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '订单表';

-- 创建订单详情表
CREATE TABLE IF NOT EXISTS order_item (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单详情ID',
  order_id BIGINT NOT NULL COMMENT '订单ID',
  product_id BIGINT NOT NULL COMMENT '商品ID',
  product_name VARCHAR(100) NOT NULL COMMENT '商品名称快照',
  product_price DECIMAL(10, 2) NOT NULL COMMENT '商品单价快照',
  quantity INT NOT NULL COMMENT '购买数量',
  subtotal DECIMAL(10, 2) NOT NULL COMMENT '小计金额'
) COMMENT '订单详情表';
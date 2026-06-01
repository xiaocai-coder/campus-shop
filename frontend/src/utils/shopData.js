const CATEGORY_KEY = 'campus_shop_categories'
const PRODUCT_KEY = 'campus_shop_products'

const defaultCategories = ['饮料', '零食', '文具', '日用品']

const defaultProducts = [
  { id: 1, name: '矿泉水', category: '饮料', price: 2, stock: 50, icon: '💧', description: '550ml 清爽解渴', status: '上架' },
  { id: 2, name: '可乐', category: '饮料', price: 3.5, stock: 30, icon: '🥤', description: '冰镇更好喝', status: '上架' },
  { id: 3, name: '薯片', category: '零食', price: 6, stock: 20, icon: '🍟', description: '香脆休闲零食', status: '上架' }
]

export function loadCategories() {
  const data = JSON.parse(localStorage.getItem(CATEGORY_KEY) || 'null')
  if (data) return data

  localStorage.setItem(CATEGORY_KEY, JSON.stringify(defaultCategories))
  return defaultCategories
}

export function saveCategories(categories) {
  localStorage.setItem(CATEGORY_KEY, JSON.stringify(categories))
}

export function loadProducts() {
  const data = JSON.parse(localStorage.getItem(PRODUCT_KEY) || 'null')
  if (data) return data

  localStorage.setItem(PRODUCT_KEY, JSON.stringify(defaultProducts))
  return defaultProducts
}

export function saveProducts(products) {
  localStorage.setItem(PRODUCT_KEY, JSON.stringify(products))
}
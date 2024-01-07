import './assets/main.css'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from '@/router/index.js'
import axiosInstance from '@/plugins/api.js'
import { createPinia } from 'pinia'
import piniaPluginPersist from 'pinia-plugin-persist'

const app = createApp(App)
app.config.globalProperties.$axiosInstance = axiosInstance
app.use(
  createPinia().use(piniaPluginPersist))
  .use(router)
  .use(ElementPlus)
  .mount('#app')

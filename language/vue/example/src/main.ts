import {createApp} from 'vue'
import './style.css'
// @ts-ignore
import App from './App.vue'
import router from "./router";
import {key, store} from "./store";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

createApp(App)
    .use(router)
    .use(store, key)
    .use(ElementPlus)
    .mount('#app')

import {createApp} from 'vue'
import './style.css'
// @ts-ignore
import App from './App.vue'
import router from "./router";
import {key, store} from "./store";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as Icons from '@element-plus/icons-vue'

const app = createApp(App);

Object.keys(Icons).forEach((iconName)=> {
    // console.log(iconName)
    app.component(iconName, Icons[iconName as keyof typeof Icons])
})

app.use(router)
    .use(store, key)
    .use(ElementPlus)
    .mount('#app');
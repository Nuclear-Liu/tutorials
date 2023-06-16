import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
// @ts-ignore
import Layout from "@/layout/Index.vue";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'home',
        component: Layout,
    }
]

const router = createRouter({
    history:createWebHistory(),
    routes
})

export default router
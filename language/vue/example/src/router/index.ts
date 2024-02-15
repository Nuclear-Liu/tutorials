import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
// @ts-ignore
import Layout from "@/layout/Index.vue";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [
            {
                path: '/dashboard',
                component: () => {
                    // @ts-ignore
                    return import('@/layout/dashboard/Index.vue')
                },
                name: 'dashboard',
                meta: {
                    title: '首页',
                    icon: 'DataBoard'
                }
            }
        ]
    },
    {
        path: '/system',
        component: Layout,
        name: 'system',
        meta: {
            title: "系统管理",
            icon: "Tools",
            roles: ["sys:manage"],
            parentId: 0,
        },
        children: [
            {
                path: "/department",
                component: () => {
                    // @ts-ignore
                    return import('@/views/system/department/Department.vue')
                },
                name: "department",
                meta: {
                    title: "机构管理",
                    icon: "Management",
                    roles: ["sys:dept"]
                },
            },
            {
                path: "/userList",
                component: () => {
                    // @ts-ignore
                    return import('@/views/system/user/UserList.vue')
                },
                name: "userList",
                meta: {
                    title: "用户管理",
                    icon: "UserFilled",
                    roles: ["sys:menu"]
                },
            },
            {
                path: "/roleList",
                component: () => {
                    // @ts-ignore
                    return import('@/views/system/role/RoleList.vue')
                },
                name: "roleList",
                meta: {
                    title: "角色管理",
                    icon: "Operation",
                    roles: ["sys:role"]
                },
            },
            {
                path: "/menuList",
                component: () => {
                    // @ts-ignore
                    return import('@/views/system/menu/MenuList.vue')
                },
                name: "menuList",
                meta: {
                    title: "权限管理",
                    icon: "Lock",
                    roles: ["sys:menu"]
                },
            },
        ],
    },
    {
        path: "/goods",
        component: Layout,
        name: "goods",
        meta: {
            title: "商品管理",
            icon: "Goods",
            roles: ["sys:goods"]
        },
        children: [
            {
                path: "/goodCategory",
                component: () => {
                    // @ts-ignore
                    return import('@/views/goods/category/GoodsCategoryList.vue')
                },
                name: "goodCategory",
                meta: {
                    title: "商品分类",
                    icon: "CollectionTag",
                    roles: ["sys:goodsCategory"]
                },
            },
        ],
    },
    {
        path: "/systenConfig",
        component: Layout,
        name: "systenConfig",
        meta: {
            title: "系统工具",
            icon: "Tools",
            roles: ["sys:systenConfig"]
        },
        children: [
            {
                path: "/document",
                component: () => {
                    // @ts-ignore
                    return import('@/views/system/config/SystemDocument.vue')
                },
                name: "http://42.193.158.170:8089/swagger-ui/index.html",
                meta: {
                    title: "接口文档",
                    icon: "Link",
                    roles: ["sys:document"]
                },
            },
        ],
    }
]

const router = createRouter({
    history:createWebHistory(),
    routes
})

export default router

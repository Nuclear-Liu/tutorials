<script setup lang="ts">
import {reactive, ref} from 'vue'
import MenuItem from "@/layout/menu/MenuItem.vue";
import MenuLog from "@/layout/menu/MenuLog.vue";

//菜单数据
let menuList = reactive([
  {
    path: '/dashboard',
    component: "Layout",
    meta: {
      title: "首页",
      icon: "el-icon-s-home",
      roles: ["sys:manage"]
    },
    children: []
  },
  {
    path: "/system",
    component: "Layout",
    alwaysShow: true,
    name: "system",
    meta: {
      title: "系统管理",
      icon: "el-icon-menu",
      roles: ["sys:manage"],
      parentId: 0,
    },
    children: [
      {
        path: "/department",
        component: "/system/department/department",
        alwaysShow: false,
        name: "department",
        meta: {
          title: "机构管理",
          icon: "el-icon-document",
          roles: ["sys:dept"],
          parentId: 17,
        },
      },
      {
        path: "/userList",
        component: "/system/User/UserList",
        alwaysShow: false,
        name: "userList",
        meta: {
          title: "用户管理",
          icon: "el-icon-s-custom",
          roles: ["sys:user"],
          parentId: 17,
        },
      },
      {
        path: "/roleList",
        component: "/system/Role/RoleList",
        alwaysShow: false,
        name: "roleList",
        meta: {
          title: "角色管理",
          icon: "el-icon-s-tools",
          roles: ["sys:role"],
          parentId: 17,
        },
      },
      {
        path: "/menuList",
        component: "/system/Menu/MenuList",
        alwaysShow: false,
        name: "menuList",
        meta: {
          title: "权限管理",
          icon: "el-icon-document",
          roles: ["sys:menu"],
          parentId: 17,
        },
      },
    ],
  },
  {
    path: "/goods",
    component: "Layout",
    alwaysShow: true,
    name: "goods",
    meta: {
      title: "商品管理",
      icon: "el-icon-document",
      roles: ["sys:goods"],
      parentId: 0,
    },
    children: [
      {
        path: "/goodCategory",
        component: "/goods/goodsCategory/goodsCategoryList",
        alwaysShow: false,
        name: "goodCategory",
        meta: {
          title: "商品分类",
          icon: "el-icon-document",
          roles: ["sys:goodsCategory"],
          parentId: 34,
        },
      },
    ],
  },
  {
    path: "/systenConfig",
    component: "Layout",
    alwaysShow: true,
    name: "systenConfig",
    meta: {
      title: "系统工具",
      icon: "el-icon-document",
      roles: ["sys:systenConfig"],
      parentId: 0,
    },
    children: [
      {
        path: "/document",
        component: "/system/config/systemDocument",
        alwaysShow: false,
        name: "http://42.193.158.170:8089/swagger-ui/index.html",
        meta: {
          title: "接口文档",
          icon: "el-icon-document",
          roles: ["sys:document"],
          parentId: 42,
        },
      },
    ],
  },
]);
const isCollapse = ref(false)
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>

<template>
  <MenuLog></MenuLog>
  <el-menu default-active="2" class="el-menu-vertical-demo" background-color="#304156" :collapse="isCollapse" @open="handleOpen"
           @close="handleClose">
    <MenuItem :menuList="menuList"></MenuItem>
  </el-menu>
</template>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 180px;
  min-height: 400px;
}

.el-menu {
  border-right: none;
}

:deep(.el-sub-menu .el-sub-menu__title) {
  color: #f4f4f5 !important;
}

:deep(.el-menu .el-menu-item) {
  color: #bfcbd9;
}

/* 菜单点中文字的颜色 */

:deep(.el-menu-item.is-active) {
  color: #409eff !important;
}

/* 当前打开菜单的所有子菜单颜色 */
:deep(.is-opened .el-menu-item) {
  background-color: #1f2d3d !important;
}

/* 鼠标移动菜单的颜色 */

:deep(.el-menu-item:hover) {
  background-color: #001528 !important;
}
</style>
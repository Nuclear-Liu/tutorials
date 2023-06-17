<script setup lang="ts">
import Header from '@/layout/header/Header.vue'
import MenuBar from "@/layout/menu/MenuBar.vue";
</script>

<template>
  <el-container class="layout">
    <el-aside class="aside" width="auto">
      <MenuBar></MenuBar>
    </el-aside>
    <el-container class="layout">
      <el-header class="header" height="40px">
        <Header></Header>
      </el-header>
      <el-main class="main">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout {
  height: 100%;

  .aside {
    background-color: #304156;
  }

  .header {
    border-bottom: 1px solid #e5e5e5;
  }

  .main {
    //background-color: darkcyan;
  }
}
</style>
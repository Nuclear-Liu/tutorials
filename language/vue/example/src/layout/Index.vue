<script setup lang="ts">
</script>

<template>
  <el-container class="layout">
    <el-aside class="aside" width="200px">Aside</el-aside>
    <el-container class="layout">
      <el-header class="header">Header</el-header>
      <el-main class="main">Main</el-main>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout {
  height: 100%;

  .aside {
    background-color: blueviolet;
  }

  .header {
    background-color: chocolate;
  }

  .main {
    background-color: darkcyan;
  }
}
</style>
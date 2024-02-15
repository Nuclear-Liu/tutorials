<script setup lang="ts">

defineProps(['menuList'])
</script>

<template>
  <template v-for="menu in menuList" :key="menu.path">
    <el-sub-menu v-if="menu.children && menu.children.length > 0" :index="menu.path">
      <template #title>
        <el-icon>
          <component :is="menu.meta.icon"/>
        </el-icon>
        <span>{{ menu.meta.title }}</span>
      </template>
      <MenuItem :menuList="menu.children"></MenuItem>
    </el-sub-menu>
    <el-menu-item v-else style="color: #f4f4f5" :index="menu.path">
      <i :class="menu.meta.icon"></i>
      <template #title>
        <el-icon>
          <component :is="menu.meta.icon"/>
        </el-icon>
        <span>{{ menu.meta.title }}</span>
      </template>
    </el-menu-item>
  </template>
</template>

<style scoped>
</style>
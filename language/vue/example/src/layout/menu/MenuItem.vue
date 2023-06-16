<script setup lang="ts">

defineProps(['menuList'])
</script>

<template>
  <template v-for="menu in menuList" :key="menu.path">
    <el-sub-menu v-if="menu.children && menu.children.length > 0" :index="menu.path">
      <template #title>
        <i v-if="menu.meta.icon && menu.meta.icon.includes('el-icon')" :class="menu.meta.icon"></i>
        <!-- 动态组件的使用方法 -->
         <component class="icons" v-else :is="menu.meta.icon" />
<!--        <Icon class="icons" v-else :icon='menu.meta.icon'></Icon>-->
        <!--        <i :class="menu.meta.icon"></i>-->
<!--        <el-icon>-->
<!--          <component class="icons" v-else :is="menu.meta.icon" />-->
<!--        </el-icon>-->
        <span>{{ menu.meta.title }}</span>
      </template>
      <MenuItem :menuList="menu.children"></MenuItem>
    </el-sub-menu>
    <el-menu-item v-else :index="menu.path" style="color: #f4f4f5">
      <i :class="menu.meta.icon"></i>
      <template #title>{{ menu.meta.title }}</template>
    </el-menu-item>
  </template>
</template>

<style scoped>
.icons{
  width: 24px;
  height: 18px;
  margin-right: 5px;
}
</style>
<script setup lang="ts">
import {RouteLocationMatched, useRoute} from "vue-router";
import {Ref, ref, watch} from "vue";

const tabs: Ref<RouteLocationMatched[]> = ref([]);
const route = useRoute();
const getBreadcrumb = () => {
  let matched = route.matched.filter(item => item.meta && item.meta.title)

  const main = matched[0];
  if (main.path != '/dashboard') {
    matched = [{path: '/dashboard', meta: {title: '首页'}} as any].concat(matched);
  }
  tabs.value = matched;
}
getBreadcrumb();
watch(()=> route.path, ()=> getBreadcrumb())
</script>

<template>
  <el-breadcrumb separator="/">
    <el-breadcrumb-item style="font-size: 16px" v-for="item in tabs">{{ item.meta.title }}</el-breadcrumb-item>
  </el-breadcrumb>
</template>

<style scoped lang="scss">

</style>
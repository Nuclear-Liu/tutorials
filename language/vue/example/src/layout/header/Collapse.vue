<script setup lang="ts">
import {Expand, Fold} from "@element-plus/icons-vue";
import {computed, ref} from "vue";
import {useStore} from "@/store/index.ts"

const store = useStore();

const status = computed(()=>{
  return store.getters['getCollapse'];
});
const toggle = () => {
  store.commit('setCollapse',!status.value);
}
</script>

<template>
  <el-icon @click="toggle" class="icons">
    <component :is="status ? Expand : Fold" />
  </el-icon>
</template>

<style scoped lang="scss">
.icons{
  display: flex;
  align-items: center;
  font-size: 24px;
  color: #303133;
  cursor: pointer;
}
</style>
<script setup lang="ts">
import {ref,computed} from 'vue';
import {useStore} from "@/store";

const store = useStore();
const count = ref(0);
const showcount = computed(()=>{
  return store.getters['menu/getCount'];
});
const addBtn =() =>{
  store.commit('menu/setCount',++count.value)
}
</script>

<template>
  <h1>{{ showcount }}</h1>
  <button type="button" @click="addBtn">add count</button>
  <!-- element plus -->
  <el-button type="primary">primary</el-button>
  <el-button type="success">success</el-button>
  <el-button type="info">info</el-button>
  <el-button type="warning">warning</el-button>
  <el-button type="danger">danger</el-button>
</template>

<style scoped>
</style>

<script setup lang="ts">
function getProperty<T,K extends keyof T>(obj:T,key:K) {
    return obj[key];
}
const psn = {
    name:'lili',
    age:22
}
console.log(getProperty(psn, 'name'))
console.log(getProperty(psn, 'age'))
</script>

<template>
  <router-view/>
</template>

<style>
</style>

<script xmlns="http://www.w3.org/1999/html">
import { reactive, watch, watchEffect } from 'vue'

export default {
  setup() {
    let person = reactive({
      name: 'zs',
      age: 18,
      score: 100
    })
    watch([() => person.name,()=>{return person.age}], (n, o) => {
      console.log(o, '-->', n)
    })
    watchEffect(()=>{
      const newAge = person.age
      console.log('new value:',newAge)
    })
    return {
      person: person
    }
  }
}
</script>
<template>
  <div>
    <input type="text" v-model="person.name">
    <div>{{ person.name }}</div>
    <input type="number" v-model="person.age">
    <div>{{ person.age }}</div>
    <div>{{ person.score }}</div>
  </div>
</template>
<style scoped>

</style>

<script>
import {computed,ref} from 'vue'

export default {
  setup() {
    let num = ref(0);
    let comp = computed(()=>{
      return parseInt(num.value) + 20
    })
    return {
      num:num,
      comp:comp
    }
  }
}
</script>
<template>
  <div>
    <input type="text" v-model="num">
    <div>{{num}}</div>
    <div>{{comp}}</div>
  </div>
</template>
<style scoped>

</style>

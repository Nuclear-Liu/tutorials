<script>
import { ref, watch } from 'vue'

export default {
  setup() {
    // let str = ref('')
    // let num = ref(0)
    // /**
    //  * 监听变量变化
    //  */
    // watch(str,(newValue,oldValue)=>{
    //   console.log(oldValue,' to:', newValue)
    //   num.value++
    // })
    let str0 = ref('')
    let num0 = ref(0)
    let str1 = ref('')
    let num1 = ref(0)
    watch([str0, str1], (newValus, oldValues) => {
      console.log(oldValues, ' --> ', newValus)
      if (newValus[0] !== oldValues[0]) {
        num0.value++
      } else if (newValus[1] !== oldValues[1]) {
        num1.value++
      }
    })
    return {
      // str: str,
      // num:num
      str0: str0,
      num0: num0,
      str1: str1,
      num1: num1
    }
  }
}
</script>
<template>
  <div>
    <!--    <input type="text" v-model="str">-->
    <!--    <div>{{ str }}</div>-->
    <!--    <div>变化次数：{{num}}</div>-->
    <input type="text" v-model="str0">
    <div>变化次数：{{ num0 }}</div>
    <input type="text" v-model="str1">
    <div>变化次数：{{ num1 }}</div>
  </div>
</template>
<style scoped>

</style>

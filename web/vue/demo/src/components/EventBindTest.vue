<script>
import { ref } from 'vue'

export default {
  setup() {
    let count = ref(0)
    let a = (e) => {
      console.log('event:',e);
      console.log('target:',e.target.tagName)
      count.value++
    }
    // function add(){
    //   count.value++
    // }
    let sum =(i,j)=> {
      count.value=i+j;
    }
    let sayHello =(e)=> {
      console.log('say hello...',e)
    }
    return {
      count: count,
      add: a,
      sum: sum,
      sayHello: sayHello
    }
  }
}
</script>
<template>
  <div>
    <button @click="add">增加</button>
    <div>{{ count }}</div>
    <button @click="sum(1,2)">1+2</button>
    <br>
    事件修饰符
    <br>
    once:
    <button @click.once="sayHello">SayHello</button>
  </div>
</template>
<style scoped>

</style>

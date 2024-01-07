<script>
import { reactive, ref } from 'vue'

export default {
  setup() {
    let tip = 'test world page load.'
    const strRef = ref('...')
    const arr = reactive(['a','b'])

    function timeJob() {
      setTimeout(() => {
        tip = ''
        console.log('tip:', tip)
      }, 500)
    }

    timeJob()

    function add() {
      arr.push(strRef.value);
    }

    return {
      tip: tip,
      strRef: strRef,
      arr:arr,
      add
    }
  }
}
</script>

<template>
  <div>
    {{ tip }}
    <div class="input">
      <input type="text" v-model="strRef">
      <div class="tip">{{ strRef }}</div>
      <button @click="add">增加</button>
      <div class="tip">{{ arr }}</div>
    </div>
  </div>
</template>

<style scoped>

</style>
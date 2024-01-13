<script xmlns="http://www.w3.org/1999/html">
import { ref } from 'vue'

export default {
  setup() {
    let name = 'little_hui'
    let flag = ref(false)
    let strHtml = '<b>little</b>'

    function switchColor() {
      console.log('switch color.')
      flag.value = !flag.value
    }

    return {
      name: name,
      strHtml: strHtml,
      flag: flag,
      switchColor: switchColor
    }
  }
}
</script>
<template>
  <div>
    <span>{{ name }}</span>
    <span>{{ strHtml }}</span>
    <span v-html="strHtml"></span>
    <br>
    v-if
    <br>
    <span v-if="flag">{{ name }}</span>
    <span v-else>{{ strHtml }}</span>
    <br>
    v-bind:class
    <br>
    <span :class="{'font_color':flag}">{{ name }}</span>
    <br>
    <button @click="switchColor()">切换颜色</button>
  </div>
</template>
<style scoped>
.font_color {
  color: red;
}
</style>
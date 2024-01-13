<script>
export default {
  setup() {
    return{
      strGreen:'green',
      weight: 700
    }
  }
}
</script>
<template>
  <div>
    <span :class="{blue:false,weight:true}">This a good day.</span>
    <br>
    <span :style="{color:strGreen,fontWeight:weight}">This a good day.</span>
  </div>
</template>
<style scoped>
.blue {
  color: blue;
}
.weight {
  font-weight: 700;
}
</style>

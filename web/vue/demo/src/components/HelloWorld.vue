<script>
export default {
  name: 'HelloWorld',
  setup() {
    console.log('setup method executed.')
    return {
      msg: 'This a good day.'
    }
  },
  beforeCreate() {
    console.log('beforeCreate method executed.')
  }
}
</script>

<template>
  <div>
    <h1>{{ msg }}</h1>
  </div>
</template>

<style scoped>
</style>

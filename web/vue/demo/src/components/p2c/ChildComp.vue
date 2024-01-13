<script>
import { reactive } from 'vue'

export default {
  name: 'ChildComp',
  props: ['name2'],
  setup(props, context) {
    console.log(context)
    let _name = reactive(props.name2)
    let send = ()=>{
      context.emit('getSex',_name)
    }
    return {
      name: _name,
      send:send
    }
  }
}
</script>
<template>
  <div>
    {{ name.name }}
    <br>
    <button @click="send">send</button>
  </div>
</template>
<style scoped>

</style>

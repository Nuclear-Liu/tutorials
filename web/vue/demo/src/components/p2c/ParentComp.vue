<script>
import ChildComp from '@/components/p2c/ChildComp.vue'

export default {
  name: 'ParentComp',
  components: {
    ChildComp
  },
  setup() {
    let str = {
      name: 'ThisAGodDay'
    }
    let pGetSex = (p) => {
      console.log(p)
    }
    return {
      str: str,
      pGetSex: pGetSex
    }
  }
}
</script>
<template>
  <div>
    <child-comp :name2="str" @getSex="pGetSex" />
  </div>
</template>
<style scoped>

</style>
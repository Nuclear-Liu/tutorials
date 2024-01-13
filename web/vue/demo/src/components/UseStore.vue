<script>
import storeDefinition from '@/store/index.js'
import { reactive } from 'vue'
import { storeToRefs } from 'pinia'

export default {
  setup() {
    const store = storeDefinition();
    let  data = reactive({
      tip:'loading',
      nameA:store.name
    })
    let {name,age,changeAge} = storeToRefs(store)
    const _changeName=(_tmpName) =>{
      store.changeName(_tmpName)
    }
    return {
      data:data,
      name:name,
      age:age,
      _changeName:_changeName
    }
  }
}
</script>
<template>
  <div>
    {{data.tip}},{{data.nameA}}
    <br>
    {{name}},{{age}}
    <div>
      <el-button @click="_changeName('lllllll')">更改</el-button>
    </div>
  </div>
</template>
<style scoped></style>

<script>
import { getCurrentInstance, reactive } from 'vue'

export default {
  setup() {
    let result = reactive({
      tip:""
    })
    const {proxy} = getCurrentInstance()
    let clk = ()=>{
      // request data.
      proxy.$axiosInstance({
        url: 'http://rttop.cn/api/?day=1-1',
        method: 'GET'
      }).then(d=> {
        result.tip = d.data
      })
    }
    return {
      result:result,
      clk:clk
    }
  }
}
</script>
<template>
  <div>
    {{ result.tip }}
    <button @click="clk">请求</button>
  </div>
</template>

<script>
import { reactive } from 'vue'

export default {
  setup() {
    let frm = reactive({
      name:'',
      password:''
    })
    return {
      frm: frm
    }
  }
}
</script>
<template>
  <div class="form">
    <el-form :model="frm" label-width="80px">
      <el-form-item label="用户名：">
        <el-input v-model="frm.name"/>
      </el-form-item>
      <el-form-item label="密码：">
        <el-input type="password" v-model="frm.password"/>
      </el-form-item>
    </el-form>
  </div>
</template>
<style scoped>
.form {
  width: 320px;
  padding: 15px;
  background-color: #eee;
}
</style>

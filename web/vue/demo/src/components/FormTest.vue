<script>
import { ref } from 'vue'

export default {
  setup() {
    let strName = ref('')
    let strPassword = ref('')
    let submit = () => {
      console.log('submitting...')
    }
    let checkForm = (e) => {
      console.log('check form')
      if (strName.value === '123' && strPassword.value === '456') {
        return true
      }
      alert('check form failed.')
      e.preventDefault()
      return false
    }
    return {
      strName: strName,
      strPassword: strPassword,
      submit: submit,
      checkForm: checkForm
    }
  }
}
</script>
<template>
  <div class="form">
    <h3>用户登录：</h3>
    <form action="" method="post" @submit="checkForm">
      <div class="row">
        <label for="name">用户名：</label>
        <input type="text" id="name" v-model="strName">
      </div>
      <div class="row">
        <label for="password">密码：</label>
        <input type="password" id="password" v-model="strPassword">
      </div>
      <div class="row" style="text-align: center">
        <input type="submit" value="提交" @click="submit">
      </div>
    </form>
  </div>
</template>
<style scoped>
h3 {
  padding: 0;
  margin: 0;
  text-align: left;
}

.form {
  width: 320px;
  margin: 20px auto;
  padding: 10px;
  background-color: #eee;
}

.row {
  margin: 10px;
}

.row label {
  display: inline-block;
  width: 80px;
  text-align: right;
}
</style>

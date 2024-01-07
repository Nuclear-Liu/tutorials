import { defineStore } from 'pinia'

const storeDefinition = defineStore('storeId', {
  /* 1. 定义并初始化数据 */
  state: () => {
    return {
      name: 'bad-hui',
      age: 18
    }
  },
  /* 2. getters: 重置初始值 */
  getters: {
    changeAge() {
      return this.age + 1
    }
  },
  /* 3. actions: 修改定义的变量 */
  actions: {
    changeName(newName) {
      this.name = newName
    }
  },
  persist: {
    enabled: true,
    strategies: [{
      storage: localStorage
    }]
  }
})


export default storeDefinition
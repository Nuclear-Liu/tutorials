<!--
tables demo
-->
<script>
import { ref } from 'vue'

export default {
  setup() {
    const tabs = [{
      idx: 1,
      title: '新闻',
      content: 'today is a good day'
    }, {
      idx: 2,
      title: '图片',
      content: 'a beautify picture.'
    }, {
      idx: 3,
      title: '热点',
      content: '一个热点事件'
    }]
    let idx = ref(0)

    function switchTitle(index) {
      console.log('switch title.')
      idx.value = index
    }

    return {
      tabs: tabs,
      idx: idx,
      switchTitle: switchTitle
    }
  }
}
</script>
<template>
  <div>
    <div class="titles"
         v-for="(item,index) in tabs" :key="index"
         :class="{'force':index===idx}"
         @click="switchTitle(index)">
      {{ item.title }}
    </div>
    <div class="content">
      {{ tabs[idx].content }}
    </div>
  </div>
</template>
<style scoped>
.titles {
  display: inline-flex;
  width: 300px;
  justify-content: space-around;
  cursor: pointer;
  border: solid 1px #ccc;
  padding: 8px 0;
}
.titles title {
  padding: 8px 0;
}
.force {
  color: red;
  background-color: #eee;
  padding: 8px 0;
  width: 100px;
}
.content {
  width: 300px;
  margin: 10px 0;
}
</style>

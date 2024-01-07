<script>
// import ParentComp from '@/components/p2c/ParentComp.vue'
import { reactive } from 'vue'

export default {
  name: 'App',
  components: {
    // ParentComp
  },
  setup() {
    const app = reactive({
      title: 'Vue 3',
      user: 'BadMain'
    })
    const navigation = {
      title: '基础内容',
      index: 1,
      icon: 'StarFilled',
      sub: [
        {
          index:1,
          name:'第一页',
          url: '/a'
        },{
          index:2,
          name:'第二页',
          url: '/b'
        },{
          index:3,
          name:'参数传递',
          url: '/c?'
        },{
          index:1,
          name:'第一页',
          url: '/a'
        },{
          index:1,
          name:'第一页',
          url: '/a'
        },{
          index:1,
          name:'第一页',
          url: '/a'
        },{
          index:1,
          name:'第一页',
          url: '/a'
        },{
          index:1,
          name:'第一页',
          url: '/a'
        },
      ]
    }
    return {
      app: app
    }
  }
}
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <h1>{{ app.title }}</h1>
        <span>{{ app.user }}</span>
      </el-header>
      <el-container>
        <el-aside width="220px">
          <el-scrollbar style="height: 600px">
            <el-menu default-active="2" class="el-menu-vertical-demo">
              <el-sub-menu index="1">
                <template #title>
                  <el-icon>
                    <location />
                  </el-icon>
                  <span>Navigator One</span>
                </template>
                <el-menu-item-group title="Group One">
                  <el-menu-item index="1-1">item one</el-menu-item>
                  <el-menu-item index="1-2">item two</el-menu-item>
                </el-menu-item-group>
                <el-menu-item-group title="Group Two">
                  <el-menu-item index="1-3">item three</el-menu-item>
                </el-menu-item-group>
                <el-sub-menu index="1-4">
                  <template #title>item four</template>
                  <el-menu-item index="1-4-1">item one</el-menu-item>
                </el-sub-menu>
              </el-sub-menu>
              <el-menu-item index="2">
                <el-icon>
                  <icon-menu />
                </el-icon>
                <span>Navigator Two</span>
              </el-menu-item>
              <el-menu-item index="3" disabled>
                <el-icon>
                  <document />
                </el-icon>
                <span>Navigator Three</span>
              </el-menu-item>
              <el-menu-item index="4">
                <el-icon>
                  <setting />
                </el-icon>
                <span>Navigator Four</span>
              </el-menu-item>
            </el-menu>
          </el-scrollbar>
        </el-aside>
        <el-container>
          <el-main>Main</el-main>
          <el-footer>Footer</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>

</style>

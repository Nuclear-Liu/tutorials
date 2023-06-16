# Vue

## vite

### 创建项目

* 初始化项目: `npm init vite@latest` or `yarn create vite`

* 安装依赖: `npm install`

* 启动项目: `npm run dev`

* 解决 `Network: use --host to expose`

`vite.config.ts` 文件:
```typescript
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    host: '0.0.0.0',
    port: 88,
    open: true
  }
})
```

* vite 配置别名: `npm install @types/node --save-dev`

`vite.config.ts` 文件:
```typescript
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import {resolve} from 'path'

export default defineConfig({
  plugins: [vue()],
  server: {
    host: '0.0.0.0',
    port: 88,
    open: true
  },
  resolve: {
    alias: [
      {
        find: '@',
        replacement:resolve(__dirname, 'src')
      }
    ]
  }
})
```

* 配置路由: `npm install vue-router@4`

通过 `createRouter` 创建路由， `router/index.ts` 文件:
```typescript
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HelloWorld from "../components/HelloWorld.vue";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'home',
        component: HelloWorld,
    }
]

const router = createRouter({
    history:createWebHistory(),
    routes
})

export default router
```

`main.ts` 文件，添加使用路由:
```typescript
import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from "./router";

createApp(App).use(router).mount('#app')
```

`App.vue` 文件:
```vue
<script setup lang="ts"></script>

<template>
  <router-view/>
</template>

<style>
</style>
```

* 配置 Vuex: `npm install vuex@next --save`

* ts 使用 `@` 符号：

`tsconfig.json` 文件：
```json
{
  "compilerOptions": {
    /* other setting */

    /* use `@` */
    "baseUrl": ".",
    "paths": {
      "@/*": [
        "src/*"
      ]
    },
    "skipDefaultLibCheck": true // 解决集成 Element UI 打包 报错： `vue-tsc --noEmit && vite build`
  },
  "include": ["src/**/*.ts", "src/**/*.d.ts", "src/**/*.tsx", "src/**/*.vue"],
  "references": [{ "path": "./tsconfig.node.json" }]
}

```

* eslint 安装: `npm install --save-dev eslint eslint-plugin-vue`

新增 `.eslintrc.js` 文件:
```js
module.exports = {
    root: true,
    parserOptions: {
        sourceType: 'module'
    },
    parser: 'vue-eslint-parser',
    extends: ['plugin:vue/vue3-essential', 'plugin:vue/vue3-strongly-recommended', 'plugin:vue/vue3-recommended'],
    env: {
        browser: true,
        node: true,
        es6: true
    },
    rules: {
        'no-console': 'off',
        'comma-dangle': [2, 'never'] //禁止使用拖尾逗号
    }
}
```

* 添加 CSS 预处理器 `sass`: `npm install -D sass sass-loader`

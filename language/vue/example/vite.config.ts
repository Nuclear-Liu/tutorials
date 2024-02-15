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
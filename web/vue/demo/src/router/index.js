import { createRouter, createWebHistory } from 'vue-router'

/* 具体路径与组件关系 */
const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('@/components/HelloWorld.vue')
  }, {
    path: '/a',
    name: 'a',
    component: () => import('@/components/TestWorld.vue')
  }, {
    path: '/b',
    name: 'b',
    component: () => import('@/components/ClassBindTest.vue')
  }, {
    path: '/school',
    name: 'school',
    component: () => import('@/components/school/SchoolIndex.vue'),
    children: [
      {
        path: 'grade',
        name: 'grade',
        component: () => import('@/components/school/GradeIndex.vue'),
        children: [
          {
            path: 'class',
            name: 'class',
            component: () => import('@/components/school/ClassIndex.vue')
          }
        ]
      }
    ]
  }, {
    path: '/tip',
    name: 'tip',
    component: () => import('@/components/UserTip.vue')
  },{
    path: '/store',
    name: 'store',
    component: () => import('@/components/UseStore.vue')
  }, {
    path: '/login',
    name: 'login',
    component: () => import('@/components/UserLogin.vue')
  }, {
    path: '/api-test',
    name: 'ApiTest',
    component: () => import('@/components/ApiTest.vue')
  }, {
    path: '/error',
    redirect: '/tip'
  }, {
    path: '/:pathMatch(.*)*',
    name: 'NotFindPage',
    component: () => import('@/components/NotFind.vue')
  }
]

/* 路由对象 */
const router = createRouter({
  history: createWebHistory(),
  routes
})

/* 路由守卫 */
router.beforeEach((to, from) => {
  console.log('to:',to)
  console.log('from:',from)
  if (to.name === 'a' && !localStorage.getItem('login')) {
    return '/login'
  }
})

/* 导出配置的路由对象 */
export default router
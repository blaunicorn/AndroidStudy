import Vue from 'vue'
import Router from 'vue-router'

vue.use(Router)

export default new Router({
  routes: [
      {
          path: '',
          redirect: "/home"
      },
      {
          path: '/home',
          component: Main,
          children: [
              {
               path: '',
              redirect: "index"
              }
          ]
      }
  ]
})
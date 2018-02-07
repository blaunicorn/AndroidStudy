import Vue from 'vue'
import App from './App.vue'
import './base/js/base.js'
import Router from 'vue-router'

Vue.use(Router)

// 引入并使用vue-resource网络请求模块


new Vue({
  el: '#app',
  Router,
  render: h => h(App)
})

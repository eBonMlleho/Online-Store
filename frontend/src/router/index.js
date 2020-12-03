import Vue from 'vue'
import Router from 'vue-router'
import signup from '@/components/signup'
import login from '@/components/login'
import home from '@/components/home'
import postitem from '@/components/postitem'
import itemdetail from '@/components/itemdetail'
import myorder from '@/components/myorder'
import mypost from '@/components/mypost'
import cart from '@/components/cart'
import reset from '@/components/reset'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/signup',
      name: 'signup',
      component: signup
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/home',
      name: 'home',
      component: home
    },
    {
      path: '/postitem',
      name: 'postitem',
      component:postitem
    },
                {
      path: '/itemdetail',
      name: 'itemdetail',
      component:itemdetail
    },

    {
      path: '/myorder',
      name: 'myorder',
      component:myorder
    },
    {
      path: '/reset',
      name: 'reset',
      component:reset
    },
    {
      path: '/mypost',
      name: 'mypost',
      component:mypost
    },
    {
      path: '/cart',
      name: 'cart',
      component:cart
    },
  ]
})

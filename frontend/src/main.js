// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import iView from 'iview';
import 'iview/dist/styles/iview.css';
import axios from 'axios';
import VueSocketio from 'vue-socket.io';
import socketio from 'socket.io-client';
Vue.prototype.$ajax = axios
Vue.prototype.HOST='/api'
Vue.use(iView);
Vue.config.productionTip = false;
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

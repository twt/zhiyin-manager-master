import Vue from 'vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import App from './App.vue'
import Hello from './components/Hello.vue'
import User from './components/user/UserView.vue'
import Hotspot from './components/hotspot/HotspotView.vue'
import AddHotspot from './components/hotspot/AddHotspot.vue'
import ChooseCity from './components/hotspot/ChooseCity.vue'
import ChooseHotspot from './components/hotspot/ChooseHotspot.vue'
import RoleHome from './components/role/RoleHome.vue'
import AddRole from './components/role/AddRole.vue'
import VueMdl from 'vue-mdl'

// install router
Vue.use(VueRouter)
Vue.use(VueResource)
Vue.use(VueMdl)

// create router
const router = new VueRouter({
  linkActiveClass: 'active'
})

router.map({
  '/': {
    component: Hello
  },
  '/user': {
    component: User
  },
  '/c': {
    component: ChooseCity
  },
  '/c/city/:cityId': {
    component: ChooseHotspot,
    name: 'ChooseHotspot'
  },
  '/c/add-hotspot': {
    name: 'addHotspot',
    component: AddHotspot
  },
  '/c/city/:cityId/hotspot/:hotspotId': {
    name: 'HotSpotPage',
    component: Hotspot
  },
  '/role': {
    name: 'RoleHomepage',
    component: RoleHome
  },
  '/role/add': {
    name: 'AddRole',
    component: AddRole
  }
})

router.beforeEach(function () {
  window.scrollTo(0, 0)
})

router.redirect({
  '*': '/'
})

router.start(App, '#app')

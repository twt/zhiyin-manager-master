/**
 * Created by Alice-jj on 2016/5/29.
 */
import Vue from 'vue'
import VueResource from 'vue-resource'
import Config from '../config.js'

Vue.use(VueResource)
var base = Config.data()

export default {
  getSubHotPotById: function (addrId) {
    return Vue.http.get(base.api + 'addrs/info/' + 101 + '/son').then(function (response) {
      // success callback
      console.log('get all the subhotpots info...')
      console.log(response.data.data.customAddressList)
      return response.data.data.customAddressList
    }, function (response) {
      // error callback
      console.log('error to get all the subhotpots info')
      console.log(response)
    })
  }
}

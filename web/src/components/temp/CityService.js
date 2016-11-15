import Vue from 'vue'
import VueResource from 'vue-resource'
import Config from '../config.js'

Vue.use(VueResource)
var base = Config.data()

export default {
  getRootHotspot: function () {
    console.log('get root hostpot')
    var resource = Vue.resource(base.api + '/addrs/info/list')
    var result = resource.get().then(function (response) {
      console.log(response.data.data.customAddressList)
      return response.data.data.customAddressList
    }, function (respoonse) {
      // error callback
      console.log('Failed to get root hostpot')
    })
    console.log(result)
    return result
  },
  getSubHotspot: function (pId) {
    var resource = Vue.resource(base.api + '/addrs/info/{addrId}/son')
    return resource.get({addrId: pId}).then(function (response) {
      // success callback
      console.log('success to get sub hotspot: ' + pId)
      console.log(response.data.data.customAddressList)
      return response.data.data.customAddressList
    }, function (response) {
      // error callback
      console.log('success to get sub hotspot')
    })
  }
}

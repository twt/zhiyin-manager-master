import Vue from 'vue'
import VueResource from 'vue-resource'
import Config from '../config.js'

Vue.use(VueResource)
var base = Config.data()

export default {
  getGrpByHotspotId: function (hotspotId) {
    console.log('getGrpByHotspotId hotspotId: ' + hotspotId)
    var resource = Vue.resource(base.api + '/contents/addrs/{addrId}/cgroups')
    return resource.get({addrId: hotspotId}).then(function (response) {
      console.log(response)
      return response.data.data.list
    }, function (respoonse) {
      // error callback
      console.log('Failed to get content group of hotspot: ' + hotspotId)
    })
  },
  deleteLocation: function (hotspotId, locId) {
    var resource = Vue.resource(base.api + '/addrs/{addrId}/locs/{id}')
    return resource.delete({addrId: hotspotId, id: locId}).then(function (response) {
      // success callback
      console.log('success to delete location: ' + hotspotId + ', ' + locId)
    }, function (response) {
      // error callback
      console.log('failed to delete location: ' + hotspotId + ', ' + locId)
    })
  }
}

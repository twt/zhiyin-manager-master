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
  getHotspotInfo: function (id) {
    var resource = Vue.resource(base.api + '/addrs/info/{addrId}')
    return resource.get({addrId: id}).then(function (response) {
      // success callback
      console.log('success to get hotspot info: ' + id)
      console.log(response.data.data)
      return response.data.data
    }, function (response) {
      // error callback
      console.log('Failed to get hotspot info: ' + id)
    })
  },
  getSubHotspots: function (pId) {
    var resource = Vue.resource(base.api + '/addrs/info/{addrId}/son')
    return resource.get({addrId: pId}).then(function (response) {
      // success callback
      console.log('success to get sub hotspot: ' + pId)
      console.log(response.data.data.customAddressList)
      return response.data.data.customAddressList
    }, function (response) {
      // error callback
      console.log('Failed to get sub hotspot')
    })
  },
  removeHotspot: function (id) {
    var resource = Vue.resource(base.api + '/addrs/info/{addrId}')
    return resource.delete({addrId: id})
  },
  updateHotspot: function (id, data) {
    console.log('update hotspot: ' + id)
    console.log(data)
    var resource = Vue.resource(base.api + '/addrs/info/{addrId}')
    return resource.save({addrId: id}, data)
  },
  addHotspot: function (data) {
    console.log('add hotspot-------------')
    console.log(data)
    var resource = Vue.resource(base.api + '/addrs/info')
    return resource.save(data).then(function (response) {
      console.log(response.data)
      return response.data.data
    }, function (response) {
      // error callback
      console.log('Failed to add hotspot: ' + data)
    })
  },
  getHotspotLocations: function (hotspotId) {
    console.log('getHotspotLocations, hotspotId: ' + hotspotId)
    var resource = Vue.resource(base.api + '/addrs/{addrId}/locs')
    return resource.get({addrId: hotspotId}).then(function (response) {
      console.log(response)
      return response.data.data.list
    }, function (response) {
      // error callback
      console.log('Failed to get hotspot locations: ' + hotspotId)
    })
  },
  addLocation: function (data) {
    console.log('add location---------')
    console.log(data)
    var resource = Vue.resource(base.api + '/addrs/{addrId}/locs')
    return resource.update({addrId: data.addressId}, data)
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

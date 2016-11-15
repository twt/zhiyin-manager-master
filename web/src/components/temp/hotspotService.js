import Vue from 'vue'
import VueResource from 'vue-resource'
import config from './config.js'

Vue.use(VueResource)

export default {
  getHotspotsByCityId: function (cityId, degree) {
    console.log('getHotspotsByCityId')
    // GET request
    var data = {
      cityId: cityId,
      degree: degree
    }
    return Vue.$http.get(config.api + 'address/city/hotpot/degree', data).then(function (response) {
      // success callback
      console.log(response)
      return response.data.data.customAddressList
    }, function (response) {
      // error callback
      console.log('error')
    })
  },

  getSubHotspots: function (parentHotspotId) {
    console.log('getSubHotspots')
    // GET request
    var data = {
      parentId: parentHotspotId
    }
    return Vue.$http.get(config.api + 'address/hotpot/son', data).then(function (response) {
      // success callback
      console.log(response)
      return response.data.data.customAddressList
    }, function (response) {
      // error callback
      console.log('error')
    })
  },

  getHotspotInfo: function (hotspotId) {
    console.log('getHotspotInfo')
    // GET request
    var data = {
      id: parseInt(hotspotId)
    }
    console.log(data)
    console.log(Vue)
    console.log(Vue.$http)
    Vue.$http.get(config.api + 'address/hotpot/id', data).then(function (response) {
      // success callback
      console.log(response)
      return response.data.data
    }, function (response) {
      // error callback
      console.log('error')
    })
  }
}

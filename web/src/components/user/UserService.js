import Vue from 'vue'
import VueResource from 'vue-resource'
import Config from '../config.js'

Vue.use(VueResource)
var base = Config.data()
var resource = Vue.resource(base.api + '/users/infos/{id}')

export default {
  getAllUsers: function (pageNum = 1, pageSize = 20) {
    var data = {pageNum: pageNum, pageSize: pageSize}
    return Vue.http.post(base.api + '/users/infos', data).then(function (response) {
      // success callback
      console.log('get all the users info...')
      console.log(response.data.data.list)
      return response.data.data.list
    }, function (response) {
      // error callback
      console.log('error to get all the users info')
      console.log(response)
    })
  },

  getUserById: function (id) {
    return resource.get({id: id}).then(function (response) {
      console.log(response)
      return response.data.data
    }, function (respoonse) {
      // error callback
      console.log('failed to get user: ' + id)
    })
  }
}

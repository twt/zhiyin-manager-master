import Vue from 'vue'
import VueResource from 'vue-resource'
import Config from '../config.js'

Vue.use(VueResource)
var base = Config.data()
var resource = Vue.resource(base.api + '/anchors/roles/{id}')

export default {
  getRoles: function () {
    return Vue.http.get(base.api + '/anchors/roles').then(function (response) {
      // success callback
      console.log('get all the roles info...')
      console.log(response.data.data.list)
      return response.data.data.list
    }, function (response) {
      // error callback
      console.log('error to get all the roles info')
      console.log(response)
    })
  },

  getRole: function (id) {
    return resource.get({id: id}).then(function (response) {
      console.log(response)
      return response.data.data
    }, function (respoonse) {
      // error callback
      console.log('failed to get user: ' + id)
    })
  },
  addRole: function (data) {
    console.log('save role---------------')
    var resource = Vue.resource(base.api + '/anchors/roles')
    return resource.save(data).then(function (response) {
      console.log(response)
      return response.data
    }, function (respoonse) {
      // error callback
      console.log('failed to save role: ' + data)
    })
  },
  updateRole: function (data) {
    return resource.update({id: data.id}, data).then(function (response) {
      console.log('--------------')
      console.log(response)
      return response.data
    }, function (respoonse) {
      // error callback
      console.log('failed to update user: ' + data)
    })
  },
  deleteRole: function (id) {
    return resource.delete({id: id}).then(function (response) {
      console.log('delete role: ' + id)
      console.log(response)
      return true
    }, function (respoonse) {
      // error callback
      console.log('failed to delete role: ' + id)
      return false
    })
  }
}

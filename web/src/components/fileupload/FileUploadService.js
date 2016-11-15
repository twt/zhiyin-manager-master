import Vue from 'vue'
import VueResource from 'vue-resource'
import Config from '../config.js'

Vue.use(VueResource)
var base = Config.data()

export default {
  getToken: function (filename, fileType) {
    // var self = this
    var data = {
      'name': filename,
      'type': fileType
    }
    console.log('get token')
    console.log(data)
    return Vue.http.post(base.api + '/files/jstoken', data).then(function (response) {
      // success callback
      return response.data.data
    }, function (response) {
      // error callback
      return response.data
    })
  }
}

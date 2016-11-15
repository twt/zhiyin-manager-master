/**
 * Created by Alice-jj on 2016/5/25.
 */
import Vue from 'vue'
import VueResource from 'vue-resource'
import Config from '../config.js'

Vue.use(VueResource)
var base = Config.data()
// var resource = Vue.resource(base.api + 'contents/groups/{id}')

export default{
  getAllContentGroups: function (pageNum = 1, pageSize = 20) {
    var data = {pageNum: pageNum, pageSize: pageSize}
    return Vue.http.post(base.api + 'contents/groups', data).then(function (response) {
      // success callback
      console.log('get all the contents groups info...')
      // console.log(response.data.data.list)
      return response.data.data.list
    }, function (response) {
      // error callback
      console.log('error to get all the users goups info')
      // console.log(response)
    })
  }

}

<template>
  <h4>{{msg}}</h4>
  <span v-link="{ name: 'addContent'}">添加内容</span>
  <table class="mdl-data-table mdl-shadow--1dp">
    <thead>
    <tr>
      <th class="mdl-data-table__cell--non-numeric">title</th>
      <th>roleId</th>
      <th>operations</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="content in contents">
      <td class="mdl-data-table__cell--non-numeric">{{content.title}}</td>
      <td>{{content.roleId}}</td>
      <td><span>查看组内容&nbsp;</td>
    </tr>
    </tbody>
  </table>
</template>
<style>
  body{
    background-color:#ff0000;
  }
</style>
<script>
  // import ContentService from './ContentService.js'
  import Config from '../config.js'

  var base = Config.data()
  export default{
    data () {
      return {
        msg: 'ContentList View',
        groupId: null,
        contents: []
      }
    },
    components: {
    },
    route: {
      data: function (transition) {
        var groupId = transition.to.params.groupId
        return {
          groupId: groupId,
          contents: this.getContentByGroupId(groupId)
        }
      }
    },
    methods: {
      getContentByGroupId: function (groupId) {
        return this.$http.get(base.api + 'contents/' + groupId + '/infos').then(function (response) {
          // success callback
          return response.data.data.list
        }, function (response) {
          // error callback
          console.log('error')
        })
      }
    }
  }
</script>

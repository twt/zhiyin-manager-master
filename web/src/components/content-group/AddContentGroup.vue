<template>
  <h4>{{msg}}</h4>
  <div>title:<input v-model="content.title"></div>
  <div>keyword:<input v-model="content.keyword"></div>
  <div>description:<input v-model="content.description"></div>
  <div>role:
    <select v-model="content.roleId">
      <option v-for="role in roles" value="{{role.id}}">{{role.name}}</option>
    </select>
  </div>
  <button v-on:click="AddContentGroup">提交</button>
</template>
<style>
  body{
    background-color:#ff0000;
  }
</style>
<script>
  import RoleService from '../role/RoleService.js'
  const baseAPI = 'http://101.200.185.137:8080/zhiyin-manager/'
  export default{
    data () {
      return {
        msg: 'Add Content Group',
        roles: [],
        content: {
          title: null,
          keyword: null,
          description: null,
          roleId: null
        }
      }
    },
    components: {
    },
    route: {
      data: function (transition) {
        return {
          roles: RoleService.getAllRoles()
        }
      }
    },
    methods: {
      AddContentGroup: function () {
        var data = {
          description: this.$data.content.description,
          id: 0,
          keyword: this.$data.content.keyword,
          roleId: parseInt(this.$data.content.roleId),
          title: this.$data.content.title
        }
        console.log(data)
        this.$http.put(baseAPI + 'contents/groups', data).then(function (response) {
          // success callback
          console.log('success')
          this.$dispatch('successMsg', 'Remove successfully!')
        }, function (response) {
          // error callback
          console.log('error')
        })
      }
    }
  }
</script>

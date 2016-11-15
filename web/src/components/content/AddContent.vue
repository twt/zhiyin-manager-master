<template>
  <h4>{{msg}}</h4>
  <div>
    <div>title:<span class="red">*</span><input v-model="ct.title"></div>
    <div>articleId:
      <select v-model="ct.articleId">
        <option v-for="article in articles" value="{{article.id}}">{{article.title}}</option>
      </select>
    </div>
    <div>roleId:
      <select v-model="ct.roleId">
        <option v-for="role in roles" value="{{role.id}}">{{role.name}}</option>
      </select>
    </div>
    <div>adminDescription:<input v-model="ct.adminDescription"></div>
    <div>keywords:<input v-model="ct.keyword"></div>
    <div>description:<input v-model="ct.description"></div>
    <div>tag:<input v-model="ct.tag"></div>
    <div>savePath:<input v-model="ct.savePath"></div>
    <div>chapterNum:<input v-model="ct.chapterNum"></div>
    <button v-on:click="AddContent">添加</button>
  </div>
</template>
<style>
  body{
    background-color:#ff0000;
  }
  .red{
    color:#ff0000;
  }
</style>
<script>
  import RoleService from '../role/RoleService.js'
  import ContentGroupService from '../content-group/ContentGroupService.js'
  import Config from '../config.js'

  var base = Config.data()
  export default{
    data () {
      return {
        msg: 'Add Content',
        roles: [],
        articles: [],
        ct: {
          title: null,
          articleId: null,
          roleId: null,
          adminDescription: null,
          keyword: null,
          description: null,
          tag: null,
          savePath: null,
          chapterNum: null
        }
      }
    },
    components: {
    },
    route: {
      data: function (transition) {
        return {
          roles: RoleService.getAllRoles(),
          articles: ContentGroupService.getAllContentGroups(1, 120)
        }
      }
    },
    methods: {
      AddContent: function () {
        var data = {
          title: this.$data.ct.title,
          articleId: this.$data.ct.articleId,
          roleId: this.$data.ct.roleId,
          adminDescription: this.$data.ct.adminDescription,
          keyword: this.$data.ct.keyword,
          description: this.$data.ct.description,
          tag: this.$data.ct.tag,
          savePath: this.$data.ct.savePath,
          chapterNum: this.$data.ct.chapterNum,
          id: 0,
          gid: 0,
          difficultyDegree: 0,
          duration: 0,
          fsType: 0,
          playPriority: 0,
          source: 0
        }
        console.log(data)
        this.$http.put(base.api + 'contents/infos', data).then(function (response) {
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

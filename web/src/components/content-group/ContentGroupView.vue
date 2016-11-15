<template>
  <span>{{msg}}</span>
  <span v-link="{ name: 'addContentGroup'}">添加内容组内容</span>
  <table class="mdl-data-table mdl-shadow--1dp">
    <thead>
      <tr>
        <th class="mdl-data-table__cell--non-numeric">title</th>
        <th>roleId</th>
        <th>operations</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="contentsgroup in contentsgroups">
        <td class="mdl-data-table__cell--non-numeric">{{contentsgroup.title}}</td>
        <td>{{contentsgroup.roleId}}</td>
        <td>查看组内容&nbsp;<span v-link="{ name: 'contentList', params: { groupId: contentsgroup.id}}">查看子内容</span></td>
      </tr>
    </tbody>
  </table>
</template>

<script>
    import ContentGroupService from './ContentGroupService.js'

    export default{
      data () {
        return {
          msg: 'content groups list',
          contentsgroups: null
        }
      },
      route: {
        data: function (transition) {
          return {
            contentsgroups: ContentGroupService.getAllContentGroups()
          }
        }
      }
    }
</script>

<template>
  <h4>{{msg}}</h4>
  <span v-link="{name: 'uploadPics'}">上传图片</span>
  <table class="mdl-data-table mdl-shadow--1dp">
    <thead>
    <tr>
      <th class="mdl-data-table__cell--non-numeric">name</th>
      <th>parentId</th>
      <th>operations</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="hotpot in hotpots">
      <td class="mdl-data-table__cell--non-numeric">{{hotpot.name}}</td>
      <td>{{hotpot.parentId}}</td>
      <td><span>查看子热点</span></td>
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
  import HotpotService from './HotPotService.js'
  export default{
    data () {
      return {
        msg: 'hotpot View',
        addrId: null,
        hotpots: []
      }
    },
    components: {
    },
    route: {
      data: function (transition) {
        var addrId = transition.to.params.addrId
        return {
          addrId: addrId,
          hotpots: HotpotService.getSubHotPotById(addrId)
        }
      }
    }
  }
</script>

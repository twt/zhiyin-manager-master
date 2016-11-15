<template>
  <div class="mdl-grid">
    <h6 class='mdl-list mdl-cell mdl-cell--12-col'>
	    请选择城市
    </h6>
    <ul class='mdl-list mdl-cell mdl-cell--12-col'>
     <h5 v-for="root in roots" @click="getCities(root.id, root.name)">{{root.name}}</h5>
    </ul>


  <mdl-dialog :title="selectedRoot" display-on="chooseCities" id="select_root" cancellable>
    <div class="mdl-grid">
      <div class="mdl-cell mdl-cell--4-col"
       v-link="{ name: 'ChooseHotspot', params: { cityId: item.id}}"
       v-for="item in subHotspots">{{item.name}}</div>
    </div>
    <template slot="actions">
      <mdl-button>取消</mdl-button>
    </template>
  </mdl-dialog>

  </div>
</template>

<style>
.mdl-list__item {
  padding: 8px 0;
  min-height: 38px;
  cursor: pointer;
}
#select_root > .mdl-dialog {
  min-width: 100%;
}
</style>

<script>
import HotspotService from './HotspotService.js'

export default {
  data () {
    return {
      roots: null,
      selectedRoot: ''
    }
  },
  route: {
    data: function (transition) {
      return {
        roots: HotspotService.getRootHotspot()
      }
    }
  },
  methods: {
    getCities: function (id, name) {
      var vm = this
      console.log(name + ' selected.')
      var promise = HotspotService.getSubHotspots(id)
      promise.then(function (response) {
        // success callback
        console.log(response)
        vm.$set('subHotspots', response)
      }, function (response) {
        // error callback
        console.log('success to get sub hotspot')
      })
      this.selectedRoot = name
      this.$broadcast('chooseCities')
    }
  }
}
</script>
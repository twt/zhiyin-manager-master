<template>
  <div class="hotspot-nav mdl-layout__drawer">
    <!-- hotspot card -->
    <div class="mdl-card hotspot-info-card">
      <div class="mdl-card__media">
        <div class="map"><map-view map-container="viewmap" :hotspot-info="hotspotInfo"></map-view></div>
      </div>
      <div class="mdl-card__title">
         <h2 class="mdl-card__title-text">{{hotspotInfo.name}}</h2>
      </div>
      <div class="mdl-card__supporting-text">
        <div class="description">{{hotspotInfo.description}}</div>
        <div class="center-positon">{{hotspotInfo.centerLongitude}} - {{hotspotInfo.centerLatitude}}</div>
      </div>
      <div class="mdl-card__actions">
       <mdl-button @click="editHotsppotModel">修改</mdl-button>
       <mdl-button>删除</mdl-button>
       <mdl-button>添加子热点</mdl-button>
      </div>
    </div>
    <!-- ./hotspot-card -->
    <nav class="mdl-navigation">
      <div v-show="subHotspotList.length < 1">暂无子热点</div>
      <a class="mdl-navigation__link" href="" v-for="item in subHotspotList">{{item.name}}</a>
    </nav>
  </div>
</template>

<style>
.hotspot-nav .mdl-layout__header-row {
  padding: 0 6px 0 0;
  font-size: 16px;
}
.mdl-layout__drawer>.drawer-level>.mdl-layout-title {
  line-height: 64px;
  padding-left: 30px;
}
.mdl-layout__drawer>.drawer-level>.mdl-navigation {
	padding-top: 0;
}
.mdl-layout__drawer>.drawer-level>.mdl-navigation a{
	cursor: pointer;
}
.hotspot-info-card {
  width: auto;
}
.hotspot-info-card .mdl-card__title{
  padding-bottom: 0;
}
.hotspot-info-card .mdl-card__title-text {
  font-size: 24px;
}
.mdl-card__media .map {
  height: 420px;
}
</style>

<script>
import MapView from './MapView.vue'

export default {
  name: 'HotspotSidebar',
  props: {
    hotspotInfo: Object,
    subHotspotList: Array
  },
  components: {
    MapView
  },
  methods: {
    editHotsppotModel: function () {
      this.$dispatch('editHotspotClicked')
      console.log(MapView)
    }
  }
}
</script>


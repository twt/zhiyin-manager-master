<template>
    <div id="{{mapContainer}}">
      <div class="control-bar" v-if="mode == 'edit'">
        <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent"
                @click="toggleSetCenter">
        设置中心点
        </button>
        <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent"
                @click="toggleSetRegion">
         添加区域
        </button>
        <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent"
                @click="resetRegions">
         重置区域
        </button>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable mdl-textfield--floating-label mdl-textfield--align-right mdl-button--raised mdl-button--accent search is-upgraded">
          <label class="mdl-button mdl-js-button mdl-button--icon mdl-color-text--grey-600"
                 for="fixed-header-drawer-map-{{mapContainer}}">
            <i class="material-icons">search</i>
          </label>
          <div class="mdl-textfield__expandable-holder mdl-button--accent mdl-color-text--grey-600">
            <input class="mdl-textfield__input" type="text" id="fixed-header-drawer-map-{{mapContainer}}">
          </div>
        </div>

      </div>
    </div>
</template>

<style>
.amap-container {
  height: 100%;
  width: 100%;
}
.control-bar {
  position: absolute;
  z-index: 100;
  top: 8px;
  left: 8px;
}
.control-bar .search {
  background-color: white !important;
  color: ;
  padding: 4px 0;
}
.control-bar .search .mdl-button {
  bottom: 0;
}
.mdl-button {
  z-index: 100;
}
</style>

<script>
import AMap from 'AMap'

var centerMarker = new AMap.Marker()
// var region = new AMap.Polygon({
//   strokeColor: '#1791fc', // 线颜色
//   strokeOpacity: 0.6, // 线透明度
//   strokeWeight: 2,    // 线宽
//   fillColor: '#1791fc', // 填充色
//   fillOpacity: 0.25 // 填充透明度
// })
var mousetool = null

export default {
  props: {
    mapContainer: String,
    hotspotInfo: Object,
    mode: String,
    hotspotLocations: Array
  },
  data () {
    return {
      isSetCenter: false,
      isRegion: false,
      regions: [],
      mapObj: null
    }
  },

  ready () {
    var vm = this
    vm.mapObj = new AMap.Map(this.mapContainer, {
      zoom: 15,
      center: [116.39, 39.9]
    })

    // var auto = new AMap.Autocomplete({
    //   input: 'fixed-header-drawer-map'
    // })
    // AMap.event.addListener(auto, 'select', vm.selectPositon)
    vm.mapObj.plugin(['AMap.Autocomplete'], function () {
      var inputContainer = 'fixed-header-drawer-map-' + vm.mapContainer
      console.log(inputContainer)
      var auto = new AMap.Autocomplete({
        input: inputContainer
      })
      AMap.event.addListener(auto, 'select', vm.selectPositon)
    })

    // function selectPositon (e) {
    //   if (e.poi && e.poi.location) {
    //     console.log('------------------------')
    //     console.log(vm)
    //     console.log(vm.mapObj)
    //     vm.mapObj.setZoom(15)
    //     vm.mapObj.setCenter(e.poi.location)
    //   }
    // }
    // console.log('vm.mapObj')
    // console.log(vm.mapObj)
    vm.mapObj.plugin(['AMap.MouseTool'], function () {
      mousetool = new AMap.MouseTool(vm.mapObj)
      AMap.event.addListener(mousetool, 'draw', vm.drawFinished)
    })
  },
  methods: {
    toggleSetCenter: function () {
      console.log('toggle set center')
      this.isSetCenter = !this.isSetCenter
      mousetool.marker()
    },
    toggleSetRegion: function () {
      console.log('toggle set region')
      this.isSetRegion = !this.isSetRegion
      mousetool.rectangle() // 使用鼠标工具，在地图上画标记点
      // region.hide()
    },
    resetRegions: function () {
      for (var item in this.regions) {
        this.regions[item].hide()
      }
      this.regions = []
    },
    selectPositon: function (e) {
      if (e.poi && e.poi.location) {
        this.mapObj.setZoom(15)
        this.mapObj.setCenter(e.poi.location)
      }
    },
    drawFinished: function (e) {
      console.log('draw finished')
      console.log(e)
      if (e.obj.CLASS_NAME === 'AMap.Polygon') {
        console.log('AMap.Polygon')
        console.log(e.obj.getPath())
        // region = e.obj
        this.regions.push(e.obj)
        mousetool.close()
      } else if (e.obj.CLASS_NAME === 'AMap.Marker') {
        centerMarker.hide()
        console.log('AMap.Marker')
        centerMarker = e.obj
        centerMarker.show()
        console.log(centerMarker.getPosition())
        var pos = centerMarker.getPosition()
        this.hotspotInfo.centerLongitude = pos.lng
        this.hotspotInfo.centerLatitude = pos.lat
        mousetool.close()
      }
    }
  },
  events: {
    'setCenter': function (center) {
      console.log('set center:' + center[0] + ',' + center[1])
      this.mapObj.setCenter(center)
      centerMarker.setPosition(center)
      centerMarker.setMap(this.mapObj)
    },
    'setRegion': function (points) {
      console.log('set region')
      console.log(this.hotspotLocations)
      var locations = this.hotspotLocations
      for (var item in locations) {
        var region = new AMap.Polygon({
          strokeColor: '#1791fc', // 线颜色
          strokeOpacity: 0.6, // 线透明度
          strokeWeight: 2,    // 线宽
          fillColor: '#1791fc', // 填充色
          fillOpacity: 0.25 // 填充透明度
        })
        region.setPath(locations[item].pointArray)
        this.regions.push(region)
        region.setMap(this.mapObj)
      }
    }
  }
}
</script>
<template>
  <div class="mdl-grid">
	  <h4>在{{pHotspot.name}}下添加热点</h4>
  </div>

  <div class="mdl-grid">
    <div class="mdl-cell mdl-cell--8-col map">
      <map-view v-ref:map map-container="edit-map" mode="edit"
        :hotspot-info="hotspotInfo" 
        :hotspot-locations="hotspotLocations"></map-view>
    </div>
    <div class="mdl-cell mdl-cell--4-col">
        <form action="#">
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="hotspotInfo.name ? 'is-dirty' : ''">
            <input class="mdl-textfield__input" type="text" id="spot-name" v-model="hotspotInfo.name">
            <label class="mdl-textfield__label" for="spot-name">热点名</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="hotspotInfo.description ? 'is-dirty' : ''">
            <input class="mdl-textfield__input" type="text" id="spot-description" v-model="hotspotInfo.description">
            <label class="mdl-textfield__label" for="spot-description">描述</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="region1lng ? 'is-dirty' : ''">
            <input class="mdl-textfield__input" type="text" id="spot-description" v-model="region1lng">
            <label class="mdl-textfield__label" for="spot-description">区域坐标1-经度(longitude)</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="region1lat ? 'is-dirty' : ''">
            <input class="mdl-textfield__input" type="text" id="spot-description" v-model="region1lat">
            <label class="mdl-textfield__label" for="spot-description">区域坐标1-纬度(latitude)</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="region2lng ? 'is-dirty' : ''">
            <input class="mdl-textfield__input" type="text" id="spot-description" v-model="region2lng">
            <label class="mdl-textfield__label" for="spot-description">区域坐标2-经度(longitude)</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="region2lat ? 'is-dirty' : ''">
            <input class="mdl-textfield__input" type="text" id="spot-description" v-model="region2lat">
            <label class="mdl-textfield__label" for="spot-description">区域坐标2-纬度(latitude)</label>
          </div>
        </form>
        <mdl-button primary @click="addHotspot">添加</mdl-button>
        <mdl-button @click="cancelEditHotspot">取消</mdl-button>
    </div>
  </div>

</template>

<script>
import MapView from '../MapView.vue'
import HotspotService from './HotspotService.js'

export default {
  name: 'AddHotspot',
  components: {
    MapView
  },
  data () {
    return {
      hotspotInfo: {
        'centerCoord': 2,
        'centerIsset': 0,
        'centerLatitude': 0,
        'centerLongitude': 0,
        'degree': 0,
        'description': '',
        'fullname': '',
        'id': 0,
        'name': '',
        'nickname': '',
        'parentId': 0
      },
      region1lng: null,
      region1lat: null,
      region2lng: null,
      region2lat: null
    }
  },
  route: {
    data: function (transition) {
      console.log(transition)
      var pId = transition.to.query.pid
      var pHotspot = HotspotService.getHotspotInfo(pId)
      return {
        pHotspot: pHotspot
      }
    }
  },
  methods: {
    addHotspot: function () {
      var self = this
      console.log('add')
      this.hotspotInfo.parentId = this.pHotspot.id
      this.hotspotInfo.degree = this.pHotspot.degree + 1
      this.hotspotInfo.centerLatitude = this.$refs.map.hotspotInfo.centerLatitude
      this.hotspotInfo.centerLongitude = this.$refs.map.hotspotInfo.centerLongitude
      if (this.hotspotInfo.centerLatitude !== 0 || this.hotspotInfo.centerLongitude !== 0) {
        this.hotspotInfo.centerIsset = 1
      }
      console.log(self.hotspotInfo)
      var promise = HotspotService.addHotspot(this.hotspotInfo)
      promise.then(function (response) {
        // success callback
        console.log('success to add hotspot')
        console.log(response)

        var location = {
          'addressId': response.id,
          'id': 0,
          'pointArray': [],
          'rectangleCoord': 2
        }
        var regions = self.$refs.map.regions
        if (regions.length > 0) {
          for (var i = 0; i < regions.length; i++) {
            var pointPath = regions[i].getPath()
            for (var j = 0; j < pointPath.length; j++) {
              var point = pointPath[j]
              location.pointArray.push(point.lng, point.lat)
            }
          }
        } else if (self.region1lng && self.region1lat && self.region2lng && self.region2lat) {
          console.log('-------------------fdsfdsfsf')
          location.pointArray = []
          location.pointArray.push(self.region1lng, self.region1lat, self.region2lng, self.region2lat)
        }
        console.log(location.pointArray)
        console.log(location)
        var locationPromise = HotspotService.addLocation(location)
        locationPromise.then(function (response) {
          // success callback
          console.log('success to add location')
          console.log(response)
          self.$dispatch('successMsg', 'Add hotspot ' + self.hotspotInfo.name + ' successfully!')
          location.pointArray = []
          self.hotspotInfo.centerLatitude = 0
          self.hotspotInfo.centerLongitude = 0
          self.hotspotInfo.description = ''
          self.hotspotInfo.fullname = ''
          self.hotspotInfo.name = ''
          self.hotspotInfo.nickname = ''
          self.region1lng = null
          self.region1lat = null
          self.region2lng = null
          self.region2lat = null
        }, function (response) {
          // error callback
          self.$dispatch('errorMsg', response.data)
        })
      }, function (response) {
        // error callback
        self.$dispatch('errorMsg', response.data)
      })
    }
  }
}
</script>

<style>
#edit-map {
  min-height: 640px;
}
</style>
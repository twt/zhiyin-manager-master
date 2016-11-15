<template>

	<!-- The drawer is always open in large screens. The header is always shown,
	  even in small screens. -->
	<div id="hotspot-container" class="mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
    <header class="mdl-layout__header mdl-color--grey-50"></header>
    <Hotspot-Sidebar :hotspot-info="hotspotInfo" :sub-hotspot-list="subHotspotList"></Hotspot-Sidebar>
    <main class="mdl-layout__content hotspot-content">
      <section class="section--center mdl-grid mdl-grid--no-spacing mdl-shadow--2dp">
        <header class="section__play-btn mdl-cell mdl-cell--3-col-desktop mdl-cell--2-col-tablet mdl-cell--4-col-phone mdl-color--teal-100 mdl-color-text--white">
          <i class="material-icons">play_circle_filled</i>
        </header>
        <div class="mdl-card mdl-cell mdl-cell--9-col-desktop mdl-cell--6-col-tablet mdl-cell--4-col-phone">
          <div class="mdl-card__supporting-text">
            <h4>热点内容1</h4>
            有没有描述？？？？？？
          </div>
          <div class="mdl-card__actions">
            <a href="#" class="mdl-button">操作按钮？</a>
          </div>
        </div>
        <div class="mdl-menu__container is-upgraded" style="right: 8px; top: 40px; width: 124px; height: 160px;">
          <div class="mdl-menu__outline mdl-menu--bottom-right" style="width: 124px; height: 160px;"></div>
          <ul class="mdl-menu mdl-js-menu mdl-menu--bottom-right" for="btn1" data-upgraded=",MaterialMenu" style="clip: rect(0px 124px 0px 124px);">
            <li class="mdl-menu__item" tabindex="-1">Lorem</li>
            <li class="mdl-menu__item" disabled="" tabindex="-1">Ipsum</li>
            <li class="mdl-menu__item" tabindex="-1">Dolor</li>
          </ul>
        </div>
      </section>
    </main>
  </div>
  <mdl-dialog title="修改热点" display-on="editHotspotModel" class="hotspot-edit">
    <div class="mdl-grid">
      <div class="mdl-cell mdl-cell--8-col map">
        <map-view map-container="edit-map" :hotspot-info="hotspotInfo" :hotspot-locations="hotspotLocations" mode="edit"></map-view>
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
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="hotspotInfo.centerLongitude ? 'is-dirty' : ''">
              <input class="mdl-textfield__input" type="text" id="spot-longitude" v-model="hotspotInfo.centerLongitude">
              <label class="mdl-textfield__label" for="spot-longitude">经度</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="hotspotInfo.centerLatitude ? 'is-dirty' : ''">
              <input class="mdl-textfield__input" type="text" id="spot-latitude" v-model="hotspotInfo.centerLatitude">
              <label class="mdl-textfield__label" for="spot-latitude">纬度</label>
            </div>
            <div v-for="hotspotLocation in hotspotLocations">
              <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="hotspotLocation.rectangleX1 ? 'is-dirty' : ''">
                <input class="mdl-textfield__input" type="text" id="spot-latitude" v-model="hotspotLocation.rectangleX1">
                <label class="mdl-textfield__label" for="spot-latitude">区域点1-X</label>
              </div>
              <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="hotspotLocation.rectangleY1 ? 'is-dirty' : ''">
                <input class="mdl-textfield__input" type="text" id="spot-latitude" v-model="hotspotLocation.rectangleY1">
                <label class="mdl-textfield__label" for="spot-latitude">区域点1-Y</label>
              </div>
              <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="hotspotLocation.rectangleX2 ? 'is-dirty' : ''">
                <input class="mdl-textfield__input" type="text" id="spot-latitude" v-model="hotspotLocation.rectangleX1">
                <label class="mdl-textfield__label" for="spot-latitude">区域点2-X</label>
              </div>
              <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="hotspotLocation.rectangleY2 ? 'is-dirty' : ''">
                <input class="mdl-textfield__input" type="text" id="spot-latitude" v-model="hotspotLocation.rectangleY1">
                <label class="mdl-textfield__label" for="spot-latitude">区域点2-Y</label>
              </div>
            </div>
          </form>
      </div>
    </div>
    <template slot="actions">
      <mdl-button primary @click="updateHotspotEdit">保存</mdl-button>
      <mdl-button @click="cancelEditHotspot">取消</mdl-button>
    </template>
  </mdl-dialog>
</template>

<script>
import HotspotSidebar from './HotspotSidebar.vue'
import MapView from './MapView.vue'
import Config from './config.js'

var base = Config.data()

export default {
  name: 'HotSpotPage',
  components: {
    HotspotSidebar,
    MapView
  },
  data () {
    return {
      hotspotInfo: {'ff': 'ggg'},
      originHotspotInfo: {},
      hotspotLocations: [],
      originHotspotLocations: [],
      subHotspotList: []
    }
  },
  route: {
    data: function (transition) {
      // var cityId = transition.to.params.cityId
      var hotspotId = transition.to.params.hotspotId
      var hotspotInfo = this.getHotspotInfo(hotspotId)
      var subHotspotList = this.getSubHotspots(hotspotId)
      var hotspotLocations = this.getHotspotLocations(hotspotId)
      // var center = [this.hotspotInfo.centerLongitude, this.hotspotInfo.centerLatitude]
      // this.$broadcast('setCenter', center)
      return {
        hotspotInfo: hotspotInfo,
        originHotspotInfo: hotspotInfo,
        subHotspotList: subHotspotList,
        hotspotLocations: hotspotLocations,
        originHotspotLocations: hotspotLocations
      }
    }
  },
  methods: {
    getHotspotInfo: function (hotspotId) {
      console.log('getHotspotInfo')
      // GET request
      var data = {
        id: parseInt(hotspotId)
      }
      return this.$http.get(base.api + 'address/hotpot/id', data).then(function (response) {
        // success callback
        return response.data.data
      }, function (response) {
        // error callback
        console.log('error')
      })
    },
    getHotspotLocations: function (hotspotId) {
      console.log('getHotspotLocations')
      // GET request
      var data = {
        addressId: parseInt(hotspotId)
      }
      return this.$http.get(base.api + 'address/loc/addrId', data).then(function (response) {
        // success callback
        return response.data.data.customAddressLocationList
      }, function (response) {
        // error callback
        console.log('error')
      })
    },
    getSubHotspots: function (parentId) {
      console.log('getSubHotspots')
      // GET request
      var data = {
        parentId: parentId
      }
      return this.$http.get(base.api + 'address/hotpot/closeson', data).then(function (response) {
        // success callback
        return response.data.data.customAddressList
      }, function (response) {
        // error callback
        console.log('error')
      })
    },
    cancelEditHotspot: function () {
      console.log('cancel')
    },
    updateHotspotEdit: function () {
      console.log('update')
    }
  },

  events: {
    'editHotspotClicked': function () {
      this.$broadcast('editHotspotModel')
      var center = [this.hotspotInfo.centerLongitude, this.hotspotInfo.centerLatitude]
      this.$broadcast('setCenter', center)
      var point1 = [this.hotspotLocations[0].rectangleX1, this.hotspotLocations[0].rectangleY1]
      var point2 = [this.hotspotLocations[0].rectangleX2, this.hotspotLocations[0].rectangleY2]
      var points = [point1, point2]
      console.log('setRegion' + points)
      this.$broadcast('setRegion', points)
    },
    'hotspotCenterUpdated': function (center) {
      console.log(center)
      this.hotspotInfo.centerLongitude = center[0]
      this.hotspotInfo.centerLatitude = center[1]
    }
  }
}
</script>

<style>
#hotspot-container {
    position: absolute;
}
#hotspot-container .mdl-layout__drawer {
    width: 520px;
    -webkit-transform: translateX(-530px);
    transform: translateX(-530px);
}
@media screen and (min-width: 1025px) {
  #hotspot-container .mdl-layout__drawer {
    -webkit-transform: translateX(0);
    transform: translateX(0);
  }
}

#hotspot-container .mdl-layout__drawer.is-visible {
    -webkit-transform: translateX(0);
    transform: translateX(0);
}
#hotspot-container .mdl-layout__content {
    margin-left: 520px;
}
@media screen and (max-width: 1024px) {
  #hotspot-container .mdl-layout__content {
      margin-left: 0;
  }
}
.mdl-dialog-container.hotspot-edit .mdl-dialog {
  width: 86%;
}
.mdl-dialog-container.hotspot-edit .map{
  height: 600px;
}
.hotspot-content {
    padding: 20px;
}
.hotspot-content section {
    position: relative;
    margin-bottom: 48px;
}
.hotspot-content section > header {
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-align-items: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
}
.hotspot-content section > header > .material-icons {
    font-size: 3rem;
}
.hotspot-content section.section--center {
    max-width: 860px;
}
.hotspot-content .mdl-card .mdl-card__supporting-text {
    margin: 12px 24px;
    -webkit-flex-grow: 1;
    -ms-flex-positive: 1;
    flex-grow: 1;
    padding: 0;
    color: inherit;
    width: calc(100% - 60px);
}
.hotspot-content .mdl-card__supporting-text h4 {
    margin-top: 0;
    margin-bottom: 20px;
}
.hotspot-content .mdl-card__supporting-text + .mdl-card__actions {
    border-top: 1px solid rgba(0, 0, 0, 0.12);
}
.hotspot-content .mdl-card__actions {
    margin: 0;
    padding: 4px 12px;
    color: inherit;
}
.hotspot-content .mdl-card > * {
    height: auto;
}
.hotspot-content .mdl-card__actions a {
    color: #00BCD4;
    margin: 0;
}
.hotspot-content section > button {
    position: absolute;
    z-index: 99;
    top: 8px;
    right: 8px;
}
</style>


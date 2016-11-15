<template>

	<!-- The drawer is always open in large screens. The header is always shown,
	  even in small screens. -->
	<div id="hotspot-container" class="mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
	  <province-city-side-nav></province-city-side-nav>
	  <main class="mdl-layout__content">
    <div class="hotspot-content">
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
        <button class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon" id="btn1" data-upgraded=",MaterialButton,MaterialRipple">
          <i class="material-icons">more_vert</i>
          <span class="mdl-button__ripple-container">
            <span class="mdl-ripple is-animating" style="width: 92.5097px; height: 92.5097px; transform: translate(-50%, -50%) translate(21px, 15px);"></span>
          </span>
        </button>
        <div class="mdl-menu__container is-upgraded" style="right: 8px; top: 40px; width: 124px; height: 160px;">
          <div class="mdl-menu__outline mdl-menu--bottom-right" style="width: 124px; height: 160px;"></div>
          <ul class="mdl-menu mdl-js-menu mdl-menu--bottom-right" for="btn1" data-upgraded=",MaterialMenu" style="clip: rect(0px 124px 0px 124px);">
            <li class="mdl-menu__item" tabindex="-1">Lorem</li>
            <li class="mdl-menu__item" disabled="" tabindex="-1">Ipsum</li>
            <li class="mdl-menu__item" tabindex="-1">Dolor</li>
          </ul>
        </div>
      </section>
    </div>

	    <div class="page-content">

			<div class="mdl-grid">
			  <div class="mdl-cell mdl-cell--8-col"><Map></Map></div>
			  <div class="mdl-cell mdl-cell--4-col">
          <form action="#">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" v-bind:class="hotspotInfo.name ? 'is-dirty' : ''">
              <input class="mdl-textfield__input" type="text" id="spot-name" v-model="hotspotInfo.name">
              <label class="mdl-textfield__label" for="spot-name">热点名</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" v-bind:class="hotspotInfo.description ? 'is-dirty' : ''">
              <input class="mdl-textfield__input" type="text" id="spot-description" v-model="hotspotInfo.description">
              <label class="mdl-textfield__label" for="spot-description">描述</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" v-bind:class="hotspotInfo.centerLongitude ? 'is-dirty' : ''">
              <input class="mdl-textfield__input" type="text" id="spot-longitude" v-model="hotspotInfo.centerLongitude">
              <label class="mdl-textfield__label" for="spot-longitude">经度</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" v-bind:class="hotspotInfo.centerLatitude ? 'is-dirty' : ''">
              <input class="mdl-textfield__input" type="text" id="spot-latitude" v-model="hotspotInfo.centerLatitude">
              <label class="mdl-textfield__label" for="spot-latitude">纬度</label>
            </div>
          </form>
          <!-- Primary-colored flat button -->
          <button class="mdl-button mdl-js-button mdl-button--primary" v-on:click="updateHotspot">
            更新
          </button>
          <!-- Accent-colored flat button -->
          <button class="mdl-button mdl-js-button">
            删除
          </button>
        </div>
			</div>

	    </div>
	  </main>
	</div>

</template>

<style>
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

.hotspot-content {
    padding: 20px;
    padding-top: 48px;
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
<script>
import Map from './MapView.vue'
import ProvinceCitySideNav from './ProvinceCitySideNavView.vue'

const baseAPI = 'http://123.57.230.238:8080/zhiyin-manager/'

export default {
  data () {
    return {
      hotspotInfo: {},
      currentProvince: null,
      currentCity: null,
      currentDegree: null
    }
  },
  components: {
    Map,
    ProvinceCitySideNav
  },
  methods: {
    updateHotspot: function () {
      console.log('update hotspot info')
      if (this.$data.hotspotInfo.centerLongitude) {
        this.$data.hotspotInfo.centerIsset = 1
      }
      var data = this.$data.hotspotInfo
      data['cityId'] = this.$data.currentCity.id
      console.log(data)
      // POST request
      this.$http.post(baseAPI + 'address/hotpot/update', data).then(function (response) {
        // success callback
        console.log('success')
      }, function (response) {
        // error callback
        console.log('error')
      })
    },
    resetHotspot: function () {
      // empty all the hotspot info
      this.$data.hotspotInfo.name = ''
      this.$data.hotspotInfo.description = ''
      this.$data.hotspotInfo.centerLongitude = null
      this.$data.hotspotInfo.centerLatitude = null
    },
    addHotspot: function () {
      console.log('add hotspot info')
      if (this.$data.hotspotInfo.centerLongitude) {
        this.$data.hotspotInfo.centerIsset = 1
        this.$data.hotspotInfo.centerCoord = 3
      }
      var data = this.$data.hotspotInfo
      data['cityId'] = this.$data.currentCity.id
      if (this.$data.currentDegree === 6) {
        data['parentId'] = this.$data.hotspotInfo.id
      } else if (this.$data.currentDegree === 5) {
        data['parentId'] = 0
      }
      data['degree'] = this.$data.currentDegree
      delete data['id']
      console.log(data)
      // POST request
      this.$http.post(baseAPI + 'address/hotpot/add', data).then(function (response) {
        // success callback
        console.log('success')
      }, function (response) {
        // error callback
        console.log('error')
      })
    }
  },
  events: {
    'hotspotSelected': function (data) {
      console.log('handleSelectedHotspot')
      console.log(data.name)
      this.$data.hotspotInfo = data
      console.log(this.$data.hotspotInfo.name)
    },
    'hotspotCenterUpdated': function (data) {
      console.log('hotspotCenterUpdated')
      console.log(data)
      this.$data.hotspotInfo.centerLongitude = data[0]
      this.$data.hotspotInfo.centerLatitude = data[1]
    },
    'provinceUpdated': function (data) {
      console.log('provinceUpdated')
      console.log(data)
      this.$data.currentProvince = data
    },
    'cityUpdated': function (data) {
      console.log('cityUpdated')
      console.log(data)
      this.$data.currentCity = data
    },
    'degreeUpdated': function (data) {
      console.log('cityUpdated')
      console.log(data)
      this.$data.currentDegree = data
    }
  }
}
</script>



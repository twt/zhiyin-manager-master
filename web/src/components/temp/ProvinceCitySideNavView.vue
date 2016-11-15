<template>
  <div class="hotspot-nav mdl-layout__drawer">
      <div class="mdl-card hotspot-info-card">
        <div class="mdl-card__media">
          <img src="/static/logo.png">
        </div>
        <div class="mdl-card__title">
           <h2 class="mdl-card__title-text">大北京</h2>
        </div>
        <div class="mdl-card__supporting-text">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit.
          Mauris sagittis pellentesque lacus eleifend lacinia...
        </div>
        <div class="mdl-card__actions">
           <a class="mdl-button mdl-js-button mdl-js-ripple-effect">查看</a>
        </div>
      </div>
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
</style>

<script>
const baseAPI = 'http://123.57.230.238:8080/zhiyin-manager/'

export default {
  data () {
    return {
      provinces: [],
      cities: [],
      hotspots: [],
      position: 'province',
      currentPosition: '',
      currentCity: null,
      currentCityName: null,
      currentProvince: null,
      currentProvinceName: null,
      hotspotInfo: {},
      degree: null
    }
  },
  ready: function () {
    console.log('Hello')
    // GET request
    this.$http.get(baseAPI + 'address/province').then(function (response) {
      // success callback
      this.$data.provinces = response.data.data.list
      console.log(this.$data)
    }, function (response) {
      // error callback
      console.log('error')
    })
  },
  methods: {
    showCity: function (event) {
      // body...
      this.$data.currentProvince = parseInt(event.target.dataset.id)
      this.getCities(this.$data.currentProvince)
      this.$data.position = 'city'
      this.$data.currentPosition = event.target.dataset.name
      this.$data.currentProvinceName = event.target.dataset.name
      var data = {
        id: this.$data.currentProvince,
        name: this.$data.currentProvinceName
      }
      this.$dispatch('provinceUpdated', data)
    },
    showProvince: function (event) {
      console.log('back to province')
      this.$data.position = 'province'
      this.$data.currentPosition = ''
    },
    showHotspots: function (event) {
      this.$data.currentCity = parseInt(event.target.dataset.id)
      this.$data.degree = 5
      this.getHotspotsByCityId(this.$data.currentCity, this.$data.degree)
      this.$data.position = 'hotspot'
      this.$data.currentPosition = event.target.dataset.name
      this.$data.currentCityName = event.target.dataset.name
      var data = {
        id: this.$data.currentCity,
        name: this.$data.currentCityName
      }
      this.$dispatch('cityUpdated', data)
      this.$dispatch('degreeUpdated', this.$data.degree)
    },
    showSubHotspots: function (event) {
      var hotspotId = event.target.dataset.id
      this.getSubHotspots(hotspotId)
      this.$data.position = 'hotspot'
      this.$data.currentPosition = event.target.dataset.name
      this.$data.degree = 6
      this.$dispatch('degreeUpdated', this.$data.degree)
    },
    showHotspotInfo: function (event) {
      console.log('showHotspotInfo')
      var hotspotId = event.target.dataset.id
      if (this.$data.degree <= 6) {
        this.$data.degree += 1
        this.getSubHotspots(hotspotId)
      }
      this.getHotspotInfo(hotspotId)
      this.$data.position = 'hotspot'
      this.$data.currentPosition = event.target.dataset.name

      this.$dispatch('degreeUpdated', this.$data.degree)
    },
    upperNav: function () {
      if (this.$data.position === 'hotspot' && this.$data.degree > 6) {
        this.$data.degree -= 1
        this.getSubHotspots(this.$data.hotspotInfo.parentId)
        this.$dispatch('degreeUpdated', this.$data.degree)
      } else if (this.$data.position === 'hotspot' && this.$data.degree === 6) {
        this.$data.degree = 5
        this.getHotspotsByCityId(this.$data.currentCity, this.$data.degree)
        this.$data.currentPosition = this.$data.currentCityName
        this.$dispatch('degreeUpdated', this.$data.degree)
      } else if (this.$data.position === 'hotspot' && this.$data.degree === 5) {
        this.$data.degree = 5
        this.$data.position = 'city'
      }
    },
    getCities: function (provinceId) {
      console.log('getCities')
      console.log(provinceId)
      // GET request
      this.$http.get(baseAPI + 'address/province/city', {provinceId: provinceId}).then(function (response) {
        // success callback
        this.$data.cities = response.data.data.cityList
        console.log(this.$data)
      }, function (response) {
        // error callback
        console.log('error')
      })
    },
    getHotspotsByCityId: function (cityId, degree) {
      console.log('getHotspotsByCityId')
      // GET request
      var data = {
        cityId: cityId,
        degree: degree
      }
      this.$http.get(baseAPI + 'address/city/hotpot/degree', data).then(function (response) {
        // success callback
        this.$data.hotspots = response.data.data.customAddressList
        console.log(this.$data)
      }, function (response) {
        // error callback
        console.log('error')
      })
    },
    getSubHotspots: function (parentHotspotId) {
      console.log('getSubHotspots')
      // GET request
      var data = {
        parentId: parentHotspotId
      }
      this.$http.get(baseAPI + 'address/hotpot/son', data).then(function (response) {
        // success callback
        this.$data.hotspots = response.data.data.customAddressList
        console.log(this.$data)
      }, function (response) {
        // error callback
        console.log('error')
      })
    },
    getHotspotInfo: function (hotspotId) {
      console.log('getHotspotInfo')
      // GET request
      var data = {
        id: hotspotId
      }
      this.$http.get(baseAPI + 'address/hotpot/id', data).then(function (response) {
        // success callback
        this.$data.hotspotInfo = response.data.data
        console.log(this.$data.hotspotInfo)
        this.$dispatch('hotspotSelected', response.data.data)
      }, function (response) {
        // error callback
        console.log('error')
      })
    }
  }
}
</script>


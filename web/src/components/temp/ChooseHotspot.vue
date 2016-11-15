<template>
  <div class="mdl-grid">
      <div class="mdl-cell mdl-cell--12-col"><h4>{{cityName}}</h4></div>
      <div class="mdl-card mdl-color--primary mdl-shadow--2dp mdl-cell mdl-cell--3-col">
        <div class="mdl-card__media">
          <!--<img src="/static/logo.png">-->
        </div>
        <div class="mdl-card__title mdl-color-text--blue-grey-50">
           <h2 class="mdl-card__title-text">暂无根热点</h2>
        </div>
        <div class="mdl-card__actions">
           <a class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-color-text--blue-grey-50" href="http://en.wikipedia.org/wiki/Sky_Tower_%28Auckland%29">添加总热点</a>
        </div>
      </div>
      <div class="mdl-card mdl-shadow--2dp mdl-cell mdl-cell--3-col">
        <div class="mdl-card__media">
          <!--<img src="/static/logo.png">-->
        </div>
        <div class="mdl-card__title">
           <h2 class="mdl-card__title-text">大北京</h2>
        </div>
        <div class="mdl-card__actions">
           <a class="mdl-button mdl-js-button mdl-js-ripple-effect">查看</a>
        </div>
      </div>
  </div>

  <div class="mdl-grid">
      <div class="mdl-card mdl-shadow--2dp mdl-cell mdl-cell mdl-cell--3-col" v-for="hotspot in hotspots">
        <div class="mdl-card__media">
          <img src="">
        </div>
        <div class="mdl-card__title">
           <h2 class="mdl-card__title-text">{{hotspot.name}}</h2>
        </div>
        <div class="mdl-card__actions">
           <a class="mdl-button mdl-js-button mdl-js-ripple-effect" v-link="{ name: 'HotSpotPage', params: { cityId: cityId, hotspotId: hotspot.id }}">查看</a>
        </div>
      </div>
  </div>
</template>

<script>
import CityService from './cityService.js'
import Config from '../config.js'

var base = Config.data()

export default {
  data () {
    return {
      cityName: '',
      cityId: null,
      hotspots: []
    }
  },

  route: {
    data: function (transition) {
      var cityId = transition.to.params.cityId
      return {
        cityId: cityId,
        cityName: CityService.getCityNameById(cityId),
        hotspots: this.getHotspotsByCityId(cityId, 5)
      }
    }
  },
  methods: {
    getHotspotsByCityId: function (cityId, degree) {
      console.log('getHotspotsByCityId')
      // GET request
      var data = {
        cityId: cityId,
        degree: degree
      }
      return this.$http.get(base.api + 'address/city/hotpot/degree', data).then(function (response) {
        // success callback
        return response.data.data.customAddressList
      }, function (response) {
        // error callback
        console.log('error')
      })
    }
  }
}
</script>

<style>
.mdl-card {
  min-height: 100px;
}
.mdl-card__title-text {
  font-size: 16px;
}
</style>

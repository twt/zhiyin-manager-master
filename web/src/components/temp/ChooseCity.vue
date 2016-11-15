<template>
  <div class="mdl-grid">
    <h6 class='mdl-list mdl-cell mdl-cell--12-col'>
	    请选择城市
    </h6>
    <ul class='mdl-list mdl-cell mdl-cell--12-col' v-for="province in provinces">
     <h4>{{province.name}}</h4>
      <li class="mdl-list__item" v-for="city in province.cities" data-id="{{city.id}}" v-link="{ name: 'city', params: { cityId: city.id }}">
        <span class="mdl-list__item-primary-content">{{city.name}}</span>
      </li>
    </ul>


  </div>
</template>

<style>
.mdl-list__item {
  padding: 8px 0;
  min-height: 38px;
  cursor: pointer;
}
</style>

<script>
import CityService from './cityService.js'

export default {
  data () {
    return {
      provinces: CityService.getProvinceCities()
    }
  }
}
</script>
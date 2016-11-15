<script>
import AMap from 'AMap'

var mapObj = null
export default {
  data () {
    return {
      markers: [{
        title: 'Test',
        position: [116.39, 39.9]
      }]
    }
  },
  ready () {
    mapObj = new AMap.Map('map-container', {
      zoom: 11,
      center: [116.39, 39.9]
    })
    console.log(mapObj)

    var markers = []
    for (var i = 0; i < this.$data.markers.length; i++) {
      var _marker = this.$data.markers[i]
      console.log(_marker)
      var marker = new AMap.Marker({
        position: _marker.position,
        title: _marker.title,
        map: mapObj
      })
      markers.push(marker)
    }
  },
  method: {
    setCity: function (city) {
      console.log('set city' + city)
      mapObj.setCity(city)
    }
  }
}
</script>
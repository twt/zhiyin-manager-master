<script>
import Config from './config.js'
var base = Config.data()

export default {
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
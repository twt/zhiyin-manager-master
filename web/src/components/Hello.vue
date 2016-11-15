<template>
  <div class="hello">
    <h1>{{ msg }}111111</h1>

    <ul class="mdl-list">
      <li v-for="item in list">
        <span class="mdl-list__item-primary-content">
          {{item.name}} - {{item.description}}
        </span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  ready: function () {
    // GET request
    this.$http({url: '/static/data.json', method: 'GET'}).then(function (response) {
      // success callback
      console.log('success')
      this.$data = response.data.data
    }, function (response) {
      // error callback
      console.log('error')
    })
  }
}
</script>

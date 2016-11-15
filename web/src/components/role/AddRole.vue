<template>
  <div id="add-role" class="role-content">
    <section class="section--center mdl-grid mdl-grid--no-spacing mdl-shadow--2dp">
      <header class="section__play-btn mdl-cell mdl-cell--2-col-desktop mdl-cell--2-col-tablet mdl-cell--4-col-phone">
        <img :src="role.avatar" height="200px">
      </header>
      <div class="mdl-card mdl-cell mdl-cell--10-col-desktop mdl-cell--6-col-tablet mdl-cell--4-col-phone">
        <div class="mdl-card__supporting-text">
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="role.name ? 'is-dirty' : ''">
            <input class="mdl-textfield__input" type="text" id="role-name" v-model="role.name">
            <label class="mdl-textfield__label" for="role-name">角色名</label>
          </div>
        </div>
        <div class="mdl-card__supporting-text">
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="role.description ? 'is-dirty' : ''">
            <input class="mdl-textfield__input" type="text" id="role-description" v-model="role.description">
            <label class="mdl-textfield__label" for="role-description">描述</label>
          </div>
        </div>
        <div class="mdl-card__actions">
            <upload-button upload-type="RoleAvatar" upload-name="角色图片" @uploaded="uploaded"></upload-button>
            <upload-button upload-type="RoleAudioIntro" upload-name="音频" @uploaded="uploaded"></upload-button>
            <mdl-button primary @click='saveRole'>保存</mdl-button>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import RoleService from './RoleService.js'
import UploadButton from '../fileupload/UploadButton.vue'

export default {
  name: 'AddRole',
  components: {
    UploadButton
  },
  data () {
    return {
      role: {
        'audio': '',
        'avatar': '',
        'description': '',
        'id': 0,
        'name': ''
      }
    }
  },
  methods: {
    saveRole: function () {
      console.log('try to add role: ' + this.role)
      var avatar = this.role.avatar
      var audio = this.role.audio
      this.role.avatar = avatar.split('/')[avatar.split('/').length - 1]
      this.role.audio = audio.split('/')[audio.split('/').length - 1]
      var promise = RoleService.addRole(this.role)
      promise.then(function (response) {
        // success callback
        console.log('success to add role')
        this.$dispatch('successMsg', 'Add role ' + this.role.name + ' successfully!')
      }, function (response) {
        // error callback
        this.$dispatch('errorMsg', response.data)
      })
      this.role.avatar = ''
      this.role.audio = ''
      this.role.name = ''
      this.role.description = ''
    }
  },
  events: {
    'uploaded': function (data) {
      if (data.type === 'RoleAvatar') {
        this.role.avatar = data.link
      } else if (data.type === 'RoleAudioIntro') {
        console.log('set audio')
        this.role.audio = data.link
      }
    }
  }
}
</script>

<style>
#add-role .mdl-card__supporting-text + .mdl-card__actions {
    position: relative;
}
</style>
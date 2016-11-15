<template>
  <div class="role-content">

      <section class="section--center mdl-grid mdl-grid--no-spacing mdl-shadow--2dp" v-for="role in roles">
        <header class="section__play-btn mdl-cell mdl-cell--2-col-desktop mdl-cell--2-col-tablet mdl-cell--4-col-phone">
          <img :src="role.avatar" height="200px">
        </header>
        <div class="mdl-card mdl-cell mdl-cell--10-col-desktop mdl-cell--6-col-tablet mdl-cell--4-col-phone">
          <div class="mdl-card__supporting-text">
            <h3>{{role.name}}</h3>
            {{role.description}}
          </div>
          <div class="mdl-card__actions">
            <button class="mdl-button" @click="playRole(role.id)">播放</button>
            <button class="mdl-button" @click="updateRole(role.id)">修改</button>
            <button class="mdl-button" @click="deleteRole(role.id)">删除</button>
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
  </div>

  <audio id="myAudio" controls="controls" autoplay="autoplay">
    <source id="audioSrc" src="{{selectedAudio}}" type="audio/mpeg">
     您的浏览器不支持 audio 与元素。
  </audio>
  <mdl-dialog title="确认" display-on="confirmAction">
    <p>确认要删除角色 <strong>{{selectedRole.name}}</strong>?</p>
    <template slot="actions">
      <mdl-button primary>删除</mdl-button>
      <mdl-button>取消</mdl-button>
    </template>
  </mdl-dialog>

  <mdl-dialog title="修改角色" display-on="updateAction" id="update_role">
    <section class="section--center mdl-grid mdl-grid--no-spacing">
      <header class="section__play-btn mdl-cell mdl-cell--2-col-desktop mdl-cell--2-col-tablet mdl-cell--4-col-phone">
        <img :src="selectedRole.avatar" height="200px">
      </header>
      <div class="mdl-card mdl-cell mdl-cell--10-col-desktop mdl-cell--6-col-tablet mdl-cell--4-col-phone">
        <div class="mdl-card__supporting-text">
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="selectedRole.name ? 'is-dirty' : ''">
            <input class="mdl-textfield__input" type="text" id="role-name" v-model="selectedRole.name">
            <label class="mdl-textfield__label" for="role-name">热点名</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" :class="selectedRole.description ? 'is-dirty' : ''">
            <input class="mdl-textfield__input" type="text" id="role-description" v-model="selectedRole.description">
            <label class="mdl-textfield__label" for="role-description">描述</label>
          </div>
          <div class="mdl-card__actions">
            <upload-button upload-type="RoleAvatar" upload-name="角色图片" @uploaded="uploaded"></upload-button>
            <upload-button upload-type="RoleAudioIntro" upload-name="音频" @uploaded="uploaded"></upload-button>
<!--             <div class="mdl-textfield mdl-js-textfield mdl-textfield--file">
              <input class="mdl-textfield__input" placeholder="上传音频" type="text" id="uploadRoleAudio" value="{{uploadAudio}}" readonly/>
              <div class="mdl-button mdl-button--primary mdl-button--icon mdl-button--file">
                <i class="material-icons">attach_file</i>
                <div id="uploadAudioBtn_container"><input type="file" id="uploadAudioBtn" @change="audioBtnClicked"></div>
              </div>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--file">
              <input class="mdl-textfield__input" placeholder="上传角色图片" type="text" id="uploadRoleImg" value="{{uploadImg}}" readonly/>
              <div class="mdl-button mdl-button--primary mdl-button--icon mdl-button--file">
                <i class="material-icons">attach_file</i>
                <div id="uploadRoleImgBtn-{{selectedRole.id}}-container"><input type="file" id="uploadRoleImgBtn-{{selectedRole.id}}" @change="imgBtnClicked"></div>
              </div>
            </div> -->
          </div>
        </div>
      </div>
    </section>
    <template slot="actions">
      <mdl-button primary>保存</mdl-button>
      <mdl-button>取消</mdl-button>
    </template>
  </mdl-dialog>

</template>

<style>

.mdl-button--file input {
    cursor: pointer;
    height: 100%;
    right: 0;
    opacity: 0;
    position: absolute;
    top: 0;
    width: 300px;
    z-index: 4;
}
.mdl-textfield--file .mdl-textfield__input {
    box-sizing: border-box;
    width: calc(100% - 32px);
}
.mdl-textfield--file .mdl-button--file {
    right: 0;
}

.mdl-list__item {
  padding: 8px 0;
  min-height: 38px;
  cursor: pointer;
}
.role-content section {
    position: relative;
    margin-bottom: 48px;
}
.role-content section.section--center {
    max-width: 860px;
}
.role-content .mdl-card__supporting-text + .mdl-card__actions {
    position: absolute;
    bottom: 0;
    border-top: 1px solid rgba(0, 0, 0, 0.12);
}
#update_role > .mdl-dialog {
  min-width: 80%;
}
</style>

<script>
import RoleService from './RoleService.js'
import UploadButton from '../fileupload/UploadButton.vue'

export default {
  name: 'RoleHomepage',
  components: {
    UploadButton
  },
  data () {
    return {
      roles: null,
      selectedRole: null,
      uploadImg: null,
      uploadAudio: null
    }
  },
  route: {
    data: function (transition) {
      return {
        roles: RoleService.getRoles()
      }
    }
  },
  methods: {
    getSelectedRole: function (id) {
      var self = this
      for (var i = 0; i < self.roles.length; i++) {
        if (self.roles[i].id === id) {
          self.selectedRole = self.roles[i]
        }
      }
    },
    playRole: function (id, audio) {
      console.log('play audio:' + audio)
      this.getSelectedRole(id)
      document.getElementById('audioSrc').src = this.selectedRole.audio
      document.getElementById('myAudio').load()
      // x.play()
    },
    updateRole: function (id) {
      var self = this
      this.getSelectedRole(id)
      this.$broadcast('updateAction', function () {
        console.log('try to update role: ' + self.selectedRole)
        var avatar = self.selectedRole.avatar
        var audio = self.selectedRole.audio
        self.selectedRole.avatar = avatar.split('/')[avatar.split('/').length - 1]
        console.log(self.selectedRole.avatar)
        self.selectedRole.audio = audio.split('/')[audio.split('/').length - 1]
        var result = RoleService.updateRole(self.selectedRole)
        console.log('result: ')
        console.log(result)
        self.selectedRole.avatar = avatar
        self.selectedRole.audio = audio
      }, function () {
        console.log('cancel')
      })
    },
    deleteRole: function (id) {
      var self = this
      this.getSelectedRole(id)
      this.$broadcast('confirmAction', function () {
        // remove role
        console.log('try to delete role: ' + this.selectedRole)
        var result = RoleService.deleteRole(id)
        if (result) {
          for (var i = 0; i < self.roles.length; i++) {
            if (self.roles[i].id === id) {
              self.roles.splice(i, 1)
            }
          }
        }
        console.log('result: ' + result.toString())
      }, function () {
        console.log('cancel')
      })
    }
  },
  events: {
    'uploaded': function (data) {
      console.log('get upload info')
      console.log(data)
      if (data.type === 'RoleAvatar') {
        console.log('set avatar')
        console.log(this)
        this.selectedRole.avatar = data.link
      } else if (data.type === 'RoleAudioIntro') {
        console.log('set audio')
        this.selectedRole.audio = data.link
      }
    }
  }
}
</script>
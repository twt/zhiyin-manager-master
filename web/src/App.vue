<template>
  <div class="mdl-layout__container">
    <div class="docs-layout mdl-layout mdl-js-layout">
      <!-- header start -->
      <header class="docs-layout-header mdl-layout__header is-casting-shadow">
        <div class="mdl-layout__header-row">
          <span class="docs-layout-title mdl-layout-title">知音——管理面板</span>
        </div>
        <div class="docs-navigation__container">
          <nav class="docs-navigation mdl-navigation">
            <a v-link="{ path: '/' }" class="mdl-navigation__link">首页</a>
            <a v-link="{ path: '/user' }" class="mdl-navigation__link">用户</a>
            <a v-link="{ path: '/c' }" class="mdl-navigation__link">内容</a>
            <a v-link="{ path: '/role' }" class="mdl-navigation__link">角色</a>
			
            <a v-link="{ path: '/ctg' }" class="mdl-navigation__link templates">内容组</a>
            <a v-link="{ path: '/hp', params: {addrId: 101} }" class="mdl-navigation__link templates">热点图片</a>
			
            <div class="spacer"></div>
          </nav>
        </div>
        <i class="material-icons scrollindicator scrollindicator--right"></i>
        <i class="material-icons scrollindicator scrollindicator--left disabled"></i>
      </header>
      <!-- header end -->
      <main class="mdl-layout__content mdl-color-text--grey-600">
        <router-view
          class="view"
          keep-alive
          transition
          transition-mode="out-in">
        </router-view>
      </main>


    </div>
  </div>

  <mdl-dialog title="Sorry, 出错了" display-on="errorAction" cancellable>
    <h4>Error: {{errorMsg.error}} —— {{errorMsg.status}}</h4>
    <p>{{errorMsg.message}}</p>
    <p>{{errorMsg.exception}}</p>
    <p>Path: {{errorMsg.path}}</p>
    <template slot="actions">
      <mdl-button>确定</mdl-button>
    </template>
  </mdl-dialog>
  <mdl-snackbar display-on="successAction"></mdl-snackbar>
</template>

<script>
export default {
  data () {
    return {
      errorMsg: null
    }
  },
  events: {
    'errorMsg': function (msg) {
      this.errorMsg = msg
      this.$broadcast('errorAction')
    },
    'successMsg': function (msg) {
      this.$broadcast('successAction', {
        message: msg
      })
    }
  }
}
</script>

<style>
html>body {
    font-family: 'Roboto', 'Helvetica', 'Arial', sans-serif!important;
    background-color: #FAFAFA
}

.mdl-button--file {
  input {
    cursor: pointer;
    height: 100%;
    right: 0;
    opacity: 0;
    position: absolute;
    top: 0;
    width: 300px;
    z-index: 4;
  }
}

.mdl-textfield--file {
  .mdl-textfield__input {
    box-sizing: border-box;
    width: calc(100% - 32px);
  }
  .mdl-button--file {
    right: 0;
  }
}

.docs-layout .docs-layout-header.mdl-layout__header {
    height: 100px;
    max-height: 50%;
    background-size: auto;
    background-repeat: no-repeat;
    background-position: center center;
    box-shadow: none!important;
    -webkit-align-items: flex-start;
    -ms-flex-align: start;
    align-items: flex-start;
    padding: 20px 40px 40px 40px;
    -webkit-flex-shrink: 0;
    -ms-flex-negative: 0;
    flex-shrink: 0;
    position: relative;
    display: -webkit-flex!important;
    display: -ms-flexbox!important;
    display: flex!important
}

body.about .docs-layout.is-small-screen .docs-layout-header.mdl-layout__header {
    height: 280px;
    background-size: auto 58px, cover;
    background-repeat: no-repeat, no-repeat;
    background-position: center center, center center
}

.docs-layout-header .mdl-textfield {
    padding-top: 0
}

.docs-layout-header>.mdl-layout__header-row {
    padding: 0;
    -webkit-align-items: flex-start;
    -ms-flex-align: start;
    -ms-grid-row-align: flex-start;
    align-items: flex-start;
    height: auto
}

.docs-layout-header .docs-navigation .github,
.docs-layout-header .docs-navigation .download {
    text-transform: none
}

.docs-layout-title {
    font-weight: 500;
    text-transform: uppercase;
    line-height: 1.5em;
    font-size: 1rem
}

.docs-layout .docs-layout-title a {
    font-weight: inherit;
    color: #fff
}

.docs-layout-header .mdl-textfield .mdl-button {
    right: 0
}

.docs-layout-header .mdl-textfield .mdl-textfield__expandable-holder {
    margin-right: 32px
}

.docs-layout-header .mdl-textfield label:after {
    background-color: rgba(255, 255, 255, .12)
}

.about .docs-layout-header.mdl-layout__header {
    background-color: #37474f;
    background-size: auto 118px, cover;
    background-repeat: no-repeat, no-repeat;
    background-position: center center, center center
}

@media (max-height:600px) {
    .about .docs-layout-header.mdl-layout__header {
        background-size: auto 80px, cover
    }
}


.docs-navigation__container {
    overflow: hidden;
    position: absolute;
    height: 64px;
    width: 100%;
    bottom: 0;
    left: 0
}

.docs-navigation {
    box-shadow: none!important;
    border: 0!important;
    width: 100%;
    height: 85px;
    -webkit-flex-shrink: 0;
    -ms-flex-negative: 0;
    flex-shrink: 0;
    padding: 0 16px 0 24px;
    overflow-x: auto;
    overflow-y: hidden;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    margin-bottom: -16px
}

.docs-layout.is-small-screen .docs-navigation {
    padding: 0
}

.docs-layout.is-small-screen .docs-navigation .mdl-navigation__link .material-icons {
    display: none
}

.docs-navigation::-webkit-scrollbar {
    display: none
}

body:not(.about) .docs-layout.is-small-screen .docs-layout-header {
    background-image: none
}

.docs-layout.mdl-layout.is-small-screen .docs-layout-header.mdl-layout__header {
    padding: 0;
    height: 64px
}

.docs-layout.is-small-screen .docs-layout-header .docs-layout-title,
.docs-layout.is-small-screen .docs-layout-header .mdl-textfield {
    display: none
}

.docs-layout.is-small-screen .docs-navigation__container {
    top: 0;
    bottom: auto;
    left: 48px;
    width: calc(100% - 2 * 48px);
    padding: 0!important
}

.docs-layout .scrollindicator {
    position: absolute;
    top: 0;
    line-height: 64px;
    height: 64px;
    width: 48px;
    display: none;
    margin-right: 0;
    text-align: center;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none
}

.docs-layout .scrollindicator.disabled {
    opacity: .12;
    cursor: default
}

.docs-layout .scrollindicator.scrollindicator--right {
    right: 0
}

.docs-layout .scrollindicator.scrollindicator--left {
    left: 0
}

.docs-layout.is-small-screen .scrollindicator.scrollindicator.scrollindicator {
    display: block
}

.mdl-navigation__link--icon>span,
.mdl-navigation__link--icon>.material-icons {
    line-height: 64px;
    margin-right: 8px;
    line-height: inherit
}

.docs-navigation .mdl-navigation__link {
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-flex-shrink: 0;
    -ms-flex-negative: 0;
    flex-shrink: 0;
    -webkit-user-select: inherit;
    -moz-user-select: inherit;
    -ms-user-select: inherit;
    user-select: inherit;
    height: 64px
}

.docs-navigation .mdl-navigation__link:hover,
.docs-navigation .mdl-navigation__link.download:hover>span,
.docs-navigation .mdl-navigation__link.download:hover>.material-icons {
    background-color: inherit;
    opacity: 1!important
}

.docs-navigation .mdl-navigation__link,
section.download {
    font-weight: 500;
    font-size: 13px;
    text-transform: uppercase;
    line-height: 64px;
    padding: 0 16px;
    color: #fff;
    box-sizing: border-box;
    border-bottom: 3px solid transparent
}a

.docs-layout.is-small-screen .docs-navigation .mdl-navigation__link,
.docs-layouy.is-small-screen section.download {
    padding: 0 12px
}

.about .docs-layout:not(.is-small-screen) .mdl-navigation__link.download>.material-icons,
.about .docs-layout.is-small-screen .mdl-navigation__link.download>button,
body:not(.about) .mdl-navigation__link.download>button {
    display: none
}

.docs-navigation .download button .material-icons {
    color: #000;
    opacity: .54
}

.docs-navigation .active {
    opacity: 1;
    border-bottom-color: #18FFFF
}

.mdl-layout__content.docs-layout-content {
    overflow: visible
}

.docs-layout-content>.download {
    width: 100%;
    height: 6rem;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-align-items: center;
    -ms-flex-align: center;
    align-items: center
}

.docs-layout-content>.download>a {
    font-weight: 500;
    text-transform: uppercase
}

.docs-layout.is-small-screen .about-panel {
    padding: 40px
}

.docs-layout.is-small-screen .about-panel--text:not(:first-of-type) {
    display: none
}

.docs-layout.is-small-screen .about-panel--text dl {
    -webkit-flex-direction: column;
    -ms-flex-direction: column;
    flex-direction: column
}

.docs-layout.is-small-screen .about-panel--text dd,
.docs-layout.is-small-screen .about-panel--text dt {
    margin: 0;
    padding: 0;
    text-align: left;
    width: 100%
}

.docs-layout-content .about-panel--start>p {
    color: #424242;
    text-transform: uppercase;
    font-size: 1.6rem;
    margin-top: 20px;
    margin-bottom: 0
}



.docs-layout-content dd {
    font-size: 13px
}

.docs-layout-content p {
    font-size: 13px;
    margin-bottom: 32px;
    max-width: 640px
}

.docs-layout-content p,
.docs-text-styling ol li {
    font-family: 'Roboto', 'Helvetica', 'Arial', sans-serif
}

.docs-text-styling h1 {
    color: #c2185b;
    margin: .67em 0
}

.docs-text-styling h2 {
    padding-top: 48px;
    line-height: 32px;
    margin-bottom: 30px;
    color: #c2185b
}

.docs-text-styling h1,
.docs-text-styling h2,
.docs-text-styling h3,
.docs-text-styling h4 {
    font-size: 16px;
    color: rgba(0, 0, 0, .54);
    font-weight: 500;
    text-transform: uppercase
}

.docs-layout-content h2.mdl-card__title-text {
    padding-top: 0;
    margin-bottom: 0
}

.docs-text-styling a {
    text-decoration: none
}

.docs-layout-content .mdl-download {
    color: #000;
    font-weight: 400
}

.docs-layout-title a {
    color: inherit;
    text-decoration: none
}

.docs-layout .docs-text-styling pre[class*=language-markup] {
    max-width: calc(100vw - 32px)
}

.docs-layout pre[class*=language-markup] {
    max-width: 100vw;
    box-sizing: border-box;
    overflow: hidden
}

.docs-layout pre[class*=language-markup].codepen-button-enabled {
    padding-right: 0
}

.docs-layout pre[class*=language-markup].codepen-button-enabled code {
    padding-right: 50px
}

@media (max-width:850px) {
    .started .docs-layout .docs-text-styling pre[class*=language-markup] {
        max-width: none
    }
    .docs-layout .code-with-text {
        width: 100%;
        margin-left: -40px;
        margin-right: -40px;
        padding: 15px 40px;
        display: block;
        box-sizing: content-box
    }
    .docs-layout .code-with-text pre {
        width: auto;
        padding-left: 40px;
        padding-right: 40px;
        margin-left: -40px;
        margin-right: -40px
    }
}


.docs-navigation .spacer {
    -webkit-flex-grow: 1;
    -ms-flex-positive: 1;
    flex-grow: 1
}

</style>

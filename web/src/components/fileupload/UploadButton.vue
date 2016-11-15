<template>
  <div id="container-{{uploadType}}">
    <mdl-button v-mdl-ripple-effect id="button-{{uploadType}}">上传{{uploadName}}</mdl-button>
    <input type="hidden" id="uptoken_url" value="{{uptoken}}">
  </div>
</template>

<script>
import Qiniu from 'Qiniu'
import FileService from './FileUploadService.js'

export default {
  props: {
    uploadType: String,
    container: String,
    uploadName: String
  },
  data () {
    return {
      uploader: null,
      uptoken: '',
      domain: '',
      prefix: ''
    }
  },
  ready: function () {
    var self = this
    console.log(self)
    var filename = ''
    var promise = FileService.getToken(filename, self.uploadType)
    promise.then(function (response) {
      // success callback
      var result = response
      self.uptoken = result.token
      self.domain = result.domain
      self.key = result.key
      self.prefix = result.prefix
      self.uploader = Qiniu.uploader({
        prefix: self.prefix,
        runtimes: 'html5,flash,html4',       // 上传模式,依次退化
        upload_type: self.uploadType,
        browse_button: 'button-' + self.uploadType,          // 上传选择的点选按钮，**必需**
        // uptoken_url: '/token',            // Ajax请求upToken的Url，**强烈建议设置**（服务端提供）
        uptoken: self.uptoken,
        // unique_names: true, // 默认 false，key为文件名。若开启该选项，SDK为自动生成上传成功后的key（文件名）。
        // save_key: true,   // 默认 false。若在服务端生成uptoken的上传策略中指定了 `sava_key`，则开启，SDK会忽略对key的处理
        domain: self.domain,   // bucket 域名，下载资源时用到，**必需**
        get_new_uptoken: true,            // 设置上传文件的时候是否每次都重新获取新的token
        container: 'container-' + self.uploadType,            // 上传区域DOM ID，默认是browser_button的父元素，
        max_file_size: '100mb',            // 最大文件体积限制
        // flash_swf_url: 'js/plupload/Moxie.swf',  // 引入flash,相对路径
        max_retries: 3,                    // 上传失败最大重试次数
        dragdrop: false,                   // 开启可拖曳上传
        // drop_element: 'container',      // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
        chunk_size: '4mb',                 // 分块上传时，每片的体积
        auto_start: true,                 // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
        init: {
          'FilesAdded': function (up, files) {
            // console.log('FilesAdded')
            // for (var i = 0; i < files.length; i++) {
            //   var file = files[i]
            //   console.log(file.name)
            // }
            // console.log(up.id)
            // console.log(up.getOption().domain)
          },
          'BeforeUpload': function (up, file) {
            // 每个文件上传前,处理相关的事情
            // console.log('BeforeUpload')
            // console.log(file.name)
          },
          'UploadProgress': function (up, file) {
            // 每个文件上传时,处理相关的事情
            console.log('正在上传' + file.name)
            console.log(up.getOption().domain)
            console.log(up.getOption().uptoken)
          },
          'FileUploaded': function (up, file, info) {
            console.log('FileUploaded')
            var domain = up.getOption('domain')
            console.log(info)
            var res = JSON.parse(info)
            var sourceLink = 'http://' + domain + '/' + res.key     // 获取上传成功后的文件的Url
            var data = {
              'type': up.getOption('upload_type'),
              'link': sourceLink,
              'updatePath': file.id + '_' + file.name
            }
            console.log(self)
            console.log(data)
            self.$dispatch('uploaded', data)
            console.log('---------------')
            // 每个文件上传成功后,处理相关的事情
            // 其中 info 是文件上传成功后，服务端返回的json，形式如
            // {
            //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
            //    "key": "gogopher.jpg"
            //  }
            // 参考http://developer.qiniu.com/docs/v6/api/overview/up/response/simple-response.html

            // var domain = up.getOption('domain');
            // var res = parseJSON(info);
            // var sourceLink = domain + res.key; 获取上传成功后的文件的Url
          },
          'Error': function (up, err, errTip) {
            // 上传出错时,处理相关的事情
            console.log('error')
            console.log(err)
            console.log(errTip)
          },
          'UploadComplete': function () {
            // 队列文件处理完毕后,处理相关的事情
            console.log('UploadComplete')
          },
          'Key': function (up, file) {
            // console.log('-----------------')
            // console.log(this)
            // console.log('---------------')
            // console.log(self)
            // console.log(self.prefix)
            // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
            // 该配置必须要在 unique_names: false , save_key: false 时才生效
            // 此处的key为测试数据，正常情况应从获取的数据中读取key的值
            // console.log('key')
            var key = up.getOption().prefix + file.id + '_' + file.name
            // console.log(key)
            // var key = "zhiyin/content/audio/732204597354958800.png";
            // do something with key here
            return key
          }
        }
      })
    }, function (response) {
      // error callback
      console.log('error')
    })
  }
}
</script>




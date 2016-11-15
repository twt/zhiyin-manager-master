<template>
  <h4>{{msg}}</h4>
  <!--<form action="files/upload/qiniu" method="post" enctype="multipart/form-data">
    选择文件:<input type="file" name="file">
    <input name="file_type" id="file_type">
    <br/>
    <input type="submit" value="提交">
  </form>-->
</template>
<style>
  body{
    background-color:#ff0000;
  }
</style>
<script>
  export default{
    data () {
      return {
        msg: 'uploadPics'
      }
    },
    components: {
    }
  }
</script>

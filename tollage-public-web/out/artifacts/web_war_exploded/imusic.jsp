<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>iMusic 后台系统</title>
    <style type="text/css">
      .div0 {
        margin:0 auto;
        background:url(images/bg.jpg);
        align-content: center;
        background-size:100%;
        width: 450px;
        height: 700px; }
      .text_title {
        font-size: large;
        align-content: center;
      }
    </style>
  </head>
  <body>
    <div class="div0">
      <br>
      <label class="text_title">iMusic 后台系统</label><br><br><br>

      <form action="imusic/upload" method="post" enctype="multipart/form-data">
        <label class="text_content">上传音乐：</label>
        <input type="file" multiple name="upload">
        <input type="submit" value="上传">
      </form>
    </div>
  </body>
</html>
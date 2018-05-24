<%--
  Created by IntelliJ IDEA.
  User: liuru3
  Date: 2018/4/14
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>SpringMVC文件上传</p>
<form name="form1" action="/admin/product/upload.do" enctype="multipart/form-data" method="post">
    <input type="file" name="upload_file">
    <input type="submit" value="SpingMVC上传文件">
</form>

<p>富文本文件上传</p>
<form name="form2" action="/admin/product/richtext_img_upload.do" enctype="multipart/form-data" method="post">
    <input type="file" name="upload_file">
    <input type="submit" value="富文本文件上传">
</form>
</body>
</html>

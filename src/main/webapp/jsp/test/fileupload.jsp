<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传测试</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/layui.css"/>
    <%--    <link rel="stylesheet" href="//res.layui.com/layui/dist/css/layui.css" media="all">--%>
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>
<%--通过表单上传文件
get:    上传文件大小有限制
post:   上传文件大小没有限制
--%>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
    <legend>图片上传</legend>
</fieldset>

<div class="layui-upload">
    <form action="${pageContext.request.contextPath}/upload.do" enctype="multipart/form-data" method="post">
        <input class="layui-btn" type="file" name="file1">
        <input class="layui-btn" type="submit">
    </form>
</div>

</body>
</html>

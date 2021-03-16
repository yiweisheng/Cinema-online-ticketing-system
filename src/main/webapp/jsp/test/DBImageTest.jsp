<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DBImageTest</title>
</head>
<body>
<h1>图片测试</h1>

<form method="get" id="getImg" action="${pageContext.request.contextPath }/jsp/test/DBimageTest.do">
    <img src="${img}" name="img" id="img"/>2
<%--    <img src="../../DBimages/movie/001.jpg" name="img" id="img"/>--%>
    <input type="submit" id="getImgBut" name="getImgBut" value="获取图片">

</form>

</body>
</html>.

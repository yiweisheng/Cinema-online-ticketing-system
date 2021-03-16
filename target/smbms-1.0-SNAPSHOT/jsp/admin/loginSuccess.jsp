<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录成功</title>
</head>
<body>
<h1>登录成功</h1>

<header class="publicHeader">
    <h1>影院在线售票系统</h1>
    <div class="publicHeaderR">
        <p><span style="color: #fff21b"> ${customerSession.customer_Name }</span> , 欢迎你！</p>
        <a href="${pageContext.request.contextPath }/jsp/costlogout.do">退出</a>
    </div>
</header>
</body>
</html>

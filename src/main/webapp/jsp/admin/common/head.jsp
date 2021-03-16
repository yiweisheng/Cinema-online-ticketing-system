<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>	
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>影厅在线售票系统</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/public.css" />
</head>
<body>
<!--头部-->
    <header class="publicHeader">
        <h1>影厅在线售票系统</h1>
        <div class="publicHeaderR">
            <p><span style="color: #fff21b"> ${customerSession.customer_Name }</span> , 欢迎你！</p>
            <a href="${pageContext.request.contextPath }/jsp/customerlogout.do">退出</a>
        </div>
    </header>
<!--时间-->
    <section class="publicTime">
        <span id="time">2015年1月1日 11:11  星期一</span>
        <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（IE10+）</a>
    </section>
 <!--主体内容-->
 <section class="publicMian ">
     <div class="left">
         <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
         <nav>
             <ul class="list">
                 <li ><a href="${pageContext.request.contextPath }/jsp/admin/schedule.do?method=query">时间表管理</a></li>
                 <li><a href="${pageContext.request.contextPath }/jsp/admin/movie.do?method=query">影片管理</a></li>
                 <li><a href="${pageContext.request.contextPath }/jsp/admin/customer.do?method=query">会员管理</a></li>
                 <li><a href="${pageContext.request.contextPath }/jsp/admin/order.do?method=query">订单管理</a></li>
                 <li><a href="${pageContext.request.contextPath }/jsp/admin/order.do?method=cuorderlist">用户订单查看</a></li>
                 <li><a href="${pageContext.request.contextPath }/jsp/admin/cu_customermodify.jsp">信息修改</a></li>
                 <li><a href="${pageContext.request.contextPath }/jsp/customerlogout.do">退出系统</a></li>
             </ul>
         </nav>
     </div>
     <input type="hidden" id="path" name="path" value="${pageContext.request.contextPath }"/>
     <input type="hidden" id="referer" name="referer" value="<%=request.getHeader("Referer")%>"/>
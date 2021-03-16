<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/admin/common/head.jsp" %>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/layui.css" />

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>当前登录用户订单查看页面</span>
    </div>
    <form class="layui-form" method="get" action="${pageContext.request.contextPath}/jsp/admin/order.do">
        <input name="method" value="taketicket" class="input-text" type="hidden">
        <input type="hidden" id="Order_ID" name="Order_ID" value="${order.getOrder_ID()}"/>
        <p><strong>订单ID：</strong><span>${order.getOrder_ID() }</span></p>
        <p><strong>会员ID：</strong><span>${order.getCustomer_ID() }</span></p>
        <div class="layui-form-item">
            <label class="layui-form-label">购票凭证</label>
            <div class="layui-input-block">
                <input type="text" name="Order_SecretKey" id="Order_SecretKey" lay-verify="title" autocomplete="off"
                       placeholder="请输入购票凭证"
                       class="layui-input">
            </div>
        </div>
        <input value="确认取票" type="submit" id="taketicketbutton">
    </form>
    <input type="hidden" id="path" name="path" value="${pageContext.request.contextPath }"/>
    <input type="hidden" id="referer" name="referer" value="<%=request.getHeader("Referer")%>"/>
</div>
</section>

<%@include file="/jsp/admin/common/foot.jsp" %>

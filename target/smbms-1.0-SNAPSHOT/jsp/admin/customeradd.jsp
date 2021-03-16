<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/jsp/admin/common/head.jsp"%>


<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>会员管理页面 >> 会员添加页面</span>
        </div>
        <div class="providerAdd">
            <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/jsp/admin/customer.do">
				<input type="hidden" name="method" value="add">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div>
                    <label for="Customer_Name">会员名称：</label>
                    <input type="text" name="Customer_Name" id="Customer_Name" value="">
					<!-- 放置提示信息 -->
					<font color="red"></font>
                </div>
                <div>
                    <label for="Customer_Email">会员邮箱：</label>
                    <input type="text" name="Customer_Email" id="Customer_Email" value="">
                    <font color="red"></font>
                </div>
                <div>
                    <label for="Customer_Mobile">会员电话：</label>
                    <input type="text" name="Customer_Mobile" id="Customer_Mobile" value="">
                    <font color="red"></font>
                </div>
                <div>
                    <label for="Customer_PassWord">会员密码：</label>
                    <input type="password" name="Customer_PassWord" id="Customer_PassWord" value="">
					<font color="red"></font>
                </div>
                <div>
                    <label for="rCustomer_PassWord">确认密码：</label>
                    <input type="password" name="rCustomer_PassWord" id="rCustomer_PassWord" value="">
					<font color="red"></font>
                </div>
                <div>
                    <label >会员等级：</label>
                    <!-- 列出所有的会员等级 -->
                    <input type="hidden" value="${hall_IDRole}" id="rid" />
                    <select name="Class_IDRole" id="Class_IDRole"></select>
                    <font color="red"></font>
                </div>
                <div class="providerAddBtn">
                    <input type="button" name="add" id="add" value="保存" >
					<input type="button" id="back" name="back" value="返回" >
                </div>
            </form>
        </div>
</div>
</section>
<%@include file="/jsp/admin/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/customeradd.js"></script>

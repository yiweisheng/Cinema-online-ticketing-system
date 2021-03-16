<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/jsp/admin/common/head.jsp"%>

    <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>会员页面 >> 会员修改页面</span>
        </div>
        <div class="providerAdd">
        <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/jsp/admin/customer.do">
			<input type="hidden" name="method" value="cu_modifysave">
            <div>
                <label for="Customer_Name">会员名称：</label>
                <input type="text" name="Customer_Name" id="Customer_Name">
                <!-- 放置提示信息 -->
                <font color="red"></font>
            </div>
            <div>
                <label for="Customer_Email">会员邮箱：</label>
                <input type="text" name="Customer_Email" id="Customer_Email">
                <font color="red"></font>
            </div>
            <div>
                <label for="Customer_Mobile">会员电话：</label>
                <input type="text" name="Customer_Mobile" id="Customer_Mobile">
                <font color="red"></font>
            </div>
            <div>
                <label for="Customer_OldPassword">旧密码：</label>
                <input type="password" name="Customer_OldPassword" id="Customer_OldPassword">
                <font color="red"></font>
            </div>
            <div>
                <label for="Customer_NewPassword">新密码：</label>
                <input type="password" name="Customer_NewPassword" id="Customer_NewPassword">
                <font color="red"></font>
            </div>

			 <div class="providerAddBtn">
                    <input type="button" name="save" id="save" value="保存" />
                    <input type="button" id="back" name="back" value="返回"/>
                </div>
            </form>
        </div>
    </div>
</section>
<%@include file="/jsp/admin/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/cu_customermodify.js"></script>

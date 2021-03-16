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
			<input type="hidden" name="method" value="modifysave">
			<input type="hidden" name="Customer_ID" value="${customer.getCustomer_ID()}"/>
            <div>
                <label for="Customer_Name">会员名称：</label>
                <input type="text" name="Customer_Name" id="Customer_Name" value="${customer.getCustomer_Name()}" readonly="readonly">
                <!-- 放置提示信息 -->
                <font color="red"></font>
            </div>
            <div>
                <label for="Customer_Email">会员邮箱：</label>
                <input type="text" name="Customer_Email" id="Customer_Email" value="${customer.getCustomer_Email()}">
                <font color="red"></font>
            </div>
            <div>
                <label for="Customer_Mobile">会员电话：</label>
                <input type="text" name="Customer_Mobile" id="Customer_Mobile" value="${customer.getCustomer_Mobile()}">
                <font color="red"></font>
            </div>
            <div>
                <label >会员等级：</label>
                <!-- 列出所有的会员等级 -->
                <input type="hidden" value="${Class_ID}" id="rid" />
                <select name="Class_IDRole" id="Class_IDRole"></select>
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
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/customermodify.js"></script>

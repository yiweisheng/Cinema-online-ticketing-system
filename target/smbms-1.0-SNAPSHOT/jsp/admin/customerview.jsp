<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/jsp/admin/common/head.jsp"%>

 <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>会员页面 >> 信息查看</span>
        </div>

        <div class="providerView">
            <p><strong>会员ID：</strong><span>${customer.getCustomer_ID() }</span></p>
            <p><strong>会员名：</strong><span>${customer.getCustomer_Name() }</span></p>
            <p><strong>会员邮箱：</strong><span>${customer.getCustomer_Email() }</span></p>
            <p><strong>会员电话：</strong><span>${customer.getCustomer_Mobile() }</span></p>
            <p><strong>会员等级：</strong><span>${customer.getClass_ID() }</span></p>
            <p><strong>创建时间：</strong><span>${customer.getCustomer_CreationDate().toString().substring(0,customer.getCustomer_CreationDate().toString().length()-2) }</span></p>
            <p><strong>更新时间：</strong><span>${customer.getCustomer_ModifyDate().toString().substring(0,customer.getCustomer_ModifyDate().toString().length()-2)}</span></p>
            <div class="providerAddBtn">
            	<input type="button" id="back" name="back" value="返回" >
            </div>
        </div>

    </div>
</section>
<%@include file="/jsp/admin/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/customerview.js"></script>

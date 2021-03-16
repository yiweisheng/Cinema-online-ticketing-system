<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/jsp/admin/common/head.jsp"%>
 <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>订单管理页面 >> 订单信息查看页面</span>
        </div>
        <div class="providerView">
            <p><strong>订单ID：</strong><span>${order.getOrder_ID() }</span></p>
            <p><strong>会员ID：</strong><span>${order.getCustomer_ID() }</span></p>
            <p><strong>时间表ID：</strong><span>${order.getSchedule_ID() }</span></p>
            <p><strong>座位ID：</strong><span>${order.getSeat_ID() }</span></p>
            <p><strong>订单是否可用：</strong>
            	<span>
            		<c:if test="${order.getOrder_IsActive() == 0 }">不可用</c:if>
					<c:if test="${order.getOrder_IsActive() == 1 }">可用</c:if>
				</span>
			</p>
            <p><strong>会员打折之后的价格：</strong><span>${order.getOrder_AdjustedPrice() }</span></p>
            <p><strong>购买时间：</strong><span>${order.getOrder_BuyDate().toString().substring(0,order.getOrder_BuyDate().toString().length()-2)}</span></p>
			<div class="providerAddBtn">
            	<input type="button" id="back" name="back" value="返回" >
            </div>
        </div>
    </div>
</section>
<%@include file="/jsp/admin/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/orderview.js"></script>
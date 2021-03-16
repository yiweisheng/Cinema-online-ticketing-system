<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/jsp/admin/common/head.jsp"%>
    <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>订单表页面 >> 订单表修改页面</span>
        </div>
        <div class="providerAdd">
        <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/jsp/admin/order.do">
			<input type="hidden" name="method" value="modifysave">
			<input type="hidden" name="o_id" value="${order.getOrder_ID() }"/>
            <div>
                <label for="Order_ID">订单ID：</label>
                <input type="text" name="Order_ID" id="Order_ID" value="${order.getOrder_ID() }" readonly="readonly">
                <font color="red"></font>
            </div>
            <div>
                <label for="Customer_ID">会员ID：</label>
                <input type="text" name="Customer_ID" id="Customer_ID" value="${order.getCustomer_ID() }" readonly="readonly">
                <font color="red"></font>
            </div>
            <div>
                <label for="Schedule_ID">时间表ID：</label>
                <input type="text" name="Schedule_ID" id="Schedule_ID" value="${order.getSchedule_ID() }" readonly="readonly">
                <font color="red"></font>
            </div>
            <div>
                <label for="Seat_ID">座位ID：</label>
                <input type="text" name="Seat_ID" id="Seat_ID" value="${order.getSeat_ID() }">
                <font color="red"></font>
            </div>
            <div>
                <label >订单是否可用：</label>
                <!-- 列出所有的角色分类 -->
                <select name="Order_IsActive" id="Order_IsActive">
                    <c:if test="${orderIsActiveList != null }">
                        <option value="0">--全部--</option>
                        <c:forEach var="orderAc" items="${orderIsActiveList}">
                            <option <c:if test="${orderAc.getIsActive() == Order_IsActive }">selected="selected"</c:if>
                                    value="${orderAc.getIsActive()}">${orderAc.getName()}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
            <div>
                <label for="Order_AdjustedPrice">会员打折之后的价格：</label>
                <input type="text" name="Order_AdjustedPrice" id="Order_AdjustedPrice" value="${order.getOrder_AdjustedPrice() }">
                <font color="red"></font>
            </div>
            <div>
                <label for="Order_BuyDate">购买时间：</label>
                <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"
                       id="Order_BuyDate" name="Order_BuyDate"
                       readonly="readonly" onclick="WdatePicker();"value="${order.getOrder_BuyDate().toString().substring(0,order.getOrder_BuyDate().toString().length()-2)}" readonly="readonly"/>
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
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/ordermodify.js"></script>

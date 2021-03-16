<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/admin/common/head.jsp" %>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>当前登录用户订单查看页面</span>
    </div>
    <div class="search">
        <form method="get" action="${pageContext.request.contextPath }/jsp/admin/order.do">
            <input name="method" value="cuorderlist" class="input-text" type="hidden">
            <span>订单是否可用：</span>
            <select name="Order_IsActive" id="Order_IsActive">
                <c:if test="${orderIsActiveList != null }">
                    <option value="-1">--全部--</option>
                    <c:forEach var="orderAc" items="${orderIsActiveList}">
                        <option
                                <c:if test="${orderAc.getIsActive() == Order_IsActive }">selected="selected"</c:if>
                                value="${orderAc.getIsActive()}">${orderAc.getName()}</option>
                    </c:forEach>
                </c:if>
            </select>
            <input type="hidden" name="pageIndex" value="1"/>
            <input value="查 询" type="submit" id="searchbutton">
        </form>
    </div>
    <!--用户-->
    <table class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="10%">订单ID</th>
            <th width="10%">会员ID</th>
            <th width="10%">时间表ID</th>
            <th width="10%">座位ID</th>
            <th width="10%">订单是否可用</th>
            <th width="10%">购票凭证码</th>
            <th width="10%">会员打折之后的价格</th>
            <th width="20%">购买时间</th>
            <th width="10%">取票</th>
        </tr>
        <c:forEach var="order" items="${orderList }" varStatus="status">
            <tr>
                <td>
                    <span>${order.getOrder_ID() }</span>
                </td>
                <td>
                    <span>${order.getCustomer_ID() }</span>
                </td>
                <td>
                    <span>${order.getSchedule_ID() }</span>
                </td>
                <td>
                    <span>${order.getSeat_ID() }</span>
                </td>
                <td>
                    <span>
                        <c:if test="${order.getOrder_IsActive()==0}">不可用</c:if>
                        <c:if test="${order.getOrder_IsActive()==1}">可用</c:if>
                    </span>
                </td>
                <td>
                    <span>${order.getOrder_SecretKey()}</span>
                </td>
                <td>
                    <span>${order.getOrder_AdjustedPrice()}</span>
                </td>
                <td>
                    <span>${order.getOrder_BuyDate().toString().substring(0,order.getOrder_BuyDate().toString().length()-2)}</span>
                </td>
                <td>
                    <span><a class="viewUser" href="javascript:;"
                             Order_ID=${order.getOrder_ID()} Customer_ID=${order.getCustomer_ID() }>
                        <img src="${pageContext.request.contextPath }/images/read.png" alt="取票" title="取票"/></a></span>
                </td>
            </tr>
        </c:forEach>
    </table>
    <input type="hidden" id="totalPageCount" value="${totalPageCount}"/>
    <c:import url="rollpage.jsp">
        <c:param name="totalCount" value="${totalCount}"/>
        <c:param name="currentPageNo" value="${currentPageNo}"/>
        <c:param name="totalPageCount" value="${totalPageCount}"/>
    </c:import>
</div>
</section>
<%@include file="/jsp/admin/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/customerorderlist.js"></script>


<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery.seat-charts.css"/>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/mystyle.css"/>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.seat-charts.min.js"></script>

<div class="wrapper">
    <div class="container">
        <div>荧幕</div>
        <div class="info">${error}</div>
        <div id="seat-map">
            <div class="front-indicator">座位</div>
        </div>
        <div class="booking-details">
            <h3>已选中的座位 (<span id="counter">0</span>):</h3>
            <ul id="selected-seats" name="selected-seats">
            </ul>
            <p>总价: <b>$<span id="total">0</span></b></p>
            <p>
                <button class="checkout-button" >
                    结算
                    <a class="viewUser" href="javascript:;" Seat_ID=1></a>
                </button>
                <input type="button" id="back" name="back" value="返回" >
            </p>
            <div id="legend"></div>
        </div>

        <div class="booking-details" id="Information">
            <ul id="hall_Information" name="hall_Information">
            </ul>
        </div>

    </div>
</div>

<input type="hidden" id="hall_ID" name="hall_ID" value="${pageContext.request.getAttribute("hall_ID")}"/>
<input type="hidden" id="schedule_ID" name="schedule_ID" value="${pageContext.request.getAttribute("schedule_ID")}"/>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeProv">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain" >
            <p>你确定要删除该影片吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<input type="hidden" id="path" name="path" value="${pageContext.request.contextPath}"/>
<input type="hidden" id="referer" name="referer" value="<%=request.getHeader("Referer")%>"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/orderaddseat.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/orderadd.js"></script>





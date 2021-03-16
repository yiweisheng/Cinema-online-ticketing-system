<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/admin/common/head.jsp"%>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>时间表页面 >> 信息查看</span>
    </div>
    <div class="providerView">
        <p><strong>时间表ID：</strong><span>${schedule.getSchedule_ID() }</span></p>
        <p><strong>电影ID：</strong><span>${schedule.getMovie_ID() }</span></p>
        <p><strong>电影名：</strong><span>${schedule.getMovie_NameByMovie_ID() }</span></p>
        <p><strong>影厅ID：</strong><span>${schedule.getHall_ID() }</span></p>
        <p><strong>时间表上票价：</strong><span>${schedule.getSchedule_price() }</span></p>
        <p><strong>电影放映时间：</strong><span>${schedule.getSchedule_BeginDateTime().toString().substring(0,schedule.getSchedule_BeginDateTime().toString().length()-2)}</span></p>
        <p><strong>电影封面路径：</strong><span>../${schedule.getMovie_ImageSrcByMovie_ID() }</span></p>
        <img class="movieImg" src="../${schedule.getMovie_ImageSrcByMovie_ID() }">
        <a class="addSchedule" style="padding: 10px;margin: 10px;width:50px;height:30px;float: right;" href="javascript:;" Hall_ID=${schedule.getHall_ID() } Schedule_ID=${schedule.getSchedule_ID() }>订票</a>
        <div class="providerAddBtn">
            <input type="button" id="back" name="back" value="返回">
        </div>
    </div>
</div>
</section>
<%@include file="/jsp/admin/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/scheduleview.js"></script>

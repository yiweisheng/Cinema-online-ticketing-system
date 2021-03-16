<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/jsp/admin/common/head.jsp"%>
    <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>时间表页面 >> 时间表修改页面</span>
        </div>
        <div class="providerAdd">
        <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/jsp/admin/schedule.do">
			<input type="hidden" name="method" value="modifysave">
			<input type="hidden" name="s_id" value="${schedule.getSchedule_ID()}"/>
            <div>
                <label for="Schedule_ID">时间表ID：</label>
                <input type="text" name="Schedule_ID" id="Schedule_ID" value="${schedule.getSchedule_ID()}" readonly="readonly">
                <font color="red"></font>
            </div>
            <div>
                <label for="Movie_ID">电影ID：</label>
                <input type="text" name="Movie_ID" id="Movie_ID" value="${schedule.getMovie_ID()}" readonly="readonly">
                <font color="red"></font>
            </div>
            <div>
                <label for="Movie_Name">电影名：</label>
                <input type="text" name="Movie_Name" id="Movie_Name" value="${schedule.getMovie_NameByMovie_ID()}" readonly="readonly">
                <font color="red"></font>
            </div>
            <div>
                <label >影厅选择：</label>
                <!-- 列出所有的角色分类 -->
                <input type="hidden" value="${hall_IDRole}" id="rid" />
                <select name="hall_IDRole" id="hall_IDRole"></select>
                <font color="red"></font>
            </div>
            <div>
                <label for="Schedule_price">时间表上票价：</label>
                <input type="text" name="Schedule_price" id="Schedule_price" value="${schedule.getSchedule_price() }">
                <font color="red"></font>
            </div>
<%--            <div>--%>
<%--                <label for="Schedule_BeginDateTime">电影放映时间：</label>--%>
<%--                <input type="text" name="Schedule_BeginDateTime" id="Schedule_BeginDateTime" value="${schedule.getSchedule_BeginDateTime().toString().substring(0,schedule.getSchedule_BeginDateTime().toString().length()-2)}">--%>
<%--                <font color="red"></font>--%>
<%--            </div>--%>
            <div>
                <label for="Schedule_BeginDateTime">电影放映时间：</label>
                <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"
                       id="Schedule_BeginDateTime" name="Schedule_BeginDateTime"
                       readonly="readonly" onclick="WdatePicker();"
                       value="${schedule.getSchedule_BeginDateTime().toString().substring(0,schedule.getSchedule_BeginDateTime().toString().length()-2)}"
                       readonly="readonly"/>
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
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/schedulemodify.js"></script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/jsp/admin/common/head.jsp"%>

<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>时间表管理页面 >> 排片添加页面</span>
        </div>
        <div class="providerAdd">
            <form id="scheduleForm" name="scheduleForm" method="post" action="${pageContext.request.contextPath }/jsp/admin/schedule.do">
				<input type="hidden" name="method" value="add">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div>
                    <label for="Schedule_ID">时间表ID：</label>
                    <input name="Schedule_ID" id="Schedule_ID" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
					<!-- 放置提示信息 -->
					<font color="red"></font>
                </div>
                <div>
                    <label for="Movie_ID">电影ID：</label>
                    <input type="text" name="Movie_ID" id="Movie_ID" value="">
					<font color="red"></font>
                </div>

                <div>
                    <label >影厅选择：</label>
                    <!-- 列出所有的影厅 -->
                    <input type="hidden" value="${hall_IDRole}" id="rid" />
                    <select name="hall_IDRole" id="hall_IDRole"></select>
                    <font color="red"></font>
                </div>

                <div>
                    <label for="Schedule_price">时间表上票价：</label>
                    <input type=text name="Schedule_price" id="Schedule_price" t_value="" o_value="" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}">
					<font color="red"></font>
                </div>
                <div>
                    <label for="Schedule_BeginDateTime">电影放映时间：</label>
                    <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"
                           id="Schedule_BeginDateTime" name="Schedule_BeginDateTime"
                           readonly="readonly" onclick="WdatePicker();"pattern="yyyy-MM-dd HH:mm:ss"/>
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
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/scheduleadd.js"></script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/admin/common/head.jsp"%>
        <div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>时间表管理页面</span>
            </div>
            <div class="search">
           		<form method="get" action="${pageContext.request.contextPath }/jsp/admin/schedule.do">
					<input name="method" value="query" class="input-text" type="hidden">

					<span>时间表ID：</span>
					<input name="Schedule_ID" class="input-text"	type="text" value="${Schedule_ID }">
					<span>电影ID：</span>
					<input name="Movie_ID" class="input-text"	type="text" value="${Movie_ID }">
					<span>电影名：</span>
					<input name="Movie_Name" class="input-text"	type="text" value="${Movie_Name }">

					<span>影厅选择：</span>
					 <select name="Hall_ID">
						<c:if test="${hallList != null }">
						   <option value="0">--请选择--</option>
						   <c:forEach var="hall" items="${hallList}">
						   		<option <c:if test="${hall.getHall_ID() == Hall_ID }">selected="selected"</c:if>
						   		value="${hall.getHall_ID()}">${hall.getHall_Name()}</option>
						   </c:forEach>
						</c:if>
	        		</select>

					<br>
					<span>订单是否可用：</span>
					<select name="Schedule_IsShow" id="Schedule_IsShow">
						<c:if test="${scheduleIsShowList != null }">
							<option value="-1">--全部--</option>
							<c:forEach var="scheduleIS" items="${scheduleIsShowList}">
								<option <c:if test="${scheduleIS.getIsShow() == schedule_IsShow }">selected="selected"</c:if>
										value="${scheduleIS.getIsShow()}">${scheduleIS.getName()}</option>
							</c:forEach>
						</c:if>
					</select>
					 
					 <input type="hidden" name="pageIndex" value="1"/>
					 <input	value="查 询" type="submit" id="searchbutton">
					 <a href="${pageContext.request.contextPath}/jsp/admin/scheduleadd.jsp" >添加时间表</a>
				</form>
            </div>
            <!--用户-->
            <table class="providerTable" cellpadding="0" cellspacing="0">
                <tr class="firstTr">
                    <th width="10%">时间表ID</th>
                    <th width="10%">电影ID</th>
					<th width="10%">电影名</th>
					<th width="10%">影厅ID</th>
                    <th width="10%">时间表上票价</th>
                    <th width="20%">电影放映时间</th>
                    <th width="30%">操作</th>
                </tr>
                   <c:forEach var="schedule" items="${scheduleList }" varStatus="status">
					<tr>
						<td>
							<span>${schedule.getSchedule_ID() }</span>
						</td>
						<td>
							<span>${schedule.getMovie_ID() }</span>
						</td>
						<td>
							<span>${schedule.getMovie_NameByMovie_ID() }</span>
						</td>
						<td>
							<span>${schedule.getHall_ID() }</span>
						</td>
						<td>
							<span>${schedule.getSchedule_price()}</span>
						</td>
						<td>
							<span>${schedule.getSchedule_BeginDateTime().toString().substring(0,schedule.getSchedule_BeginDateTime().toString().length()-2)}</span>
						</td>
						<td>
						<span><a class="viewUser" href="javascript:;" Schedule_ID=${schedule.getSchedule_ID()} Movie_Name=${schedule.getMovie_NameByMovie_ID() }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
						<span><a class="modifyUser" href="javascript:;" Schedule_ID=${schedule.getSchedule_ID() } Movie_Name=${schedule.getMovie_NameByMovie_ID() }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
						<span><a class="deleteUser" href="javascript:;" Schedule_ID=${schedule.getSchedule_ID() } Movie_Name=${schedule.getMovie_NameByMovie_ID() }><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
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

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除时间表记录嘛？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/jsp/admin/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/schedulelist.js"></script>

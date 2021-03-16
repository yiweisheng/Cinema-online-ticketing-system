<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>
<style type="text/css">
	.search input[type='text']{
		width: 150px;
		height: 30px;
		outline: none;
		padding-left: 10px;
		border: 1px solid #8ab2d5;
		border-radius: 4px;
	}
</style>
<div class="right">
	<div class="location">
		<strong>你现在所在的位置是:</strong>
		<span>影片管理页面</span>
	</div>
	<div class="search">
		<form method="get" action="${pageContext.request.contextPath }/jsp/movie.do">
			<input name="method" value="query" type="hidden">
			<span>影片编码：</span>
			<input name="Movie_ID" type="text" value="${Movie_ID}">

			<span>影片名称：</span>
			<input name="Movie_Name" type="text" value="${Movie_Name}">

			<span>影片产地：</span>
			<input name="Movie_Region" type="text" value="${Movie_Region}">

			<span>影片类型：</span>
			<input name="Movie_Type" type="text" value="${Movie_Type}">

            <input type="hidden" name="pageIndex" value="1"/>
			<input value="查 询" type="submit" id="searchbutton">
<%--			<a href="${pageContext.request.contextPath }/jsp/movieadd.jsp">添加影片</a>--%>
		</form>
	</div>
	<!--电影操作表格-->
	<table class="providerTable" cellpadding="0" cellspacing="0">
		<tr class="firstTr">
			<th width="20%">影片ID</th>
			<th width="20%">影片名</th>
			<th width="10%">影片产地</th>
			<th width="20%">影片类型</th>
			<th width="20%">时长</th>
			<th width="10%">操作</th>
		</tr>
		<c:forEach var="movie" items="${movieList}" varStatus="status">
			<tr>
				<td>
					<span>${movie.getMovie_ID()}</span>
				</td>
				<td>
					<span>${movie.getMovie_Name() }</span>
				</td>
				<td>
					<span>${movie.getMovie_Region()}</span>
				</td>
				<td>
					<span>${movie.getMovie_Type()}</span>
				</td>
				<td>
					<span>
						<span>${movie.getMovie_Duration().toString()}</span>
					</span>
				</td>
				<td>
					<span><a class="viewProvider" href="javascript:;" movieid=${movie.getMovie_ID()} moviename=${movie.getMovie_Name()  }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
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

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/movielist.js"></script>

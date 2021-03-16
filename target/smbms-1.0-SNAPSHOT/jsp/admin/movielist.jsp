<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/admin/common/head.jsp" %>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>影片管理页面</span>
    </div>
    <div class="search">
        <form method="get" action="${pageContext.request.contextPath }/jsp/admin/movie.do">
            <input name="method" value="query" type="hidden">
            <span>影片编码：</span>
            <input name="Movie_ID" type="text" value="${Movie_ID}">

            <span>影片名称：</span>
            <input name="Movie_Name" type="text" value="${Movie_Name}">

            <span>影片产地：</span>
            <input name="Movie_Region" type="text" value="${Movie_Region}">
            <br>
            <span>影片类型：</span>
            <input name="Movie_Type" type="text" value="${Movie_Type}">

            <input type="hidden" name="pageIndex" value="1"/>
            <input value="查 询" type="submit" id="searchbutton">
            <a href="${pageContext.request.contextPath }/jsp/admin/movieadd.jsp">添加影片</a>
        </form>
    </div>
    <!--电影操作表格-->
    <table class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="10%">影片ID</th>
            <th width="20%">影片名</th>
            <th width="10%">影片产地</th>
            <th width="10%">影片类型</th>
            <th width="10%">封面</th>
            <th width="10%">时长</th>
            <th width="30%">操作</th>
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
                    <span>../${movie.getMovie_ImageSrc()}</span>
                </td>
                <td>
					<span>
						<span>${movie.getMovie_Duration().toString()}</span>
					</span>
                </td>
                <td>
                    <span><a class="viewProvider" href="javascript:;"
                             movieid=${movie.getMovie_ID()} moviename=${movie.getMovie_Name()  }><img
                            src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
                    <span><a class="modifyProvider" href="javascript:;"
                             movieid=${movie.getMovie_ID()} moviename=${movie.getMovie_Name()  }><img
                            src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
                    <span><a class="deleteProvider" href="javascript:;"
                             movieid=${movie.getMovie_ID()} moviename=${movie.getMovie_Name() }><img
                            src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
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
        <div class="removeMain">
            <p>你确定要删除该影片吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/jsp/admin/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/movielist.js"></script>

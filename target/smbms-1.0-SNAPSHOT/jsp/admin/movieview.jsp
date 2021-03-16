<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/jsp/admin/common/head.jsp"%>
 <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>影片页面 >> 信息查看</span>
        </div>

        <div class="providerView">
            <p><strong>影片ID：</strong><span>${movie.getMovie_ID() }</span></p>
            <p><strong>影片名：</strong><span>${movie.getMovie_Name() }</span></p>
            <p><strong>影片产地：</strong><span>${movie.getMovie_Region() }</span></p>
            <p><strong>影片类型：</strong><span>${movie.getMovie_Type() }</span></p>
            <p><strong>主要演员：</strong><span>${movie.getMovie_MainActor() }</span></p>
            <p><strong>导演：</strong><span>${movie.getMovie_Director()}</span></p>
            <p><strong>时长：</strong><span>${movie.getMovie_Duration().toString()}</span></p>
            <p><strong>概述：</strong><span>${movie.getMovie_Description()}</span></p>
            <p><strong>封面路径：</strong><span>../${movie.getMovie_ImageSrc()}</span></p>
            <img class="movieImg" src="../${movie.getMovie_ImageSrc()}">

            <div class="providerAddBtn">
            	<input type="button" id="back" name="back" value="返回" >
            </div>
        </div>

    </div>
</section>
<%@include file="/jsp/admin/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/movieview.js"></script>

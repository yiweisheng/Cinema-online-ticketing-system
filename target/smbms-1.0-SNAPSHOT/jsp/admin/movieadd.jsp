<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/admin/common/head.jsp"%>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>影片管理页面 >> 影片添加页面</span>
    </div>
    <div class="providerAdd">
        <form id="providerForm" name="providerForm" method="post" enctype="multipart/form-data"
              action="${pageContext.request.contextPath }/jsp/admin/upload.do">
            <input type="hidden" name="method" value="add">
            <!--div的class 为error是验证错误，ok是验证成功-->
<%--            <div class="">--%>
<%--                <label for="Movie_ID">影片ID：</label>--%>
<%--                <input type="text" name="Movie_ID" id="Movie_ID" value="">--%>
<%--                <!-- 放置提示信息 -->--%>
<%--                <font color="red"></font>--%>
<%--            </div>--%>
            <div>
                <label for="Movie_Name">影片名：</label>
                <input type="text" name="Movie_Name" id="Movie_Name" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="Movie_Region">影片产地：</label>
                <input type="text" name="Movie_Region" id="Movie_Region" value="">
                <font color="red"></font>

            </div>
            <div>
                <label for="Movie_Type">影片类型：</label>
                <input type="text" name="Movie_Type" id="Movie_Type" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="Movie_MainActor">主要演员：</label>
                <input type="text" name="Movie_MainActor" id="Movie_MainActor" value="">
            </div>
            <div>
                <label for="Movie_Director">导演：</label>
                <input type="text" name="Movie_Director" id="Movie_Director" value="">
            </div>
            <div>
                <label for="Movie_Duration">时长：</label>
                <input type="text" name="Movie_Duration" id="Movie_Duration" value="">
            </div>
            <div>
                <label for="Movie_Description">描述：</label>
                <input type="text" name="Movie_Description" id="Movie_Description" value="">
            </div>
<%--            <div>--%>
<%--                <label for="Movie_ImageSrc">封面路径：</label>--%>
<%--                <input type="text" name="Movie_ImageSrc" id="Movie_ImageSrc" value="">--%>
<%--            </div>--%>
            <div>
                <label for="file1">封面图片上传：</label>
                <input type="file" name="file1" id="file1">
            </div>

            <div class="providerAddBtn">
                <input type="button" name="add" id="add" value="保存">
                <input type="button" id="back" name="back" value="返回">
            </div>
        </form>
    </div>


</div>
</section>
<%@include file="/jsp/admin/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/movieadd.js"></script>

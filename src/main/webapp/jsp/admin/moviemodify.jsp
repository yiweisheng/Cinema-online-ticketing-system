<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/jsp/admin/common/head.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/laydate/laydate.js"></script>

  <div class="right">
      <div class="location">
          <strong>你现在所在的位置是:</strong>
          <span>影片管理页面 >> 影片修改页</span>
      </div>

      <div class="providerAdd">
          <form id="providerForm" name="providerForm" method="post" action="${pageContext.request.contextPath }/jsp/admin/movie.do">
              <input name="method" value="modifysave" type="hidden">
              <!--div的class 为error是验证错误，ok是验证成功-->
              <div class="">
                  <label for="Movie_ID">影片ID：</label>
                  <input type="text" name="Movie_ID" id="Movie_ID" value="${movie.getMovie_ID() }" readonly="readonly">
              </div>
              <div>
                  <label for="Movie_Name">影片名：</label>
                  <input type="text" name="Movie_Name" id="Movie_Name" value="${movie.getMovie_Name() }">
                  <font color="red"></font>
              </div>

              <div>
                  <label for="Movie_Region">影片产地：</label>
                  <input type="text" name="Movie_Region" id="Movie_Region" value="${movie.getMovie_Region() }">
                  <font color="red"></font>
              </div>

              <div>
                  <label for="Movie_Type">影片类型：</label>
                  <input type="text" name="Movie_Type" id="Movie_Type" value="${movie.getMovie_Type() }">
                  <font color="red"></font>
              </div>

              <div>
                  <label for="Movie_MainActor">主要演员：</label>
                  <input type="text" name="Movie_MainActor" id="Movie_MainActor" value="${movie.getMovie_MainActor() }">
              </div>

              <div>
                  <label for="Movie_Director">导演：</label>
                  <input type="text" name="Movie_Director" id="Movie_Director" value="${movie.getMovie_Director() }">
              </div>

<%--              <div>--%>
<%--                  <label for="Movie_Duration">时长：</label>--%>
<%--                  <input type="text" name="Movie_Duration" id="Movie_Duration" value="${movie.getMovie_Duration().toString() }">--%>
<%--              </div>--%>

              <div class="layui-inline">
                  <label for="Movie_Duration">时长：</label>
<%--                  <label class="layui-form-label">时间选择器</label>--%>
<%--                  <div class="layui-input-inline">--%>
                      <input type="text" class="layui-input" name="Movie_Duration" id="Movie_Duration"
                             value="${movie.getMovie_Duration().toString() }" placeholder="HH:mm:ss">
<%--                  </div>--%>
              </div>

              <div>
                  <label for="Movie_Description">概述：</label>
                  <input type="text" name="Movie_Description" id="Movie_Description" value="${movie.getMovie_Description() }">
              </div>
              <div>
                  <label for="Movie_ImageSrc">封面路径：</label>
                  <input type="text" name="Movie_ImageSrc" id="Movie_ImageSrc" value="${movie.getMovie_ImageSrc() }">
              </div>

              <div class="providerAddBtn">
                  <input type="button" name="save" id="save" value="保存">
                  <input type="button" id="back" name="back" value="返回" >
              </div>
          </form>
      </div>

  </div>

<script>
    //时间选择器
    laydate.render({
        elem: '#Movie_Duration'
        ,type: 'time'
    });
</script>

</section>
<%@include file="/jsp/admin/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/moviemodify.js"></script>

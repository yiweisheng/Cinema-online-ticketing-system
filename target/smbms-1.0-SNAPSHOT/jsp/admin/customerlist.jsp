<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/admin/common/head.jsp"%>

        <div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>会员管理页面</span>
            </div>
            <div class="search">
           		<form method="get" action="${pageContext.request.contextPath }/jsp/admin/customer.do">
					<input name="method" value="query" class="input-text" type="hidden">
					 <span>会员ID：</span>
					 <input name="Customer_ID" class="input-text"	type="text" value="${Customer_ID }">
					<span>会员名：</span>
					<input name="Customer_Name" class="input-text"	type="text" value="${Customer_Name }">

					<span>会员等级：</span>
					 <select name="Class_IDRole">
						<c:if test="${customerClassList != null }">
						   <option value="0">--全部--</option>
						   <c:forEach var="customerClass" items="${customerClassList}">
						   		<option <c:if test="${customerClass.getClass_ID() == Class_IDRole }">selected="selected"</c:if>
						   		value="${customerClass.getClass_ID()}">${customerClass.getClass_Name()}</option>
						   </c:forEach>
						</c:if>
	        		</select>
					 
					 <input type="hidden" name="pageIndex" value="1"/>
					 <input	value="查 询" type="submit" id="searchbutton">
					 <a href="${pageContext.request.contextPath}/jsp/admin/customeradd.jsp" >添加会员</a>
				</form>
            </div>
            <!--用户-->
            <table class="providerTable" cellpadding="0" cellspacing="0">
                <tr class="firstTr">
                    <th width="10%">会员ID</th>
                    <th width="10%">会员名</th>
                    <th width="10%">会员邮箱</th>
                    <th width="10%">会员电话</th>
                    <th width="10%">会员等级</th>
					<th width="20%">创建时间</th>
					<th width="30%">操作</th>
                </tr>
                   <c:forEach var="customer" items="${customerList }" varStatus="status">
					<tr>
						<td>
						<span>${customer.getCustomer_ID() }</span>
						</td>
						<td>
						<span>${customer.getCustomer_Name() }</span>
						</td>
						<td>
							<span>${customer.getCustomer_Email() }</span>
						</td>
						<td>
							<span>${customer.getCustomer_Mobile()}</span>
						</td>
						<td>
						<span>${customer.getClass_ID()}</span>
						</td>
						<td>
							<span>${customer.getCustomer_CreationDate().toString().substring(0,customer.getCustomer_CreationDate().toString().length()-2)}</span>
						</td>
						<td>
						<span><a class="viewUser" href="javascript:;" customer_ID=${customer.getCustomer_ID() } customer_Name=${customer.getCustomer_Name() }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
						<span><a class="modifyUser" href="javascript:;" customer_ID=${customer.getCustomer_ID() } customer_Name=${customer.getCustomer_Name() }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
						<span><a class="deleteUser" href="javascript:;" customer_ID=${customer.getCustomer_ID() } customer_Name=${customer.getCustomer_Name() }><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
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
            <p>你确定要删除该用户吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/jsp/admin/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsp/admin/js/customerlist.js"></script>

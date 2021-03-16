<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/sign-up-login.css">
    <link rel="stylesheet" type="${pageContext.request.contextPath }/text/css" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/inputEffect.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/tooltips.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/spop.min.css"/>

    <script src="${pageContext.request.contextPath }/js/jquery-1.8.3.min.js"></script>
    <script src="${pageContext.request.contextPath }/js/snow.js"></script>
    <script src="${pageContext.request.contextPath }/js/jquery.pure.tooltips.js"></script>
    <script src="${pageContext.request.contextPath }/js/spop.min.js"></script>
    <script src="${pageContext.request.contextPath }/js/login.js"></script>
    <script src="${pageContext.request.contextPath }/js/registered.js"></script>

</head>
<body>
<!-- 雪花背景 -->
<div class="snow-container"></div>
<!-- 登录控件 -->
<div id="login">
    <input id="tab-1" type="radio" name="tab" class="sign-in hidden" checked/>
    <input id="tab-2" type="radio" name="tab" class="sign-up hidden"/>
    <input id="tab-3" type="radio" name="tab" class="sign-out hidden"/>
    <div class="wrapper">
        <!-- =====================登录页面=========================== -->
        <div class="login sign-in-htm">
            <form class="container offset1 loginform" action="${pageContext.request.contextPath }/customerlogin.do"
                  name="actionForm" id="actionForm" method="post">
                <!-- 猫头鹰控件 -->
                <div id="owl-login" class="login-owl">
                    <div class="hand"></div>
                    <div class="hand hand-r"></div>
                    <div class="arms">
                        <div class="arm"></div>
                        <div class="arm arm-r"></div>
                    </div>
                </div>
                <div class="pad input-container">
                    <div class="info">${error}</div>
                    <section class="content">
								<span class="input input--hideo">
									<input class="input__field input__field--hideo" type="text"
                                           id="lCustomer_Name" name="lCustomer_Name" autocomplete="off"
                                           placeholder="请输入用户名"
                                           tabindex="1" maxlength="15"/>
									<label class="input__label input__label--hideo" for="lCustomer_Name">
										<i class="fa fa-fw fa-user icon icon--hideo"></i>
										<span class="input__label-content input__label-content--hideo"></span>
									</label>
								</span>
                        <span class="input input--hideo">
									<input class="input__field input__field--hideo" type="password"
                                           id="login-password" name="login-password"
                                           placeholder="请输入密码"
                                           tabindex="2" maxlength="15"/>
									<label class="input__label input__label--hideo" for="login-password">
										<i class="fa fa-fw fa-lock icon icon--hideo"></i>
										<span class="input__label-content input__label-content--hideo"></span>
									</label>
								</span>
                    </section>
                </div>
                <div class="form-actions">
                    <a tabindex="4" class="btn pull-left btn-link text-muted" onClick="goto_forget()">忘记密码?</a>
                    <a tabindex="5" class="btn btn-link text-muted" onClick="goto_register()">注册</a>
                    <input class="btn btn-primary" type="submit" tabindex="3" value="登录" style="color:white;"/>
                </div>
            </form>
        </div>
        <!-- ===============================忘记密码页面============================= -->
        <div class="login sign-out-htm">
            <form method="post" class="container offset1 loginform" action="${pageContext.request.contextPath }/resetpassword.do">
                <input type="hidden" name="method" value="resetpassword">
                <!-- 猫头鹰控件 -->
                <div id="owl-login" class="forget-owl">
                    <div class="hand"></div>
                    <div class="hand hand-r"></div>
                    <div class="arms">
                        <div class="arm"></div>
                        <div class="arm arm-r"></div>
                    </div>
                </div>
                <div class="pad input-container">
                    <div class="info">${error}</div>
                    <section class="content">
                        <span class="input input--hideo">
                            <input class="input__field input__field--hideo" type="text" id="forget-username" name="forget-username"
                                   autocomplete="off"
                                   placeholder="请输入用户名"/>
                            <label class="input__label input__label--hideo" for="forget-username">
                                <i class="fa fa-fw fa-user icon icon--hideo"></i>
                                <span class="input__label-content input__label-content--hideo"></span>
                            </label>
                        </span>
                        <span class="input input--hideo">
                            <input class="input__field input__field--hideo" type="text" id="forget-phone" name="forget-phone"
                                   autocomplete="off" placeholder="请输入注册手机号"/>
                            <label class="input__label input__label--hideo" for="forget-phone">
                                <i class="fa fa-fw fa-wifi icon icon--hideo"></i>
                                <span class="input__label-content input__label-content--hideo"></span>
                            </label>
                        </span>
                    </section>
                </div>
                <div class="form-actions">
                    <a class="btn pull-left btn-link text-muted" onClick="goto_login()">返回登录</a>
                    <input class="btn btn-primary" type="submit" value="重置密码" style="color:white;"/>
                </div>
            </form>
        </div>
        <!-- ===============================注册页面================================ -->
        <div class="login sign-up-htm">
            <form method="post" class="container offset1 loginform"
                  action="${pageContext.request.contextPath }/customerregistered.do">
                <input type="hidden" name="method" value="registered">
                <input type="hidden" id="path" name="path" value="${pageContext.request.contextPath }"/>
                <input type="hidden" name="rClass_IDRole" value="1">
                <!-- 猫头鹰控件 -->
                <div id="owl-login" class="register-owl">
                    <div class="hand"></div>
                    <div class="hand hand-r"></div>
                    <div class="arms">
                        <div class="arm"></div>
                        <div class="arm arm-r"></div>
                    </div>
                </div>
                <div class="pad input-container">
                    <section class="content">
                        <span class="input input--hideo">
                            <input class="input__field input__field--hideo" type="text"
                                   id="rCustomer_Name" name="rCustomer_Name" autocomplete="off"
                                   placeholder="请输入用户名" maxlength="15"/>
<%--                            <label class="input__label input__label--hideo" for="rCustomer_Name">--%>
<%--                                <i class="fa fa-fw fa-user icon icon--hideo"></i>--%>
<%--                                <span class="input__label-content input__label-content--hideo"></span>--%>
<%--                            </label>--%>
                            <!-- 放置提示信息 -->
                            <font color="red"></font>
                        </span>

                        <span class="input input--hideo">
                            <input class="input__field input__field--hideo" type="text"
                                   id="rCustomer_Email" name="rCustomer_Email" placeholder="请输入邮箱"
                                   maxlength="15"/>
<%--                            <label class="input__label input__label--hideo" for="rCustomer_Email">--%>
<%--                                <i class="fa fa-fw fa-lock icon icon--hideo"></i>--%>
<%--                                <span class="input__label-content input__label-content--hideo"></span>--%>
<%--                            </label>--%>
                            <!-- 放置提示信息 -->
                            <font color="red"></font>
                        </span>

                        <span class="input input--hideo">
                            <input class="input__field input__field--hideo" type="text"
                                   id="rCustomer_Mobile" name="rCustomer_Mobile" placeholder="请输入电话号码"
                                   maxlength="15"/>
                            <!-- 放置提示信息 -->
                            <font color="red"></font>
<%--                            <label class="input__label input__label--hideo" for="rCustomer_Mobile">--%>
<%--                                <i class="fa fa-fw fa-lock icon icon--hideo"></i>--%>
<%--                                <span class="input__label-content input__label-content--hideo"></span>--%>
<%--                            </label>--%>
                        </span>

                        <span class="input input--hideo">
                            <input class="input__field input__field--hideo" type="password"
                                   id="register-password" name="register-password" placeholder="请输入密码"
                                   maxlength="15"/>
                            <!-- 放置提示信息 -->
                            <font color="red"></font>
<%--                            <label class="input__label input__label--hideo" for="rCustomer_PassWord">--%>
<%--                                <i class="fa fa-fw fa-lock icon icon--hideo"></i>--%>
<%--                                <span class="input__label-content input__label-content--hideo"></span>--%>
<%--                            </label>--%>
                        </span>

                        <span class="input input--hideo">
                            <input class="input__field input__field--hideo" type="password"
                                   id="register-repassword" name="register-repassword" placeholder="请确认密码"
                                   maxlength="15"/>
                            <!-- 放置提示信息 -->
                            <font color="red"></font>
<%--                            <label class="input__label input__label--hideo" for="rrCustomer_PassWord">--%>
<%--                                <i class="fa fa-fw fa-lock icon icon--hideo"></i>--%>
<%--                                <span class="input__label-content input__label-content--hideo"></span>--%>
<%--                            </label>--%>
                        </span>
                    </section>
                </div>
                <div class="form-actions">
                    <a class="btn pull-left btn-link text-muted" onClick="goto_login()">返回登录</a>
                    <input class="btn btn-primary" name="add" id="add" type="submit" value="注册" style="color:white;"/>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>


var Customer_Name = null;
var Customer_Email = null;
var Customer_Mobile = null;
var Customer_OldPassword = null;
var Customer_NewPassword = null;
var saveBtn = null;
var backBtn = null;

$(function () {
    Customer_Name = $("#Customer_Name");
    Customer_Email = $("#Customer_Email");
    Customer_Mobile = $("#Customer_Mobile");
    Customer_OldPassword = $("#Customer_OldPassword");
    Customer_NewPassword = $("#Customer_NewPassword");
    saveBtn = $("#save");
    backBtn = $("#back");

    Customer_Name.next().html("*");
    Customer_OldPassword.next().html("*");

    Customer_Name.bind("blur",function(){
        //ajax后台验证--会员名是否已存在
        //customer.do?method=ucexist&userCode=**
        $.ajax({
            type:"GET",//请求类型
            url:path+"/jsp/customer.do",//请求的url
            data:{method:"cu_customerexist",customer_Name:Customer_Name.val()},//请求参数
            dataType:"json",//ajax接口（请求url）返回的数据类型
            success:function(data){//data：返回数据（json对象）
                if(data.newcustomer_Name == "isnull"){//会员名为空
                    validateTip(Customer_Name.next(),{"color":"red"},imgNo+ " 会员名不可为空",false);
                }else if(data.newcustomer_Name == "exist"){//时间表ID已存在，错误提示
                    validateTip(Customer_Name.next(),{"color":"red"},imgNo+ " 该会员名已存在",false);
                }else if(data.newcustomer_Name == "notexist"){//时间表ID可用，正确提示
                    validateTip(Customer_Name.next(),{"color":"green"},imgYes+" 该会员名可以使用",true);
                }
            },
            error:function(data){//当访问时候，404，500 等非200的错误状态码
                validateTip(Customer_Name.next(),{"color":"red"},imgNo+" 您访问的页面不存在",false);
            }
        });
    }).bind("focus",function(){
        //显示友情提示
        validateTip(Customer_Name.next(),{"color":"#666666"},"* 会员名是用于登录的登录名",false);
    }).focus();


    Customer_Email.bind("focus", function () {
        validateTip(Customer_Email.next(), {"color": "#666666"}, "* 请输入邮箱", false);
    }).bind("blur", function () {
        var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
        if (Customer_Email.val() === "") {
            validateTip(Customer_Email.next(), {"color": "green"}, imgYes, true);
        } else if (!reg.test(Customer_Email.val())) {
            validateTip(Customer_Email.next(), {"color": "red"}, imgNo + " 邮箱输入的不符合规范，请重新输入", false);
        } else {
            validateTip(Customer_Email.next(), {"color": "green"}, imgYes, true);
        }
    });

    Customer_Mobile.bind("focus", function () {
        validateTip(Customer_Mobile.next(), {"color": "#666666"}, "* 请输入手机号", false);
    }).bind("blur", function () {
        var patrn = /^[1][3,4,5,7,8,9][0-9]{9}$/;
        if (Customer_Mobile.val() === "") {
            validateTip(Customer_Mobile.next(), {"color": "green"}, imgYes, true);
        }else if (Customer_Mobile.val().match(patrn)) {
            validateTip(Customer_Mobile.next(), {"color": "green"}, imgYes, true);
        } else {
            validateTip(Customer_Mobile.next(), {"color": "red"}, imgNo + " 输入的手机号格式不正确", false);
        }
    });


    saveBtn.on("click", function () {

        //ajax后台验证--用户旧密码是否正确
        //customer.do?method=customerexist&customer_Name=**
        $.ajax({
            type: "GET",//请求类型
            url: path + "/jsp/customer.do",//请求的url
            data: {method: "oldpasswordverify", cu_oldpassword: Customer_OldPassword.val()},//请求参数
            dataType: "json",//ajax接口（请求url）返回的数据类型
            success: function (data) {//data：返回数据（json对象）
                if (data.cu_oldpassword == "isnull") {//旧密码为空
                    validateTip(Customer_OldPassword.next(), {"color": "red"}, imgNo + " 旧密码不可为空", false);
                } else if (data.cu_oldpassword == "matchesFalse") {//旧密码匹配失败
                    validateTip(Customer_OldPassword.next(), {"color": "red"}, imgNo + " 旧密码错误", false);
                } else if (data.cu_oldpassword == "matchesSuccessful") {//旧密码匹配成功
                    validateTip(Customer_OldPassword.next(), {"color": "green"}, imgYes + "旧密码正确", true);
                }
            },
            error: function (data) {//当访问时候，404，500 等非200的错误状态码
                validateTip(Customer_OldPassword.next(), {"color": "red"}, imgNo + " 您访问的页面不存在", false);
            }
        });


        if (Customer_OldPassword.attr("validateStatus") != "true") {
            Customer_OldPassword.blur();
        } else {
            if (confirm("是否确认提交数据")) {
                $("#userForm").submit();
            }
        }
    });

    backBtn.on("click", function () {
        //alert("modify: "+referer);
        if (referer != undefined
            && null != referer
            && "" != referer
            && "null" != referer
            && referer.length > 4) {
            window.location.href = referer;
        } else {
            history.back(-1);
        }
    });
});
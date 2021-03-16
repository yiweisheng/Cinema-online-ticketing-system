var rCustomer_Name = null;
var rCustomer_Email = null;
var rCustomer_Mobile = null;
var rCustomer_PassWord = null;
var rrCustomer_PassWord = null;
var path = null;
var imgYes = null;
var imgNo = null;
var addBtn = null;


function validateTip(element, css, tipString, status) {
    element.css(css);
    element.html(tipString);
    element.prev().attr("validateStatus", status);
}

$(function () {
    rCustomer_Name = $("#rCustomer_Name");
    rCustomer_Email = $("#rCustomer_Email");
    rCustomer_Mobile = $("#rCustomer_Mobile");
    rCustomer_PassWord = $("#rCustomer_PassWord");
    rrCustomer_PassWord = $("#rrCustomer_PassWord");
    path = $("#path").val();
    imgYes = "<img width='15px' src='"+path+"/images/y.png' />";
    imgNo = "<img width='15px' src='"+path+"/images/n.png' />";
    addBtn = $("#add");
    //初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
    rCustomer_Name.next().html("*");
    rCustomer_Mobile.next().html("*");
    rCustomer_PassWord.next().html("*");
    rrCustomer_PassWord.next().html("*");

    /*
     * 验证
     * 失焦\获焦
     * jquery的方法传递
     */
    rCustomer_Name.bind("blur", function () {
        console.log("path:" + path);
        //ajax后台验证--会员名是否已存在
        //customer.do?method=ucexist&userCode=**
        $.ajax({
            type: "GET",//请求类型
            url: path+"/customerregistered.do",//请求的url
            data: {method: "customerexist", customer_Name: rCustomer_Name.val()},//请求参数
            dataType: "json",//ajax接口（请求url）返回的数据类型
            success: function (data) {//data：返回数据（json对象）
                if (data.customer_Name == "isnull") {//会员名为空
                    validateTip(rCustomer_Name.next(), {"color": "red"}, imgNo + " 会员名不可为空", false);
                } else if (data.customer_Name == "exist") {//时间表ID已存在，错误提示
                    validateTip(rCustomer_Name.next(), {"color": "red"}, imgNo + " 该会员名已存在", false);
                } else if (data.customer_Name == "notexist") {//时间表ID可用，正确提示
                    validateTip(rCustomer_Name.next(), {"color": "green"}, imgYes + " 该会员名可以使用", true);
                }
            },
            error: function (data) {//当访问时候，404，500 等非200的错误状态码
                validateTip(rCustomer_Name.next(), {"color": "red"}, imgNo + " 您访问的页面不存在", false);
            }
        });
    }).bind("focus", function () {
        //显示友情提示
        validateTip(rCustomer_Name.next(), {"color": "#666666"}, "* 会员名是用于登录的登录名", false);
    }).focus();

    rCustomer_Email.bind("focus", function () {
        validateTip(rCustomer_Email.next(), {"color": "#666666"}, "* 请输入邮箱", false);
    }).bind("blur", function () {
        var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
        if (rCustomer_Email.val() === "") {
            validateTip(rCustomer_Email.next(), {"color": "green"}, imgYes, true);
        } else if (!reg.test(rCustomer_Email.val())) {
            validateTip(rCustomer_Email.next(), {"color": "red"}, imgNo + " 邮箱输入的不符合规范，请重新输入", false);
        } else {
            validateTip(rCustomer_Email.next(), {"color": "green"}, imgYes, true);
        }
    });

    rCustomer_Mobile.bind("focus", function () {
        validateTip(rCustomer_Mobile.next(), {"color": "#666666"}, "* 请输入手机号", false);
    }).bind("blur", function () {
        var patrn = /^[1][3,4,5,7,8,9][0-9]{9}$/;
        if (rCustomer_Mobile.val().match(patrn)) {
            validateTip(rCustomer_Mobile.next(), {"color": "green"}, imgYes, true);
        } else {
            validateTip(rCustomer_Mobile.next(), {"color": "red"}, imgNo + " 输入的手机号格式不正确", false);
        }
    });

    rCustomer_PassWord.bind("focus", function () {
        validateTip(rCustomer_PassWord.next(), {"color": "#666666"}, "* 密码长度必须是大于6小于20", false);
    }).bind("blur", function () {
        if (rCustomer_PassWord.val() != null && rCustomer_PassWord.val().length > 6
            && rCustomer_PassWord.val().length < 20) {
            validateTip(rCustomer_PassWord.next(), {"color": "green"}, imgYes, true);
        } else {
            validateTip(rCustomer_PassWord.next(), {"color": "red"}, imgNo + " 密码输入不符合规范，请重新输入", false);
        }
    });

    rrCustomer_PassWord.bind("focus", function () {
        validateTip(rrCustomer_PassWord.next(), {"color": "#666666"}, "* 请输入与上面的密码一致", false);
    }).bind("blur", function () {
        if (rrCustomer_PassWord.val() != null && rrCustomer_PassWord.val().length > 6
            && rrCustomer_PassWord.val().length < 20 && rCustomer_PassWord.val() == rrCustomer_PassWord.val()) {
            validateTip(rrCustomer_PassWord.next(), {"color": "green"}, imgYes, true);
        } else {
            validateTip(rrCustomer_PassWord.next(), {"color": "red"}, imgNo + " 两次密码输入不一致，请重新输入", false);
        }
    });


    addBtn.bind("click", function () {
        if (rCustomer_Name.attr("validateStatus") != "true") {
            rCustomer_Name.blur();
        } else if (rCustomer_Email.attr("validateStatus") != "true") {
            rCustomer_Email.blur();
        } else if (rCustomer_Mobile.attr("validateStatus") != "true") {
            rCustomer_Mobile.blur();
        } else if (rCustomer_PassWord.attr("validateStatus") != "true") {
            rCustomer_PassWord.blur();
        } else if (rrCustomer_PassWord.attr("validateStatus") != "true") {
            rrCustomer_PassWord.blur();
        } else {
            if (confirm("是否确认提交数据")) {
                $("#userForm").submit();
            }
        }
    });

});
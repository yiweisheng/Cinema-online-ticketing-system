var Customer_Name = null;
var Customer_Email = null;
var Customer_Mobile = null;
var Customer_PassWord = null;
var rCustomer_PassWord = null;
var Class_IDRole = null;
var addBtn = null;
var backBtn = null;



$(function(){
	Customer_Name = $("#Customer_Name");
	Customer_Email = $("#Customer_Email");
	Customer_Mobile = $("#Customer_Mobile");
	Customer_PassWord = $("#Customer_PassWord");
	rCustomer_PassWord = $("#rCustomer_PassWord");
	Class_IDRole = $("#Class_IDRole");
	addBtn = $("#add");
	backBtn = $("#back");
	//初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
	Customer_Name.next().html("*");
	Customer_Mobile.next().html("*");
	Customer_PassWord.next().html("*");
	rCustomer_PassWord.next().html("*");
	Class_IDRole.next().html("*");

	$.ajax({
		type:"GET",//请求类型
		url:path+"/jsp/customer.do",//请求的url
		data:{method:"getclasslist"},//请求参数
		dataType:"json",//ajax接口（请求url）返回的数据类型
		success:function(data){//data：返回数据（json对象）
			if(data != null){

				var rid = $("#rid").val();
				Class_IDRole.html("");
				var options = "<option value=\"0\">请选择</option>";
				for(var i = 0; i < data.length; i++){
					if(rid != null && rid != undefined && data[i].class_ID == rid ){
						options += "<option selected=\"selected\" value=\""+data[i].class_ID+"\" >"+data[i].class_Name+"</option>";
					}else{
						options += "<option value=\""+data[i].class_ID+"\" >"+data[i].class_Name+"</option>";
					}
				}
				Class_IDRole.html(options);
			}
		},
		error:function(data){//当访问时候，404，500 等非200的错误状态码
			validateTip(Class_IDRole.next(),{"color":"red"},imgNo+" 获取会员等级列表error",false);
		}
	});



	/*
	 * 验证
	 * 失焦\获焦
	 * jquery的方法传递
	 */
	Customer_Name.bind("blur",function(){
		//ajax后台验证--会员名是否已存在
		//customer.do?method=ucexist&userCode=**
		$.ajax({
			type:"GET",//请求类型
			url:path+"/jsp/customer.do",//请求的url
			data:{method:"customerexist",customer_Name:Customer_Name.val()},//请求参数
			dataType:"json",//ajax接口（请求url）返回的数据类型
			success:function(data){//data：返回数据（json对象）
				if(data.customer_Name == "isnull"){//会员名为空
					validateTip(Customer_Name.next(),{"color":"red"},imgNo+ " 会员名不可为空",false);
				}else if(data.customer_Name == "exist"){//时间表ID已存在，错误提示
					validateTip(Customer_Name.next(),{"color":"red"},imgNo+ " 该会员名已存在",false);
				}else if(data.customer_Name == "notexist"){//时间表ID可用，正确提示
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

	Customer_Email.bind("focus",function(){
		validateTip(Customer_Email.next(),{"color":"#666666"},"* 请输入邮箱",false);
	}).bind("blur",function(){
		var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
		if(Customer_Email.val() === ""){
			validateTip(Customer_Email.next(),{"color":"green"},imgYes,true);
		}else if (!reg.test(Customer_Email.val())){
			validateTip(Customer_Email.next(),{"color":"red"},imgNo+" 邮箱输入的不符合规范，请重新输入",false);
		}else{
			validateTip(Customer_Email.next(),{"color":"green"},imgYes,true);
		}
	});

	Customer_Mobile.bind("focus",function(){
		validateTip(Customer_Mobile.next(),{"color":"#666666"},"* 请输入手机号",false);
	}).bind("blur",function(){
		var patrn=/^[1][3,4,5,7,8,9][0-9]{9}$/;
		if(Customer_Mobile.val().match(patrn)){
			validateTip(Customer_Mobile.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(Customer_Mobile.next(),{"color":"red"},imgNo + " 输入的手机号格式不正确",false);
		}
	});

	Customer_PassWord.bind("focus",function(){
		validateTip(Customer_PassWord.next(),{"color":"#666666"},"* 密码长度必须是大于6小于20",false);
	}).bind("blur",function(){
		if(Customer_PassWord.val() != null && Customer_PassWord.val().length > 6
			&& Customer_PassWord.val().length < 20 ){
			validateTip(Customer_PassWord.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(Customer_PassWord.next(),{"color":"red"},imgNo + " 密码输入不符合规范，请重新输入",false);
		}
	});

	rCustomer_PassWord.bind("focus",function(){
		validateTip(rCustomer_PassWord.next(),{"color":"#666666"},"* 请输入与上面一只的密码",false);
	}).bind("blur",function(){
		if(rCustomer_PassWord.val() != null && rCustomer_PassWord.val().length > 6
			&& rCustomer_PassWord.val().length < 20 && Customer_PassWord.val() == rCustomer_PassWord.val()){
			validateTip(rCustomer_PassWord.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(rCustomer_PassWord.next(),{"color":"red"},imgNo + " 两次密码输入不一致，请重新输入",false);
		}
	});

	Class_IDRole.bind("focus",function(){
		validateTip(Class_IDRole.next(),{"color":"#666666"},"* 请选择会员等级",false);
	}).bind("blur",function(){
		if(Class_IDRole.val() != null && Class_IDRole.val() > 0){
			validateTip(Class_IDRole.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(Class_IDRole.next(),{"color":"red"},imgNo + " 请重新选择会员等级",false);
		}
	});

	addBtn.bind("click",function(){
		if(Customer_Name.attr("validateStatus") != "true"){
			Customer_Name.blur();
		}else if(Customer_Email.attr("validateStatus") != "true"){
			Customer_Email.blur();
		}else if(Customer_Mobile.attr("validateStatus") != "true"){
			Customer_Mobile.blur();
		}else if(Customer_PassWord.attr("validateStatus") != "true"){
			Customer_PassWord.blur();
		}else if(rCustomer_PassWord.attr("validateStatus") != "true"){
			rCustomer_PassWord.blur();
		}else if(Class_IDRole.attr("validateStatus") != "true"){
			Class_IDRole.blur();
		}else{
			if(confirm("是否确认提交数据")){
				$("#userForm").submit();
			}
		}
	});

	backBtn.on("click",function(){
		if(referer != undefined
			&& null != referer
			&& "" != referer
			&& "null" != referer
			&& referer.length > 4){
			window.location.href = referer;
		}else{
			history.back(-1);
		}
	});
});
var Customer_Name = null;
var Customer_Email = null;
var Customer_Mobile = null;
var Class_IDRole = null;
var saveBtn = null;
var backBtn = null;

$(function(){
	Customer_Name = $("#Customer_Name");
	Customer_Email = $("#Customer_Email");
	Customer_Mobile = $("#Customer_Mobile");
	Class_IDRole = $("#Class_IDRole");
	saveBtn = $("#save");
	backBtn = $("#back");

	Customer_Name.next().html("*");
	Customer_Email.next().html("*");
	Customer_Mobile.next().html("*");
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

	Class_IDRole.bind("focus",function(){
		validateTip(Class_IDRole.next(),{"color":"#666666"},"* 请选择会员等级",false);
	}).bind("blur",function(){
		if(Class_IDRole.val() != null && Class_IDRole.val() > 0){
			validateTip(Class_IDRole.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(Class_IDRole.next(),{"color":"red"},imgNo + " 请重新选择会员等级",false);
		}
	});
	
	saveBtn.on("click",function(){
		if(Customer_Email.attr("validateStatus") != "true"){
			Customer_Email.blur();
		}else if(Customer_Mobile.attr("validateStatus") != "true"){
			Customer_Mobile.blur();
		}else if(Class_IDRole.attr("validateStatus") != "true"){
			Class_IDRole.blur();
		}else{
			if(confirm("是否确认提交数据")){
				$("#userForm").submit();
			}
		}
	});
	
	backBtn.on("click",function(){
		//alert("modify: "+referer);
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
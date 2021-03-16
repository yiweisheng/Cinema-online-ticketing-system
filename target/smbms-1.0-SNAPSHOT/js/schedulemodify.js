var Schedule_ID = null;
var Movie_ID = null;
var Movie_Name = null;
var hall_IDRole = null;
var Schedule_price = null;
var Schedule_BeginDateTime = null;
var saveBtn = null;
var backBtn = null;

$(function(){
	Schedule_ID = $("#Schedule_ID");
	Movie_ID = $("#Movie_ID");
	Movie_Name = $("#Movie_Name");
	hall_IDRole = $("#hall_IDRole");
	Schedule_price = $("#Schedule_price");
	Schedule_BeginDateTime = $("#Schedule_BeginDateTime");
	saveBtn = $("#save");
	backBtn = $("#back");

	Schedule_ID.next().html("*");
	Movie_ID.next().html("*");
	Movie_Name.next().html("*");
	hall_IDRole.next().html("*");
	Schedule_price.next().html("*");
	Schedule_BeginDateTime.next().html("*");



	$.ajax({
		type:"GET",//请求类型
		url:path+"/jsp/schedule.do",//请求的url
		data:{method:"gethalllist"},//请求参数
		dataType:"json",//ajax接口（请求url）返回的数据类型
		success:function(data){//data：返回数据（json对象）
			if(data != null){
				// console.log(data);
				// console.log(data[0].hall_ID);
				var rid = $("#rid").val();
				hall_IDRole.html("");
				var options = "<option value=\"0\">请选择</option>";
				for(var i = 0; i < data.length; i++){
					if(rid != null && rid != undefined && data[i].hall_ID == rid ){
						options += "<option selected=\"selected\" value=\""+data[i].hall_ID+"\" >"+data[i].hall_Name+"</option>";
					}else{
						options += "<option value=\""+data[i].hall_ID+"\" >"+data[i].hall_Name+"</option>";
					}
					
				}
				hall_IDRole.html(options);
			}
		},
		error:function(data){//当访问时候，404，500 等非200的错误状态码
			validateTip(userRole.next(),{"color":"red"},imgNo+" 获取影厅列表error",false);
		}
	});




	Schedule_price.on("focus",function(){
		validateTip(Schedule_price.next(),{"color":"#666666"},"* 请输入票价",false);
	}).on("blur",function(){
		if(Schedule_price.val() != null && Schedule_price.val() != ""){
			validateTip(Schedule_price.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(Schedule_price.next(),{"color":"red"},imgNo + " 必须输入票价！",false);
		}
	});

	
	saveBtn.on("click",function(){
		Schedule_price.blur();
		if(Schedule_price.attr("validateStatus") == "true" ){
			if(confirm("是否确认要提交数据？")){
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
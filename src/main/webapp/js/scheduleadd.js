var Schedule_ID = null;
var Movie_ID = null;
var hall_IDRole = null;
var Schedule_price = null;
var Schedule_BeginDateTime = null;
var addBtn = null;
var backBtn = null;


$(function(){
	Schedule_ID = $("#Schedule_ID");
	Movie_ID = $("#Movie_ID");
	hall_IDRole = $("#hall_IDRole");
	Schedule_price = $("#Schedule_price");
	Schedule_BeginDateTime = $("#Schedule_BeginDateTime");
	addBtn = $("#add");
	backBtn = $("#back");

	//初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
	Schedule_ID.next().html("*");
	Movie_ID.next().html("*");
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


	/*
	 * 验证
	 * 失焦\获焦
	 * jquery的方法传递
	 */
	Schedule_ID.bind("blur",function(){
		//ajax后台验证--时间表ID是否已存在
		//user.do?method=ucexist&userCode=**
		$.ajax({
			type:"GET",//请求类型
			url:path+"/jsp/schedule.do",//请求的url
			data:{method:"SIDexist",Schedule_ID:Schedule_ID.val()},//请求参数
			dataType:"json",//ajax接口（请求url）返回的数据类型
			success:function(data){//data：返回数据（json对象）
				if(data.schedule_ID == "isnull"){//时间表ID已存在，错误提示
					validateTip(Schedule_ID.next(),{"color":"red"},imgNo+ " 时间表ID不可为空",false);
				}else if(data.schedule_ID == "exist"){//时间表ID已存在，错误提示
					validateTip(Schedule_ID.next(),{"color":"red"},imgNo+ " 该时间表ID已存在",false);
				}else if(data.schedule_ID == "notexist"){//时间表ID可用，正确提示
					validateTip(Schedule_ID.next(),{"color":"green"},imgYes+" 该时间表ID可以使用",true);
				}

			},
			error:function(data){//当访问时候，404，500 等非200的错误状态码
				validateTip(Schedule_ID.next(),{"color":"red"},imgNo+" 您访问的页面不存在",false);
			}
		});
	}).bind("focus",function(){
		//显示友情提示
		validateTip(Schedule_ID.next(),{"color":"#666666"},"* 时间表ID是用于确定该排片的唯一标识,且由数字组成",false);
	}).focus();

	Movie_ID.bind("blur",function(){
		//ajax后台验证--Movie_ID是否已存在
		//user.do?method=ucexist&userCode=**
		$.ajax({
			type:"GET",//请求类型
			url:path+"/jsp/schedule.do",//请求的url
			data:{method:"MIDexist",Movie_ID:Movie_ID.val()},//请求参数
			dataType:"json",//ajax接口（请求url）返回的数据类型
			success:function(data){//data：返回数据（json对象）
				if(data.movie_ID == "isnull"){//Movie_ID已存在
					validateTip(Movie_ID.next(),{"color":"green"},imgNo+ " 电影ID不可为空",true);
				}else if(data.movie_ID == "exist"){//Movie_ID已存在
					validateTip(Movie_ID.next(),{"color":"green"},imgYes+ " 电影ID存在",true);
				}else if(data.movie_ID == "notexist"){//Movie_ID不存在，提示错误
					validateTip(Movie_ID.next(),{"color":"red"},imgNo+" 电影ID不存在，请重新输入",false);
				}
			},
			error:function(data){//当访问时候，404，500 等非200的错误状态码
				validateTip(Movie_ID.next(),{"color":"red"},imgNo+" 您访问的页面不存在",false);
			}
		});

	}).bind("focus",function(){
		//显示友情提示
		validateTip(Movie_ID.next(),{"color":"#666666"},"* 请输入数据库中已有电影ID",false);
	}).focus();

	hall_IDRole.bind("focus",function(){
		validateTip(hall_IDRole.next(),{"color":"#666666"},"* 请选择影厅",false);
	}).bind("blur",function(){
		if(hall_IDRole.val() != null &&hall_IDRole.val() >0){
			validateTip(hall_IDRole.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(hall_IDRole.next(),{"color":"red"},imgNo+" 必须选择影厅！",false);
		}
	});

	Schedule_price.bind("focus",function(){
		validateTip(Schedule_price.next(),{"color":"#666666"},"* 价格只能又数字和小数点组成",false);
	}).bind("blur",function(){
		if(Schedule_price.val() != null){
			validateTip(Schedule_price.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(Schedule_price.next(),{"color":"red"},imgNo + " 价格输入不符合规范，请重新输入",false);
		}
	});

	Schedule_BeginDateTime.bind("focus",function(){
		validateTip(Schedule_BeginDateTime.next(),{"color":"#666666"},"* 点击输入框，选择日期",false);
	}).bind("blur",function(){
		if(Schedule_BeginDateTime.val() != null && Schedule_BeginDateTime.val() != ""){
			validateTip(Schedule_BeginDateTime.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(Schedule_BeginDateTime.next(),{"color":"red"},imgNo + " 选择的日期不正确,请重新输入",false);
		}
	});

	addBtn.bind("click",function(){
		if(Schedule_ID.attr("validateStatus") != "true"){
			Schedule_ID.blur();
		}else if(Movie_ID.attr("validateStatus") != "true"){
			Movie_ID.blur();
		}else if(hall_IDRole.attr("validateStatus") != "true"){
			hall_IDRole.blur();
		}else if(Schedule_price.attr("validateStatus") != "true"){
			Schedule_price.blur();
		}else if(Schedule_BeginDateTime.attr("validateStatus") != "true"){
			Schedule_BeginDateTime.blur();
		}else{
			if(confirm("是否确认提交数据")){
				$("#scheduleForm").submit();
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
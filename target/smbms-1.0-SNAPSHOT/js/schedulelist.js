var scheduleObj;

$.ajax({
	type:"GET",
	url:path+"/jsp/schedule.do",
	data:{method:"query"},
	dataType:"json",
	success:function(data){
	},
	error:function(data){
		//alert("对不起，删除失败");
		changeDLGContent("对不起，加载失败");
	}
});




//用户管理页面上点击删除按钮弹出删除框(schedulelist.jsp)
function deleteUser(obj){
	$.ajax({
		type:"GET",
		url:path+"/jsp/schedule.do",
		data:{method:"delschedule",schedule_ID:obj.attr("Schedule_ID")},
		dataType:"json",
		success:function(data){
			if(data.delResult == "true"){//删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();
			}else if(data.delResult == "false"){//删除失败
				//alert("对不起，删除用户【"+obj.attr("username")+"】失败");
				changeDLGContent("对不起，删除时间表ID：【"+obj.attr("Schedule_ID")+"】失败");
			}else if(data.delResult == "notexist"){
				//alert("对不起，用户【"+obj.attr("username")+"】不存在");
				changeDLGContent("对不起，时间表ID：【"+obj.attr("Schedule_ID")+"】不存在");
			}else{
				//alert("对不起，该供应商【"+obj.attr("proname")+"】下有【"+data.delResult+"】条订单，不能删除");
				changeDLGContent("对不起，时间表ID：【"+obj.attr("Schedule_ID")+"】下有【"+data.delResult+"】条有效订单，不能删除");
			}
		},
		error:function(data){
			//alert("对不起，删除失败");
			changeDLGContent("对不起，删除失败");
		}
	});
}

function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeUse').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeUse').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}

$(function(){
	//通过jquery的class选择器（数组）
	//对每个class为viewUser的元素进行动作绑定（click）
	/**
	 * bind、live、delegate
	 * on
	 */
	$(".viewUser").on("click",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		window.location.href=path+"/jsp/schedule.do?method=view&schedule_ID="+ obj.attr("Schedule_ID");
	});
	
	$(".modifyUser").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/jsp/schedule.do?method=modify&schedule_ID="+ obj.attr("Schedule_ID");
	});

	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteUser(scheduleObj);
	});

	$(".deleteUser").on("click",function(){
		scheduleObj = $(this);
		changeDLGContent("你确定要删除时间表ID【"+scheduleObj.attr("Schedule_ID")+"】吗？");
		openYesOrNoDLG();
	});
});
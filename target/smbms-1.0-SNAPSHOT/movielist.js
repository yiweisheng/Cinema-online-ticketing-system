var movieObj;


//供应商管理页面上点击删除按钮弹出删除框(movielist.jsp)
function deleteProvider(obj){
	$.ajax({
		type:"GET",
		url:path+"/movie.do",
		data:{method:"delprovider",movieid:obj.attr("movieid")},
		dataType:"json",
		success:function(data){
			if(data.delResult == "true"){//删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();
			}else if(data.delResult == "false"){//删除失败
				//alert("对不起，删除供应商【"+obj.attr("proname")+"】失败");
				changeDLGContent("对不起，删除影片【"+obj.attr("moviename")+"】失败");
			}else if(data.delResult == "notexist"){
				//alert("对不起，供应商【"+obj.attr("proname")+"】不存在");
				changeDLGContent("对不起，影片【"+obj.attr("moviename")+"】不存在");
			}else{
				//alert("对不起，该供应商【"+obj.attr("proname")+"】下有【"+data.delResult+"】条订单，不能删除");
				changeDLGContent("对不起，该影片【"+obj.attr("moviename")+"】下有【"+data.delResult+"】排片，不能删除");
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
	$('#removeProv').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeProv').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}
$(function(){
	$(".viewProvider").on("click",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		window.location.href=path+"/movie.do?method=view&movieid="+ obj.attr("movieid");
	});
	
	$(".modifyProvider").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/movie.do?method=modify&movieid="+ obj.attr("movieid");
	});

	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteProvider(movieObj);
	});

	$(".deleteProvider").on("click",function(){
		movieObj = $(this);
		changeDLGContent("你确定要删除供应商【"+movieObj.attr("moviename")+"】吗？");
		openYesOrNoDLG();
	});
	
});
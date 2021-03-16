var Movie_Name = null;
var saveBtn = null;
var backBtn = null;

$(function(){
	Movie_Name = $("#Movie_Name");
	saveBtn = $("#save");
	backBtn = $("#back");
	
	//初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
	Movie_Name.next().html("*");
	
	/*
	 * 验证
	 * 失焦\获焦
	 * jquery的方法传递
	 */

	Movie_Name.on("focus",function(){
		validateTip(Movie_Name.next(),{"color":"#666666"},"* 请输入影片名",false);
	}).on("blur",function(){
		if(Movie_Name.val() != null && Movie_Name.val() != ""){
			validateTip(Movie_Name.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(Movie_Name.next(),{"color":"red"},imgNo+" 影片名不能为空，请重新输入",false);
		}

	});

	
	saveBtn.on("click",function(){
		Movie_Name.blur();
		if(Movie_Name.attr("validateStatus") == "true"){
			if(confirm("是否确认提交数据")){
				$("#providerForm").submit();
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
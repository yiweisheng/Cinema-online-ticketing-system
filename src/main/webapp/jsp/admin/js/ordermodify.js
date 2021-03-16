var Order_ID = null;
var Customer_ID = null;
var Schedule_ID = null;
var Seat_ID = null;
var Order_IsActive = null;
var Order_AdjustedPrice = null;
var Order_BuyDate = null;
var saveBtn = null;
var backBtn = null;

$(function(){
	Order_ID = $("#Order_ID");
	Customer_ID = $("#Customer_ID");
	Schedule_ID = $("#Schedule_ID");
	Seat_ID = $("#Seat_ID");
	Order_IsActive = $("#Order_IsActive");
	Order_AdjustedPrice = $("#Order_AdjustedPrice");
	Order_BuyDate = $("#Order_BuyDate");
	saveBtn = $("#save");
	backBtn = $("#back");

	Order_ID.next().html("*");
	Customer_ID.next().html("*");
	Schedule_ID.next().html("*");
	Seat_ID.next().html("*");
	Order_IsActive.next().html("*");
	Order_AdjustedPrice.next().html("*");
	Order_BuyDate.next().html("*");



	Order_AdjustedPrice.on("focus",function(){
		validateTip(Order_AdjustedPrice.next(),{"color":"#666666"},"* 会员打折之后的价格",false);
	}).on("blur",function(){
		if(Order_AdjustedPrice.val() != null && Order_AdjustedPrice.val() != ""){
			validateTip(Order_AdjustedPrice.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(Order_AdjustedPrice.next(),{"color":"red"},imgNo + " 会员打折之后的价格！",false);
		}
	});

	
	saveBtn.on("click",function(){
		Order_AdjustedPrice.blur();
		if(Order_AdjustedPrice.attr("validateStatus") == "true" ){
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
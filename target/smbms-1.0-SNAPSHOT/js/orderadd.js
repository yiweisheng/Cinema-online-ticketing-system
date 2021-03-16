var path = $("#path").val();
var referer = $("#referer").val();
var selected_seats = null;
var backBtn = null;

var movieObj;

backBtn = $("#back");

//供应商管理页面上点击删除按钮弹出删除框(movielist.jsp)
function deleteProvider(obj){

    // 将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
    var obj = $(this);

    var seatlist = [];
    temp = document.getElementById('selected-seats');
    lis = temp.getElementsByTagName('li');
    // alert(lis.length); //显示li元素的个数
    for (let i = 0; i < lis.length; i++) {
        console.log("selected_seats:" + lis.item(i).textContent.slice(3, 7).replace(/[^0-9]/ig, ""));
        seatlist[i] = lis.item(i).textContent.slice(3, 7).replace(/[^0-9]/ig, "");
    }

    $.ajax({
        type: "GET",//请求类型
        url: path + "/jsp/order.do",//请求的url
        contentType : "application/json" ,
        data: {method: "selectseat",seatlist:JSON.stringify(seatlist),hall_ID:$("#hall_ID").val(),schedule_ID:$("#schedule_ID").val()},//请求参数
        dataType: "json",//ajax接口（请求url）返回的数据类型
        success:function(data){
            if(data.GenerateResults == "true"){//删除成功：移除删除行
                changeDLGContent("订单生成成功,购买凭证为："+data.sKey);
                $('#yes').remove();
                $('#no').click(function () {
                    location.reload();
                });
            }else if(data.GenerateResults == "false"){//删除失败
                //alert("对不起，删除供应商【"+obj.attr("proname")+"】失败");
                changeDLGContent("对不起，订单生成失败");
            }
        },
        error:function(data){
            //alert("对不起，删除失败");
            changeDLGContent("对不起，未知错误");
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
$(function() {

    $('#no').click(function () {
        cancleBtn();
    });

    $('#yes').click(function () {
        deleteProvider(movieObj);
    });

    $(".checkout-button").on("click",function(){
        movieObj = $(this);

        var seatlist = [];
        temp = document.getElementById('selected-seats');
        lis = temp.getElementsByTagName('li');
        // alert(lis.length); //显示li元素的个数
        for (let i = 0; i < lis.length; i++) {
            console.log("selected_seats:" + lis.item(i).textContent.slice(3, 7).replace(/[^0-9]/ig, ""));
            seatlist[i] = lis.item(i).textContent.slice(3, 7).replace(/[^0-9]/ig, "");
        }
        $.ajax({
            type: "GET",//请求类型
            url: path + "/jsp/order.do",//请求的url
            contentType : "application/json" ,
            data: {method: "calculationTotalPrice",seatlist:JSON.stringify(seatlist),schedule_ID:$("#schedule_ID").val()},//请求参数
            dataType: "json",//ajax接口（请求url）返回的数据类型
            success:function(data){
                if(data.totalPrice == "isNull"){//计算票价失败
                    //alert("对不起，删除供应商【"+obj.attr("proname")+"】失败");
                    changeDLGContent("对不起，订单生成失败");
                }else{//计算票价成功
                    changeDLGContent("确认付款，总票价为："+data.totalPrice);
                }
            },
            error:function(data){
                //alert("对不起，删除失败");
                changeDLGContent("对不起，未知错误");
            }
        });
        // changeDLGContent("确认已付款");
        openYesOrNoDLG();
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

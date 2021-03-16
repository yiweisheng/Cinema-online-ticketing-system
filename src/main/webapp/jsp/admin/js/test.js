var img = null;
var getImgBut=null;

$(function(){
    img = $("#img");
    getImgBut=$("#getImgBut");

    img.next().html("*");
    getImgBut.next().html("*");

    $.ajax({
        type:"GET",//请求类型
        url:path+"/jsp/test/DBimageTest.do",//请求的url
        data:{method:"getrolelist"},//请求参数
        dataType:"json",//ajax接口（请求url）返回的数据类型
        success:function(data){//data：返回数据（json对象）
            changeDLGContent("图片获取成功");
        },
        error:function(data){//当访问时候，404，500 等非200的错误状态码
            changeDLGContent("对不起，图片获取失败");

        }
    });
});

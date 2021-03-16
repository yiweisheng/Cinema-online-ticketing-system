var backBtn = null;

$(function () {
    backBtn = $("#back");

    $(".addSchedule").on("click",function(){
        //将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
        var obj = $(this);
        window.location.href=path+"/jsp/admin/schedule.do?method=jumpselectseat&hall_ID="+ obj.attr("Hall_ID")+"&schedule_ID="+obj.attr("Schedule_ID");
    });

    backBtn.on("click", function () {
        //alert("view : "+referer);
        if (referer != undefined
            && null != referer
            && "" != referer
            && "null" != referer
            && referer.length > 4) {
            window.location.href = referer;
        } else {
            history.back(-1);
        }
    });
});
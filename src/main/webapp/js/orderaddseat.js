var path = $("#path").val();
var referer = $("#referer").val();
var firstSeatLabel = 1; //可以用来给位置取名字的数，可以自由改变，这里用的是1
var unavailablelist = []; //不可用座位列表
var map = [];//座位列表
var movie = null;
var schedule = null;
var obj = null;
var hall_Information = document.getElementById("hall_Information");


$.ajax({
    type: "GET",//请求类型
    url: path + "/jsp/seat.do",//请求的url
    data: {method: "getseatlist", hall_ID: $("#hall_ID").val(), schedule_ID: $("#schedule_ID").val()},//请求参数
    dataType: "json",//ajax接口（请求url）返回的数据类型
    success: function (data) {//data：返回数据（json对象）
        if (data != null) {

            map = [data.seatMapArr];
            unavailablelist = [data.unavailableArr];
            schedule = data.schedule;
            movie = data.movie;

            console.log(data);
            console.log("map:" + map);
            console.log("unavailablelist:" + unavailablelist);
            console.log("schedule:" + schedule);
            console.log("movie:" + movie);

            $('#seat-map').empty(); //清空之前的座位html内容
            $('#legend').empty(); //清空之前的座位
            $(document).ready(function () { //页面加载完毕后就开始画座位
                var $cart = $('#selected-seats'),
                    $counter = $('#counter'),
                    $total = $('#total'),
                    sc = $('#seat-map').seatCharts({
                        map: map,//引用之前的map
                        seats: {
                            s: {
                                price: schedule.schedule_price,
                                classes: 'first-class', //你的自定义CSS类
                                category: '头等舱'
                            },
                            r: {
                                price: 0,
                                classes: 'economy-class', //你的自定义CSS类
                                category: '已预定'
                            }
                        },
                        naming: {
                            top: false,
                            left: false,
                            getLabel: function (character, row, column) {
                                return firstSeatLabel++;	//座位的名字
                            },
                        },
                        legend: {
                            node: $('#legend'),		//提示信息存放位置
                            items: [
                                ['s', 'available', '头等舱'],
                                // ['e', 'available', '经济舱'],
                                ['r', 'unavailable', '已预定']
                            ]
                        },
                        click: function () {
                            if (this.status() == 'available') {//当元素触发点击效果时，return的值将成为新的status
                                $('<li>' + this.data().category + this.settings.label + '号座位' + '：<br/>价格：<b>$' + this.data().price +
                                    '</b> <a href="#" class="cancel-cart-item">[删除]</a></li>')
                                    .attr('id', 'cart-item-' + this.settings.id)
                                    .data('seatId', this.settings.id)
                                    .appendTo($cart);
                                $counter.text(sc.find('selected').length + 1);
                                $total.text(recalculateTotal(sc) + this.data().price);
                                return 'selected';
                            } else if (this.status() == 'selected') {
                                //update the counter
                                $counter.text(sc.find('selected').length - 1);
                                //and total
                                $total.text(recalculateTotal(sc) - this.data().price);
                                //remove the item from our cart
                                $('#cart-item-' + this.settings.id).remove();
                                //seat has been vacated
                                return 'available';
                            } else if (this.status() == 'unavailable') {
                                //座位已被预定
                                return 'unavailable';
                            } else {
                                return this.style();
                            }
                        }
                    });

                //这将处理“[cancel]”链接点击
                $('#selected-seats').on('click', '.cancel-cart-item', function () {
                    //让我们在适当的位置上触发Click事件，这样我们就不必在这里重复逻辑了
                    sc.get($(this).parents('li:first').data('seatId')).click();
                });

                //让我们假设有些座位已经被预定
                console.log("unavailablelist:" + unavailablelist);
                // sc.get(['1_9','2_9','3_4']).status('unavailable');
                // sc.get(['1_9','2_9','3_4']).status('unavailable');

                function recalculateTotal(sc) {
                    var total = 0;
                    //basically find every selected seat and sum its price
                    sc.find('selected').each(function () {
                        total += this.data().price;
                    });
                    return total;
                }
            });
            // console.log("map"+map);
            // console.log("unavailable"+unavailable);

            //添加影片和影厅数据
            var div = document.getElementById("Information");
            //换行
            var br = document.createElement("br");
            div.appendChild(br);
            //添加label ，存放指标名称
            var div2 = document.createElement("label");
            div2.innerText ="影厅ID："+ data.schedule.hall_ID;
            div.appendChild(div2);

            //换行
            var br = document.createElement("br");
            div.appendChild(br);
            //添加label ，存放指标名称
            var div2 = document.createElement("label");
            div2.innerText ="影片ID："+ data.movie.movie_ID;
            div.appendChild(div2);

            //换行
            var br = document.createElement("br");
            div.appendChild(br);
            //添加label ，存放指标名称
            var div2 = document.createElement("label");
            div2.innerText ="影片名ID："+ data.movie.movie_Name;
            div.appendChild(div2);

            //换行
            var br = document.createElement("br");
            div.appendChild(br);
            //添加label ，存放指标名称
            var div2 = document.createElement("label");
            div2.innerText ="票价："+ data.schedule.schedule_price;
            div.appendChild(div2);

            //换行
            var br = document.createElement("br");
            div.appendChild(br);
            var myImage = document.createElement("img");
            myImage.src =data.movie.movie_ImageSrc;
            console.log(data.movie.movie_ImageSrc);
            div.appendChild(myImage);
            myImage.style.height = "200px";




        }
    },
    error: function (data) {//当访问时候，404，500 等非200的错误状态码
        console.log("座位表获取失败");
    }
});




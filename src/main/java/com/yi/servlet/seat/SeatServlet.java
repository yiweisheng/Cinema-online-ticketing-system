package com.yi.servlet.seat;

import com.alibaba.fastjson.JSONArray;
import com.yi.pojo.Movie;
import com.yi.pojo.Schedule;
import com.yi.pojo.Seat;
import com.yi.service.movie.MovieService;
import com.yi.service.movie.MovieServiceImpl;
import com.yi.service.schedule.ScheduleService;
import com.yi.service.schedule.ScheduleServiceImpl;
import com.yi.service.seat.SeatService;
import com.yi.service.seat.SeatServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatServlet extends HttpServlet {
    public SeatServlet() {
        super();
    }

    @Override
    public void destroy() {
        super.destroy();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        System.out.println("method----> " + method);

        if (method != null && method.equals("getseatlist")) {
            //选座测试
            this.getseatlist(req, resp);
        }
    }


    //获取seat列表
    private void getseatlist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("SeatServlet-->getseatlist");
        SeatService seatService = new SeatServiceImpl();
        ScheduleService scheduleService = new ScheduleServiceImpl();
        MovieService movieService=new MovieServiceImpl();
        String Hall_ID = request.getParameter("hall_ID");
        String Schedule_ID = request.getParameter("schedule_ID");
        Schedule schedule = null;
        Movie movie=null;
        List<Seat> seatList = null;
        System.out.println("Hall_ID:" + Hall_ID);
        System.out.println("Schedule_ID:" + Schedule_ID);
        int seatRow = 1;
        int seatColumn = 0;
        StringBuffer seatMap = new StringBuffer();
        StringBuffer unavailable = new StringBuffer();
        Map map = new HashMap();


        schedule = scheduleService.getScheduleById(Schedule_ID);
        movie=movieService.getMovieById(schedule.getMovie_ID());
        seatList = seatService.getSeatList(Hall_ID);
        //生成 orderaddseat.js 使用的地图和已不可使用的位置的字符串
        for (int i = 0; i < seatList.size(); i++) {
//            System.out.println("out i="+i);
            if (seatList.get(i).getSeat_Row() == seatRow) {     //第seatRow行'
                seatMap.append("'");
                seatColumn = 0;
                while (i < seatList.size() && seatList.get(i).getSeat_Row() == seatRow) {//还是第seatRow行的时候
//                    System.out.println("in i="+i);
                    seatColumn++;
                    if (seatList.get(i).getSeat_IsCorridor() == 0) {//是否为过道为 0 不是过道
                        if(i==18){
                            System.out.println("i=18:"+seatList.get(i).getSeat_IsActive());
                        }
                        if (seatList.get(i).getSeat_IsActive() == 1) {//是否可用为 1 可用
                            seatMap.append("s");
                        } else {//不可用情况
                            seatMap.append("r");
                            if (unavailable.toString().equals("")) {//第一个不可使用位置
                                unavailable.append("'").append(seatRow).append("_").append(seatColumn).append("'");
//                                unavailable.append(seatRow).append("_").append(seatColumn);
                            } else {//后续不可使用位置
                                unavailable.append(",'").append(seatRow).append("_").append(seatColumn).append("'");
//                                unavailable.append(",").append(seatRow).append("_").append(seatColumn);
                            }
                        }
                    } else {//为过道情况
                        seatMap.append("_");
                    }
                    i++;
                }
            } else {        //座位换行
                seatMap = seatMap.append("',");
                seatRow++;
                i--;    //内部的i++
                i--;    //外部的i++
            }
        }
        seatMap = seatMap.append("'");
        String[] seatMapArr = seatMap.toString().split(","); // 用,分割
        String[] unavailableArr = unavailable.toString().split(","); // 用,分割
        System.out.println(Arrays.toString(seatMapArr));
        System.out.println(Arrays.toString(unavailableArr));
        map.put("seatMapArr", seatMap);
        map.put("unavailableArr", unavailable);
        map.put("schedule",schedule);
        map.put("movie",movie);


        //把 seatMap，unavailable 转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(map));
//        outPrintWriter.write(JSONArray.toJSONString(movie));
        System.out.println("JSONArray.toJSONString(map):" + JSONArray.toJSONString(map));
//        System.out.println("JSONArray.toJSONString(movie):" + JSONArray.toJSONString(movie));
        outPrintWriter.flush();
        outPrintWriter.close();

//        request.getRequestDispatcher("ordermodify.jsp").forward(request, response);

    }

}

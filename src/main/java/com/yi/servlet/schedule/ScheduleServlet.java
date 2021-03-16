package com.yi.servlet.schedule;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.yi.pojo.*;
import com.yi.service.hall.HallService;
import com.yi.service.hall.HallServiceImpl;
import com.yi.service.movie.MovieService;
import com.yi.service.movie.MovieServiceImpl;
import com.yi.service.schedule.ScheduleService;
import com.yi.service.schedule.ScheduleServiceImpl;
import com.yi.util.Constants;
import com.yi.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ScheduleServlet extends HttpServlet {
    public ScheduleServlet() {
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

        if (method != null && method.equals("query")) {
            //获取时间表列表
            this.query(req, resp);
        } else if (method != null && method.equals("view")) {
            //通过时间表ID查看时间表详细信息
            this.getScheduleById(req, resp, "scheduleview.jsp");
        } else if (method != null && method.equals("modify")) {
            //修改时间表信息页面跳转
            this.getScheduleById(req, resp, "schedulemodify.jsp");
        } else if (method != null && method.equals("modifysave")) {
            //修改时间表信息执行操作
            this.modify(req, resp);
        } else if (method != null && method.equals("gethalllist")) {
            //获取影厅列表
            this.getHallList(req, resp);
        } else if (method != null && method.equals("SIDexist")) {
            //ajax后台验证--Schedule_ID是否已存在
            this.Schedule_IDexist(req, resp);
        } else if (method != null && method.equals("MIDexist")) {
            //ajax后台验证--Movie_ID是否已存在
            this.Movie_IDexist(req, resp);
        } else if (method != null && method.equals("add")) {
            //增加时间表操作
            this.add(req, resp);
        } else if (method != null && method.equals("delschedule")) {
            //删除时间表记录
            this.delSchedule(req, resp);
        } else if (method != null && method.equals("jumpselectseat")) {
            //选座测试
            this.jumpselectseat(req, resp);
        }
    }

    //获取时间表列表
    private void query(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //查询用户列表

        //从前端获取数据
        String Schedule_ID = request.getParameter("Schedule_ID");
        String Movie_ID = request.getParameter("Movie_ID");
        String Movie_Name = request.getParameter("Movie_Name");
        String temp = request.getParameter("Hall_ID");
        String temp_IsShow = request.getParameter("Schedule_IsShow");
        String pageIndex = request.getParameter("pageIndex");
        int Hall_ID = 0;
        int schedule_IsShow = 1;

        //获取用户列表

        ScheduleService scheduleService = new ScheduleServiceImpl();

        //第一次走页面一定是第一页,页面大小固定的
        List<Schedule> scheduleList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;
        /**
         * http://localhost:8090/SMBMS/userlist.do
         * ----queryUserName --NULL
         * http://localhost:8090/SMBMS/userlist.do?queryname=
         * --queryUserName ---""
         */
        System.out.println("Schedule_ID servlet--------" + Schedule_ID);
        System.out.println("Movie_ID servlet--------" + Movie_ID);
        System.out.println("Movie_Name servlet--------" + Movie_Name);
        System.out.println("Hall_ID servlet--------" + Hall_ID);
        System.out.println("temp_IsShow servlet--------" + temp_IsShow);
        System.out.println("Hall_ID Default servlet--------" + Hall_ID);
        System.out.println("query pageIndex--------- > " + pageIndex);

        if (Schedule_ID == null) {
            Schedule_ID = "";
        }
        if (Movie_ID == null) {
            Movie_ID = "";
        }
        if (Movie_Name == null) {
            Movie_Name = "";
        }
        if (temp != null && !temp.equals("")) {
            Hall_ID = Integer.parseInt(temp);//给查询赋值
        }
        if (temp_IsShow != null && !temp_IsShow.equals("")) {
            schedule_IsShow = Integer.parseInt(temp_IsShow);//给查询赋值
        }

        if (pageIndex != null) {
            try {
                currentPageNo = Integer.valueOf(pageIndex);
            } catch (NumberFormatException e) {
                response.sendRedirect("error.jsp");
            }
        }
        //总数量（表）
        int totalCount = scheduleService.getScheduleCount(Schedule_ID, Movie_ID, Movie_Name, String.valueOf(Hall_ID),schedule_IsShow);
        //总页数
        PageSupport pages = new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);
        int totalPageCount = pages.getTotalPageCount();

        //控制首页和尾页
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }


        scheduleList = scheduleService.getScheduleList(Schedule_ID, Movie_ID, Movie_Name, String.valueOf(Hall_ID),schedule_IsShow, currentPageNo, pageSize);


        List<ScheduleIsShow> scheduleIsShowList = new ArrayList<ScheduleIsShow>();
        ScheduleIsShow sheduleIsShow1 = new ScheduleIsShow();
        sheduleIsShow1.setIsShow(0);
        sheduleIsShow1.setName("不在映");
        scheduleIsShowList.add(sheduleIsShow1);
        ScheduleIsShow sheduleIsShow2 = new ScheduleIsShow();
        sheduleIsShow2.setIsShow(1);
        sheduleIsShow2.setName("在映");
        scheduleIsShowList.add(sheduleIsShow2);

//        System.out.println("上映时间格式测试"+scheduleList.get(1).getSchedule_BeginDateTime());
        request.setAttribute("scheduleList", scheduleList);
        List<Hall> hallList = null;
        HallService roleService = new HallServiceImpl();
        hallList = roleService.getHallList();
        request.setAttribute("hallList", hallList);
        request.setAttribute("Schedule_ID", Schedule_ID);
        request.setAttribute("Movie_ID", Movie_ID);
        request.setAttribute("Movie_Name", Movie_Name);
        request.setAttribute("Hall_ID", Hall_ID);
        request.setAttribute("schedule_IsShow", schedule_IsShow);
        request.setAttribute("scheduleIsShowList", scheduleIsShowList);
        request.setAttribute("totalPageCount", totalPageCount);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPageNo", currentPageNo);
        request.getRequestDispatcher("schedulelist.jsp").forward(request, response);
    }

    //通过时间表ID查看时间表详细信息
    //修改时间表信息页面跳转
    private void getScheduleById(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        System.out.println("ScheduleServlet-->getScheduleById");
        System.out.println("url:" + url);
        String id = request.getParameter("schedule_ID");
        if (!StringUtils.isNullOrEmpty(id)) {
            ScheduleService scheduleService = new ScheduleServiceImpl();
            Schedule schedule = null;
            schedule = scheduleService.getScheduleById(id);
            request.setAttribute("schedule", schedule);
            if (url.equals("schedulemodify.jsp")) {
                request.setAttribute("hall_IDRole", schedule.getHall_ID());
            }
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    //修改时间表信息执行操作
    private void modify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ScheduleServlet-->modify");
        BigInteger Schedule_ID = BigInteger.valueOf(Long.parseLong(request.getParameter("Schedule_ID")));
        BigInteger Movie_ID = BigInteger.valueOf(Long.parseLong(request.getParameter("Movie_ID")));
        String Hall_ID = request.getParameter("hall_IDRole");
        Float Schedule_price = Float.parseFloat(request.getParameter("Schedule_price"));
        String Schedule_BeginDateTime = request.getParameter("Schedule_BeginDateTime");

        if (Schedule_BeginDateTime.equals("") || null == Schedule_BeginDateTime) {
            Schedule_BeginDateTime = "1970-01-01 00:00:00";
        }
        //转化为时间格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp dateTime = null;
        dateTime = Timestamp.valueOf(Schedule_BeginDateTime);

        Schedule schedule = new Schedule();
        schedule.setSchedule_ID(Schedule_ID);
        schedule.setMovie_ID(Movie_ID);
        schedule.setHall_ID(Hall_ID);
        schedule.setSchedule_price(Schedule_price);
        schedule.setSchedule_BeginDateTime(dateTime);

        boolean flag = false;
        ScheduleService scheduleService = new ScheduleServiceImpl();
        flag = scheduleService.modify(schedule);
        if (flag) {
            response.sendRedirect(request.getContextPath() + "/jsp/schedule.do?method=query");
        } else {
            request.getRequestDispatcher("schedulemodify.jsp").forward(request, response);
        }
    }

    //获取影厅列表
    private void getHallList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ScheduleServlet->getHallList");
        List<Hall> hallList = null;
        HallService hallService = new HallServiceImpl();
        hallList = hallService.getHallList();
        //把hallList转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(hallList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    //ajax后台验证--Schedule_ID是否已存在
    private void Schedule_IDexist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ScheduleServlet->Schedule_IDexist");
        //判断用Schedule_ID是否已存在
        String schedule_ID = request.getParameter("Schedule_ID");

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (StringUtils.isNullOrEmpty(schedule_ID)) {
            //userCode == null || userCode.equals("")
            resultMap.put("schedule_ID", "isnull");
        } else {
            ScheduleService scheduleService = new ScheduleServiceImpl();
            Schedule schedule = scheduleService.selectUserCodeExist(schedule_ID);
            if (null != schedule) {
                resultMap.put("schedule_ID", "exist");
            } else {
                resultMap.put("schedule_ID", "notexist");
            }
        }

        //把resultMap转为json字符串以json的形式输出
        //配置上下文的输出类型
        response.setContentType("application/json");
        //从response对象中获取往外输出的writer对象
        PrintWriter outPrintWriter = response.getWriter();
        //把resultMap转为json字符串 输出
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();//刷新
        outPrintWriter.close();//关闭流
    }

    //ajax后台验证--Movie_ID是否存在
    private void Movie_IDexist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ScheduleServlet->Movie_IDexist");
        //判断Movie_ID是否存在
        BigInteger Movie_ID = BigInteger.valueOf(Long.parseLong(request.getParameter("Movie_ID")));
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (StringUtils.isNullOrEmpty(Movie_ID.toString())) {
            //userCode == null || userCode.equals("")
            resultMap.put("movie_ID", "isnull");
        } else {
            MovieService movieService = new MovieServiceImpl();
            Movie movie = movieService.getMovieById(Movie_ID);
            if (null != movie) {
                resultMap.put("movie_ID", "exist");
            } else {
                resultMap.put("movie_ID", "notexist");
            }
        }

        //把resultMap转为json字符串以json的形式输出
        //配置上下文的输出类型
        response.setContentType("application/json");
        //从response对象中获取往外输出的writer对象
        PrintWriter outPrintWriter = response.getWriter();
        //把resultMap转为json字符串 输出
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();//刷新
        outPrintWriter.close();//关闭流
    }

    //增加时间表操作
    private void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("add()================");
        BigInteger Schedule_ID = BigInteger.valueOf(Long.parseLong(request.getParameter("Schedule_ID")));
        BigInteger Movie_ID = BigInteger.valueOf(Long.parseLong(request.getParameter("Movie_ID")));
        String hall_IDRole = request.getParameter("hall_IDRole");
        String Schedule_price = request.getParameter("Schedule_price");
        String Schedule_BeginDateTime = request.getParameter("Schedule_BeginDateTime");

        Schedule schedule = new Schedule();
        schedule.setSchedule_ID(Schedule_ID);
        schedule.setMovie_ID(Movie_ID);
        schedule.setHall_ID(hall_IDRole);
        schedule.setSchedule_price(Float.valueOf(Schedule_price));

        if (Schedule_BeginDateTime.equals("") || null == Schedule_BeginDateTime) {
            Schedule_BeginDateTime = "1970-01-01 00:00:00";
        }
        //转化为时间格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp dateTime = null;
        dateTime = Timestamp.valueOf(Schedule_BeginDateTime);
        schedule.setSchedule_BeginDateTime(dateTime);

        ScheduleService scheduleService = new ScheduleServiceImpl();


        if (scheduleService.add(schedule)) {
            response.sendRedirect(request.getContextPath() + "/jsp/schedule.do?method=query");
        } else {
            request.getRequestDispatcher("schedulemodify.jsp").forward(request, response);
        }
    }

    //删除时间表记录
    private void delSchedule(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ScheduleServlet->delSchedule");
        String schedule_ID = request.getParameter("schedule_ID");
        System.out.println("Schedule_ID:" + schedule_ID);

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (!schedule_ID.matches("[0-9]")) {
            resultMap.put("delResult", "notexist");
        } else {
            ScheduleService scheduleService = new ScheduleServiceImpl();
            int flag = scheduleService.deleteScheduleById(schedule_ID);
            System.out.println("flag" + flag);
            if (flag == 0) {
                resultMap.put("delResult", "true");
            } else if (flag == -1) {//删除失败
                resultMap.put("delResult", "false");
            } else if (flag > 0) {//该该排片下有订单，不能删除，返回订单数
                resultMap.put("delResult", String.valueOf(flag));
            }
        }
        //把resultMap转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    //选座页面的跳转
    private void jumpselectseat(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ScheduleServlet-->jumpselectseat");
        String hall_ID = request.getParameter("hall_ID");
        String schedule_ID = request.getParameter("schedule_ID");
        System.out.println("hall_ID:" + hall_ID);
        System.out.println("schedule_ID:" + schedule_ID);
        request.setAttribute("hall_ID", hall_ID);
        request.setAttribute("schedule_ID", schedule_ID);
        request.getRequestDispatcher("orderadd.jsp").forward(request, response);
    }

}

package com.yi.servlet.movie;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.yi.pojo.Movie;
import com.yi.service.movie.MovieService;
import com.yi.service.movie.MovieServiceImpl;
import com.yi.util.Constants;
import com.yi.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MovieServlet extends HttpServlet {

    public MovieServlet() {
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
            //获取影片列表
            this.query(req, resp);
        } else if (method != null && method.equals("view")) {
            //查看影片详细信息
            this.getProviderById(req, resp, "movieview.jsp");
        } else if (method != null && method.equals("modify")) {
            //修改影片信息页面
            this.getProviderById(req, resp, "moviemodify.jsp");
        } else if (method != null && method.equals("add")) {
            //增加影片
//            this.add(req, resp);
        } else if (method != null && method.equals("modifysave")) {
            //保存影片信息修改
            this.modify(req, resp);
        } else if (method != null && method.equals("delprovider")) {
            //删除影片
            this.delProvider(req, resp);
        }

    }
    //获取影片列表
    private void query(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String Movie_ID = request.getParameter("Movie_ID");
        String Movie_Name = request.getParameter("Movie_Name");
        String Movie_Region = request.getParameter("Movie_Region");
        String Movie_Type = request.getParameter("Movie_Type");
        String pageIndex = request.getParameter("pageIndex");
        int queryUserRole = 0;
        MovieService movieService = new MovieServiceImpl();


        //获取影片列表
        //第一次走页面一定是第一页,页面大小固定的
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;
        if (pageIndex != null) {
            try {
                currentPageNo = Integer.valueOf(pageIndex);
            } catch (NumberFormatException e) {
                response.sendRedirect("error.jsp");
            }
        }
        //总数量（表）
        int totalCount = movieService.getMovieCount(Movie_ID, Movie_Name, Movie_Region, Movie_Type);
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

        if (StringUtils.isNullOrEmpty(Movie_ID)) {
            Movie_ID = "";
        }
        if (StringUtils.isNullOrEmpty(Movie_Name)) {
            Movie_Name = "";
        }
        if (StringUtils.isNullOrEmpty(Movie_Region)) {
            Movie_Region = "";
        }
        if (StringUtils.isNullOrEmpty(Movie_Type)) {
            Movie_Type = "";
        }
        List<Movie> movieList = new ArrayList<Movie>();
        movieList = movieService.getMovieList(Movie_ID, Movie_Name, Movie_Region, Movie_Type, currentPageNo, pageSize);
        request.setAttribute("movieList", movieList);
        request.setAttribute("Movie_ID", Movie_ID);
        request.setAttribute("Movie_Name", Movie_Name);
        request.setAttribute("Movie_Region", Movie_Region);
        request.setAttribute("Movie_Type", Movie_Type);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPageNo", currentPageNo);
        request.setAttribute("totalPageCount", totalPageCount);

        request.getRequestDispatcher("movielist.jsp").forward(request, response);
    }
    //通过影片ID查看影片详细信息
    //修改影片信息页面
    private void getProviderById(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        BigInteger id = BigInteger.valueOf(Long.parseLong(request.getParameter("movieid"))) ;
        if (id!=null) {
            MovieService movieService = new MovieServiceImpl();
            Movie movie = null;
            movie = movieService.getMovieById(id);
            request.setAttribute("movie", movie);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    //添加影片
    //由FileServlet调用
    public void add(HttpServletRequest req, HttpServletResponse resp,Movie movie) throws ServletException, IOException {

        System.out.println("MovieServlet=======>add");
        System.out.println("Movie_ID:"+movie.getMovie_ID());
        System.out.println("Movie_Name:"+movie.getMovie_Name());
        System.out.println("Movie_Region:"+movie.getMovie_Region());
        System.out.println("Movie_Type:"+movie.getMovie_Type());
        System.out.println("Movie_MainActor:"+movie.getMovie_MainActor());
        System.out.println("Movie_Director:"+movie.getMovie_Director());
        System.out.println("Movie_Duration:"+movie.getMovie_Duration());
        System.out.println("Movie_Description:"+movie.getMovie_Description());
        System.out.println("Movie_ImageSrc:"+movie.getMovie_ImageSrc());

        boolean flag = false;
        MovieService movieService = new MovieServiceImpl();
        flag = movieService.add(movie);
        if (flag) {
            resp.sendRedirect(req.getContextPath() + "/jsp/admin/movie.do?method=query");
        } else {
            req.getRequestDispatcher("movieadd.jsp").forward(req, resp);
        }
    }
    //修改影片信息
    private void modify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BigInteger Movie_ID = BigInteger.valueOf(Long.parseLong(request.getParameter("Movie_ID")));
        String Movie_Name = request.getParameter("Movie_Name");
        String Movie_Region = request.getParameter("Movie_Region");
        String Movie_Type = request.getParameter("Movie_Type");
        String Movie_MainActor = request.getParameter("Movie_MainActor");
        String Movie_Director = request.getParameter("Movie_Director");
        String Movie_Duration = request.getParameter("Movie_Duration");
        String Movie_Description = request.getParameter("Movie_Description");
        String Movie_ImageSrc = request.getParameter("Movie_ImageSrc");

        if (Movie_Duration.equals("") || null == Movie_Duration) {
            Movie_Duration = "00:00:00";
        }
        //转化为时间格式
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(Movie_Duration);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Time time = new Time(date.getTime());

        Movie movie = new Movie();
        movie.setMovie_ID(Movie_ID);
        movie.setMovie_Name(Movie_Name);
        movie.setMovie_Region(Movie_Region);
        movie.setMovie_Type(Movie_Type);
        movie.setMovie_MainActor(Movie_MainActor);
        movie.setMovie_Director(Movie_Director);
        movie.setMovie_Duration(time);
        movie.setMovie_Description(Movie_Description);
        movie.setMovie_ImageSrc(Movie_ImageSrc);

        boolean flag = false;
        MovieService movieServiceService = new MovieServiceImpl();
        flag = movieServiceService.modify(movie);
        if (flag) {
            response.sendRedirect(request.getContextPath() + "/jsp/movie.do?method=query");
        } else {
            request.getRequestDispatcher("moviemodify.jsp").forward(request, response);
        }
    }
    //删除影片
    private void delProvider(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("movieid");
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (!StringUtils.isNullOrEmpty(id)) {
            MovieService movieService = new MovieServiceImpl();
            int flag = movieService.deleteProviderById(id);
            if (flag == 0) {//删除成功
                resultMap.put("delResult", "true");
            } else if (flag == -1) {//删除失败
                resultMap.put("delResult", "false");
            } else if (flag > 0) {//该供应商下有订单，不能删除，返回订单数
                resultMap.put("delResult", String.valueOf(flag));
            }
        } else {
            resultMap.put("delResult", "notexit");
        }
        //把resultMap转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

}

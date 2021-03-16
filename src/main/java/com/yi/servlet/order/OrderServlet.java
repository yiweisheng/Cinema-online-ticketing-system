package com.yi.servlet.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import com.yi.pojo.*;
import com.yi.service.customer.CustomerService;
import com.yi.service.customer.CustomerServiceImpl;
import com.yi.service.customerClass.CustomerClassService;
import com.yi.service.customerClass.CustomerClassServiceImpl;
import com.yi.service.hall.HallService;
import com.yi.service.hall.HallServiceImpl;
import com.yi.service.movie.MovieService;
import com.yi.service.movie.MovieServiceImpl;
import com.yi.service.order.OrderService;
import com.yi.service.order.OrderServiceImpl;
import com.yi.service.schedule.ScheduleService;
import com.yi.service.schedule.ScheduleServiceImpl;
import com.yi.service.seat.SeatService;
import com.yi.service.seat.SeatServiceImpl;
import com.yi.util.Constants;
import com.yi.util.PageSupport;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class OrderServlet extends HttpServlet {

    public OrderServlet() {
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
            //获取订单列表
            this.query(req, resp);
        } else if (method != null && method.equals("delorder")) {
            //删除订单表记录
            this.delOrder(req, resp);
        } else if (method != null && method.equals("view")) {
            //通过订单表ID查看订单表详细信息
            this.getOrderById(req, resp, "orderview.jsp");
        } else if (method != null && method.equals("modify")) {
            //通过订单表ID查看订单表详细信息
            this.getOrderById(req, resp, "ordermodify.jsp");
        } else if (method != null && method.equals("modifysave")) {
            //修改时间表信息执行操作
            this.modify(req, resp);
        } else if (method != null && method.equals("selectseat")) {
            //选座
            this.selectseat(req, resp);
        }else if (method != null && method.equals("calculationTotalPrice")) {
            //计算应付票价
            this.calculationTotalPrice(req, resp);
        }else if (method != null && method.equals("cuorderlist")) {
            //查询登录用户下的订单
            this.cuorderlist(req, resp);
        }else if (method != null && method.equals("taketicketjump")) {
            //取票页面跳转
            this.taketicketjump(req, resp);
        }else if (method != null && method.equals("taketicket")) {
            //取票操作
            this.taketicket(req, resp);
        }
    }
    //获取订单列表
    private void query(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("OrderServlet->query");
        String Customer_ID = request.getParameter("Customer_ID");
        String Schedule_ID = request.getParameter("Schedule_ID");
        String temp = request.getParameter("Order_IsActive");
        String pageIndex = request.getParameter("pageIndex");
        OrderService orderService = new OrderServiceImpl();
        int Order_IsActive = -1;
        if (temp != null && !temp.equals("")) {
            Order_IsActive = Integer.parseInt(temp);//给查询赋值
        }

        //获取订单列表
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
        int totalCount = orderService.getOrderCount(Customer_ID, Schedule_ID, Order_IsActive);
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

        if (StringUtils.isNullOrEmpty(Customer_ID)) {
            Customer_ID = "";
        }
        if (StringUtils.isNullOrEmpty(Schedule_ID)) {
            Schedule_ID = "";
        }
        List<Order> orderList = new ArrayList<Order>();
        orderList = orderService.getOrderList(Customer_ID, Schedule_ID, Order_IsActive, currentPageNo, pageSize);

        List<OrderIsActive> orderIsActiveList = new ArrayList<OrderIsActive>();
        OrderIsActive orderIsActive1 = new OrderIsActive();
        orderIsActive1.setIsActive(0);
        orderIsActive1.setName("不可用");
        orderIsActiveList.add(orderIsActive1);
        OrderIsActive orderIsActive2 = new OrderIsActive();
        orderIsActive2.setIsActive(1);
        orderIsActive2.setName("可用");
        orderIsActiveList.add(orderIsActive2);


        request.setAttribute("orderList", orderList);
        request.setAttribute("Customer_ID", Customer_ID);
        request.setAttribute("Schedule_ID", Schedule_ID);
        request.setAttribute("Order_IsActive", Order_IsActive);
        request.setAttribute("orderIsActiveList", orderIsActiveList);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPageNo", currentPageNo);
        request.setAttribute("totalPageCount", totalPageCount);
        request.getRequestDispatcher("orderlist.jsp").forward(request, response);
    }
    //删除订单表记录
    private void delOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("OrderServlet->delOrder");
        String order_ID = request.getParameter("order_ID");
        System.out.println("order_ID:" + order_ID);

        HashMap<String, String> resultMap = new HashMap<String, String>();

        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        System.out.println("order_ID是否为数字：" + pattern.matcher(order_ID).matches());
        if (!pattern.matcher(order_ID).matches()) {
            resultMap.put("delResult", "notexist");
        } else {
            OrderService orderService = new OrderServiceImpl();
            int flag = orderService.deleteOrderById(order_ID);
            System.out.println("flag" + flag);
            if (flag == 0) {
                resultMap.put("delResult", "true");
            } else if (flag == -1) {//删除失败
                resultMap.put("delResult", "false");
            } else if (flag > 0) {//该订单依然有效，不可删除
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
    //通过订单表ID查看时间表详细信息
    //修改订单表信息页面跳转
    private void getOrderById(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        System.out.println("OrderServlet-->getOrderById");
        String id = request.getParameter("order_ID");
        if (!StringUtils.isNullOrEmpty(id)) {
            OrderService orderService = new OrderServiceImpl();
            Order order = null;
            order = orderService.getOrderById(id);
            request.setAttribute("order", order);
            if (url.equals("ordermodify.jsp")) {
                List<OrderIsActive> orderIsActiveList = new ArrayList<OrderIsActive>();
                OrderIsActive orderIsActive1 = new OrderIsActive();
                orderIsActive1.setIsActive(0);
                orderIsActive1.setName("不可用");
                orderIsActiveList.add(orderIsActive1);
                OrderIsActive orderIsActive2 = new OrderIsActive();
                orderIsActive2.setIsActive(1);
                orderIsActive2.setName("可用");
                orderIsActiveList.add(orderIsActive2);
                request.setAttribute("orderIsActiveList", orderIsActiveList);
                request.setAttribute("Order_IsActive", order.getOrder_IsActive());
            }
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    //修改订单表信息执行操作
    private void modify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("OrderServlet-->modify");
        BigInteger Order_ID = BigInteger.valueOf(Long.parseLong(request.getParameter("Order_ID")));
        BigInteger Customer_ID = BigInteger.valueOf(Long.parseLong(request.getParameter("Customer_ID")));
        BigInteger Schedule_ID = BigInteger.valueOf(Long.parseLong(request.getParameter("Schedule_ID")));
        String Seat_ID = request.getParameter("Seat_ID");
        int Order_IsActive = Integer.valueOf(request.getParameter("Order_IsActive"));
        Float Order_AdjustedPrice = Float.parseFloat(request.getParameter("Order_AdjustedPrice"));
        String Order_BuyDate = request.getParameter("Order_BuyDate");


        if (Order_BuyDate.equals("") || null == Order_BuyDate) {
            Order_BuyDate = "1970-01-01 00:00:00";
        }
        //转化为时间格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp dateTime = null;
        dateTime = Timestamp.valueOf(Order_BuyDate);

        Order order = new Order();
        order.setOrder_ID(Order_ID);
        order.setCustomer_ID(Customer_ID);
        order.setSchedule_ID(Schedule_ID);
        order.setSeat_ID(Seat_ID);
        order.setOrder_IsActive(Order_IsActive);
        order.setOrder_AdjustedPrice(Order_AdjustedPrice);
        order.setOrder_BuyDate(dateTime);

        boolean flag = false;
        OrderService orderService = new OrderServiceImpl();
        flag = orderService.modify(order);

        if (flag) {
            response.sendRedirect(request.getContextPath() + "/jsp/order.do?method=query");
        } else {
            request.getRequestDispatcher("ordermodify.jsp").forward(request, response);
        }
    }
    //添加订单
    private void selectseat(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("OrderServlet-->selectset");
        String seatlistR = request.getParameter("seatlist");
        String hall_ID = request.getParameter("hall_ID");
        String schedule_ID = request.getParameter("schedule_ID");
        List<Seat> seatList = null;   //座位列表
        Customer customer = null;   //会员
        Schedule schedule = null;//时间表
        CustomerClass customerClass = null;//会员等级
        Movie movie = null;//电影
        float totalPrice = 0;//总票价
        Seat seat = null;//座位
        boolean flage = false;//判断订单是否生成成功
        SeatService seatService = new SeatServiceImpl();
        ScheduleService scheduleService = new ScheduleServiceImpl();
        CustomerClassService customerClassService = new CustomerClassServiceImpl();
        MovieService movieService = new MovieServiceImpl();
        HallService hallService = new HallServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        Map map = new HashMap();//用于存储传递给js的数据
        String sKey="";


        seatList = seatService.getSeatList(hall_ID);
        customer = (Customer) request.getSession().getAttribute(Constants.CUSTOMER_SESSION);
        int class_ID = customer.getClass_ID();
        System.out.println("class_ID:" + class_ID);
        schedule = scheduleService.getScheduleById(schedule_ID);
        customerClass = customerClassService.getCustomerClassById(class_ID);
        movie = movieService.getMovieById(schedule.getMovie_ID());

        //剔除seatList中的过道
        for (int i = 0; i < seatList.size(); i++) {
            if (seatList.get(i).getSeat_IsCorridor() == 1) {
                seatList.remove(i--);
            }
        }


        //订单生成
        /*
        1.通过orderService生成订单
        2.通过前端返回座位列表得到应被预定的座位ID
        3.将预定的座位置为以预定，即不可使用状态Seat_IsActive=0
        4.计算打折后的票价
        5.计算总票价
         */


        String[] seatarray = seatlistR.substring(1, seatlistR.length() - 1).split(",");
        for (int i = 0; i < seatarray.length; i++) {
            seatarray[i] = seatarray[i].replace("\"", "");
            System.out.println("seatarray[" + i + "]=" + seatarray[i]);
            seat = seatList.get(Integer.parseInt(seatarray[i]) - 1);//获取对应座位
            BigInteger Customer_ID = customer.getCustomer_ID();     //会员ID
            BigInteger Schedule_ID = schedule.getSchedule_ID();     //时间表ID
            String Seat_ID = seat.getSeat_ID();         //座位ID
            int Order_IsActive = 1;     //订单是否可用

            /*
            返回购票凭证，即经过随机生成的6位字符
            加密使用该字符串加密
            加密数据为用户ID和订单ID拼接的字符
            用户取票时使用该凭证取票
            若解密成功则成功取票
            */

            Encryption encryption = new Encryption();
            sKey= encryption.generateRandomStr(6);
            String lsKey = "0000000000" + sKey;
            String cSrc = Customer_ID.toString()+Schedule_ID.toString()+Seat_ID;
            //加密购票数据，并返回购票凭证
            String encrypt = encryption.encrypt(cSrc, lsKey);
            System.out.println("encrypt:" + encrypt);

            String decrypt = encryption.decrypt(encrypt, lsKey);
            System.out.println("decrypt:" + decrypt);


            float Order_AdjustedPrice = schedule.getSchedule_price() * customerClass.getClass_Discount();//会员打折之后的价格
            //获取当前时间日期
            LocalDateTime dateTime = LocalDateTime.now(); // get the current date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Timestamp Order_BuyDate = Timestamp.valueOf(dateTime.format(formatter));    //购买时间

            Order order = new Order();
            order.setCustomer_ID(Customer_ID);
            order.setSchedule_ID(Schedule_ID);
            order.setSeat_ID(Seat_ID);
            order.setOrder_IsActive(Order_IsActive);
            order.setOrder_SecretKey(sKey);
            order.setOrder_Cipher(encrypt);
            order.setOrder_AdjustedPrice(Order_AdjustedPrice);
            order.setOrder_BuyDate(Order_BuyDate);

            boolean flageadd = orderService.add(order);
            if (flageadd) {
                flage = seatService.setSeatIsActiveFalse(seat.getSeat_ID());
                if (flage) {
                    System.out.println("座位ID:" + Integer.parseInt(seatarray[i]) + " 预定成功");
                }
            }
        }
        totalPrice = schedule.getSchedule_price() * customerClass.getClass_Discount()* seatarray.length;
        System.out.println("totalPrice:" + totalPrice);


        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (flage) {
            map.put("GenerateResults", "true");
            map.put("sKey",sKey);
        } else {
            map.put("GenerateResults", "false");
        }
        //把resultMap转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(map));
        outPrintWriter.flush();
        outPrintWriter.close();

    }
    //计算应付票价
    private void calculationTotalPrice(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("OrderServlet-->calculationTotalPrice");
        String seatlistR = request.getParameter("seatlist");
        String schedule_ID = request.getParameter("schedule_ID");
        Customer customer = null;               //会员
        Schedule schedule = null;               //时间表
        CustomerClass customerClass = null;     //会员等级
        float totalPrice = -1;                   //总票价
        ScheduleService scheduleService = new ScheduleServiceImpl();
        CustomerClassService customerClassService = new CustomerClassServiceImpl();

        schedule = scheduleService.getScheduleById(schedule_ID);    //获取时间表详细信息
        customer = (Customer) request.getSession().getAttribute(Constants.CUSTOMER_SESSION);//获取当前用户
        int class_ID = customer.getClass_ID();                      //获取当前用户等级
        customerClass = customerClassService.getCustomerClassById(class_ID);    //获取用户等级对应的折扣比例
        String[] seatarray = seatlistR.substring(1, seatlistR.length() - 1).split(","); //获取选座列表

        totalPrice = schedule.getSchedule_price() * customerClass.getClass_Discount()* seatarray.length;    //计算总票价
        System.out.println("totalPrice:" + totalPrice);

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (totalPrice!=-1) {
            resultMap.put("totalPrice", Float.toString(totalPrice));
        } else {
            resultMap.put("totalPrice","isNull");
        }
        //把resultMap转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }
    //查询登录用户下的订单
    private void cuorderlist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("OrderServlet->cuorderlist");
        //从session中获取用户
        Customer customer = (Customer)request.getSession().getAttribute(Constants.CUSTOMER_SESSION);
        String Customer_ID = String.valueOf(customer.getCustomer_ID());
        String temp = request.getParameter("Order_IsActive");
        String pageIndex = request.getParameter("pageIndex");
        OrderService orderService = new OrderServiceImpl();
        int Order_IsActive = -1;
        if (temp != null && !temp.equals("")) {
            Order_IsActive = Integer.parseInt(temp);//给查询赋值
        }

        //获取订单列表
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
        int totalCount = orderService.getCuOrderCount(Customer_ID,  Order_IsActive);
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
        if (StringUtils.isNullOrEmpty(Customer_ID)) {
            Customer_ID = "";
        }
        List<Order> orderList = new ArrayList<Order>();
        orderList = orderService.getCuOrderList(Customer_ID, Order_IsActive, currentPageNo, pageSize);

        List<OrderIsActive> orderIsActiveList = new ArrayList<OrderIsActive>();
        OrderIsActive orderIsActive1 = new OrderIsActive();
        orderIsActive1.setIsActive(0);
        orderIsActive1.setName("不可用");
        orderIsActiveList.add(orderIsActive1);
        OrderIsActive orderIsActive2 = new OrderIsActive();
        orderIsActive2.setIsActive(1);
        orderIsActive2.setName("可用");
        orderIsActiveList.add(orderIsActive2);


        request.setAttribute("orderList", orderList);
        request.setAttribute("Order_IsActive", Order_IsActive);
        request.setAttribute("orderIsActiveList", orderIsActiveList);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPageNo", currentPageNo);
        request.setAttribute("totalPageCount", totalPageCount);
        request.getRequestDispatcher("customerorderlist.jsp").forward(request, response);
    }
    //取票页面跳转
    private void taketicketjump(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("OrderServlet->taketicketjump");
        String id = request.getParameter("order_ID");
        if (!StringUtils.isNullOrEmpty(id)) {
            OrderService orderService = new OrderServiceImpl();
            Order order = null;
            order = orderService.getOrderById(id);
            Customer customer = (Customer)request.getSession().getAttribute(Constants.CUSTOMER_SESSION);
            int class_id = customer.getClass_ID();
            if(order.getOrder_IsActive()!=1){
                if (class_id==5){
                    response.sendRedirect(request.getContextPath() + "/jsp/admin/order.do?method=cuorderlist");
                    return;
                }else{
                    response.sendRedirect(request.getContextPath() + "/jsp/order.do?method=cuorderlist");
                    return;
                }
            }
            request.setAttribute("order", order);
            request.getRequestDispatcher("taketicket.jsp").forward(request, response);
        }
    }
    //取票操作
    private void taketicket(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("OrderServlet->taketicket");
        Customer customer = (Customer)request.getSession().getAttribute(Constants.CUSTOMER_SESSION);
        int class_id = customer.getClass_ID();
        String order_ID = request.getParameter("Order_ID");
        String Order_SecretKey = request.getParameter("Order_SecretKey");
        OrderServiceImpl orderService = new OrderServiceImpl();
        Order order = orderService.getOrderById(order_ID);


        String order_cipher = order.getOrder_Cipher();
        String LOrder_SecretKey="0000000000"+Order_SecretKey;
        System.out.println("order_cipher:"+order_cipher);
        System.out.println("LOrder_SecretKey:"+LOrder_SecretKey);
        Encryption encryption = new Encryption();
        String decrypt = encryption.decrypt(order_cipher, LOrder_SecretKey);

        boolean flage=false;
        String plaintext=order.getCustomer_ID().toString()+order.getSchedule_ID().toString()+order.getSeat_ID();
        flage=plaintext.equals(decrypt);

        //设置该订单的Order_IsActive为0，即不可用状态
        if(flage){
            flage = orderService.changeOrder_IsActive(order);
        }
//        System.out.println("Customer_ID:"+Customer_ID);
//        System.out.println("order_ID:"+order_ID);
//        System.out.println("Order_SecretKey:"+Order_SecretKey);
//        System.out.println("decrypt:"+decrypt);
//        System.out.println("flage:"+flage);

        if(flage){
            if (class_id==5){
                response.sendRedirect(request.getContextPath() + "/jsp/admin/order.do?method=cuorderlist");
            }else{
                response.sendRedirect(request.getContextPath() + "/jsp/order.do?method=cuorderlist");
            }
        }else{
            request.getRequestDispatcher("taketicketerror.jsp").forward(request, response);
        }
    }
    @Test
    public void test() {
        Encryption encryption = new Encryption();
        String sKey = encryption.generateRandomStr(6);
        System.out.println("sKey:"+sKey);
        String lsKey = "0000000000" + sKey;
        String encrypt = encryption.encrypt("000010001", lsKey);
        System.out.println("encrypt:" + encrypt);
        String decrypt = encryption.decrypt("0GkLAq7k3xzrD0WVBIaSOw==", "0000000000pBLfcw");
        System.out.println("decrypt:" + decrypt);
    }
}

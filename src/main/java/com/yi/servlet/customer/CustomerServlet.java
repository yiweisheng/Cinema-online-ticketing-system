package com.yi.servlet.customer;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.yi.pojo.*;
import com.yi.service.customer.CustomerService;
import com.yi.service.customer.CustomerServiceImpl;
import com.yi.service.customerClass.CustomerClassService;
import com.yi.service.customerClass.CustomerClassServiceImpl;
import com.yi.util.Constants;
import com.yi.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class CustomerServlet extends HttpServlet {

    public CustomerServlet() {
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

        if(method!=null&&method.equals("query")){
            //查询会员列表
            this.query(req, resp);
        }else if(method != null && method.equals("add")){
            //增加操作
            this.add(req, resp);
        }else if(method != null && method.equals("customeradd")){
            //增加操作
            this.add(req, resp);
        }else if(method != null && method.equals("getclasslist")){
            //获取会员等级列表
            this.getClassList(req, resp);
        }else if(method != null && method.equals("customerexist")){
            //判断会员名是否已存在
            this.customerNameExist(req, resp);
        }else if(method != null && method.equals("view")) {
            //通过会员ID查看会员详细信息
            this.getCustomerById(req, resp, "customerview.jsp");
        }else if(method != null && method.equals("modify")) {
            //修改会员信息页面跳转
            this.getCustomerById(req, resp, "customermodify.jsp");
        }else if(method != null && method.equals("modifysave")){
            //修改会员信息执行操作
            this.modify(req, resp);
        }else if(method != null && method.equals("delcustomer")){
            //删除时间表记录
            this.delCustomer(req, resp);
        }else if(method != null && method.equals("oldpasswordverify")){
            //判断会员名是否与登录会员一致或是否已存在
            this.oldPasswordVerify(req, resp);
        }else if(method != null && method.equals("cu_customerexist")){
            //判断会员名是否与登录会员一致或是否已存在
            this.cu_customerNameExist(req, resp);
        }else if(method != null && method.equals("cu_modifysave")){
            //会员自己信息执行操作
            this.cu_modifysave(req, resp);
        }
    }

    //查询会员列表
    private void query(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //查询用户列表

        //从前端获取数据
        String Customer_ID = request.getParameter("Customer_ID");
        String Customer_Name = request.getParameter("Customer_Name");
        String temp = request.getParameter("Class_IDRole");
        String pageIndex = request.getParameter("pageIndex");
        int Class_IDRole = 0;

        //获取用户列表

        CustomerService customerService = new CustomerServiceImpl();

        //第一次走页面一定是第一页,页面大小固定的
        List<Customer> customerList = null;
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
        System.out.println("Customer_ID servlet--------"+Customer_ID);
        System.out.println("Customer_Name servlet--------"+Customer_Name);
        System.out.println("Class_IDRole servlet--------"+temp);
        System.out.println("query pageIndex--------- > " + pageIndex);
        if(Customer_ID == null){
            Customer_ID = "";
        }
        if(Customer_Name == null){
            Customer_Name = "";
        }
        if(temp != null && !temp.equals("")){
            Class_IDRole = Integer.parseInt(temp);//给查询赋值
        }

        if(pageIndex != null){
            try{
                currentPageNo = Integer.valueOf(pageIndex);
            }catch(NumberFormatException e){
                response.sendRedirect("error.jsp");
            }
        }
        //总数量（表）
        int totalCount	= customerService.getCustomerCount(Customer_ID,Customer_Name,Class_IDRole);
        //总页数
        PageSupport pages=new PageSupport();

        pages.setCurrentPageNo(currentPageNo);

        pages.setPageSize(pageSize);

        pages.setTotalCount(totalCount);

        int totalPageCount = pages.getTotalPageCount();

        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }


        customerList = customerService.getCustomerList(Customer_ID,Customer_Name,Class_IDRole,currentPageNo, pageSize);
        request.setAttribute("customerList", customerList);
        List<CustomerClass> customerClassList = null;
        CustomerClassService customerClassService = new CustomerClassServiceImpl();
        customerClassList = customerClassService.getcustomerClassList();
        request.setAttribute("customerClassList", customerClassList);
        request.setAttribute("Customer_ID", Customer_ID);
        request.setAttribute("Customer_Name", Customer_Name);
        request.setAttribute("Class_IDRole", Class_IDRole);
        request.setAttribute("totalPageCount", totalPageCount);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPageNo", currentPageNo);
        request.getRequestDispatcher("customerlist.jsp").forward(request, response);
    }
    //增加操作
    private void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("add()================");
        String Customer_Name = request.getParameter("Customer_Name");
        String Customer_Email = request.getParameter("Customer_Email");
        String Customer_Mobile = request.getParameter("Customer_Mobile");
        String Customer_PassWord = request.getParameter("Customer_PassWord");
        String rCustomer_PassWord = request.getParameter("rCustomer_PassWord");
        String Class_ID = request.getParameter("Class_IDRole");

        //获取当前时间日期
        LocalDateTime dateTime = LocalDateTime.now(); // get the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp Customer_CreationDate = Timestamp.valueOf(dateTime.format(formatter));
        Timestamp Customer_ModifyDate =  Timestamp.valueOf(dateTime.format(formatter));

//        System.out.println(dateTime.format(formatter));

        Customer customer = new Customer();
//        customer.setCustomer_ID(null);
        customer.setCustomer_Name(Customer_Name);
        customer.setCustomer_Email(Customer_Email);
        customer.setCustomer_Mobile(Customer_Mobile);
        customer.setPassWord(Customer_PassWord);
        customer.setClass_ID(Integer.parseInt(Class_ID));
        customer.setCustomer_CreationDate(Customer_CreationDate);
        customer.setCustomer_ModifyDate(Customer_ModifyDate);

        CustomerService customerService = new CustomerServiceImpl();
        if(customerService.add(customer)){
            response.sendRedirect(request.getContextPath()+"/jsp/customer.do?method=query");
        }else{
            request.getRequestDispatcher("customeradd.jsp").forward(request, response);
        }

    }
    //获取会员等级列表
    private void getClassList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<CustomerClass> customerClassList = null;
        CustomerClassService customerClassService = new CustomerClassServiceImpl();
        customerClassList = customerClassService.getcustomerClassList();
        //把roleList转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(customerClassList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }
    //判断会员名是否已存在
    private void customerNameExist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("CustomerServlet->customerNameExist");
        //判断用户账号是否可用
        String customer_Name = request.getParameter("customer_Name");

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(customer_Name)){
            //userCode == null || userCode.equals("")
            resultMap.put("customer_Name", "isnull");
        }else{
            CustomerService customerService = new CustomerServiceImpl();
            Customer customer = customerService.selectCustomerNameExist(customer_Name);
            if(null != customer){
                resultMap.put("customer_Name","exist");
            }else{
                resultMap.put("customer_Name", "notexist");
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
    //通过会员ID查看会员详细信息
    //修改会员表信息页面跳转
    private void getCustomerById(HttpServletRequest request, HttpServletResponse response,String url)
            throws ServletException, IOException {
        System.out.println("CustomerServlet-->getCustomerById");
        System.out.println("url:"+url);
        String id = request.getParameter("customer_ID");
        if(!StringUtils.isNullOrEmpty(id)){
            CustomerService customerService = new CustomerServiceImpl();
            Customer customer = null;
            customer = customerService.getCustomerById(id);
            request.setAttribute("customer", customer);
            if (url.equals("customermodify.jsp")){
                request.setAttribute("Class_ID", customer.getClass_ID());
            }
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    //修改会员信息执行操作
    private void modify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("CustomerServlet-->modify");
        String Customer_ID = request.getParameter("Customer_ID");
        String Customer_Name = request.getParameter("Customer_Name");
        String Customer_Email = request.getParameter("Customer_Email");
        String Customer_Mobile = request.getParameter("Customer_Mobile");
        String Class_IDRole = request.getParameter("Class_IDRole");

        //获取当前时间日期
        LocalDateTime dateTime = LocalDateTime.now(); // get the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp Customer_ModifyDate = Timestamp.valueOf(dateTime.format(formatter));

        Customer customer = new Customer();
        customer.setCustomer_ID(new BigInteger(Customer_ID));
        customer.setCustomer_Name(Customer_Name);
        customer.setCustomer_Email(Customer_Email);
        customer.setCustomer_Mobile(Customer_Mobile);
        customer.setClass_ID(Integer.parseInt(Class_IDRole));
        customer.setCustomer_ModifyDate(Customer_ModifyDate);

        boolean flag = false;
        CustomerService customerService = new CustomerServiceImpl();
        flag = customerService.modify(customer);
        if(flag){
            response.sendRedirect(request.getContextPath()+"/jsp/schedule.do?method=query");
        }else{
            request.getRequestDispatcher("schedulemodify.jsp").forward(request, response);
        }
    }
    //删除会员表记录
    private void delCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("CustomerServlet->delCustomer");
        String customer_ID = request.getParameter("customer_ID");
        System.out.println("customer_ID:"+customer_ID);

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(!customer_ID.matches("[0-9]")){
            resultMap.put("delResult", "notexist");
        }else{
            CustomerService customerService = new CustomerServiceImpl();
            int flag = customerService.deleteCustomerById(customer_ID);
            System.out.println("flag"+flag);
            if(flag== 0){
                resultMap.put("delResult", "true");
            }else if(flag == -1){//删除失败
                resultMap.put("delResult", "false");
            }else if(flag > 0){//该会员下有有效订单，不能删除，返回订单数
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
    //判断旧密码与会员名是否匹配
    private void oldPasswordVerify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("CustomerServlet->oldPasswordVerify");
        String cu_oldpassword = request.getParameter("cu_oldpassword");
        //从session中获取用户
        Customer customer = (Customer)request.getSession().getAttribute(Constants.CUSTOMER_SESSION);
        //判断密码是否匹配
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(cu_oldpassword)){
            //userCode == null || userCode.equals("")
            resultMap.put("cu_oldpassword", "isnull");
        }else{
            if(customer.getPassWord().equals(cu_oldpassword)){
                resultMap.put("cu_oldpassword","matchesSuccessful");
            }else{
                resultMap.put("cu_oldpassword","matchesFalse");
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
    //判断会员名是否已存在
    private void cu_customerNameExist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("CustomerServlet->cu_customerNameExist");
        //判断用户账号是否可用
        String newcustomer_Name = request.getParameter("customer_Name");
        Customer session_customer = (Customer)request.getSession().getAttribute(Constants.CUSTOMER_SESSION);

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(newcustomer_Name)){//用户名为空
            //userCode == null || userCode.equals("")
            resultMap.put("newcustomer_Name", "isnull");
        }else{
            if(newcustomer_Name.equals(session_customer.getCustomer_Name())){//用户名和登录用户名一致
                resultMap.put("newcustomer_Name", "notexist");
            }else{
                CustomerService customerService = new CustomerServiceImpl();
                Customer customer = customerService.selectCustomerNameExist(newcustomer_Name);
                if(null == customer){//用户名与登录用户名不一致，但数据库中不存在
                    resultMap.put("newcustomer_Name", "notexist");
                }else{//用户名与登录用户名不一致，且数据库中存在
                    resultMap.put("newcustomer_Name","exist");
                }
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
    //会员自己信息执行操作
    private void cu_modifysave(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("CustomerServlet-->cu_modifysave");
        Customer session_customer = (Customer)request.getSession().getAttribute(Constants.CUSTOMER_SESSION);
        BigInteger Customer_ID = session_customer.getCustomer_ID();
        String Customer_Name = request.getParameter("Customer_Name");
        String Customer_Email = request.getParameter("Customer_Email");
        String Customer_Mobile = request.getParameter("Customer_Mobile");
        String Customer_NewPassword = request.getParameter("Customer_NewPassword");

        //获取当前时间日期
        LocalDateTime dateTime = LocalDateTime.now(); // get the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp Customer_ModifyDate = Timestamp.valueOf(dateTime.format(formatter));

        if(Customer_Email.equals("")){
            Customer_Email=session_customer.getCustomer_Email();
        }
        if (Customer_Mobile.equals("")){
            Customer_Mobile=session_customer.getCustomer_Mobile();
        }
        if (Customer_NewPassword.equals("")){
            Customer_NewPassword=session_customer.getPassWord();
        }




        Customer customer = new Customer();
        customer.setCustomer_ID(Customer_ID);
        customer.setCustomer_Name(Customer_Name);
        customer.setCustomer_Email(Customer_Email);
        customer.setCustomer_Mobile(Customer_Mobile);
        customer.setPassWord(Customer_NewPassword);
        customer.setCustomer_ModifyDate(Customer_ModifyDate);

        boolean flag = false;
        CustomerService customerService = new CustomerServiceImpl();
        flag = customerService.cu_modify(customer);
        if(flag){
            //清除session
            request.getSession().removeAttribute(Constants.CUSTOMER_SESSION);
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }else{
            request.getRequestDispatcher("cu_customermodify.jsp").forward(request, response);
        }
    }

}

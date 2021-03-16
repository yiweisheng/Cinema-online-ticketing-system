package com.yi.servlet.customer;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.yi.pojo.Customer;
import com.yi.service.customer.CustomerService;
import com.yi.service.customer.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class RegisteredServlet  extends HttpServlet {
    public RegisteredServlet() {
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

        if(method!=null&&method.equals("registered")){
            //查询会员列表
            this.registered(req, resp);
        }else if(method != null && method.equals("customerexist")){
            //判断会员名是否已存在
            this.customerNameExist(req, resp);
        }
    }

    //用户注册
    private void registered(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("registered()================");
        String Customer_Name = request.getParameter("rCustomer_Name");
        String Customer_Email = request.getParameter("rCustomer_Email");
        String Customer_Mobile = request.getParameter("rCustomer_Mobile");
        String Customer_PassWord = request.getParameter("register-password");
        String rCustomer_PassWord = request.getParameter("register-repassword");
        String Class_ID = request.getParameter("rClass_IDRole");

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
//            response.sendRedirect(request.getContextPath()+"/jsp/customer.do?method=query");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("customeradd.jsp").forward(request, response);
        }

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

}

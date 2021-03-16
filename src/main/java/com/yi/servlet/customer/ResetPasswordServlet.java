package com.yi.servlet.customer;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.yi.pojo.Customer;
import com.yi.service.customer.CustomerService;
import com.yi.service.customer.CustomerServiceImpl;
import com.yi.util.Constants;

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
import java.util.Random;

public class ResetPasswordServlet extends HttpServlet {

    public ResetPasswordServlet() {
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

        if(method!=null&&method.equals("resetpassword")){
            //查询会员列表
            this.resetpassword(req, resp);
        }
    }


    //密码重置
    private void resetpassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("resetpassword()================");
        String Customer_Name = request.getParameter("forget-username");
        String Customer_Mobile = request.getParameter("forget-phone");


        //调用service方法，进行用户匹配
        CustomerService customerService = new CustomerServiceImpl();
        Customer customer = customerService.confirmphonenumber(Customer_Name,Customer_Mobile);
        //登录成功
        if(null != customer){
            String newPassword="";
            newPassword=getRandomString(6);
            //重置密码
            boolean flage = customerService.resetpassword(Customer_Name, newPassword);
            if (flage){
                //页面跳转（login.jsp）带出提示信息--转发
                request.setAttribute("error", "新密码为："+newPassword);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }else{
            //页面跳转（login.jsp）带出提示信息--转发
            request.setAttribute("error", "用户名或手机号不正确");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

    }
    //生成随机字符串
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}

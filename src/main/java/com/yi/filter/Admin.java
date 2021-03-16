package com.yi.filter;

import com.yi.pojo.Customer;
import com.yi.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Admin implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //过滤器，从session中获取用户
        Customer customer = (Customer)request.getSession().getAttribute(Constants.CUSTOMER_SESSION);
        int class_id = customer.getClass_ID();
        System.out.println("class_id:"+class_id);
        if(class_id==5){//用户等级为管理员
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            response.sendRedirect("/smbms/error.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}

package com.yi.filter;

import com.yi.pojo.Customer;
import com.yi.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //过滤器，从session中获取用户
        Customer user = (Customer)request.getSession().getAttribute(Constants.CUSTOMER_SESSION);
        if(user==null){//以被移除或注销了，或未登录
            response.sendRedirect("/smbms/error.jsp");
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    public void destroy() {

    }
}

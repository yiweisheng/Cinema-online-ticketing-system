package com.yi.servlet.customer;

import com.yi.pojo.Customer;
import com.yi.service.customer.CustomerService;
import com.yi.service.customer.CustomerServiceImpl;
import com.yi.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("login ============ " );
		//获取用户名和密码
		String Customer_Name = request.getParameter("lCustomer_Name");
		String Customer_PassWord = request.getParameter("login-password");
		System.out.println("lCustomer_Name:"+Customer_Name);
		System.out.println("lCustomer_PassWord:"+Customer_PassWord);
		//调用service方法，进行用户匹配
		CustomerService customerService = new CustomerServiceImpl();
		Customer customer = customerService.login(Customer_Name,Customer_PassWord);
		//登录成功
		if(null != customer){
			//放入session
			request.getSession().setAttribute(Constants.CUSTOMER_SESSION, customer);
			if(customer.getClass_ID()==5){
				response.sendRedirect("jsp/admin/frame.jsp");
			}else{
				//页面跳转（frame.jsp）
				response.sendRedirect("jsp/frame.jsp");
			}
		}else{
			//页面跳转（login.jsp）带出提示信息--转发
			request.setAttribute("error", "用户名或密码不正确");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}

	}


}

package com.yi.servlet.customer;


import com.yi.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录，移除session中的用户对象Constants.USER_SESSION
 * */
public class LogoutServlet extends HttpServlet {

	public LogoutServlet() {
		super();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//清除session
		request.getSession().removeAttribute(Constants.CUSTOMER_SESSION);
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}

	@Override
	public void init() throws ServletException {

	}

}

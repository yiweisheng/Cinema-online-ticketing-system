package com.yi.servlet.test;

import com.yi.pojo.Images;
import com.yi.service.test.DBImageServiceTest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DBImageServletTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in DBImageServletTest");

        String userCode = req.getParameter("img");
        Images images = new Images();
        String imageSrc=null;

        DBImageServiceTest dbImageServiceTest = new DBImageServiceTest();
        try {
            imageSrc= dbImageServiceTest.getImageSrc();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("DBImageServletTest->imageSrc"+imageSrc);
        req.setAttribute("img", imageSrc);
        req.getRequestDispatcher("DBImageTest.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

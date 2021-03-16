package com.yi.service.test;

import com.yi.dao.BaseDao;
import com.yi.dao.test.DBImageDaoTest;
import com.yi.pojo.Images;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DBImageServiceTest {

    private DBImageDaoTest DBImageDaoTest;
    public DBImageServiceTest(){
        DBImageDaoTest = new DBImageDaoTest();
    }

    public String getImageSrc() throws SQLException {

        System.out.println("in DBImageServiceTest->getImageSrc");

        Connection connection = null;
        Images image = null;
        connection= BaseDao.getConnection();
        image = DBImageDaoTest.getImageSrc(connection);
        String imageSrc = image.getImageSrc();

        return imageSrc;

    }

    @Test
    public void test(){
        DBImageServiceTest dbImageServiceTest = new DBImageServiceTest();
        String imageSrc=null;
        try {
            imageSrc= dbImageServiceTest.getImageSrc();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("DBImageServiceTest->getImageSrc->test->imageSrc:"+imageSrc);
    }
}

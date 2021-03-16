package com.yi.dao.test;

import com.yi.dao.BaseDao;
import com.yi.pojo.Images;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBImageDaoTest {


    public Images getImageSrc(Connection connection) throws SQLException {

        System.out.println("in DBImageDaoTest->getImageSrc");

        Images images=null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        if(null != connection){
            String sql = "select * from images where imageId=?";
            Object[] params = {0001};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);

            if (rs.next()){
                images = new Images();
                images.setImageId(rs.getString("imageId"));
                images.setImageSrc(rs.getString("imageSrc"));
            }
            BaseDao.closeResource(connection, pstm, rs);
        }
        return images;
    }

    @Test
    public void test(){
        DBImageDaoTest dbImageDaoTest = new DBImageDaoTest();
        Images image=null;
        Connection connection = null;
        connection= BaseDao.getConnection();

        try {
            image=dbImageDaoTest.getImageSrc(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("imageTD:"+image.getImageId()+"imageSrc:"+image.getImageSrc());
    }
}

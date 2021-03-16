package com.yi.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//操作数据库的公共类
public class BaseDao {
    //连接smbms变量
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    //连接cots变量
    private static String mydriver;
    private static String myurl;
    private static String myusername;
    private static String mypassword;

    //静态代码块，类加载的时候就初始化了
    static {
        Properties properties = new Properties();
        //通过类加载器读取对应的资源，连接smbms
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver=properties.getProperty("driver");
        url=properties.getProperty("url");
        username=properties.getProperty("username");
        password=properties.getProperty("password");


        //通过类加载器读取对应的资源，连接cots
        InputStream myIs = BaseDao.class.getClassLoader().getResourceAsStream("mydb.properties");
        try {
            properties.load(myIs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mydriver=properties.getProperty("driver");
        myurl=properties.getProperty("url");
        myusername=properties.getProperty("username");
        mypassword=properties.getProperty("password");

    }

    //获取smbms数据库的链接
    public static Connection getConnection(){
        Connection connection=null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    //获取cots数据库的链接
    public static Connection getCotsConnection(){
        Connection Cotsconnection=null;
        try {
            Class.forName(mydriver);
            Cotsconnection = DriverManager.getConnection(myurl, myusername, mypassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Cotsconnection;
    }

    //编写查询公共方法
    public static ResultSet execute(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet,String sql,Object[] params) throws SQLException {
        //预编译的sql，在后面直接执行就可以了
        preparedStatement = connection.prepareStatement(sql);
        for(int i=0;i<params.length;i++){
            //setObject,占位符从1开始，但是我们的数组是从0开始的
            preparedStatement.setObject(i+1,params[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    //编写增删改公共方法
    public static int execute(Connection connection,PreparedStatement preparedStatement,String sql,Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        for(int i=0;i<params.length;i++){
            //setObject,占位符从1开始，但是我们的数组是从0开始的
            preparedStatement.setObject(i+1,params[i]);
        }
        int updateRows = preparedStatement.executeUpdate();
        return updateRows;
    }

    //释放资源
    public static boolean closeResource(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
        boolean flag=true;
        if (resultSet!=null){
            try {
                resultSet.close();
                //GC回收
                resultSet=null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag=false;
            }
        }

        if (preparedStatement!=null){
            try {
                preparedStatement.close();
                //GC回收
                preparedStatement=null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag=false;
            }
        }

        if (connection!=null){
            try {
                connection.close();
                //GC回收
                connection=null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag=false;
            }
        }

        return flag;
    }

}

package com.yi.dao.customer;

import com.mysql.jdbc.StringUtils;
import com.yi.dao.BaseDao;
import com.yi.pojo.Customer;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao{

    //根据条件查询会员表记录数
    @Override
    public int getCustomerCount(Connection connection, String Customer_ID, String Customer_Name, int Class_ID)
            throws Exception {
        System.out.println("CustomerDaoImpl->getCustomerCount");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from table_customer where Customer_ID is not null");
            List<Object> list = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(Customer_ID)){
                sql.append(" and Customer_ID like ?");
                list.add("%"+Customer_ID+"%");
            }
            if(!StringUtils.isNullOrEmpty(Customer_Name)){
                sql.append(" and Customer_Name like ?");
                list.add("%"+Customer_Name+"%");
            }
            if(Class_ID > 0){
                sql.append(" and u.Class_ID = ?");
                list.add(Class_ID);
            }
            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            if(rs.next()){
                count = rs.getInt("count");
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return count;
    }
    //根据条件查询会员列表
    @Override
    public List<Customer> getCustomerList(Connection connection, String Customer_ID, String Customer_Name, int Class_ID, int currentPageNo, int pageSize)
            throws Exception {
        System.out.println("CustomerDaoImpl->getCustomerList");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Customer> customerList = new ArrayList<Customer>();
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select * from table_customer where Customer_ID is not null ");
            List<Object> list = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(Customer_ID)){
                sql.append(" and Customer_ID like ?");
                list.add("%"+Customer_ID+"%");
            }
            if(!StringUtils.isNullOrEmpty(Customer_Name)){
                sql.append(" and Customer_Name like ?");
                list.add("%"+Customer_Name+"%");
            }
            if(Class_ID > 0){
                sql.append(" and Class_ID = ?");
                list.add(Class_ID);
            }
            sql.append(" order by Customer_ID asc limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            while(rs.next()){
                Customer _customer = new Customer();
                _customer.setCustomer_ID(BigInteger.valueOf(rs.getLong("Customer_ID")));
                _customer.setCustomer_Name(rs.getString("Customer_Name"));
                _customer.setCustomer_Email(rs.getString("Customer_Email"));
                _customer.setCustomer_Mobile(rs.getString("Customer_Mobile"));
                _customer.setPassWord(rs.getString("Customer_PassWord"));
                _customer.setClass_ID(rs.getInt("Class_ID"));
                _customer.setCustomer_CreationDate(rs.getTimestamp("Customer_CreationDate"));
                _customer.setCustomer_ModifyDate(rs.getTimestamp("Customer_ModifyDate"));
                customerList.add(_customer);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return customerList;
    }
    //判断会员名是否已存在
    @Override
    public Customer selectCustomerNameExist(Connection connection, String customer_Name)
            throws Exception {
        System.out.println("CustomerDaoImpl->selectCustomerNameExist");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Customer customer = null;
        if(null != connection){
            String sql = "select * from table_customer where Customer_Name=?";
            Object[] params = {customer_Name};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if(rs.next()){
                customer = new Customer();
                customer.setCustomer_ID(BigInteger.valueOf(rs.getLong("Customer_ID")));
                customer.setCustomer_Name(rs.getString("Customer_Name"));
                customer.setCustomer_Email(rs.getString("Customer_Email"));
                customer.setCustomer_Mobile(rs.getString("Customer_Mobile"));
                customer.setPassWord(rs.getString("Customer_PassWord"));
                customer.setClass_ID(rs.getInt("Class_ID"));
                customer.setCustomer_CreationDate(rs.getTimestamp("Customer_CreationDate"));
                customer.setCustomer_ModifyDate(rs.getTimestamp("Customer_ModifyDate"));
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return customer;
    }
    //增加会员表信息
    @Override
    public int add(Connection connection, Customer customer) throws Exception {
        System.out.println("CustomerDaoImpl->add");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        int updateRows = 0;
        if(null != connection){
            String sql = "insert into table_customer(Customer_Name, Customer_Email, Customer_Mobile, " +
                    "Customer_PassWord, Class_ID, Customer_CreationDate, Customer_ModifyDate)" +
                    "VALUES (?,?,?,?,?,?,?)";
            Object[] params = {customer.getCustomer_Name(),customer.getCustomer_Email(),customer.getCustomer_Mobile(),
                    customer.getPassWord(),customer.getClass_ID(), customer.getCustomer_CreationDate(),customer.getCustomer_ModifyDate()};
            updateRows = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }
    //通过会员ID查看会员详细信息
    @Override
    public Customer getCustomerById(Connection connection, String id) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("CustomerDaoImpl->getCustomerById");
        Customer customer = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if(null != connection){
            String sql = "select * from table_customer where Customer_ID=?;";

            Object[] params = {id};
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if(rs.next()){
                customer = new Customer();
                customer.setCustomer_ID(BigInteger.valueOf(rs.getLong("Customer_ID")));
                customer.setCustomer_Name(rs.getString("Customer_Name"));
                customer.setCustomer_Email(rs.getString("Customer_Email"));
                customer.setCustomer_Mobile(rs.getString("Customer_Mobile"));
                customer.setPassWord(rs.getString("Customer_PassWord"));
                customer.setClass_ID(rs.getInt("Class_ID"));
                customer.setCustomer_CreationDate(rs.getTimestamp("Customer_CreationDate"));
                customer.setCustomer_ModifyDate(rs.getTimestamp("Customer_ModifyDate"));
            }
            BaseDao.closeResource(connection, pstm, rs);
        }
        return customer;
    }
    //修改会员信息执行操作
    @Override
    public int modify(Connection connection, Customer customer) throws Exception {
        System.out.println("CustomerDaoImpl->modify");
        // TODO Auto-generated method stub
        int flag = 0;
        PreparedStatement pstm = null;
        if(null != connection){
            String sql = "update table_customer set Customer_Name=?,Customer_Email=?,Customer_Mobile=?,Class_ID=?,Customer_ModifyDate=? where Customer_ID=?";

            String Customer_Name = customer.getCustomer_Name();
            String Customer_Email = customer.getCustomer_Email();
            String Customer_Mobile = customer.getCustomer_Mobile();
            int Class_ID = customer.getClass_ID();
            Timestamp Customer_ModifyDate = customer.getCustomer_ModifyDate();
            BigInteger Customer_ID = customer.getCustomer_ID();

            System.out.println("Customer_Name:"+Customer_Name);
            System.out.println("Customer_Email:"+Customer_Email);
            System.out.println("Customer_Mobile:"+Customer_Mobile);
            System.out.println("Class_ID:"+Class_ID);
            System.out.println("Customer_ModifyDate:"+Customer_ModifyDate);
            System.out.println("Customer_ID:"+Customer_ID);


            Object[] params = {Customer_Name,Customer_Email,Customer_Mobile,Class_ID,Customer_ModifyDate,Customer_ID};
            System.out.println("sql ----> " + sql.toString());

            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }
    //删除会员表记录
    @Override
    public int deleteCustomerById(Connection connection, String delId) throws Exception {
        System.out.println("CustomerDaoImpl->deleteCustomerById");
        // TODO Auto-generated method stub
        System.out.println("delId:"+delId);
        PreparedStatement pstm = null;
        int flag = 0;
        if(null != connection){
            String sql = "delete from table_customer where Customer_ID=?";
            Object[] params = {delId};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }
    //会员登录
    @Override
    public Customer getLoginCustomer(Connection connection, String Customer_Name) throws Exception {
        System.out.println("CustomerDaoImpl->getLoginCustomer");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Customer customer = null;
        if(null != connection){
            String sql = "select * from table_customer where Customer_Name=?";
            Object[] params = {Customer_Name};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if(rs.next()){
                customer = new Customer();
                customer.setCustomer_ID(BigInteger.valueOf(rs.getLong("Customer_ID")));
                customer.setCustomer_Name(rs.getString("Customer_Name"));
                customer.setCustomer_Email(rs.getString("Customer_Email"));
                customer.setCustomer_Mobile(rs.getString("Customer_Mobile"));
                customer.setPassWord(rs.getString("Customer_PassWord"));
                customer.setClass_ID(rs.getInt("Class_ID"));
                customer.setCustomer_CreationDate(rs.getTimestamp("Customer_CreationDate"));
                customer.setCustomer_ModifyDate(rs.getTimestamp("Customer_ModifyDate"));
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return customer;
    }
    //重置密码
    @Override
    public int resetpassword(Connection connection, String Customer_Name, String newPassword) throws Exception {
        System.out.println("CustomerDaoImpl->modify");
        // TODO Auto-generated method stub
        int flag = 0;
        PreparedStatement pstm = null;
        if(null != connection){
            String sql = "update table_customer set Customer_PassWord=? where Customer_Name=?";
            Object[] params = {newPassword,Customer_Name};
            System.out.println("sql ----> " + sql.toString());
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }
    //会员自己信息执行操作
    @Override
    public int cu_modify(Connection connection, Customer customer) throws Exception {
        System.out.println("CustomerDaoImpl->modify");
        // TODO Auto-generated method stub
        int flag = 0;
        PreparedStatement pstm = null;
        if(null != connection){
            String sql = "update table_customer set Customer_Name=?,Customer_Email=?,Customer_Mobile=?," +
                    "Customer_PassWord=?,Customer_ModifyDate=?where Customer_ID=?";

            String Customer_Name = customer.getCustomer_Name();
            String Customer_Email = customer.getCustomer_Email();
            String Customer_Mobile = customer.getCustomer_Mobile();
            String Customer_NewPassword=customer.getPassWord();
            Timestamp Customer_ModifyDate = customer.getCustomer_ModifyDate();
            BigInteger Customer_ID = customer.getCustomer_ID();

            System.out.println("Customer_Name:"+Customer_Name);
            System.out.println("Customer_Email:"+Customer_Email);
            System.out.println("Customer_Mobile:"+Customer_Mobile);
            System.out.println("Customer_NewPassword:"+Customer_NewPassword);
            System.out.println("Customer_ModifyDate:"+Customer_ModifyDate);
            System.out.println("Customer_ID:"+Customer_ID);


            Object[] params = {Customer_Name,Customer_Email,Customer_Mobile,Customer_NewPassword,Customer_ModifyDate,Customer_ID};
            System.out.println("sql ----> " + sql.toString());

            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }
}

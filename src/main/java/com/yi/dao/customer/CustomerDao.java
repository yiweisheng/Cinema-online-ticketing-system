package com.yi.dao.customer;

import com.yi.pojo.Customer;

import java.sql.Connection;
import java.util.List;

public interface CustomerDao {

    //根据条件查询会员表记录数
    public int getCustomerCount(Connection connection, String Customer_ID, String Customer_Name, int Class_ID)throws Exception;
    //根据条件查询会员列表
    public List<Customer> getCustomerList(Connection connection,String Customer_ID, String Customer_Name, int Class_ID, int currentPageNo, int pageSize)throws Exception;
    //判断会员名是否已存在
    public Customer selectCustomerNameExist(Connection connection, String customer_Name)throws Exception;
    //增加会员表信息
    public int add(Connection connection, Customer customer)throws Exception;
    //通过会员ID查看会员详细信息
    public Customer getCustomerById(Connection connection, String id)throws Exception;
    //修改会员信息执行操作
    public int modify(Connection connection, Customer customer)throws Exception;
    //删除会员表记录
    public int deleteCustomerById(Connection connection, String delId)throws Exception;
    //会员登录
    public Customer getLoginCustomer(Connection connection, String Customer_Name)throws Exception;
    //重置密码
    public int resetpassword(Connection connection, String Customer_Name, String newPassword)throws Exception;
    //会员自己信息执行操作
    public int cu_modify(Connection connection, Customer customer)throws Exception;

}

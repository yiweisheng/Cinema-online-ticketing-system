package com.yi.service.customer;

import com.yi.pojo.Customer;

import java.util.List;

public interface CustomerService {
    //根据条件查询会员表记录数
    public int getCustomerCount(String Customer_ID,String Customer_Name, int Class_ID);
    //根据条件查询会员列表
    public List<Customer> getCustomerList(String Customer_ID,String Customer_Name, int Class_ID, int currentPageNo, int pageSize);
    //判断会员名是否已存在
    public Customer selectCustomerNameExist(String customer_Name);
    //增加会员表信息
    public boolean add(Customer customer);
    //通过会员ID查看会员详细信息
    public Customer getCustomerById(String id);
    //修改会员信息执行操作
    public boolean modify(Customer customer);
    //删除会员表记录
    public int deleteCustomerById(String delId);
    //会员登录
    public Customer login(String Customer_Name, String Customer_PassWord);
    //确认用户与注册手机号一致
    public Customer confirmphonenumber(String Customer_Name, String Customer_Mobile);
    //重置密码
    public boolean resetpassword(String Customer_Name, String newPassword);
    //会员自己信息执行操作
    public boolean cu_modify(Customer customer);

}

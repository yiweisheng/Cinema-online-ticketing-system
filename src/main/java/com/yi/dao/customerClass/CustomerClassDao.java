package com.yi.dao.customerClass;

import com.yi.pojo.CustomerClass;

import java.sql.Connection;
import java.util.List;

public interface CustomerClassDao {

    //获取会员等级列表
    public List<CustomerClass> CustomerClass(Connection connection)throws Exception;
    //通过会员等级获取折扣比例
    public CustomerClass getCustomerClassById(Connection connection,int class_ID)throws Exception;

}

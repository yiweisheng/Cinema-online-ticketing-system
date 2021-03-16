package com.yi.service.customerClass;

import com.yi.pojo.CustomerClass;

import java.util.List;

public interface CustomerClassService {
    //获取会员等级列表
    public List<CustomerClass> getcustomerClassList();
    //通过会员等级获取折扣比例
    public CustomerClass getCustomerClassById(int class_ID);

}

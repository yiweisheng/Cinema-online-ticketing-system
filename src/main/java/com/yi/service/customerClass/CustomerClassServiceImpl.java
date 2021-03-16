package com.yi.service.customerClass;

import com.yi.dao.BaseDao;
import com.yi.dao.customerClass.CustomerClassDao;
import com.yi.dao.customerClass.CustomerClassDaoImpl;
import com.yi.pojo.CustomerClass;

import java.sql.Connection;
import java.util.List;

public class CustomerClassServiceImpl implements CustomerClassService {

    private CustomerClassDao customerClassDao;
    public CustomerClassServiceImpl(){customerClassDao=new CustomerClassDaoImpl();}

    //获取会员等级列表
    @Override
    public List<CustomerClass> getcustomerClassList() {
        Connection connection = null;
        List<CustomerClass> customerClassList = null;
        try {
            connection = BaseDao.getCotsConnection();
            customerClassList = customerClassDao.CustomerClass(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return customerClassList;
    }
    //通过会员等级获取折扣比例
    @Override
    public CustomerClass getCustomerClassById(int class_ID) {
        System.out.println("CustomerClassServiceImpl->getCustomerClassById");
        Connection connection = null;
        CustomerClass customerClass = null;
        try {
            connection = BaseDao.getCotsConnection();
            customerClass = customerClassDao.getCustomerClassById(connection,class_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return customerClass;
    }
}

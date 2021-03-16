package com.yi.dao.customerClass;

import com.yi.dao.BaseDao;
import com.yi.pojo.CustomerClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerClassDaoImpl implements CustomerClassDao {

    //获取会员等级列表
    @Override
    public List<CustomerClass> CustomerClass(Connection connection) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<CustomerClass> customerClassList = new ArrayList<CustomerClass>();
        if (connection != null) {
            String sql = "select * from table_class";
            System.out.println("sql ----> " + sql.toString());
            Object[] params = {};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                CustomerClass _customerclass = new CustomerClass();
                _customerclass.setClass_ID(rs.getInt("class_ID"));
                _customerclass.setClass_Name(rs.getString("Class_Name"));
                _customerclass.setClass_Discount(rs.getFloat("Class_Discount"));
                customerClassList.add(_customerclass);
            }
            BaseDao.closeResource(null, pstm, rs);
        }

        return customerClassList;
    }

    //通过会员等级获取折扣比例
    @Override
    public CustomerClass getCustomerClassById(Connection connection, int class_ID) throws Exception {
        System.out.println("CustomerClassDaoImpl->getCustomerClassById");
        PreparedStatement pstm = null;
        CustomerClass customerClass = null;
        ResultSet rs = null;
        if (connection != null) {
            String sql = "select * from table_class where Class_ID=?";
            System.out.println("sql ----> " + sql.toString());
            Object[] params = {class_ID};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if(rs.next()) {
                customerClass = new CustomerClass();
                customerClass.setClass_ID(rs.getInt("Class_ID"));
                customerClass.setClass_Name(rs.getString("Class_Name"));
                customerClass.setClass_Discount(rs.getFloat("Class_Discount"));
                BaseDao.closeResource(null, pstm, rs);
            }
        }
        return customerClass;
    }
}

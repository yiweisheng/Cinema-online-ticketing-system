package com.yi.service.customer;

import com.yi.dao.BaseDao;
import com.yi.dao.customer.CustomerDao;
import com.yi.dao.customer.CustomerDaoImpl;
import com.yi.dao.order.OrderDao;
import com.yi.dao.order.OrderDaoImpl;
import com.yi.pojo.Customer;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;
    private OrderDao orderDao;

    public CustomerServiceImpl() {
        customerDao = new CustomerDaoImpl();
    }
    //根据条件查询会员表记录数
    @Override
    public int getCustomerCount(String Customer_ID, String Customer_Name, int Class_ID) {
        System.out.println("CustomerServiceImpl->getCustomerCount");
        // TODO Auto-generated method stub
        Connection connection = null;
        int count = 0;
        System.out.println("Customer_ID ---- > " + Customer_ID);
        System.out.println("Customer_Name ---- > " + Customer_Name);
        System.out.println("Class_IDRole ---- > " + Class_ID);
        try {
            connection = BaseDao.getCotsConnection();
            count = customerDao.getCustomerCount(connection, Customer_ID, Customer_Name, Class_ID);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return count;
    }
    //根据条件查询会员列表
    @Override
    public List<Customer> getCustomerList(String Customer_ID, String Customer_Name, int Class_ID, int currentPageNo, int pageSize) {
        System.out.println("CustomerServiceImpl->getCustomerList");
        // TODO Auto-generated method stub
        Connection connection = null;
        List<Customer> customerList = null;
        System.out.println("Customer_ID ---- > " + Customer_ID);
        System.out.println("Customer_Name ---- > " + Customer_Name);
        System.out.println("Class_ID ---- > " + Class_ID);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = BaseDao.getCotsConnection();
            customerList = customerDao.getCustomerList(connection, Customer_ID, Customer_Name, Class_ID, currentPageNo, pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return customerList;
    }
    //判断会员名是否已存在
    @Override
    public Customer selectCustomerNameExist(String customer_Name) {
        System.out.println("CustomerServiceImpl->selectCustomerNameExist");
        // TODO Auto-generated method stub
        Connection connection = null;
        Customer customer = null;
        try {
            connection = BaseDao.getCotsConnection();
            customer = customerDao.selectCustomerNameExist(connection, customer_Name);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return customer;
    }
    //增加会员表信息
    @Override
    public boolean add(Customer customer) {
        System.out.println("CustomerServiceImpl->add");
        // TODO Auto-generated method stub
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getCotsConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            int updateRows = customerDao.add(connection, customer);
            connection.commit();
            if (updateRows > 0) {
                flag = true;
                System.out.println("add success!");
            } else {
                System.out.println("add failed!");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                System.out.println("rollback==================");
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } finally {
            //在service层进行connection连接的关闭
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
    //通过会员ID查看会员详细信息
    @Override
    public Customer getCustomerById(String id) {
        System.out.println("CustomerServiceImpl->getCustomerById");
        // TODO Auto-generated method stub
        Customer customer = null;
        Connection connection = null;
        try {
            connection = BaseDao.getCotsConnection();
            customer = customerDao.getCustomerById(connection, id);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            customer = null;
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return customer;
    }
    //修改会员信息执行操作
    @Override
    public boolean modify(Customer customer) {
        System.out.println("CustomerServiceImpl->modify");
        // TODO Auto-generated method stub
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getCotsConnection();
            if (customerDao.modify(connection, customer) > 0)
                flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            try {
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
    //删除会员表记录
    @Override
    public int deleteCustomerById(String delId) {
        System.out.println("CustomerServiceImpl->deleteCustomerById");
        // TODO Auto-generated method stub
        Connection connection = null;
        int orderCount = -1;
        orderDao = new OrderDaoImpl();
        try {
            connection = BaseDao.getCotsConnection();
            connection.setAutoCommit(false);
            orderCount = orderDao.getOrderCountByCustomerId(connection, delId);
            System.out.println("orderCount->" + orderCount);
            if (orderCount == 0) {
                customerDao.deleteCustomerById(connection, delId);
            }
            connection.commit();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            orderCount = -1;
            try {
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return orderCount;
    }
    //会员登录
    @Override
    public Customer login(String Customer_Name, String Customer_PassWord) {
        System.out.println("CustomerServiceImpl->login");
        // TODO Auto-generated method stub
        Connection connection = null;
        Customer customer = null;
        try {
            connection = BaseDao.getCotsConnection();
            customer = customerDao.getLoginCustomer(connection, Customer_Name);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }

        //匹配密码
        if (null != customer) {
            if (!customer.getPassWord().equals(Customer_PassWord))
                customer = null;
        }

        return customer;
    }
    //确认用户与注册手机号一致
    @Override
    public Customer confirmphonenumber(String Customer_Name, String Customer_Mobile) {
        System.out.println("CustomerServiceImpl->confirmphonenumber");
        // TODO Auto-generated method stub
        Connection connection = null;
        Customer customer = null;
        System.out.println("Customer_Name:"+Customer_Name);
        System.out.println("Customer_Mobile:"+Customer_Mobile);
        try {
            connection = BaseDao.getCotsConnection();
            customer = customerDao.getLoginCustomer(connection, Customer_Name);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        System.out.println(!customer.getCustomer_Mobile().equals(Customer_Mobile));

        //注册电话匹配
        if (null != customer) {
            if (!customer.getCustomer_Mobile().equals(Customer_Mobile)) {
                customer = null;
            }
        }
        return customer;
    }
    //重置密码
    @Override
    public boolean resetpassword(String Customer_Name, String newPassword) {
        System.out.println("CustomerServiceImpl->resetpassword");
        // TODO Auto-generated method stub
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getCotsConnection();
            if (customerDao.resetpassword(connection, Customer_Name,newPassword) > 0)
                flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            try {
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
    //会员自己信息执行操作
    @Override
    public boolean cu_modify(Customer customer) {
        System.out.println("CustomerServiceImpl->cu_modify");
        // TODO Auto-generated method stub
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getCotsConnection();
            if (customerDao.cu_modify(connection, customer) > 0)
                flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            try {
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }


    @Test
    public void test() {
        String customer_Name = "易玮晟";
        String customer_Mobile = "17779284362";
        CustomerService customerService = new CustomerServiceImpl();
        Customer customer = customerService.confirmphonenumber(customer_Name,customer_Mobile);

        System.out.println("Dao_customer_Name:"+customer.getCustomer_Name());
        System.out.println(!customer.getCustomer_Mobile().equals(customer_Mobile));
    }
}

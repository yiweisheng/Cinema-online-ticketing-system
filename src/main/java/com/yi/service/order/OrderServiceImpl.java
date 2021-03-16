package com.yi.service.order;

import com.yi.dao.BaseDao;
import com.yi.dao.order.OrderDao;
import com.yi.dao.order.OrderDaoImpl;
import com.yi.pojo.Order;
import com.yi.pojo.Schedule;
import com.yi.pojo.Seat;
import com.yi.service.seat.SeatService;
import com.yi.service.seat.SeatServiceImpl;
import com.yi.util.Constants;
import org.junit.Test;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrderServiceImpl implements OrderService{

    private OrderDao orderDao;
    public OrderServiceImpl(){orderDao=new OrderDaoImpl();}

    // 根据条件查询订单表记录数
    @Override
    public int getOrderCount(String Customer_ID, String Schedule_ID,int Order_IsActive) {
        System.out.println("OrderServiceImpl->getOrderCount");
        // TODO Auto-generated method stub
        Connection connection = null;
        int count = 0;
        System.out.println("Customer_ID ---- > " + Customer_ID);
        System.out.println("Schedule_ID ---- > " + Schedule_ID);
        System.out.println("Order_IsActive ---- > " + Order_IsActive);
        try {
            connection = BaseDao.getCotsConnection();
            count = orderDao.getOrderCount(connection, Customer_ID,Schedule_ID,Order_IsActive);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return count;
    }
    //根据条件查询订单列表
    @Override
    public List<Order> getOrderList(String Customer_ID, String Schedule_ID,
                                    int Order_IsActive, int currentPageNo, int pageSize) {
        System.out.println("OrderServiceImpl->getOrderList");
        // TODO Auto-generated method stub
        Connection connection = null;
        List<Order> orderList = null;
        System.out.println("Customer_ID ---- > " + Customer_ID);
        System.out.println("Schedule_ID ---- > " + Schedule_ID);
        System.out.println("Order_IsActive ---- > " + Order_IsActive);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = BaseDao.getCotsConnection();
            orderList = orderDao.getOrderList(connection, Customer_ID,Schedule_ID,Order_IsActive,currentPageNo,pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return orderList;
    }
    //删除订单表记录
    @Override
    public int deleteOrderById(String delId) {
        System.out.println("OrderServiceImpl->deleteOrderById");
        // TODO Auto-generated method stub
        Connection connection = null;
        int orderCount = -1;
        orderDao=new OrderDaoImpl();
        try {
            connection = BaseDao.getCotsConnection();
            connection.setAutoCommit(false);
            orderCount = orderDao.getOrder_IsActive(connection,delId);
            System.out.println("scheduleCount->"+orderCount);
            if(orderCount == 0){
                orderDao.deleteOrderById(connection, delId);
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
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return orderCount;
    }
    //通过订单表ID查看时间表详细信息
    @Override
    public Order getOrderById(String id) {
        System.out.println("OrderServiceImpl->getOrderById");
        // TODO Auto-generated method stub
        Order order = null;
        Connection connection = null;
        try{
            connection = BaseDao.getCotsConnection();
            order = orderDao.getOrderById(connection, id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            order = null;
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return order;
    }
    //修改订单表信息执行操作
    @Override
    public boolean modify(Order order) {
        System.out.println("OrderServiceImpl->modify");
        // TODO Auto-generated method stub
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getCotsConnection();
            if(orderDao.modify(connection,order) > 0)
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
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
    //添加订单
    @Override
    public boolean add(Order order) {
        System.out.println("OrderServiceImpl->addOrder");
        // TODO Auto-generated method stub
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getCotsConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            if(orderDao.add(connection,order) > 0)
                flag = true;
            connection.commit();
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
        }finally{
            //在service层进行connection连接的关闭
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
    //根据条件查询登录用户下订单表记录数
    @Override
    public int getCuOrderCount(String Customer_ID, int Order_IsActive) {
        System.out.println("OrderServiceImpl->getCuOrderCount");
        // TODO Auto-generated method stub
        Connection connection = null;
        int count = 0;
        System.out.println("Customer_ID ---- > " + Customer_ID);
        System.out.println("Order_IsActive ---- > " + Order_IsActive);
        try {
            connection = BaseDao.getCotsConnection();
            count = orderDao.getCuOrderCount(connection, Customer_ID,Order_IsActive);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return count;
    }
    //根据条件查询登录用户下订单列表
    @Override
    public List<Order> getCuOrderList(String Customer_ID, int Order_IsActive, int currentPageNo, int pageSize) {
        System.out.println("OrderServiceImpl->getCuOrderList");
        // TODO Auto-generated method stub
        Connection connection = null;
        List<Order> orderList = null;
        System.out.println("Customer_ID ---- > " + Customer_ID);
        System.out.println("Order_IsActive ---- > " + Order_IsActive);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = BaseDao.getCotsConnection();
            orderList = orderDao.getCuOrderList(connection, Customer_ID,Order_IsActive,currentPageNo,pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return orderList;
    }
    //设置该订单的Order_IsActive为0，即不可用状态
    @Override
    public boolean changeOrder_IsActive(Order order) {
        System.out.println("OrderServiceImpl->changeOrder_IsActive");
        // TODO Auto-generated method stub
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getCotsConnection();
            if(orderDao.changeOrder_IsActive(connection,order) > 0)
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
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }


    @Test
    public void test(){
        List<Seat> seatList=null;   //座位列表
        SeatService seatService = new SeatServiceImpl();
        seatList=seatService.getSeatList("001");
        for (int i = 0; i < seatList.size(); i++) {
            if (seatList.get(i).getSeat_IsCorridor()==1){
                seatList.remove(i--);
            }
        }
        for (int i = 0; i < seatList.size(); i++) {
            System.out.println(i+" seatList:"+seatList.get(i).getSeat_IsCorridor());
        }
    }
}

package com.yi.dao.order;

import com.yi.pojo.Order;

import java.sql.Connection;
import java.util.List;

public interface OrderDao {
    //获取时间表下有效订单数量
    public int getOrderCountByScheduleId(Connection connection, String scheduleId)throws Exception;
    //获取会员下有效订单数量
    public int getOrderCountByCustomerId(Connection connection, String customerId)throws Exception;
    // 根据条件查询订单表记录数
    public int getOrderCount(Connection connection, String Customer_ID,
                             String Schedule_ID,int Order_IsActive)throws Exception;
    //根据条件查询订单列表
    public List<Order> getOrderList(Connection connection, String Customer_ID, String Schedule_ID,
                                    int Order_IsActive, int currentPageNo, int pageSize)throws Exception;
    //删除订单表记录
    public int deleteOrderById(Connection connection, String delId)throws Exception;
    //获取订单表是否依然有效
    public int getOrder_IsActive(Connection connection, String delId)throws Exception;
    //通过订单表ID查看时间表详细信息
    public Order getOrderById(Connection connection, String id)throws Exception;
    //修改订单表信息执行操作
    public int modify(Connection connection, Order order)throws Exception;
    //添加订单
    public int add(Connection connection, Order order)throws Exception;
    // 根据条件查询登录用户下订单表记录数
    public int getCuOrderCount(Connection connection, String Customer_ID,int Order_IsActive)throws Exception;
    //根据条件查询登录用户下订单列表
    public List<Order> getCuOrderList(Connection connection, String Customer_ID,int Order_IsActive, int currentPageNo, int pageSize)throws Exception;
    //设置该订单的Order_IsActive为0，即不可用状态
    public int changeOrder_IsActive(Connection connection, Order order)throws Exception;
}

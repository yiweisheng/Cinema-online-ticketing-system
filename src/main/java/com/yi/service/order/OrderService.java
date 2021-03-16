package com.yi.service.order;

import com.yi.pojo.Order;
import com.yi.pojo.Schedule;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public interface OrderService {
	 //根据条件查询订单表记录数
    public int getOrderCount(String Customer_ID, String Schedule_ID,int Order_IsActive);
    //根据条件查询订单列表
    public List<Order> getOrderList(String Customer_ID, String Schedule_ID,int Order_IsActive, int currentPageNo, int pageSize);
    //删除订单表记录
    public int deleteOrderById(String delId);
    //通过订单表ID查看时间表详细信息
    public Order getOrderById(String getOrderById);
    //修改订单表信息执行操作
    public boolean modify(Order order);
    //添加订单
    public boolean add(Order order);
    //根据条件查询登录用户下订单表记录数
    public int getCuOrderCount(String Customer_ID,int Order_IsActive);
    //根据条件查询登录用户下订单列表
    public List<Order> getCuOrderList(String Customer_ID,int Order_IsActive, int currentPageNo, int pageSize);
    //设置该订单的Order_IsActive为0，即不可用状态
    public boolean changeOrder_IsActive(Order order);
}

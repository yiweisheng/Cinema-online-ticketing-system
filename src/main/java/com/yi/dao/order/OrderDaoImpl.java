package com.yi.dao.order;

import com.mysql.jdbc.StringUtils;
import com.yi.dao.BaseDao;
import com.yi.pojo.Order;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    //获取时间表下有效订单数量
    @Override
    public int getOrderCountByScheduleId(Connection connection, String scheduleId) throws Exception {
        System.out.println("OrderDaoImpl->getOrderCountByScheduleId");
        // TODO Auto-generated method stub
        System.out.println("delId->" + scheduleId);
        int count = 0;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if (null != connection) {
            String sql = "select count(1) as orderCount from table_order where Order_IsActive=1 and Schedule_ID=?";
            Object[] params = {scheduleId};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()) {
                count = rs.getInt("orderCount");
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return count;
    }
    //获取会员下有效订单数量
    @Override
    public int getOrderCountByCustomerId(Connection connection, String customerId) throws Exception {
        System.out.println("OrderDaoImpl->getOrderCountByCustomerId");
        // TODO Auto-generated method stub
        System.out.println("delId->" + customerId);
        int count = 0;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if (null != connection) {
            String sql = "select count(1) as orderCount from table_order where Order_IsActive=1 and Customer_ID=?";
            Object[] params = {customerId};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()) {
                count = rs.getInt("orderCount");
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return count;
    }
    // 根据条件查询订单表记录数
    @Override
    public int getOrderCount(Connection connection, String Customer_ID,
                             String Schedule_ID, int Order_IsActive) throws Exception {
        System.out.println("OrderDaoImpl->getOrderCount");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from table_order r where Order_ID is not null");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(Customer_ID)) {
                sql.append(" and Customer_ID like ?");
                list.add("%" + Customer_ID + "%");
            }
            if (!StringUtils.isNullOrEmpty(Schedule_ID)) {
                sql.append(" and Schedule_ID like ?");
                list.add("%" + Schedule_ID + "%");
            }

            if (Order_IsActive > -1) {
                sql.append(" and Order_IsActive = ?");
                list.add(Order_IsActive);
            }
            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            if (rs.next()) {
                count = rs.getInt("count");
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return count;
    }
    //获取条件查询订单列表
    @Override
    public List<Order> getOrderList(Connection connection, String Customer_ID, String Schedule_ID,
                                    int Order_IsActive, int currentPageNo, int pageSize)
            throws Exception {
        System.out.println("OrderDaoImpl->getOrderList");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Order> orderList = new ArrayList<Order>();
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from table_order where Order_ID is not null");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(Customer_ID)) {
                sql.append(" and Customer_ID like ?");
                list.add("%" + Customer_ID + "%");
            }
            if (!StringUtils.isNullOrEmpty(Schedule_ID)) {
                sql.append(" and Schedule_ID like ?");
                list.add("%" + Schedule_ID + "%");
            }
            if (Order_IsActive > -1) {
                sql.append(" and Order_IsActive = ?");
                list.add(Order_IsActive);
            }
            sql.append(" order by Order_ID asc limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            while (rs.next()) {
                Order _order = new Order();
                _order.setOrder_ID(BigInteger.valueOf(rs.getLong("Order_ID")));
                _order.setCustomer_ID(BigInteger.valueOf(rs.getLong("Customer_ID")));
                _order.setSchedule_ID(BigInteger.valueOf(rs.getLong("Schedule_ID")));
                _order.setSeat_ID(rs.getString("Seat_ID"));
                _order.setOrder_IsActive(rs.getInt("Order_IsActive"));
                _order.setOrder_AdjustedPrice(rs.getFloat("Order_AdjustedPrice"));
                _order.setOrder_BuyDate(rs.getTimestamp("Order_BuyDate"));
                orderList.add(_order);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return orderList;
    }
    //删除订单表记录
    @Override
    public int deleteOrderById(Connection connection, String delId) throws Exception {
        System.out.println("OrderDaoImpl->deleteOrderById");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        int flag = 0;
        if (null != connection) {
            String sql = "delete from table_order where Order_ID=?";
            Object[] params = {delId};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }
    //获取订单表是否依然有效
    @Override
    public int getOrder_IsActive(Connection connection, String delId) throws Exception {
        System.out.println("OrderDaoImpl->getOrder_IsActive");
        // TODO Auto-generated method stub
        System.out.println("delId->" + delId);
        int count = 0;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if (null != connection) {
            String sql = "select Order_IsActive from table_order where Order_ID=?";
            Object[] params = {delId};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()) {
                count = rs.getInt("Order_IsActive");
            }
            BaseDao.closeResource(null, pstm, rs);
        }

        return count;
    }
    //通过订单表ID查看时间表详细信息
    @Override
    public Order getOrderById(Connection connection, String id) throws Exception {
        System.out.println("OrderDaoImpl->getOrderById");
        // TODO Auto-generated method stub
        Order order = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if (null != connection) {
            String sql = "select * from table_order where Order_ID=?";
            Object[] params = {id};
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()) {
                order = new Order();
                order.setOrder_ID(BigInteger.valueOf(rs.getLong("Order_ID")));
                order.setCustomer_ID(BigInteger.valueOf(rs.getLong("Customer_ID")));
                order.setSchedule_ID(BigInteger.valueOf(rs.getLong("Schedule_ID")));
                order.setSeat_ID(rs.getString("Seat_ID"));
                order.setOrder_IsActive(rs.getInt("Order_IsActive"));
                order.setOrder_Cipher(rs.getString("Order_Cipher"));
                order.setOrder_AdjustedPrice(rs.getFloat("Order_AdjustedPrice"));
                order.setOrder_BuyDate(rs.getTimestamp("Order_BuyDate"));
            }
            BaseDao.closeResource(connection, pstm, rs);
        }
        return order;
    }
    //修改订单表信息执行操作
    @Override
    public int modify(Connection connection, Order order) throws Exception {
        System.out.println("OrderDaoImpl->modify");
        // TODO Auto-generated method stub
        int flag = 0;
        PreparedStatement pstm = null;
        if (null != connection) {
            String sql = "update table_order set Customer_ID=?,Schedule_ID=?,Seat_ID=?," +
                    "Order_IsActive=?,Order_AdjustedPrice=?,Order_BuyDate=? where Order_ID=?";

            BigInteger Order_ID = order.getOrder_ID();
            BigInteger Customer_ID = order.getCustomer_ID();
            BigInteger Schedule_ID = order.getSchedule_ID();
            String Seat_ID = order.getSeat_ID();
            int Order_IsActive = order.getOrder_IsActive();
            Float Order_AdjustedPrice = order.getOrder_AdjustedPrice();
            Timestamp Order_BuyDate = order.getOrder_BuyDate();

            Object[] params = {Customer_ID, Schedule_ID, Seat_ID, Order_IsActive, Order_AdjustedPrice, Order_BuyDate, Order_ID};
            System.out.println("sql ----> " + sql.toString());

            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }
    //增加订单
    @Override
    public int add(Connection connection, Order order) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("OrderDaoImpl->add");
        PreparedStatement pstm = null;
        int flag = 0;
        if (null != connection) {
            String sql = "insert into table_order " +
                    "(Customer_ID,Schedule_ID,Seat_ID,Order_IsActive,Order_SecretKey,Order_Cipher,Order_AdjustedPrice,Order_BuyDate)" +
                    "values (?,?,?,?,?,?,?,?)";

            BigInteger Customer_ID=order.getCustomer_ID();     //会员ID
            BigInteger Schedule_ID=order.getSchedule_ID();     //时间表ID
            String Seat_ID=order.getSeat_ID();         //座位ID
            int Order_IsActive=order.getOrder_IsActive();     //订单是否可用
            String Order_SecretKey=order.getOrder_SecretKey();    //用于存放加密后的密文
            String Order_Cipher=order.getOrder_Cipher();    //用于存放加密后的密文
            float Order_AdjustedPrice=order.getOrder_AdjustedPrice();//会员打折之后的价格
            Timestamp Order_BuyDate=order.getOrder_BuyDate();     //购买时间

            Object[] params = {Customer_ID, Schedule_ID, Seat_ID, Order_IsActive,Order_SecretKey,Order_Cipher, Order_AdjustedPrice, Order_BuyDate};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }
    //根据条件查询登录用户下订单表记录数
    @Override
    public int getCuOrderCount(Connection connection, String Customer_ID, int Order_IsActive) throws Exception {
        System.out.println("OrderDaoImpl->getCuOrderCount");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from table_order r where Order_ID is not null");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(Customer_ID)) {
                sql.append(" and Customer_ID like ?");
                list.add("%" + Customer_ID + "%");
            }
            if (Order_IsActive > -1) {
                sql.append(" and Order_IsActive = ?");
                list.add(Order_IsActive);
            }
            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            if (rs.next()) {
                count = rs.getInt("count");
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return count;
    }
    //根据条件查询登录用户下订单列表
    @Override
    public List<Order> getCuOrderList(Connection connection, String Customer_ID, int Order_IsActive,
                                      int currentPageNo, int pageSize) throws Exception {
        System.out.println("OrderDaoImpl->getCuOrderList");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Order> orderList = new ArrayList<Order>();
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from table_order where Order_ID is not null");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(Customer_ID)) {
                sql.append(" and Customer_ID like ?");
                list.add("%" + Customer_ID + "%");
            }
            if (Order_IsActive > -1) {
                sql.append(" and Order_IsActive = ?");
                list.add(Order_IsActive);
            }
            sql.append(" order by Order_ID asc limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            while (rs.next()) {
                Order _order = new Order();
                _order.setOrder_ID(BigInteger.valueOf(rs.getLong("Order_ID")));
                _order.setCustomer_ID(BigInteger.valueOf(rs.getLong("Customer_ID")));
                _order.setSchedule_ID(BigInteger.valueOf(rs.getLong("Schedule_ID")));
                _order.setSeat_ID(rs.getString("Seat_ID"));
                _order.setOrder_IsActive(rs.getInt("Order_IsActive"));
                _order.setOrder_SecretKey(rs.getString("Order_SecretKey"));
                _order.setOrder_AdjustedPrice(rs.getFloat("Order_AdjustedPrice"));
                _order.setOrder_BuyDate(rs.getTimestamp("Order_BuyDate"));
                orderList.add(_order);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return orderList;
    }
    //设置该订单的Order_IsActive为0，即不可用状态
    @Override
    public int changeOrder_IsActive(Connection connection, Order order) throws Exception {
        System.out.println("OrderDaoImpl->changeOrder_IsActive");
        // TODO Auto-generated method stub
        int flag = 0;
        PreparedStatement pstm = null;
        if (null != connection) {
            String sql = "update table_order set Order_IsActive=? where Order_ID=?";
            BigInteger Order_ID = order.getOrder_ID();

            Object[] params = {0,Order_ID};
            System.out.println("sql ----> " + sql.toString());
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

}

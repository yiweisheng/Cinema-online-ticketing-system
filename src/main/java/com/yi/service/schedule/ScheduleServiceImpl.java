package com.yi.service.schedule;

import com.yi.dao.BaseDao;
import com.yi.dao.order.OrderDao;
import com.yi.dao.order.OrderDaoImpl;
import com.yi.dao.schedule.ScheduleDao;
import com.yi.dao.schedule.ScheduleDaoImpl;
import com.yi.pojo.Schedule;
import org.junit.Test;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ScheduleServiceImpl implements ScheduleService{

    private ScheduleDao scheduleDao;
    private OrderDao orderDao;
    public ScheduleServiceImpl(){
        scheduleDao = new ScheduleDaoImpl();
    }

    //根据条件查询时间表记录数
    @Override
    public int getScheduleCount(String Schedule_ID, String Movie_ID, String Movie_Name, String Hall_ID,int schedule_IsShow) {
        System.out.println("ScheduleServiceImpl->getScheduleCount");
        // TODO Auto-generated method stub
        Connection connection = null;
        int count = 0;
        System.out.println("Schedule_ID ---- > " + Schedule_ID);
        System.out.println("Movie_ID ---- > " + Movie_ID);
        System.out.println("Movie_Name ---- > " + Movie_Name);
        System.out.println("Hall_ID ---- > " + Hall_ID);
        System.out.println("schedule_IsShow ---- > " + schedule_IsShow);

        try {
            connection = BaseDao.getCotsConnection();
            count = scheduleDao.getScheduleCount(connection, Schedule_ID,Movie_ID,Movie_Name,Hall_ID,schedule_IsShow);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return count;
    }
    //获取时间表列表
    @Override
    public List<Schedule> getScheduleList(String Schedule_ID, String Movie_ID, String Movie_Name,
                                          String Hall_ID,int schedule_IsShow, int currentPageNo, int pageSize) {
        System.out.println("ScheduleServiceImpl->getScheduleList");
        // TODO Auto-generated method stub
        Connection connection = null;
        List<Schedule> scheduleList = null;
        System.out.println("Schedule_ID ---- > " + Schedule_ID);
        System.out.println("Movie_ID ---- > " + Movie_ID);
        System.out.println("Movie_Name ---- > " + Movie_Name);
        System.out.println("Hall_ID ---- > " + Hall_ID);
        System.out.println("schedule_IsShow ---- > " + schedule_IsShow);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = BaseDao.getCotsConnection();
            scheduleList = scheduleDao.getScheduleList(connection, Schedule_ID,Movie_ID,Movie_Name,Hall_ID,schedule_IsShow,currentPageNo,pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return scheduleList;
    }
    //通过时间表ID获取时间表信息
    @Override
    public Schedule getScheduleById(String id) {
        System.out.println("ScheduleServiceImpl->getScheduleById");
        // TODO Auto-generated method stub
        Schedule schedule = null;
        Connection connection = null;
        try{
            connection = BaseDao.getCotsConnection();
            schedule = scheduleDao.getScheduleById(connection, id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            schedule = null;
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return schedule;
    }
    //修改时间表信息执行操作
    @Override
    public boolean modify(Schedule schedule) {
        System.out.println("ScheduleServiceImpl->modify");
        // TODO Auto-generated method stub
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getCotsConnection();
            if(scheduleDao.modify(connection,schedule) > 0)
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
    //根据schedule_ID查询schedule
    @Override
    public Schedule selectUserCodeExist(String schedule_ID) {
        // TODO Auto-generated method stub
        Connection connection = null;
        Schedule schedule = null;
        try {
            connection = BaseDao.getCotsConnection();
            schedule = scheduleDao.getScheduleById(connection, schedule_ID);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return schedule;
    }
    //增加时间表信息
    @Override
    public boolean add(Schedule schedule) {
        System.out.println("ScheduleServiceImpl->add");
        // TODO Auto-generated method stub
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getCotsConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            int updateRows = scheduleDao.add(connection,schedule);
            connection.commit();
            if(updateRows > 0){
                flag = true;
                System.out.println("add success!");
            }else{
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
        }finally{
            //在service层进行connection连接的关闭
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
    //删除时间表记录
    @Override
    public int deleteScheduleById(String delId) {
        System.out.println("ScheduleServiceImpl->deleteScheduleById");
        // TODO Auto-generated method stub
        Connection connection = null;
        int orderCount = -1;
        orderDao=new OrderDaoImpl();
        try {
            connection = BaseDao.getCotsConnection();
            connection.setAutoCommit(false);
            orderCount = orderDao.getOrderCountByScheduleId(connection,delId);
            System.out.println("orderCount->"+orderCount);
            if(orderCount == 0){
                scheduleDao.deleteScheduleById(connection, delId);
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

    @Test
    public void test(){
        ScheduleService scheduleService = new ScheduleServiceImpl();

        BigInteger Schedule_ID= BigInteger.valueOf(10);
        BigInteger Movie_ID= BigInteger.valueOf(1);
        String Hall_ID="001";
        float Schedule_price=0;
        String Schedule_BeginDateTime = null;

        Schedule schedule=new Schedule();

        Schedule_BeginDateTime="1970-01-01 00:00:00";
        //转化为时间格式
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp dateTime=null;
        dateTime = Timestamp.valueOf(Schedule_BeginDateTime);
//        System.out.println("dateTime:"+dateTime);

        schedule.setSchedule_ID(Schedule_ID);
        schedule.setMovie_ID(Movie_ID);
        schedule.setHall_ID(Hall_ID);
        schedule.setSchedule_price(Schedule_price);
        schedule.setSchedule_BeginDateTime(dateTime);

        boolean count = scheduleService.add(schedule);

        System.out.println("count:"+count);

    }
}

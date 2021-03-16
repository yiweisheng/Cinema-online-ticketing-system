package com.yi.dao.schedule;

import com.mysql.jdbc.StringUtils;
import com.yi.dao.BaseDao;
import com.yi.pojo.Schedule;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDaoImpl implements ScheduleDao{
    //通过影片ID获取排片数量
    @Override
    public int getScheduleCountByMovieId(Connection connection, String movieId) throws Exception {
        System.out.println("ScheduleDaoImpl->getScheduleCountByMovieId");
        // TODO Auto-generated method stub
        System.out.println("movieId->"+movieId);
        int count = 0;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if(null != connection){
            String sql = "select count(1) as scheduleCount from table_schedule where Movie_ID=?";
            Object[] params = {movieId};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if(rs.next()){
                count = rs.getInt("scheduleCount");
            }
            BaseDao.closeResource(null, pstm, rs);
        }

        return count;
    }

    //获取排片总数量
    @Override
    public int getScheduleCount(Connection connection, String Schedule_ID, String Movie_ID, String Movie_Name, String Hall_ID,int schedule_IsShow) throws Exception {
        System.out.println("ScheduleDaoImpl->getScheduleCount");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from table_schedule s,table_movie m where s.Movie_ID=m.Movie_ID");
            List<Object> list = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(Schedule_ID)){
                sql.append(" and s.Schedule_ID like ?");
                list.add("%"+Schedule_ID+"%");
            }
            if(!StringUtils.isNullOrEmpty(Movie_ID)){
                sql.append(" and s.Movie_ID like ?");
                list.add("%"+Movie_ID+"%");
            }
            if(!StringUtils.isNullOrEmpty(Movie_Name)){
                sql.append(" and m.Movie_Name like ?");
                list.add("%"+Movie_Name+"%");
            }
            if(!StringUtils.isNullOrEmpty(Hall_ID)){
                sql.append(" and s.Hall_ID like ?");
                list.add("%"+Hall_ID+"%");
            }
            if (schedule_IsShow > -1) {
                sql.append(" and Schedule_IsShow = ?");
                list.add(schedule_IsShow);
            }
            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            if(rs.next()){
                count = rs.getInt("count");
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return count;
    }

    //获取时间表列表
    @Override
    public List<Schedule> getScheduleList(Connection connection, String Schedule_ID, String Movie_ID, String Movie_Name,
                                      String Hall_ID,int schedule_IsShow, int currentPageNo, int pageSize) throws Exception {
        System.out.println("ScheduleDaoImpl->getScheduleList");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Schedule> scheduleList = new ArrayList<Schedule>();
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select s.*,m.Movie_Name from table_schedule s,table_movie m where s.Movie_ID=m.Movie_ID");
            List<Object> list = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(Schedule_ID)){
                sql.append(" and s.Schedule_ID like ?");
                list.add("%"+Schedule_ID+"%");
            }
            if(!StringUtils.isNullOrEmpty(Movie_ID)){
                sql.append(" and s.Movie_ID like ?");
                list.add("%"+Movie_ID+"%");
            }
            if(!StringUtils.isNullOrEmpty(Movie_Name)){
                sql.append(" and m.Movie_Name like ?");
                list.add("%"+Movie_Name+"%");
            }
            if(!StringUtils.isNullOrEmpty(Hall_ID)){
                sql.append(" and s.Hall_ID like ?");
                list.add("%"+Hall_ID+"%");
            }
            if (schedule_IsShow > -1) {
                sql.append(" and Schedule_IsShow = ?");
                list.add(schedule_IsShow);
            }
            sql.append(" order by Schedule_ID asc limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            while(rs.next()){
                Schedule _schedule = new Schedule();
                _schedule.setSchedule_ID(BigInteger.valueOf(rs.getLong("Schedule_ID")));
                _schedule.setMovie_ID(BigInteger.valueOf(rs.getLong("Movie_ID")));
                _schedule.setHall_ID(rs.getString("Hall_ID"));
                _schedule.setSchedule_price(rs.getFloat("Schedule_price"));
                _schedule.setSchedule_BeginDateTime(rs.getTimestamp("Schedule_BeginDateTime"));
                scheduleList.add(_schedule);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return scheduleList;
    }

    //通过时间表ID获取时间表信息
    @Override
    public Schedule getScheduleById(Connection connection, String id) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("ScheduleDaoImpl->getScheduleById");
        Schedule schedule = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if(null != connection){
            String sql = "select * from table_schedule where Schedule_ID=?";

            Object[] params = {id};
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if(rs.next()){
                schedule = new Schedule();
                schedule.setSchedule_ID(BigInteger.valueOf(rs.getLong("Schedule_ID")));
                schedule.setMovie_ID(BigInteger.valueOf(rs.getLong("Movie_ID")));
                schedule.setHall_ID(rs.getString("Hall_ID"));
                schedule.setSchedule_price(rs.getFloat("Schedule_price"));
                schedule.setSchedule_BeginDateTime(rs.getTimestamp("Schedule_BeginDateTime"));
            }
            BaseDao.closeResource(connection, pstm, rs);
        }
        return schedule;
    }

    //修改时间表信息执行操作
    @Override
    public int modify(Connection connection, Schedule schedule) throws Exception {
        System.out.println("ScheduleDaoImpl->modify");
        // TODO Auto-generated method stub
        int flag = 0;
        PreparedStatement pstm = null;
        if(null != connection){
            String sql = "update table_schedule set Movie_ID=?,Hall_ID=?,Schedule_price=?,Schedule_BeginDateTime=? where Schedule_ID=?";

            BigInteger schedule_ID = schedule.getSchedule_ID();
            BigInteger movie_ID = schedule.getMovie_ID();
            String hall_ID = schedule.getHall_ID();
            float schedule_price = schedule.getSchedule_price();
            Timestamp schedule_BeginDateTime = schedule.getSchedule_BeginDateTime();

            Object[] params = {movie_ID,hall_ID,schedule_price,schedule_BeginDateTime,schedule_ID};
            System.out.println("sql ----> " + sql.toString());

            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    //增加时间表信息
    @Override
    public int add(Connection connection, Schedule schedule) throws Exception {
        System.out.println("ScheduleDaoImpl->add");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        int updateRows = 0;
        if(null != connection){
            String sql = "insert into table_schedule (Schedule_ID, Movie_ID, Hall_ID, Schedule_price, " +
                    "Schedule_BeginDateTime) VALUES(?,?,?,?,?)";
            Object[] params = {schedule.getSchedule_ID(),schedule.getMovie_ID(),schedule.getHall_ID(),
                    schedule.getSchedule_price(),schedule.getSchedule_BeginDateTime()};
            updateRows = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }

    //删除时间表记录
    @Override
    public int deleteScheduleById(Connection connection, String delId) throws Exception {
        System.out.println("ScheduleDaoImpl->deleteScheduleById");
        // TODO Auto-generated method stub
        System.out.println("delId:"+delId);
        PreparedStatement pstm = null;
        int flag = 0;
        if(null != connection){
            String sql = "delete from table_schedule where Schedule_ID=?";
            Object[] params = {delId};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

}

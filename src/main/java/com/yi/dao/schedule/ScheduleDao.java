package com.yi.dao.schedule;

import com.yi.pojo.Schedule;

import java.sql.Connection;
import java.util.List;

public interface ScheduleDao {
    //通过影片ID获取排片数量
    public int getScheduleCountByMovieId(Connection connection, String movieId)throws Exception;

    //获取排片总数量
    public int getScheduleCount(Connection connection, String Schedule_ID,String Movie_ID,String Movie_Name,String Hall_ID,int schedule_IsShow)throws Exception;

    //获取时间表列表
    public List<Schedule> getScheduleList(Connection connection, String Schedule_ID, String Movie_ID, String Movie_Name,
                                      String Hall_ID,int schedule_IsShow, int currentPageNo, int pageSize)throws Exception;

    //通过时间表ID获取时间表信息
    public Schedule getScheduleById(Connection connection, String id)throws Exception;

    //修改时间表信息执行操作
    public int modify(Connection connection, Schedule schedule)throws Exception;

    //增加时间表信息
    public int add(Connection connection, Schedule schedule)throws Exception;

    //删除时间表记录
    public int deleteScheduleById(Connection connection, String delId)throws Exception;


}

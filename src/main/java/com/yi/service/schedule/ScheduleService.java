package com.yi.service.schedule;

import com.yi.pojo.Schedule;

import java.util.List;

public interface ScheduleService {

    //根据条件查询时间表记录数
    public int getScheduleCount( String Schedule_ID,String Movie_ID,String Movie_Name,String Hall_ID,int schedule_IsShow);

    //获取时间表列表
    public List<Schedule> getScheduleList(String Schedule_ID,String Movie_ID,String Movie_Name,String Hall_ID,int schedule_IsShow, int currentPageNo, int pageSize);

    //通过时间表ID获取时间表信息
    public Schedule getScheduleById(String id);

    //修改时间表信息执行操作
    public boolean modify(Schedule schedule);

    //根据schedule_ID查询schedule
    public Schedule selectUserCodeExist(String schedule_ID);

    //增加时间表信息
    public boolean add(Schedule schedule);

    //删除时间表记录
    public int deleteScheduleById(String delId);


}

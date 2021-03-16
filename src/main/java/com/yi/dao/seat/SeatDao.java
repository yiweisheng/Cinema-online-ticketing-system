package com.yi.dao.seat;

import com.yi.pojo.Seat;

import java.sql.Connection;
import java.util.List;

public interface SeatDao {
    //根据影厅ID获取对应seat列表
    public List<Seat> getSeatList(Connection connection,String Hall_ID)throws Exception;
    //将预定的座位置为以预定
    public int setSeatIsActiveFalse(Connection connection,String Seat_ID)throws Exception;
}

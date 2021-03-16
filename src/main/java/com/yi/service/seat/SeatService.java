package com.yi.service.seat;

import com.yi.pojo.Seat;

import java.util.List;

public interface SeatService {
    //根据影厅ID获取对应seat列表
    public List<Seat> getSeatList(String Hall_ID);
    //将预定的座位置为以预定
    public boolean setSeatIsActiveFalse(String Seat_ID);

}

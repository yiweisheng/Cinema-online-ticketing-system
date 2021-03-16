package com.yi.service.seat;

import com.yi.dao.BaseDao;
import com.yi.dao.seat.SeatDao;
import com.yi.dao.seat.SeatDaoImpl;
import com.yi.pojo.Hall;
import com.yi.pojo.Seat;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SeatServiceImpl implements SeatService {
    private SeatDao seatDao;

    public SeatServiceImpl() {
        seatDao = new SeatDaoImpl();
    }

    //根据影厅ID获取对应seat列表
    @Override
    public List<Seat> getSeatList(String Hall_ID) {
        Connection connection = null;
        List<Seat> seatList = null;
        try {
            connection = BaseDao.getCotsConnection();
            seatList = seatDao.getSeatList(connection, Hall_ID);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return seatList;
    }
    //将预定的座位置为以预定
    @Override
    public boolean setSeatIsActiveFalse(String Seat_ID) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getCotsConnection();
            if (seatDao.setSeatIsActiveFalse(connection, Seat_ID) > 0)
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
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    @Test
    public void test() {
        SeatServiceImpl seatService = new SeatServiceImpl();
        String Hall_ID = "001";

        int seatRow = 1;
        String seatMap = "";
        String unavailable = "";

        List<Seat> seatList = seatService.getSeatList(Hall_ID);


        for (int i = 0; i < seatList.size(); i++) {
            if (seatList.get(i).getSeat_Row() == seatRow) {//第seatRow行
                int seatColumn = 0;
                while (i < seatList.size() && seatList.get(i).getSeat_Row() == seatRow) {//还是第seatRow行的时候
                    seatColumn++;
                    if (seatList.get(i).getSeat_IsCorridor() == 0) {//是否为过道为 0 不是过道
                        if (seatList.get(i).getSeat_IsActive() == 1) {//是否可用为 1 可用
                            seatMap = seatMap + "s";
                        } else {//不可用情况
                            seatMap = seatMap + "s";
                            if (unavailable.equals("")) {
                                unavailable = unavailable + "'" + seatRow + "_" + seatColumn + "'";
                            } else {
                                unavailable = unavailable + ",'" + seatRow + "_" + seatColumn + "'";
                            }
                        }
                    } else {//为过道情况
                        seatMap = seatMap + "_";
                    }
                    i++;
                }
            } else {//座位换行
                seatMap = seatMap + ",";
                seatRow++;
                i--;
            }
        }

        System.out.println("seatMap:" + seatMap);
        System.out.println("unavailable:" + unavailable);


    }
}

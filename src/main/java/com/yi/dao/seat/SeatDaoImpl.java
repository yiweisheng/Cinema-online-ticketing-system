package com.yi.dao.seat;

import com.yi.dao.BaseDao;
import com.yi.pojo.Hall;
import com.yi.pojo.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SeatDaoImpl implements SeatDao{
    //根据影厅ID获取对应seat列表
    @Override
    public List<Seat> getSeatList(Connection connection,String Hall_ID) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Seat> seatList = new ArrayList<Seat>();
        if(connection != null){
            String sql = "select * from table_seat where Hall_ID=? order by Seat_ID ";
            Object[] params = {Hall_ID};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            int i=0;
            while(rs.next()){
                Seat _seat = new Seat();
                _seat.setSeat_ID(rs.getString("Seat_ID"));
                _seat.setHall_ID(rs.getString("Hall_ID"));
                _seat.setSeat_Row(rs.getInt("Seat_Row"));
                _seat.setSeat_Column(rs.getInt("Seat_Column"));
                _seat.setSeat_IsActive(rs.getInt("Seat_IsActive"));
                _seat.setSeat_IsCorridor(rs.getInt("Seat_IsCorridor"));
                seatList.add(_seat);
            }
            BaseDao.closeResource(null, pstm, rs);
        }

        return seatList;
    }
    //将预定的座位置为以预定
    @Override
    public int setSeatIsActiveFalse(Connection connection, String Seat_ID) throws Exception {
        System.out.println("SeatDaoImpl->setSeatIsActiveFalse");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int flag=0;
        if(connection != null){
            String sql = "update table_seat set Seat_IsActive=0 where Seat_ID=?";
            System.out.println("sql ----> " + sql.toString());
            Object[] params = {Seat_ID};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, rs);
        }
        return flag;
    }
}

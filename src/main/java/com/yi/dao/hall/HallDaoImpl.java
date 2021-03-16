package com.yi.dao.hall;

import com.yi.dao.BaseDao;
import com.yi.pojo.Hall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HallDaoImpl implements HallDao{

    @Override
    public List<Hall> getHallList(Connection connection) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Hall> hallList = new ArrayList<Hall>();
        if(connection != null){
            String sql = "select * from table_hall";
            Object[] params = {};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            int i=0;
            while(rs.next()){
                Hall _hall = new Hall();
                _hall.setHall_ID(rs.getString("Hall_ID"));
                _hall.setHall_Name(rs.getString("Hall_Name"));
                _hall.setHall_Seats(rs.getInt("Hall_Seats"));
                _hall.setHall_Description(rs.getString("Hall_Description"));
                hallList.add(_hall);
            }
            BaseDao.closeResource(null, pstm, rs);
        }

        return hallList;
    }
}

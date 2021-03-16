package com.yi.dao.hall;

import com.yi.pojo.Hall;

import java.sql.Connection;
import java.util.List;

public interface HallDao {
    public List<Hall> getHallList(Connection connection)throws Exception;
}

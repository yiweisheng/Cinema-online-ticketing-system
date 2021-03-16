package com.yi.service.hall;

import com.yi.dao.BaseDao;
import com.yi.dao.hall.HallDao;
import com.yi.dao.hall.HallDaoImpl;
import com.yi.pojo.Hall;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class HallServiceImpl implements HallService{

    private HallDao hallDao;

    public HallServiceImpl(){
        hallDao = new HallDaoImpl();
    }

    @Override
    public List<Hall> getHallList() {
        Connection connection = null;
        List<Hall> hallList = null;
        try {
            connection = BaseDao.getCotsConnection();
            hallList = hallDao.getHallList(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return hallList;
    }

    @Test
    public void test(){
        HallService hallService = new HallServiceImpl();
        List<Hall> hallList = hallService.getHallList();

        for (int i = 0; i < hallList.size(); i++) {
            System.out.println(i+" Hall_ID:"+hallList.get(i).getHall_ID());
            System.out.println(i+" Hall_Name:"+hallList.get(i).getHall_Name());
            System.out.println(i+" Hall_Seats:"+hallList.get(i).getHall_Seats());
            System.out.println(i+" Hall_Description:"+hallList.get(i).getHall_Description());
        }

    }
}

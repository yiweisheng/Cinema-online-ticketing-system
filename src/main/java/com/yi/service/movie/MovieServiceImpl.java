package com.yi.service.movie;

import com.yi.dao.BaseDao;
import com.yi.dao.movie.MovieDao;
import com.yi.dao.movie.MovieDaoImpl;
import com.yi.dao.schedule.ScheduleDao;
import com.yi.dao.schedule.ScheduleDaoImpl;
import com.yi.pojo.Movie;
import org.junit.Test;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class MovieServiceImpl implements MovieService{

    private MovieDao movieDao;
    private ScheduleDao scheduleDao;
    public MovieServiceImpl(){
        movieDao = new MovieDaoImpl();
    }

    //获取电影列表
    @Override
    public List<Movie> getMovieList(String Movie_ID, String Movie_Name, String Movie_Region, String Movie_Type, int currentPageNo, int pageSize) {
        // TODO Auto-generated method stub
        System.out.println("MovieServiceImpl->getMovieList");
        Connection connection = null;
        List<Movie> movieList = null;
        System.out.println("Movie_ID ---- > " + Movie_ID);
        System.out.println("Movie_Name ---- > " + Movie_Name);
        System.out.println("Movie_Region ---- > " + Movie_Region);
        System.out.println("Movie_Type ---- > " + Movie_Type);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = BaseDao.getCotsConnection();
            movieList = movieDao.getMovieList(connection, Movie_ID,Movie_Name,Movie_Region,Movie_Type,currentPageNo,pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return movieList;
    }
    //通过影片ID获取影片信息
    @Override
    public Movie getMovieById(BigInteger id) {
        System.out.println("MovieServiceImpl->getMovieById");
        // TODO Auto-generated method stub
        Movie movie = null;
        Connection connection = null;
        try{
            connection = BaseDao.getCotsConnection();
            movie = movieDao.getMovieById(connection, id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            movie = null;
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return movie;
    }
    //增加影片
    @Override
    public boolean add(Movie movie) {
        System.out.println("MovieServiceImpl->add");
        // TODO Auto-generated method stub
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getCotsConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            if(movieDao.add(connection,movie) > 0)
                flag = true;
            connection.commit();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                System.out.println("rollback==================");
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }finally{
            //在service层进行connection连接的关闭
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
    //修改影片信息
    @Override
    public boolean modify(Movie movie) {
        System.out.println("MovieServiceImpl->modify");
        // TODO Auto-generated method stub
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getCotsConnection();
            if(movieDao.modify(connection,movie) > 0)
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
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
    //删除影片
    @Override
    public int deleteProviderById(String delId) {
        System.out.println("MovieServiceImpl->deleteProviderById");
        // TODO Auto-generated method stub
        Connection connection = null;
        int scheduleCount = -1;
        scheduleDao=new ScheduleDaoImpl();
        System.out.println("delId->"+delId);
        try {
            connection = BaseDao.getCotsConnection();
            connection.setAutoCommit(false);
            scheduleCount = scheduleDao.getScheduleCountByMovieId(connection,delId);
            System.out.println("scheduleCount->"+scheduleCount);
            if(scheduleCount == 0){
                movieDao.deleteMovieById(connection, delId);
            }
            connection.commit();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            scheduleCount = -1;
            try {
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return scheduleCount;
    }
    //根据条件查询电影数量
    @Override
    public int getMovieCount(String Movie_ID, String Movie_Name, String Movie_Region, String Movie_Type) {
        System.out.println("MovieServiceImpl->getMovieCount");
        // TODO Auto-generated method stub
        Connection connection = null;
        int count = 0;
        System.out.println("Movie_ID ---- > " + Movie_ID);
        System.out.println("Movie_Name ---- > " + Movie_Name);
        System.out.println("Movie_Region ---- > " + Movie_Region);
        System.out.println("Movie_Type ---- > " + Movie_Type);

        try {
            connection = BaseDao.getCotsConnection();
            count = movieDao.getMovieCount(connection, Movie_ID,Movie_Name,Movie_Region,Movie_Type);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return count;
    }
    //根据影片ID查询影片名称
    @Override
    public String getMovie_NameByMovie_ID(BigInteger Movie_ID) {
        System.out.println("MovieServiceImpl->getMovie_NameByMovie_ID");
        // TODO Auto-generated method stub
        Connection connection = null;
        String Movie_Name = "";
        System.out.println("Movie_ID ---- > " + Movie_ID);

        try {
            connection = BaseDao.getCotsConnection();
            Movie_Name = movieDao.getMovie_NameByMovie_ID(connection, Movie_ID);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return Movie_Name;
    }

    @Override
    public String getMovie_ImageSrcByMovie_ID(BigInteger Movie_ID) {
        System.out.println("MovieServiceImpl->getMovie_ImageSrcByMovie_ID");
        // TODO Auto-generated method stub
        Connection connection = null;
        String Movie_ImageSr = "";
        System.out.println("Movie_ID ---- > " + Movie_ID);

        try {
            connection = BaseDao.getCotsConnection();
            Movie_ImageSr = movieDao.getMovie_ImageSrcByMovie_ID(connection, Movie_ID);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return Movie_ImageSr;
    }

    @Test
    public void test(){
        Movie movie=new Movie();
        MovieService movieService = new MovieServiceImpl();
        BigInteger Movie_ID= BigInteger.valueOf(2);
        String movie_nameByMovie_id = movieService.getMovie_NameByMovie_ID(Movie_ID);
        System.out.println("movie_nameByMovie_id:"+movie_nameByMovie_id);
    }
}

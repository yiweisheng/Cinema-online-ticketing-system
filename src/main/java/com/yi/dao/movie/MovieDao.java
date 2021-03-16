package com.yi.dao.movie;

import com.yi.pojo.Movie;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.List;

public interface MovieDao {
    //获取影片列表
    public List<Movie> getMovieList(Connection connection, String Movie_ID,
                                    String Movie_Name, String Movie_Region, String Movie_Type,
                                    int currentPageNo, int pageSize)throws Exception;
    //通过影片ID获取影片信息
    public Movie getMovieById(Connection connection, BigInteger id)throws Exception;
    //增加影片
    public int add(Connection connection, Movie movie)throws Exception;
    //修改影片信息
    public int modify(Connection connection, Movie movie)throws Exception;
    //删除影片
    public int deleteMovieById(Connection connection, String delId)throws Exception;
    //根据条件查询电影数量
    public int getMovieCount(Connection connection, String Movie_ID, String Movie_Name,
                             String Movie_Region, String Movie_Type)throws Exception;
    //通过影片ID查询影片名
    public String getMovie_NameByMovie_ID(Connection connection, BigInteger Movie_ID)throws Exception;
    //通过影片ID查询影片名
    public String getMovie_ImageSrcByMovie_ID(Connection connection, BigInteger Movie_ID)throws Exception;
}

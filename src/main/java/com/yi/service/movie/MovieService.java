package com.yi.service.movie;

import com.yi.pojo.Movie;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.List;

public interface MovieService {

    //获取电影列表
    public List<Movie> getMovieList(String Movie_ID, String Movie_Name, String Movie_Region, String Movie_Type, int currentPageNo, int pageSize);
    //通过影片ID获取影片信息
    public Movie getMovieById(BigInteger id);
    //增加影片
    public boolean add(Movie movie);
    //修改影片信息
    public boolean modify(Movie movie);
    //删除影片
    public int deleteProviderById(String delId);
    //根据条件查询电影数量
    public int getMovieCount(String Movie_ID, String Movie_Name, String Movie_Region, String Movie_Type);
    //根据影片ID查询影片名
    public String getMovie_NameByMovie_ID(BigInteger Movie_ID);
    //根据影片ID查询影片封面路径
    public String getMovie_ImageSrcByMovie_ID(BigInteger Movie_ID);
}

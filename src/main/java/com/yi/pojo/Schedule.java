package com.yi.pojo;

import com.yi.service.movie.MovieService;
import com.yi.service.movie.MovieServiceImpl;

import java.math.BigInteger;
import java.sql.Timestamp;

public class Schedule {
    private BigInteger Schedule_ID;     //时间表ID
    private BigInteger Movie_ID;        //电影ID
    private String Hall_ID;         //影厅ID
    private int Schedule_IsShow;    //排片是否在映
    private float Schedule_price;   //时间表上票价
    private Timestamp Schedule_BeginDateTime;//上映时间

    public String getMovie_NameByMovie_ID(){
        MovieService movieService = new MovieServiceImpl();
        String Movie_Name="";

        Movie_Name = movieService.getMovie_NameByMovie_ID(this.Movie_ID);

        return Movie_Name;
    }
    public String getMovie_ImageSrcByMovie_ID(){
        MovieService movieService = new MovieServiceImpl();
        String Movie_ImageSr="";

        Movie_ImageSr = movieService.getMovie_ImageSrcByMovie_ID(this.Movie_ID);

        return Movie_ImageSr;
    }

    public BigInteger getSchedule_ID() {return Schedule_ID;}
    public void setSchedule_ID(BigInteger schedule_ID) {Schedule_ID = schedule_ID;}

    public BigInteger getMovie_ID() {return Movie_ID;}
    public void setMovie_ID(BigInteger movie_ID) {Movie_ID = movie_ID;}

    public String getHall_ID() {return Hall_ID;}
    public void setHall_ID(String hall_ID) {Hall_ID = hall_ID;}

    public int getSchedule_IsShow() {return Schedule_IsShow;}
    public void setSchedule_IsShow(int schedule_IsShow) {Schedule_IsShow = schedule_IsShow;}

    public float getSchedule_price() {return Schedule_price;}
    public void setSchedule_price(float schedule_price) {Schedule_price = schedule_price;}

    public Timestamp getSchedule_BeginDateTime() {return Schedule_BeginDateTime;}
    public void setSchedule_BeginDateTime(Timestamp schedule_BeginDateTime) {Schedule_BeginDateTime = schedule_BeginDateTime;}
}

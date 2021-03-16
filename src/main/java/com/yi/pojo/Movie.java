package com.yi.pojo;

import java.math.BigInteger;
import java.sql.Time;

public class Movie {
    private BigInteger Movie_ID;        //影片ID
    private String Movie_Name;      //影片名
    private String Movie_Region;    //影片产地
    private String Movie_Type;      //影片类型
    private String Movie_MainActor; //主要演员
    private String Movie_Director;  //导演
    private Time Movie_Duration;  //时长
    private String Movie_Description; //概述
    private String Movie_ImageSrc;  //封面路径

    public Movie() {
        Movie_ID = null;
        Movie_Name = "影片名";
        Movie_Region = "中国";
        Movie_Type = "喜剧";
        Movie_MainActor = "无";
        Movie_Director = "无";
        Movie_Duration = null;
        Movie_Description = "无";
        Movie_ImageSrc = "../../DBimages/movie/error.jpg";
    }

    public BigInteger getMovie_ID() {return Movie_ID;}
    public void setMovie_ID(BigInteger movie_ID) {Movie_ID = movie_ID;}

    public String getMovie_Name() {return Movie_Name;}
    public void setMovie_Name(String movie_Name) {Movie_Name = movie_Name;}

    public String getMovie_Region() {return Movie_Region;}
    public void setMovie_Region(String movie_Region) {Movie_Region = movie_Region;}

    public String getMovie_Type() {return Movie_Type;}
    public void setMovie_Type(String movie_Type) {Movie_Type = movie_Type;}

    public String getMovie_MainActor() {return Movie_MainActor;}
    public void setMovie_MainActor(String movie_MainActor) {Movie_MainActor = movie_MainActor;}

    public String getMovie_Director() {return Movie_Director;}
    public void setMovie_Director(String movie_Director) {Movie_Director = movie_Director;}

    public Time getMovie_Duration() {return Movie_Duration;}
    public void setMovie_Duration(Time movie_Duration) {Movie_Duration = movie_Duration;}

    public String getMovie_Description() {return Movie_Description;}
    public void setMovie_Description(String movie_Description) {Movie_Description = movie_Description;}

    public String getMovie_ImageSrc() {return Movie_ImageSrc;}
    public void setMovie_ImageSrc(String movie_ImageSrc) {Movie_ImageSrc = movie_ImageSrc;}
}

package com.yi.dao.movie;

import com.mysql.jdbc.StringUtils;
import com.yi.dao.BaseDao;
import com.yi.pojo.Movie;
import org.junit.Test;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieDaoImpl implements MovieDao {

    //获取影片列表
    @Override
    public List<Movie> getMovieList(Connection connection, String Movie_ID, String Movie_Name,
                                    String Movie_Region, String Movie_Type, int currentPageNo, int pageSize)
            throws Exception {
        // TODO Auto-generated method stub
        System.out.println("MovieDaoImpl->getMovieList");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Movie> movieList = new ArrayList<Movie>();
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * " +
                    "FROM table_movie m " +
                    "WHERE m.`Movie_ID` IS NOT NULL");
            List<Object> list = new ArrayList<Object>();

            if(!StringUtils.isNullOrEmpty(Movie_ID)){
                sql.append(" and m.Movie_ID like ?");
                list.add("%"+Movie_ID+"%");
            }
            if(!StringUtils.isNullOrEmpty(Movie_Name)){
                sql.append(" and m.Movie_Name like ?");
                list.add("%"+Movie_Name+"%");
            }
            if(!StringUtils.isNullOrEmpty(Movie_Region)){
                sql.append(" and m.Movie_Region like ?");
                list.add("%"+Movie_Region+"%");
            }
            if(!StringUtils.isNullOrEmpty(Movie_Type)){
                sql.append(" and m.Movie_Type like ?");
                list.add("%"+Movie_Type+"%");
            }
            sql.append(" order by Movie_ID asc limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            while(rs.next()){
                Movie _movie = new Movie();
                _movie.setMovie_ID(BigInteger.valueOf(rs.getLong("Movie_ID")));
                _movie.setMovie_Name(rs.getString("Movie_Name"));
                _movie.setMovie_Region(rs.getString("Movie_Region"));
                _movie.setMovie_Type(rs.getString("Movie_Type"));
                _movie.setMovie_MainActor(rs.getString("Movie_MainActor"));
                _movie.setMovie_Director(rs.getString("Movie_Director"));
                _movie.setMovie_Duration(rs.getTime("Movie_Duration"));
                _movie.setMovie_Description(rs.getString("Movie_Description"));
                _movie.setMovie_ImageSrc(rs.getString("Movie_ImageSrc"));
                movieList.add(_movie);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return movieList;
    }
    //通过影片ID获取影片信息
    @Override
    public Movie getMovieById(Connection connection, BigInteger id) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("MovieDaoImpl->getMovieById");
        Movie movie = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if(null != connection){
            String sql = "select * from table_movie where Movie_ID=?";
            Object[] params = {id};
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if(rs.next()){
                movie = new Movie();
                movie.setMovie_ID(BigInteger.valueOf(rs.getLong("Movie_ID")));
                movie.setMovie_Name(rs.getString("Movie_Name"));
                movie.setMovie_Region(rs.getString("Movie_Region"));
                movie.setMovie_Type(rs.getString("Movie_Type"));
                movie.setMovie_MainActor(rs.getString("Movie_MainActor"));
                movie.setMovie_Director(rs.getString("Movie_Director"));
                movie.setMovie_Duration(rs.getTime("Movie_Duration"));
                movie.setMovie_Description(rs.getString("Movie_Description"));
                movie.setMovie_ImageSrc(rs.getString("Movie_ImageSrc"));
            }
            BaseDao.closeResource(connection, pstm, rs);
        }
        return movie;
    }
    //增加影片
    @Override
    public int add(Connection connection, Movie movie) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("MovieDaoImpl->add");
        PreparedStatement pstm = null;
        int flag = 0;
        if(null != connection){
            String sql = "insert into table_movie ( Movie_Name, Movie_Region, Movie_Type," +
                    "Movie_MainActor, Movie_Director, Movie_Duration," +
                    "Movie_Description, Movie_ImageSrc)" +
                    "values (?,?,?,?,?,?,?,?)";

            String movie_name = movie.getMovie_Name();
            String movie_region = movie.getMovie_Region();
            String movie_type = movie.getMovie_Type();
            String movie_mainActor = movie.getMovie_MainActor();
            String movie_director = movie.getMovie_Director();
            Time movie_duration = movie.getMovie_Duration();
            String movie_description = movie.getMovie_Description();
            String movie_imageSrc = movie.getMovie_ImageSrc();


            //转化为时间格式
            DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            Date date=null;
            try {
                date = sdf.parse("00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Time time= new Time(date.getTime());

            if(movie_region.equals("")){movie_region="中国";}
            if(movie_type.equals("")){movie_type="喜剧";}
            if(movie_mainActor.equals("")){movie_mainActor="无";}
            if(movie_director.equals("")){movie_director="无";}
            if(null==movie_duration){movie_duration=time;}
            if(movie_description.equals("")){movie_description="无";}
            if(movie_imageSrc.equals("")){movie_imageSrc="../../DBimages/movie/error.jpg";}

            Object[] params = {movie_name,movie_region,movie_type,movie_mainActor,
                    movie_director,movie_duration,movie_description,movie_imageSrc};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    //修改影片信息
    @Override
    public int modify(Connection connection, Movie movie) throws Exception {
        System.out.println("MovieDaoImpl->modify");
        // TODO Auto-generated method stub
        int flag = 0;
        PreparedStatement pstm = null;
        if(null != connection){
            String sql = "update table_movie set Movie_Name=?,Movie_Region=?,Movie_Type=?,Movie_MainActor=?," +
                    "Movie_Director=?,Movie_Duration=?,Movie_Description=?,Movie_ImageSrc=? where Movie_ID=?";

            BigInteger movie_id = movie.getMovie_ID();
            String movie_name = movie.getMovie_Name();
            String movie_region = movie.getMovie_Region();
            String movie_type = movie.getMovie_Type();
            String movie_mainActor = movie.getMovie_MainActor();
            String movie_director = movie.getMovie_Director();
            Time movie_duration = movie.getMovie_Duration();
            String movie_description = movie.getMovie_Description();
            String movie_imageSrc = movie.getMovie_ImageSrc();

            //转化为时间格式
            DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            Date date=null;
            try {
                date = sdf.parse("00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Time time= new Time(date.getTime());

            if(movie_region.equals("")){movie_region="中国";}
            if(movie_type.equals("")){movie_type="喜剧";}
            if(movie_mainActor.equals("")){movie_mainActor="无";}
            if(movie_director.equals("")){movie_director="无";}
            if(null==movie_duration){movie_duration=time;}
            if(movie_description.equals("")){movie_description="无";}
            if(movie_imageSrc.equals("")){movie_imageSrc="../../DBimages/movie/error.jpg";}

            Object[] params = {movie_name,movie_region,movie_type,movie_mainActor,movie_director,
                    movie_duration,movie_description,movie_imageSrc,movie_id};

            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    //删除影片
    @Override
    public int deleteMovieById(Connection connection, String delId) throws Exception {
        System.out.println("MovieDaoImpl->deleteProviderById");
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        int flag = 0;
        if(null != connection){
            String sql = "delete from table_movie where Movie_ID=?";
            Object[] params = {delId};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    //根据条件查询电影数量
    @Override
    public int getMovieCount(Connection connection, String Movie_ID, String Movie_Name, String Movie_Region, String Movie_Type) throws Exception {
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT COUNT(1) AS COUNT FROM table_movie m WHERE m.`Movie_ID` IS NOT NULL");
            List<Object> list = new ArrayList<Object>();

            if(!StringUtils.isNullOrEmpty(Movie_ID)){
                sql.append(" and m.Movie_ID like ?");
                list.add("%"+Movie_ID+"%");
            }
            if(!StringUtils.isNullOrEmpty(Movie_Name)){
                sql.append(" and m.Movie_Name like ?");
                list.add("%"+Movie_Name+"%");
            }
            if(!StringUtils.isNullOrEmpty(Movie_Region)){
                sql.append(" and m.Movie_Region like ?");
                list.add("%"+Movie_Region+"%");
            }
            if(!StringUtils.isNullOrEmpty(Movie_Type)){
                sql.append(" and m.Movie_Type like ?");
                list.add("%"+Movie_Type+"%");
            }

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            if(rs.next()){
                count = rs.getInt("count");
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return count;
    }

    //通过影片ID查询影片名
    @Override
    public String getMovie_NameByMovie_ID(Connection connection, BigInteger Movie_ID) throws Exception{
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String Movie_name = "";
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select Movie_Name as Movie_name from table_movie where Movie_ID=? ");
            List<Object> list = new ArrayList<Object>();

            list.add(Movie_ID);
            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            if(rs.next()){
                Movie_name =rs.getString("Movie_Name");
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return Movie_name;
    }

    @Override
    public String getMovie_ImageSrcByMovie_ID(Connection connection, BigInteger Movie_ID) throws Exception {
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String Movie_ImageSrc = "";
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select Movie_ImageSrc as Movie_ImageSrc from table_movie where Movie_ID=? ");
            List<Object> list = new ArrayList<Object>();

            list.add(Movie_ID);
            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            if(rs.next()){
                Movie_ImageSrc =rs.getString("Movie_ImageSrc");
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return Movie_ImageSrc;
    }


    @Test
    public void test(){
        Connection connection = null;
        MovieDao movieDao = new MovieDaoImpl();
        Movie movie=new Movie();
        int add=-1;

        connection = BaseDao.getCotsConnection();

        movie.setMovie_ID(BigInteger.valueOf(6));
        movie.setMovie_Name("罗生门");

        try {
            add = movieDao.add(connection, movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("插入行数为"+add);
    }

}

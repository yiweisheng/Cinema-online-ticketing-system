package com.yi.servlet.movie;

import com.yi.pojo.Movie;
import com.yi.servlet.movie.MovieServlet;
import com.yi.util.Constants;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FileServlet-》doPost");
        //判断上传的文件是普通表单还是带文件的表单
        if (!ServletFileUpload.isMultipartContent(req)) {
            return;//终止方法运行，说明这是一个普通的表单
        }

        //创建上传文件的保存路径，建议在WEB-INF路径下，安全，用户无法直接访问上传的文件
//        String uploadPath = this.getServletContext().getRealPath("DBimages/filetest");
        String uploadPath = Constants.PROJECT_PATH + Constants.UPLOAD_PATH;

        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdir();//创建这个目录
        }

        //缓存，临时文件
        //临时路径，假如文件超过了预期的大小，我们就把它放到一个临时文件中，过几天自动删除，或者提醒用户转存为永久
        String tempPath = this.getServletContext().getRealPath("DBimages/temptest");
        File file = new File(tempPath);
        if (!file.exists()) {
            file.mkdir();//创建这个目录
        }

        //处理上传的文件，一般都需要通过流来获取，我们可以用request.getInputStream()。原生态的文件上传流获取，十分麻烦
        //但是我们都建议使用Apache的文件上传组件来实现，common-fileupload，它需要依赖于common-io组件

        /*
        ServletFileUpload负责处理上传的文件数据，并将表单中每个输入项封装成一个fileItem对象
        在使用ServletFileUpload对象解析请求是需要DiskFileItemFactory对象
        所以，我们需要在进行解析工作前构造好DiskFileItemFactory对象
        通过ServletFileUpload对象的构造方法或SetFileItemFactory()方法
        设置ServletFileUpload对象的fileItemFactory属性
         */
        Movie movie=null;
        ServletFileUpload upload = null;
        try {
            //1.创建DiskFileItemFactory对象，处理文件上传路径或者大小限制的
            DiskFileItemFactory factory = gteDiskFileItemFactory(file);
            //2.获取ServletFileUpload
            upload = getServletFileUpload(factory);
            //3.处理上传的文件
            movie = uploadParseRequest(upload, req, uploadPath);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        System.out.println("FileServlet=========>doPost");
        System.out.println("Movie_ID:"+movie.getMovie_ID());
        System.out.println("Movie_Name:"+movie.getMovie_Name());
        System.out.println("Movie_Region:"+movie.getMovie_Region());
        System.out.println("Movie_Type:"+movie.getMovie_Type());
        System.out.println("Movie_MainActor:"+movie.getMovie_MainActor());
        System.out.println("Movie_Director:"+movie.getMovie_Director());
        System.out.println("Movie_Duration:"+movie.getMovie_Duration());
        System.out.println("Movie_Description:"+movie.getMovie_Description());
        System.out.println("Movie_ImageSrc:"+movie.getMovie_Description());

        MovieServlet movieServlet = new MovieServlet();
        movieServlet.add(req, resp,movie);
    }

    //1.创建DiskFileItemFactory对象，处理文件上传路径或者大小限制的
    public static DiskFileItemFactory gteDiskFileItemFactory(File file) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //通过这个工厂设置一个缓冲区，当上传的文件大于这个缓冲区的时候，将他放到临时文件中
        factory.setSizeThreshold(1024 * 1024);//缓存区大小为1M
        factory.setRepository(file);//零食目录的保存目录，需要一个File
        return factory;
    }

    //2.获取ServletFileUpload
    public static ServletFileUpload getServletFileUpload(DiskFileItemFactory factory) {
        ServletFileUpload upload = new ServletFileUpload(factory);
        //监听文件上传进度
        upload.setProgressListener(new ProgressListener() {
            @Override
            //pBytesRead:已经读取到的文件大小
            //pContentLength:文件大小
            public void update(long pBytesRead, long pContentLength, int pItems) {
                System.out.println("总大小：" + pContentLength + "已上传：" + pBytesRead);
            }
        });
        //处理乱码问题
        upload.setHeaderEncoding("UTF-8");
        //设置单个文件的最大值
        upload.setFileSizeMax(1024 * 1024 * 10);
        //设置总共能上传文件的大小
        //1024=1kb*1024=1M*10=10M
        upload.setSizeMax(1024 * 1024 * 10);
        return upload;
    }

    //3.处理上传的文件
    public Movie uploadParseRequest(ServletFileUpload upload, HttpServletRequest req, String uploadPath)
            throws FileUploadException, IOException {
        String msg = "";
        String filePathName = "";
        String fileServerPathName = "";
        Movie movie = new Movie();
        //把前端请求解析，封装成一个FileItem对象,需要从ServletFileUpload对象中获取
        List<FileItem> fileItems = upload.parseRequest(req);

        //fileItem每一个表单对象
        int i = 0;
        for (FileItem fileItem : fileItems) {
            //判断上传的文件的是普通表单还是带文件的表单
            if (fileItem.isFormField()) {
                //getFileldName指的是前端表单控件的name
                String name = fileItem.getFieldName();
                String value = fileItem.getString("UTF-8");//处理乱码
                if (i == 1) {
                    movie.setMovie_Name(value);
                }
                if (i == 2) {
                    movie.setMovie_Region(value);
                }
                if (i == 3) {
                    movie.setMovie_Type(value);
                }
                if (i == 4) {
                    movie.setMovie_MainActor(value);
                }
                if (i == 5) {
                    movie.setMovie_Director(value);
                }
                if (i == 6) {
                    if (value.equals("")) {
                        value = "00:00:00";
                    }
                    //转化为时间格式
                    DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                    Date date = null;
                    try {
                        date = sdf.parse(value);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Time time = new Time(date.getTime());
                    movie.setMovie_Duration(time);
                }
                if (i == 7) {
                    movie.setMovie_Description(value);
                }
                if (i == 8) {
                    movie.setMovie_ImageSrc(value);
                }
                System.out.println(name + ":" + value);
                i++;
            } else {//文件
                //=============处理文件===============//
                String uploadFileName = fileItem.getName();
                //可能存在文件名不合法的情况
                if (uploadFileName.trim().equals("") || uploadFileName == null) {
                    continue;
                }
                //获得上传的文件名
                String fileName = uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
                //获得文件名的后缀
                String fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
                    /*
                    如果文件后缀名 fileExtName不是我们所需要的
                    就直接return，不处理，告诉用户文件类型不对
                     */

                //可以使用UUID（唯一识别的通用码），保证文件名唯一；
                //UUID。randomUUID()，随机生成一个唯一识别的通用码

                //网络传输中的东西，都需要序列化
                //POJO，实体类，如果想要在多个电脑上运行，需要传输===》需要吧对象都序列化
                //JNI=Java Native Interface
                //implements Serializable： 标记接口， JVM--->  Java栈  本地方法栈 native--->C++
                String uuidPath = UUID.randomUUID().toString();

                //=============存放地址===============//
                //存到哪？uploadPath
                //文件真实存在的路径 realPath
                //服务器中的路径 realServerPath
                String realServerPath=this.getServletContext().getRealPath("/DBimages/movie")+ "/" + uuidPath;
                String realPath = uploadPath + "/" + uuidPath;
                //给每个文件创建一个对应的文件夹
                File realServerPathFile = new File(realServerPath);
                if (!realServerPathFile.exists()) {
                    realServerPathFile.mkdir();
                }
                File realPathFile = new File(realPath);
                if (!realPathFile.exists()) {
                    realPathFile.mkdir();
                }
                //=============文件传输===============//
                //获得文件上传的流
                InputStream inputStream = fileItem.getInputStream();

                //创建一个文件输出流
                //realPath=真实的文件夹
                //差了一个文件；加上输出文件的名字+"/"+uuidFileName
                System.out.println("realServerPathFile:" + realServerPathFile);
                System.out.println("realPath:" + realPath);
                FileOutputStream fosServer = new FileOutputStream(realServerPathFile + "/" + fileName);
                FileOutputStream fos = new FileOutputStream(realPath + "/" + fileName);

                fileServerPathName = realPath + "/" + fileName;
                filePathName = realPath + "/" + fileName;
                //创建一个缓冲区
                byte[] buffer = new byte[1024 * 1024];

                //判断是否读取完毕
                int len = 0;
                //如果大于0说明还存在数据；
                while ((len = inputStream.read(buffer)) > 0) {
                    fosServer.write(buffer, 0, len);
                    fos.write(buffer, 0, len);
                }
                //关闭流
                fosServer.close();
                fos.close();
                inputStream.close();

                msg = "文件上传成功！";
                fileItem.delete();//上传成功，清除临时文件
            }
        }
        filePathName="../"+filePathName.substring( filePathName.indexOf("DBimages"),filePathName.length());
        movie.setMovie_ImageSrc(filePathName);
        return movie;
    }
}












package com.yi.servlet.test;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import com.yi.util.Constants;
import org.junit.Test;

import javax.servlet.http.HttpServlet;
import java.io.File;

public class test extends HttpServlet {

    @Test
    public void getLujing() throws Exception {
        //当前项目下路径
        File file = new File("");
        String filePath = Constants.PROJECT_PATH;
        filePath+=Constants.UPLOAD_PATH;

        String s="J:/IDEA/javaWeb/smbms_addTest/src/main/webapp/DBimages/movie/2b05a7a7-93cd-43f3-b6b7-f7d7ed31df13/QQ图片20201224231422.jpg";

        s="../"+s.substring( s.indexOf("DBimages"),s.length());

        System.out.println(filePath);
        System.out.println("s:"+s);

    }
}

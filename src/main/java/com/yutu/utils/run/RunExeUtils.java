package com.yutu.utils.run;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 利用py文件封装成exe调用，实现csv文件转成png图片
 *
 * @ProjectName: yutu_model_air_web
 * @Package: com.yutu.util
 * @ClassName: csvToPngUtils
 * @Author: gengwei
 * @Description: ${description}
 * @Date: 2020/3/9 16:49
 * @Version: 1.0
 */
public class RunExeUtils {
    private static Logger logger = Logger.getLogger(RunExeUtils.class);

    /**
    * @Author: zhaobc
    * @Date: 2020/3/31 15:59
    * @Description: 运行bat或exe
   **/
    public static Boolean exec(String cmdPath) {
        try {
            // 运行bat文件
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("cmd /c start " + cmdPath);
            InputStream in = process.getInputStream();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            process.waitFor();
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
        }
        return false;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/6/28 11:58
     * @Description: 获得文件创建时间
     **/
    public static Date getCreateDate(String cmdPath) {
        Date cmdDate = null;
        try {
            Process process = Runtime.getRuntime().exec("cmd /C dir " + cmdPath + "/tc" );
            InputStream in = process.getInputStream();
            String line;
            String strDate = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                String cmdExtension = cmdPath.substring(cmdPath.lastIndexOf("."));
                if (line.endsWith(cmdExtension)) {
                    strDate = line.substring(0, 17);
                    break;
                }
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd  HH:mm");
            cmdDate = format.parse(strDate);
        } catch (IOException | ParseException ioException) {
            ioException.printStackTrace();
        }
        return cmdDate;
    }
}

package com.yutu.utils.run;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
}

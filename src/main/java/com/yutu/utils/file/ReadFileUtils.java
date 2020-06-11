package com.yutu.utils.file;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhaobc
 * @Date: 2020-03-04 18:20
 * @Description:
 */
public class ReadFileUtils {
    private static Logger logger = Logger.getLogger(ReadFileUtils.class);

    /**
     * @Author: zhaobc
     * @Date: 2020/5/25 11:38
     * @Description: 只读取文件
     **/
    public static String readTxt(String txtPath) {
        File file = new File(txtPath);
        if (file.isFile() && file.exists()) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuffer sb = new StringBuffer();
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    sb.append(text + "\r\n");
                }
                //一定要释放资源
                fileInputStream.close();
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }


    /**
     * @Author: zhaobc
     * @Date: 2020/6/10 18:39
     * @Description: 写入文件方法  txtpath:地址   content:内容
     **/
    public static void writeTxt(String txtPath, String content) {
        FileOutputStream fileOutputStream = null;
        File file = new File(txtPath);
        try {
            if (file.exists()) {
                //判断文件是否存在，如果不存在就新建一个txt
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/6/10 18:37
     * @Description: 文件拷贝功能
     **/
    public static void copyFileStreams(File source, File dest) throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2020-03-04 18:21
     * @Description: 读取文件并根据map进行替换
     **/
    public static String readTxtByReplace(String txtPath, Map<String, String> map) {
        File file = new File(txtPath);
        if (file.isFile() && file.exists()) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuffer sb = new StringBuffer();
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    //判断是否需要替换的
                    boolean bool = false;
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        if (text.contains(entry.getKey())) {
                            sb.append(entry.getValue() + "\r\n");
                            bool = true;
                            break;
                        }
                    }
                    if (bool == false) {
                        sb.append(text + "\r\n");
                    }
                }
                //释放资源
                fileInputStream.close();
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}

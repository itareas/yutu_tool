package com.yutu.utils.file;

import org.apache.log4j.Logger;

import java.io.*;
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
     * @Date: 2020-03-04 18:21
     * @Description: 读取文件并根据map进行替换
     **/
    public static String readTxt(String txtPath, Map<String, String> map) {
        File file = new File(txtPath);
        if (file.isFile() && file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
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
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
    * @Author: zhaobc
    * @Date: 2020/5/25 11:38
    * @Description: 只读取文件
    **/
    public static String readTxt(String txtPath) {
        File file = new File(txtPath);
        if (file.isFile() && file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuffer sb = new StringBuffer();
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    sb.append(text + "\r\n");
                }
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 使用FileOutputStream来写入txt文件
     *
     * @param txtPath txt文件路径
     * @param content 需要写入的文本
     */
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
        }
    }

    public static void copyFileUsingFileStreams(File source, File dest) throws IOException {
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
}

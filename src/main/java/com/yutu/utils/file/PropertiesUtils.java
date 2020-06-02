package com.yutu.utils.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    private static Properties props;
    public static  String path = "application.properties";

    public PropertiesUtils() {
        loadProps();
    }

    synchronized private static void loadProps() {
        props = new Properties();
        InputStream in = null;
        try {
            in = PropertiesUtils.class.getClassLoader().getResourceAsStream(path);
            props.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/5/19 13:44
     * @Description: 获得key
     **/
    public static String get(String key) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/5/19 13:44
     * @Description: 修改或者新增key
     **/
    public static void update(String key, String value) {
        props.setProperty(key, value);
        FileOutputStream oFile = null;
        try {
            oFile = new FileOutputStream(path);
            //将Properties中的属性列表（键和元素对）写入输出流
            props.store(oFile, "");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.yutu.utils.myutils;

import com.yutu.utils.file.FileOperationUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @Author: zhaobc
 * @Date: 2020/07/01 11:10
 * @Description:
 **/
public class ReadFile extends FileOperationUtils {

    /**
     * @Author: zhaobc
     * @Date: 2020/7/1 11:11
     * @Description:
     **/
    public static Double readTxtByWhere(String txtPath,double x,double y) {
        Double douValue=null;
        File file = new File(txtPath);
        if (file.isFile() && file.exists()) {
            //try-with-resources 模式 进行流释放
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                //写入数据
                String text = null;
                int i=0;
                while ((text = bufferedReader.readLine()) != null) {
                    String[] strDatas = text.trim().split(",");
                    if (i > 7 && strDatas[0] != null) {
                        Double douX = Double.parseDouble(strDatas[0]);
                        Double douY = Double.parseDouble(strDatas[1]);
                        if ((x - 0.5) == douX && (y - 0.5) == douY) {
                            System.out.print("====================>index-"+(i+1)+"\r\n");
                            douValue = Double.parseDouble(strDatas[2]);
                        }
                    }
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return douValue;
    }
}

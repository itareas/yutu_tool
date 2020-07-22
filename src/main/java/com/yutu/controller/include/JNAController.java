package com.yutu.controller.include;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * @Author: zhaobc
 * @Date: 2020/07/22 10:59
 * @Description:
 **/
public class JNAController {
    /**
     * @Author: zhaobc
     * @Date: 2020/7/22 11:00
     * @Description: 读取dll动态库的方法
     **/
    public interface JnaLibrary extends Library {
        //初始化原始的动态库   默认路径为resource/win32-x86-64/**
        JNAController.JnaLibrary INSTANCE = Native.load("GDAL.dll", JNAController.JnaLibrary.class);

        //初始化动态库内部方法
        int  AODInversion(String pszSrcFileOne, String pszSrcFileTwo, String pszDstFile, int deleteFile);
        int  Ndvi(String pszSrcFileOne, String pszSrcFileTwo, String pszDstFile, int deleteFile);
        int  Haze(String pszAodFile, String pszDstFile);
        int  PM25Inversion(String pszAodFile, String pszDstFile, double ha, double rh);
        int  PM10Inversion(String pszAodFile, String pszDstFile, double ha, double rh);
    }

    //main函数调用
    public static void main(String[] args) {
//        int max = JNAController.JnaLibrary.INSTANCE.AODInversion("D:\\attachment\\model\\modis\\data\\orders\\2020-07-13\\MOD021KM.A2020195.0155.061.2020195130954.hdf","D:\\attachment\\model\\modis\\data\\orders\\2020-07-13\\MOD03.A2020195.0155.061.2020195085846.hdf","D:\\attachment\\model\\modis\\data\\product\\AOD\\2020-07-12\\MOD021KM.A2020195.0155.061.tif",0);
         int max = JNAController.JnaLibrary.INSTANCE.PM25Inversion("D:\\attachment\\model\\modis\\data\\product\\AOD\\2020-07-12\\AOD.MOD021KM.A2020194.0435.061.tif", "D:\\attachment\\model\\modis\\data\\product\\PM25\\2020-07-12\\MOD021KM.A2020195.0155.061.tif",-1,-1);

     }
}

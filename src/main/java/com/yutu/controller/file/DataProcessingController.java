package com.yutu.controller.file;

import com.yutu.utils.myutils.ReadFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @Author: zhaobc
 * @Date: 2020/07/01 09:50
 * @Description:数据处理工具
 **/
@RestController
@RequestMapping(value = "dataprocessing")
public class DataProcessingController {

    /**
     * @Author: zhaobc
     * @Date: 2020/7/1 9:58
     * @Description: 数据校正
     **/
    @RequestMapping(value = "correcting")
    public Object correcting(double x, double y) throws FileNotFoundException {
        Map<String, List<String>> fileMap = getAllFiles("D:\\attachment\\model\\datatemp\\pingdingshan\\pds-data-all", "_1HR_");
        List<List<Object>> dataList = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : fileMap.entrySet()) {
            List<Object> objData = new ArrayList<>();
            String date = entry.getKey().replace("_M", "").replace("_D", "").replace("_", "");
            double douPm25 = 0.0;
            double douPm10 = 0.0;
            double douNo2 = 0.0;
            double douNo3 = 0.0;
            double douSo2 = 0.0;
            double douSo4 = 0.0;

            for (String filePath : entry.getValue()) {
                String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
                String type = fileName.substring(fileName.indexOf("_L00_") + 5, fileName.indexOf("_1HR_"));
                switch (type) {
                    case "NO2":
                        douNo2 = ReadFile.readTxtByWhere(filePath, x, y);
                        break;
                    case "NO3":
                        douNo3 = ReadFile.readTxtByWhere(filePath, x, y);
                        break;
                    case "PM2.5":
                        douPm25 = ReadFile.readTxtByWhere(filePath, x, y);
                        break;
                    case "PM10":
                        douPm10 = ReadFile.readTxtByWhere(filePath, x, y);
                        break;
                    case "SO2":
                        douSo2 = ReadFile.readTxtByWhere(filePath, x, y);
                        break;
                    case "SO4":
                        douSo4 = ReadFile.readTxtByWhere(filePath, x, y);
                        break;
                }
            }
            objData.add(0, date);
            objData.add(1, douPm25);
            objData.add(2, douPm10);
            objData.add(3, douNo2);
            objData.add(4, douNo3);
            objData.add(5, douSo2);
            objData.add(6, douSo4);
            dataList.add(objData);
        }
        List<Object> headList = new ArrayList<>();
        headList.add("date");
        headList.add("pm25");
        headList.add("pm10");
        headList.add("no2");
        headList.add("no3");
        headList.add("so2");
        headList.add("so4");
        //地址
        String outputPath="D:\\attachment\\model\\datatemp\\pingdingshan\\output\\" + (int) x + "_" + (int) y + "_correcting";
        //输出结果
        boolean bool = ReadFile.writeCsv(headList, dataList, outputPath);
        if (bool) {
            return outputPath+".csv";
        } else {
            return bool;
        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/4/1 14:01
     * @Description: 获得文件夹下所有文件
     **/
    public Map<String, List<String>> getAllFiles(String path, String where) {
        Map<String, List<String>> filesMap = new LinkedHashMap<>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String filePath = tempList[i].toString();
                String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
                if (fileName.contains(where)) {
                    String fileKey = fileName.substring(0, 15);
                    if (filesMap.containsKey(fileKey)) {
                        List<String> valueList = filesMap.get(fileKey);
                        valueList.add(filePath);
                        filesMap.put(fileKey, valueList);
                    } else {
                        List<String> valueList = new ArrayList<>();
                        valueList.add(filePath);
                        filesMap.put(fileKey, valueList);
                    }
                }
            }
        }
        return filesMap;
    }

}

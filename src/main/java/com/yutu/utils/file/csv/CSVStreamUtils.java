package com.yutu.utils.file.csv;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhaobc
 * @Date: 2020/6/10 19:41
 * @Description: Csv原生流读取写入
 **/
public class CSVStreamUtils {

    /*  //样例调用
    List<Map<String,Object>> csvData= CsvStreamUtils.readCsv("C:\\Users\\yutu897\\Documents\\263EM\\zhaobc@mapuni.com\\receive_file\\RANK(ALL)_NO3_24HR_CONC.CSV",",",5,new String[]{"a","b","x","y","value"});
    Boolean bol=  CsvStreamUtils.writeCsv(csvData,"C:\\Users\\yutu897\\Documents\\263EM\\zhaobc@mapuni.com\\receive_file\\2020RANK(ALL)_NO3_24HR_CONC.CSV",null);
    System.out.print("====>"+bol);*/

    /**
     * @Author: zhaobc
     * @Date: 2020/5/25 11:38
     * @Description: 只读取文件 成List<Map<String, Object>>
     **/
    public static List<Map<String, Object>> readCsv(String filePath, String split, int index, String[] headers) {
        //设置返回值
        List<Map<String, Object>> dateList = new ArrayList<>();
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                //设置index
                int i = 1;
                StringBuffer sb = new StringBuffer();
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    String[] texts = text.split(split);
                    if (i > index && texts[0] != null && texts[0].trim().length() > 0) {
                        Map<String, Object> mapData = new HashMap<>();
                        for (int j = 0; j < texts.length; j++) {
                            //判断是否用客户输入的标题
                            if (headers != null && headers.length == texts.length) {
                                mapData.put(headers[j], texts[j]);
                            } else {
                                mapData.put(letters[j], texts[j]);
                            }
                        }
                        dateList.add(mapData);
                    }
                    i++;
                }
                //释放资源
                fileInputStream.close();
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
        return dateList;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/6/10 18:47
     * @Description: 根据List<Map < String, Object>> 写入csv文件  split:分隔符 默认为，
     **/
    public static boolean writeCsv(List<Map<String, Object>> dataList, String filePath, String split) {
        BufferedWriter csvWtriter = null;
        File csvFile = null;
        try {
            csvFile = new File(filePath + ".csv");
            //判断目录是否存在
            if (csvFile.getParentFile() != null && !csvFile.getParentFile().exists()) {
                csvFile.getParentFile().mkdirs();
            }
            csvFile.createNewFile();
            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "utf-8"), 1024);

            boolean bootHead = false;
            // 循环数据，写入
            for (Map<String, Object> map : dataList) {
                //判断是添加头部
                if (bootHead == false) {
                    //判断是添加头部
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        StringBuffer sb = new StringBuffer();
                        String rowStr = sb.append(entry.getKey()).append(split == null ? "," : split).toString();
                        csvWtriter.write(rowStr);
                    }
                    csvWtriter.newLine();
                    bootHead = true;
                }
                //添加真正数据
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    StringBuffer sb = new StringBuffer();
                    String rowStr = sb.append(entry.getValue()).append(split == null ? "," : split).toString();
                    csvWtriter.write(rowStr);
                }
                csvWtriter.newLine();
            }
            csvWtriter.flush();
            //释放资源
            csvWtriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (csvWtriter != null) {
                try {
                    csvWtriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/5/25 11:38
     * @Description: 只读取文件
     **/
    public static List<List<Double>> readCsv(String filePath, String split, int index) {
        File file = new File(filePath);
        List<List<Double>> dataList = new ArrayList<>();
        if (file.isFile() && file.exists()) {
            FileInputStream fileInputStream=null;
            try {
                fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuffer sb = new StringBuffer();
                String text = null;
                int i = 0;
                while ((text = bufferedReader.readLine()) != null) {
                    List<Double> data = new ArrayList<>();
                    String[] strDatas = text.split(split);
                    if (i > index && strDatas[0] != null) {
                        data.add(Double.parseDouble(strDatas[2]));
                        data.add(Double.parseDouble(strDatas[3]));
                        data.add(Double.parseDouble(strDatas[4]));
                        sb.append(text + "\r\n");
                        dataList.add(data);
                    }
                    i++;
                }
                //释放资源
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(fileInputStream!=null){
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return dataList;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/6/10 18:47
     * @Description: 写入csv文件
     **/
    public static File writeCsv(List<Object> headers, List<List<Object>> dataList, String filePath) {
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(filePath + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();
            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "utf-8"), 1024);
            // 写入文件头部
            writeRow(headers, csvWtriter);
            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
            //释放资源
            csvWtriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(csvWtriter!=null) {
                try {
                    csvWtriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return csvFile;
    }

    /**
     * 写一行数据方法
     *
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append(data).append(",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }
}

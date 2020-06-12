package com.yutu.utils.file;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: zhaobc
 * @Date: 2020-03-04 18:20
 * @Description:原生文件的操作类
 */
public class FileOperationUtils {
    /**
     * @Author: zhaobc
     * @Date: 2020/5/25 11:38
     * @Description: 只读取文件
     **/
    public static String readTxt(String txtPath) {
        String result = null;
        File file = new File(txtPath);
        if (file.isFile() && file.exists()) {
            //try-with-resources 模式 进行流释放
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                //写入数据
                StringBuffer sb = new StringBuffer();
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    sb.append(text + "\r\n");
                }
                result = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * @Author: zhaobc
     * @Date: 2020/6/10 18:39
     * @Description: 写入文件方法  txtpath:地址   content:内容
     **/
    public static Boolean writeTxt(String txtPath, String content) {
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(txtPath);
            //判断文件是否存在，如果不存在就新建
            if (file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
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
        return false;
    }


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
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
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
            } catch (Exception e) {
                e.printStackTrace();
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (csvWtriter != null) {
                try {
                    csvWtriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/5/25 11:38
     * @Description: 只读取文件
     **/
    public static List<List<Double>> readCsv(String filePath, String split, int rowIndex, int[] colIndexs) {
        File file = new File(filePath);
        List<List<Double>> dataList = new ArrayList<>();
        if (file.isFile() && file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuffer sb = new StringBuffer();
                String text = null;
                int i = 0;
                while ((text = bufferedReader.readLine()) != null) {
                    List<Double> data = new ArrayList<>();
                    String[] strDatas = text.split(split);
                    if (i > rowIndex && strDatas[0] != null) {
                        for (int col: colIndexs) {
                            data.add(Double.parseDouble(strDatas[col]));
                        }
                        dataList.add(data);
                    }
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/6/10 18:47
     * @Description: 写入csv文件
     **/
    public static boolean writeCsv(List<Object> headers, List<List<Object>> dataList, String filePath) {
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
            //运行结果记录
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (csvWtriter != null) {
                try {
                    csvWtriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
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


    /**
     * @Author: zhaobc
     * @Date: 2020/6/10 18:37
     * @Description: 文件拷贝功能, IO原生
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
            input.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null || output != null) {
                input.close();
                output.close();
            }
        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2020-03-04 18:21
     * @Description: 读取文件并根据map进行替换
     **/
    public static String readTxtByReplace(String txtPath, Map<String, String> map) {
        String result = null;
        File file = new File(txtPath);
        if (file.isFile() && file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
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
                result = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/6/11 14:13
     * @Description: 压缩文件   sourceFilePath 源文件路径;  zipFilePath 压缩后文件存储路径;zipFilename 压缩文件名
     **/
    public static boolean compressToZip(String sourceFilePath, String zipFilePath, String zipFilename) {
        Boolean bool = true;
        File sourceFile = new File(sourceFilePath);
        File zipPath = new File(zipFilePath);
        if (!zipPath.exists()) {
            zipPath.mkdirs();
        }
        File zipFile = new File(zipPath + File.separator + zipFilename);
        //try 自动释放资源
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            bool = writeZip(sourceFile, "", zos);
            //文件压缩完成后，删除被压缩文件
        } catch (Exception e) {
            bool = false;
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
        return bool;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/6/11 14:11
     * @Description: 遍历所有文件，压缩   file 源文件目录; parentPath 压缩文件目 ;zos 文件流
     **/
    private static boolean writeZip(File file, String parentPath, ZipOutputStream zos) {
        Boolean bool = true;
        if (file.isDirectory()) {
            //目录
            parentPath += file.getName() + File.separator;
            File[] files = file.listFiles();
            for (File f : files) {
                writeZip(f, parentPath, zos);
            }
        } else {
            //文件
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                //指定zip文件夹
                ZipEntry zipEntry = new ZipEntry(parentPath + file.getName());
                zos.putNextEntry(zipEntry);
                int len;
                byte[] buffer = new byte[1024 * 10];
                while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, len);
                    zos.flush();
                }
            } catch (Exception e) {
                bool = false;
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        }
        return bool;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/6/11 14:16
     * @Description: 删除文件夹
     **/
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        //删除空文件夹
        return dir.delete();
    }
}

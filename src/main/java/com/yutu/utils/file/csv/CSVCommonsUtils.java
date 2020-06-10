package com.yutu.utils.file.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @Author: zhaobc
 * @Date: 2020/06/10 19:41
 * @Description:Csv借助工具 commons-csv 导出导入
 **/
public class CSVCommonsUtils {



    /**
     * @Author: zhaobc
     * @Date: 2020/6/10 19:49
     * @Description: 读取CSV数据
     **/
    public static List<CSVRecord> readCSV(String filePath, String[] headers) throws IOException{
        //创建CSVFormat
        CSVFormat formator = CSVFormat.DEFAULT.withHeader(headers);
        FileReader fileReader=new FileReader(filePath);
        //创建CSVParser对象
        CSVParser parser=new CSVParser(fileReader,formator);
        List<CSVRecord> records=parser.getRecords();
        parser.close();
        fileReader.close();
        return records;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/6/10 19:45
     * @Description: CSV写入
     **/
    public static void writeCsv(String[] headers, List<String[]> data, String filePath,String split) throws IOException {
        //初始化csvformat
        CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(split);
        //创建FileWriter对象
        FileWriter fileWriter=new FileWriter(filePath);
        //创建CSVPrinter对象
        CSVPrinter printer=new CSVPrinter(fileWriter,formator);
        //写入列头数据
        printer.printRecord(headers);
        if(null!=data){
            //循环写入数据
            for(String[] lineData:data){
                printer.printRecord(lineData);
            }
        }
    }

}

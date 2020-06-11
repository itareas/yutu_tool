package com.yutu;

import com.yutu.utils.file.ReadFileUtils;
import com.yutu.utils.file.csv.CSVStreamUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class YutuApplication {

    public static void main(String[] args) throws IOException {
//        List<Map<String,Object>> mapList= CSVStreamUtils.readCsv("C:\\Users\\yutu897\\Documents\\263EM\\zhaobc@mapuni.com\\receive_file\\2020RANK(ALL)_NO3_24HR_CONC.CSV.csv",",",5,null);
        //springboot启动
                SpringApplication.run(YutuApplication.class, args);
    }

}

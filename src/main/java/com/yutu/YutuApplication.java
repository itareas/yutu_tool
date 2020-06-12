package com.yutu;

import com.yutu.utils.file.CSVUtils;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class YutuApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(YutuApplication.class, args);
    }

}

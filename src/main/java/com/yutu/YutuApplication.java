package com.yutu;

import com.yutu.entity.ConfigConstants;
import com.yutu.utils.file.CSVUtils;
import com.yutu.utils.file.PropertiesUtils;
import com.yutu.utils.file.office.WordUtils;
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
        runConfigConstants();
        SpringApplication.run(YutuApplication.class, args);
    }

    /**
     * @Author: zhaobc
     * @Date: 2020-01-14 11:02
     * @Description: 业务配置文件位置
     **/
    private static void runConfigConstants() {
        //设置配置文件路径
        PropertiesUtils.path = "application.properties";
        //获取业务配置文件区域
        ConfigConstants.MYSQL_DRIVER = PropertiesUtils.get("spring.datasource.driverClassName");
        ConfigConstants.MYSQL_URL = PropertiesUtils.get("spring.datasource.url");
        ConfigConstants.MYSQL_USERNAME = PropertiesUtils.get("spring.datasource.username");
        ConfigConstants.MYSQL_PASSWORD = PropertiesUtils.get("spring.datasource.password");
    }
}

package com.yutu.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhaobc
 * @Date: 2020/5/25 16:23
 * @Description: mysql数据库版任务获取
 */
@Repository
public interface IDatabaseDao {
    /**
     * @Author: zhaobc
     * @Date: 2020/5/25 16:29
     * @Description: 获得最新一条任务处理
     **/
    List<Map<String, Object>> getSourceslist(String region);

    int delSourceslist(String uuid);
}

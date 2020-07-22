package com.yutu.dao.impl;

import com.yutu.dao.IDatabaseDao;
import com.yutu.entity.ConfigConstants;
import com.yutu.utils.database.jdbc.SqlHelper;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhaobc
 * @Date: 2020/3/26 13:53
 * @Description:数据库操作
 */

public class DatabaseDaoImpl implements IDatabaseDao {
    private static Logger logger = Logger.getLogger(DatabaseDaoImpl.class);

    /**
     * @Author: zhaobc
     * @Date: 2020/3/26 14:00
     * @Description: 获得最新一条任务处理
     **/
    public List<Map<String, Object>> getSourceslist(String region) {
        String sqlSource = "SELECT UUID,lon,lat FROM  t_bus_sourceslist WHERE region=?";
        String[] parameters = {region};
        List<Map<String, Object>> listMap = SqlHelper.executeQuery(sqlSource, parameters);
        return listMap;
    }

    @Override
    public int delSourceslist(String uuid) {
        String updateStatus = "DELETE FROM  t_bus_sourceslist WHERE UUID=? ";
        Object[] parameters = {uuid};
        int resultIndex = SqlHelper.executeUpdate(updateStatus, parameters);
        return resultIndex;
    }

}

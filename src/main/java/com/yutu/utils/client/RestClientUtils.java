package com.yutu.utils.client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.Set;

/**
 * @Author: zhaobc
 * @Date: 2019/12/29 12:11
 * @Description:访问restFul接口通用客户端
 */
public class RestClientUtils {
    private static WebTarget target = null;

    /**
     * @Author: zhaobc
     * @Date: 2019/12/29 16:01
     * @Description: get调用接口 有参数
     **/
    public static <T> T get(String url, String path, Map<String, Object> params, Class<T> rtnClass) {
        if (url != null) {
            target = ClientBuilder.newClient().target(url);
        }
        WebTarget pathTarget = target.path(path);
        Set<String> keys = params.keySet();
        for (String string : keys) {
            pathTarget = pathTarget.queryParam(string, params.get(string));
        }
        return pathTarget.request().get(rtnClass);
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/12/29 16:01
     * @Description: post调用有参数
     **/
    public static <T> T post(String url, String path, Map<String, Object> queryParam, Class<T> rtnClass) {
        if (url != null) {
            target = ClientBuilder.newClient().target(url);
        }
        WebTarget pathTarget = target.path(path);
        Set<String> keys = queryParam.keySet();
        for (String string : keys) {
            pathTarget = pathTarget.queryParam(string, queryParam.get(string));
        }
        return pathTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(queryParam, MediaType.APPLICATION_JSON_TYPE), rtnClass);
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/12/29 16:04
     * @Description: put方式调用接口
     **/
    public static <T> T put(String url, String path, Map<String, Object> queryParam, Class<T> rtnClass) {
        if (url != null) {
            target = ClientBuilder.newClient().target(url);
        }
        WebTarget pathTarget = target.path(path);
        Set<String> keys = queryParam.keySet();
        for (String string : keys) {
            pathTarget = pathTarget.queryParam(string, queryParam.get(string));
        }
        return pathTarget.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.entity(queryParam, MediaType.APPLICATION_JSON_TYPE), rtnClass);
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/12/29 16:06
     * @Description: 发送delete请求
     **/
    public static <T> T delete(String url, String path, Map<String, Object> params, Class<T> rtnClass) {
        if (url != null) {
            target = ClientBuilder.newClient().target(url);
        }
        WebTarget pathTarget = target.path(path);
        Set<String> keys = params.keySet();
        for (String string : keys) {
            pathTarget = pathTarget.queryParam(string, params.get(string));
        }
        return pathTarget.request().delete(rtnClass);
    }
}

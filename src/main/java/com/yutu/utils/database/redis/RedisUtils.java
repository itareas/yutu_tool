package com.yutu.utils.database.redis;

import com.yutu.entity.ConfigConstants;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: zhaobc
 * @Date: 2020/5/26 15:03
 * @Description:redis操作工具类
 */
public class RedisUtils {
    private static JedisPool jedisPool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestWhileIdle(true);
        config.setMinEvictableIdleTimeMillis(60000L);
        config.setTimeBetweenEvictionRunsMillis(30000L);
        config.setNumTestsPerEvictionRun(-1);
        //最后参数设置2号库
        jedisPool = new JedisPool(config, ConfigConstants.REDIS_HOST, ConfigConstants.REDIS_PORT, 10000, ConfigConstants.REDIS_PASSWORD, 2);
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/5/26 15:30
     * @Description: 获得key值
     **/
    public static String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            jedis.close();
            e.printStackTrace();
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/5/26 15:30
     * @Description: 添加redis参数
     **/
    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            //释放redis对象
            jedis.close();
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/5/26 15:33
     * @Description: 查询队列数量
     **/
    public static long getLen(String key) {
        Jedis jedis = null;
        long pushLen = 0;
        try {
            jedis = jedisPool.getResource();
            pushLen = jedis.llen(key);
        } catch (Exception e) {
            //释放redis对象
            jedis.close();
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
        return pushLen;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/5/26 15:33
     * @Description: 【正循】存储REDIS队列 顺序存储字节类型数据
     **/
    public static void lpush(String key, String value) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(key, value);
        } catch (Exception e) {
            //释放redis对象
            jedis.close();
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/5/26 15:34
     * @Description: 【反序】存储REDIS队列 反向存储字节类型数据
     **/
    public static void rpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.rpush(key, value);

        } catch (Exception e) {
            //释放redis对象
            jedis.close();
            e.printStackTrace();

        } finally {
            //返还到连接池
            close(jedis);

        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/5/26 15:35
     * @Description: 【先进后出】获取队列字节类型数据
     **/
    public static String lpop(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.lpop(key);
        } catch (Exception e) {
            //释放redis对象
            jedis.close();
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
        return value;
    }

    /**
     * 【先进先出】获取队列字节类型数据
     *
     * @param key
     * @return
     * @author:zhaobc
     * @creatTime:2019年2月18日 - 下午2:45:36
     */
    public static String rpop(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.rpop(key);
        } catch (Exception e) {
            //释放redis对象
            jedis.close();
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
        return value;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/5/26 15:30
     * @Description: 释放redis资源
     **/
    private static void close(Jedis jedis) {
        try {
            jedis.close();
        } catch (Exception e) {
            if (jedis.isConnected()) {
                jedis.quit();
                jedis.disconnect();
            }
        }
    }
}

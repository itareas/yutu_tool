package com.yutu.utils.data.sort;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: zhaobc
 * @Date: 2020/06/09 13:32
 * @Description:map集合排序类
 **/
public class MapSortUtils {


   /**
   * @Author: zhaobc
   * @Date: 2020/6/16 22:26
   * @Description: 按键升序排列
   **/
    public static <K, V> Map<K, V> sortByKeyAsc(Map<K, V> originMap) {
        if (originMap == null) {
            return null;
        }
        return sort(originMap, comparatorByKeyAsc);
    }

    /**
    * @Author: zhaobc
    * @Date: 2020/6/16 22:27
    * @Description: 按键降序排列
    **/
    public static <K, V> Map<K, V> sortByKeyDesc(Map<K, V> originMap) {
        if (originMap == null) {
            return null;
        }
        return sort(originMap, comparatorByKeyDesc);
    }


    /**
    * @Author: zhaobc
    * @Date: 2020/6/16 22:27
    * @Description: 按值升序排列
    **/
    public static <K, V> Map<K, V> sortByValueAsc(Map<K, V> originMap) {
        if (originMap == null) {
            return null;
        }
        return sort(originMap, comparatorByValueAsc);
    }

    /**
    * @Author: zhaobc
    * @Date: 2020/6/16 22:27
    * @Description: 按值降序排列
    **/
    public static <K, V> Map<K, V> sortByValueDesc(Map<K, V> originMap) {
        if (originMap == null) {
            return null;
        }
        return sort(originMap, comparatorByValueDesc);
    }

    /**
    * @Author: zhaobc
    * @Date: 2020/6/16 22:28
    * @Description: 排序主方法
    **/
    private static <K, V> Map<K, V> sort(Map<K, V> originMap, Comparator<Map.Entry> comparator) {
        return originMap.entrySet().stream().sorted(comparator).collect(
             Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,LinkedHashMap::new));
    }

    private static Comparator<Map.Entry> comparatorByKeyAsc = (Map.Entry o1, Map.Entry o2) -> {
        if (o1.getKey() instanceof Comparable) {
            return ((Comparable) o1.getKey()).compareTo(o2.getKey());
        }
        throw new UnsupportedOperationException("键的类型尚未实现Comparable接口");
    };


    private static Comparator<Map.Entry> comparatorByKeyDesc = (Map.Entry o1, Map.Entry o2) -> {
        if (o1.getKey() instanceof Comparable) {
            return ((Comparable) o2.getKey()).compareTo(o1.getKey());
        }
        throw new UnsupportedOperationException("键的类型尚未实现Comparable接口");
    };


    private static Comparator<Map.Entry> comparatorByValueAsc = (Map.Entry o1, Map.Entry o2) -> {
        if (o1.getValue() instanceof Comparable) {
            return ((Comparable) o1.getValue()).compareTo(o2.getValue());
        }
        throw new UnsupportedOperationException("值的类型尚未实现Comparable接口");
    };


    private static Comparator<Map.Entry> comparatorByValueDesc = (Map.Entry o1, Map.Entry o2) -> {
        if (o1.getValue() instanceof Comparable) {
            return ((Comparable) o2.getValue()).compareTo(o1.getValue());
        }
        throw new UnsupportedOperationException("值的类型尚未实现Comparable接口");
    };
}

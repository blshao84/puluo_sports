/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puluo.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * map工具
 * 
 * @author mefan
 * 
 */
public class Maps
{
    static class DoubleValueMapComparator<K> implements Comparator
    {
        Map<K, Double> map;
        
        public DoubleValueMapComparator(Map<K, Double> map)
        {
            this.map = map;
        }
        
        @Override
        public int compare(Object o1, Object o2)
        {
            Double tmp = map.get(o1);
            Double tmp2 = map.get(o2);
            double value1 = tmp == null ? -1 : tmp.doubleValue();
            double value2 = tmp2 == null ? -1 : tmp2.doubleValue();
            if (value1 < value2) return 1;
            else if (value1 == value2) return 0;
            else return -1;
        }
        
    }
    
    /**
     * 以两个参数为一个元组单位，创建一个map
     * 
     * @author mefan
     * @param objects
     * @return
     */
    public static Map create(Object... objects)
    {
        if ((objects != null) && ((objects.length % 2) > 0))
            throw new RuntimeException("别太放肆，没什么用");
        Map mo = new HashMap();
        if (ArrayUtils.isEmpty(objects)) return mo;
        for (int i = 0; i < objects.length; i += 2)
            mo.put(objects[i], objects[i + 1]);
        return mo;
    }
    
    /**
     * 当不存在时使用默认值
     * 
     * @author mefan
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @param d
     * @return
     */
    public static <K, V> V getOrDefault(Map<K, V> map, K key, V d)
    {
        V valueV = map.get(key);
        if (valueV == null) return d;
        return valueV;
    }
    
    /**
     * 从map中找出key对应的值，如果值不存在，则使用newClazz new出一个新实例并保存到map
     * 
     * @author mefan
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @param newClazz
     * @return
     */
    public static <K, V> V getOrNew(Map<K, V> map, K key, Class<? extends V> newClazz)
    {
        V valueV = map.get(key);
        if (valueV == null)
        {
            try
            {
                valueV = newClazz.newInstance();
            }
            catch (Exception e)
            {
                throw new RuntimeException("class 必须有默认构建方法(无参)");
            }
            map.put(key, valueV);
            return valueV;
        }
        return valueV;
    }
    
    /**
     * 同getOrNew，用于new一个集合<br>
     * ps:该死的java泛型
     * 
     * @author mefan
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @param newClazz
     * @return
     */
    public static <K, V, S extends V> S getOrNewColl(Map<K, V> map, K key, Class<S> newClazz)
    {
        V valueV = map.get(key);
        if (valueV == null)
        {
            try
            {
                valueV = newClazz.newInstance();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
            map.put(key, valueV);
            return (S) valueV;
        }
        return (S) valueV;
    }
    
    /**
     * 同getOrDefault,null会使用默认值替换
     * 
     * @author mefan
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @param d
     * @return
     */
    public static <K, V> V getOrSet(Map<K, V> map, K key, V d)
    {
        V valueV = map.get(key);
        if (valueV == null)
        {
            map.put(key, d);
            return d;
        }
        return valueV;
    }
    
    /**
     * 使 计数型map 增长
     * 
     * @author mefan
     * @param <T>
     * @param map
     *            待增长的map
     * @param key
     * @param count
     *            对应的value增长多少
     */
    public static <T> void inc(Map<T, Integer> map, T key, int count)
    {
        int result = Maps.getOrDefault(map, key, 0);
        map.put(key, result + count);
    }
    
    /**
     * 一个map是否空对象
     * 
     * @author mefan
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map)
    {
        return (map == null) || map.isEmpty();
    }
    
    /**
     * map 排序
     * 
     * @author mefan
     * @param <K>
     * @param <V>
     * @param map
     * @param comparator
     * @return
     */
    public static <K, V> Map<K, V> sort(Map<K, V> map, Comparator comparator)
    {
        if (isEmpty(map) || (comparator == null)) return Collections.EMPTY_MAP;
        TreeMap<K, V> sorted_map = new TreeMap(comparator);
        sorted_map.putAll(map);
        return sorted_map;
    }
    
    public static <K, Double> Map<K, Double> sortDoubleValueMap(Map<K, Double> map)
    {
        Comparator comparator = new DoubleValueMapComparator(map);
        return sort(map, comparator);
        
    }
    
}

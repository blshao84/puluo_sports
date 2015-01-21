package com.puluo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


// import lejian.extensions.log.Log;
// import lejian.extensions.log.LogFactory;
/**
 * 集合工具
 * 
 * @author mefan
 * 
 */
public abstract class Colls
{
    private static final Log LOG = LogFactory.getLog(Colls.class);
    
    /**
     * 一个集合是否空集合
     * 
     * @author mefan
     * @param coll
     * @return
     */
    public static boolean isEmpty(final Collection coll)
    {
        return (coll == null) || coll.isEmpty();
    }
    
    /**
     * 判断集合的数量是否少于指于数目
     * 
     * @author mefan
     * @param coll
     * @param num
     * @return
     */
    public static boolean isLessThan(final Collection coll, final int num)
    {
        Condition.assertion(num > 0);
        if (Colls.isEmpty(coll)) { return true; }
        if (coll instanceof ArrayList)
        {
            return coll.size() < num;
        }
        else
        {
            final Iterator it = coll.iterator();
            for (int i = 0; i < num; i++)
            {
                if (it.hasNext())
                {
                    it.next();
                }
                else
                {
                    return true;
                }
            }
            return false;
        }
        
    }
    
    /**
     * 分割一个集合成为子集合
     * 
     * @author mefan
     * @param <T>
     * @param coll
     * @param splitCount
     *            多少进行分割
     * @param collClass
     *            子集合类型
     * @return
     */
    public static <T> Collection<Collection<T>> split(final Collection<T> coll, final int splitCount,
            Class<? extends Collection> collClass)
    {
        if (Colls.isEmpty(coll) || (splitCount == 0)) { return Collections.EMPTY_LIST; }
        if ((coll instanceof ArrayList) && (coll.size() <= splitCount)) { return Arrays.asList(coll); }
        
        collClass = collClass == null ? ArrayList.class : collClass;
        
        final Collection<Collection<T>> collections = new ArrayList<Collection<T>>();
        Collection<T> subCollection = null;
        int count = 0;
        for (final T t : coll)
        {
            final int remainder = count % splitCount;
            if (remainder == 0)
            {
                try
                {
                    subCollection = collClass.newInstance();
                }
                catch (final InstantiationException e)
                {
                    Colls.LOG.error(e);
                }
                catch (final IllegalAccessException e)
                {
                    Colls.LOG.error(e);
                }
                collections.add(subCollection);
            }
            subCollection.add(t);
            count++;
        }
        return collections;
    }
    
    /**
     * 分割字符串
     * 
     * @author mefan
     * @param <T>
     * @param str
     *            待分割
     * @param split
     *            分割符
     * @param toType
     *            分割后转换的类型
     * @return
     */
    public static <T> Collection<T> split(String str, String split, Class<T> toType)
    {
        if (Strs.isEmpty(str)) { return Collections.EMPTY_LIST; }
        String[] results = str.split(split);
        if (ArrayUtils.isEmpty(results)) { return Collections.EMPTY_LIST; }
        if ((toType == null) || toType.equals(String.class)) { return (Collection<T>) Arrays.asList(results); }
        
        if (toType.equals(Long.class))
        {
            List<Long> numbers = new LinkedList<Long>();
            for (String string : results)
            {
                if (!Strs.isEmpty(string))
                {
                    numbers.add(Long.valueOf(string));
                }
            }
            return (Collection<T>) numbers;
        }
        
        throw new RuntimeException("未实现的分支类型");
        
    }
    
    /**
     * set转list
     * 
     * @author mefan
     * @param <T>
     * @param set
     * @return
     */
    public static <T> List<T> toList(Collection<T> coll)
    {
        if (coll == null) { return Collections.EMPTY_LIST; }
        if (coll instanceof List) { return (List<T>) coll; }
        List<T> list = new ArrayList<T>(coll);
        return list;
    }
    
    /**
     * list转set
     * 
     * @author mefan
     * @param <T>
     * @param list
     * @return
     */
    public static <T> Set<T> toSet(Collection<T> coll)
    {
        if (Colls.isEmpty(coll)) { return Collections.EMPTY_SET; }
        if (coll instanceof Set) { return (Set<T>) coll; }
        Set<T> set = new LinkedHashSet<T>(coll);
        return set;
    }
    
    /**
     * FIXME:存在性能问题，当前版本不推荐使用
     * 
     * @author mefan
     * @param <T>
     * @param list
     * @return
     */
    public static <T> List<T> toUniqueList(List<T> list)
    {
        return Colls.toList(Colls.toSet(list));
    }
}

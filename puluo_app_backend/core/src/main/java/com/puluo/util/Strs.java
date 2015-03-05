package com.puluo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * StringUtils的语法糖
 * 
 * @author mefan
 * 
 */
public abstract class Strs
{
    public static final String BLANK = "";
    public static final String NULL = null;
    
    /**
     * 构建一个重复的字符串,如?,?,?,?
     * 
     * @author mefan
     * @param repeatStr要重复的字符串
     * @param count要重复多少次
     * @param split分割符是什么
     *            ，允许为null
     * @return
     */
    public static String build(String repeatStr, int count, String split)
    {
        Condition.assertion(count > 0, "count不能小于1");
        StringBuilder builder = new StringBuilder();
        split = split == null ? Strs.BLANK : split;
        for (int i = 0; i < count; i++)
        {
            builder.append(repeatStr);
            builder.append(split);
        }
        int size = builder.length();
        builder.delete(size - 1, size);
        return builder.toString();
    }
    
    /**
     * 两个字符串是否匹配
     * 
     * @author mefan
     * @param str
     * @param str2
     * @return
     */
    public static boolean equals(String str, String str2)
    {
        if ((str == null) && (str2 == null)) { return true; }
        if (str != null) { return str.equals(str2); }
        return str2.equals(str);
    }
    
    public static String filter(String str)
    {
        if (Strs.isEmpty(str)) { return Strs.BLANK; }
        return str.replaceAll("[^\u4e00-\u9fa5\\w\\s]", "");
    }
    
    /**
     * 一个字符串是否为空
     * 
     * @author mefan
     * @param str
     * @return
     */
    public static boolean isEmpty(String str)
    {
        return (str == null) || str.isEmpty();
    }
    
    /**
     * 将一个集合合成一个字符串
     * 
     * @author mefan
     * @param collection
     * @param split分割符
     * @return
     */
    public static String join(Collection collection, String split)
    {
        return Strs.join(collection, split, null, null);
    }
    
    /**
     * 合并一个集合成字符串
     * 
     * @author mefan
     * @param <T>
     * @param ts
     * @param split
     * @return
     */
    public static String join(Collection collection, String split, String left, String right)
    {
        if (Colls.isEmpty(collection)) { return Strs.BLANK; }
        split = (split == null) ? Strs.BLANK : split;
        left = (left == null) ? Strs.BLANK : left;
        right = (right == null) ? Strs.BLANK : right;
        StringBuilder builder = new StringBuilder();
        for (Object object : collection)
        {
            
            builder.append(left);
            builder.append(object == null ? null : object.toString());
            builder.append(right);
            builder.append(split);
        }
        if (!Strs.isEmpty(split))
        {
            builder.delete(builder.length() - split.length(), builder.length());
        }
        
        return builder.toString();
    }
    
    public static String join(Map map, String entrySplit, String kvSplit)
    {
        if (Maps.isEmpty(map)) { return Strs.BLANK; }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry entry : (Set<Map.Entry>) map.entrySet())

        {
            builder.append(entry.getKey()).append(kvSplit).append(entry.getValue());
            builder.append(entrySplit);
        }
        if (!Strs.isEmpty(entrySplit))
        {
            builder.delete(builder.length() - entrySplit.length(), builder.length());
        }
        return builder.toString();
        
    }
    
    /**
     * 构建一个字符串，功能 类似 "str1" + "str2"
     * 
     * @author mefan
     * @param contents
     * @return
     */
    public static String join(Object... contents)
    {
        if ((contents == null) || (contents.length == 0)) { return Strs.BLANK; }
        if (contents.length == 1)
        {
            return contents[0].toString();
        }
        else
        {
            StringBuilder builder = new StringBuilder();
            for (Object content : contents)
            {
                if (content != null)
                {
                    builder.append(content);
                }
            }
            return builder.toString();
        }
    }
    
    /**
     * 合并一个集合成字符串
     * 
     * @author mefan
     * @param <T>
     * @param ts
     * @param split
     * @return
     */
    public static <T> String join(T[] ts, String split)
    {
        if (ArrayUtils.isEmpty(ts)) { return Strs.BLANK; }
        StringBuilder builder = new StringBuilder();
        for (Object object : ts)
        {
            builder.append(object.toString());
            builder.append(split);
        }
        if (!Strs.isEmpty(split))
        {
            builder.delete(builder.length() - split.length(), builder.length());
        }
        return builder.toString();
    }
    
    public static void main(String[] args)
    {
        System.out.println(Strs.filter(" dsdfsdf,23.990901023123{}[]]123123(**@&^@#!@J吴津经"));
    }
    
    /**
     * 分割
     * 
     * @author mefan
     * @param str
     * @param splitter
     * @return
     */
    public static List<String> split(String str, String splitter)
    {
        if (Strs.isEmpty(str)) { return Collections.EMPTY_LIST; }
        StringTokenizer stringTokenizer = new StringTokenizer(str, splitter);
        List<String> list = new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens())
        {
            String tmpString = stringTokenizer.nextToken();
            tmpString = tmpString.trim();
            if (!tmpString.equals(""))
            {
                list.add(tmpString);
            }
        }
        return list;
    }
    
    /**
     * 使首字母大写
     * 
     * @author mefan
     * @param str
     * @return
     */
    public static String toUpperFirstChar(String str)
    {
        char[] cs = str.toCharArray();
        cs[0] = Character.toUpperCase(cs[0]);
        return String.valueOf(cs);
    }
    
    public static String prettyDouble(double d, int format) {
        String formatter = Strs.join("%.",format,"f");
        return String.format(formatter, d);
    }
}

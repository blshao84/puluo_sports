package com.puluo.util;



public abstract class ArrayUtils
{
    /**
     * 判断一个数组是否空数组
     * @author mefan
     * @param <T>
     * @param array
     * @return
     */
    public static <T> boolean isEmpty(T[] array)
    {
        if (array == null)
            return true;
        for (T t : array)
            if (t != null)
                return false;

        return true;
    }
}

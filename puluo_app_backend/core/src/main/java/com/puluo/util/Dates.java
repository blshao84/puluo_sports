package com.puluo.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author mefan
 * 
 */
public abstract class Dates
{
    public static final Date NULL = null;

    /**
     * 判断date2是否在date之后
     * 
     * @param date
     *            如果为null,date2要比date后
     * @param date2
     * @return
     */
    public static boolean after(final Date date, final Date date2)
    {
        if (date2 == null) { return false; }
        if (date == null) { return true; }
        return date2.after(date);
    }

    public static int getCurDay()
    {
        return (int) (new Date().getTime() / (24 * 3600));
    }

    public static int getCurHour()
    {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static void main(final String[] args)
    {
        System.out.println(getCurDay());

        System.out.println(Dates.getCurHour());
    }

    /**
     * 返回当前时间
     * 
     * @author mefan
     * @return
     */
    public static Date now()
    {
        return new Date();
    }

}

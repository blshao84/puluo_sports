package com.puluo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {
    public static String formatDate(Date dt){
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        f.setTimeZone(TimeZone.getDefault());
        String format;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -10);
        try {
            format = f.format(dt);

        } catch (Exception e) {
            java.util.Date defDay = c.getInstance().getTime();
            format = f.format(defDay);
        }
        return format;
    }
}

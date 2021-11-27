package com.bigdata.store.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: duYang
 * @Date: 2021/6/22 11:06
 * @Version: 1.0
 */
public class TimeUtil {
    private static SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getTime(){
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static long getTimeMillis(){
        Date date = new Date();
        return date.getTime();
    }
}

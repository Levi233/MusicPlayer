package com.chenhao.musicplayer.utils;

import android.text.format.Time;

import java.text.SimpleDateFormat;

/**
 * Created by chenhao on 2016/11/7.
 */

public class DateUtils {

    private static Time today = new Time();
    private static android.text.format.Time time = new Time();

    public static String millisecondFormat(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        String format = simpleDateFormat.format(time);
        return format;
    }

    public static String commentTimeFormat(long msec, boolean hasTime) {
        today.setToNow();
        time.set(msec);  //%Y%m%dT%H%M
        if (time.year < today.year) { //去年的时间
            return time.format(hasTime ? "%Y-%m-%d %H:%M" : "%Y-%m-%d");
        } else if (time.year == today.year) { //今年时间
            if (time.month < today.month) {  //上一个月以前
                return time.format(hasTime ? "%m-%d %H:%M" : "%m-%d");
            } else if (time.month == today.month) {  //当前月
                if (time.monthDay == today.monthDay) {  //当天时间
                    long minSpace = System.currentTimeMillis() - msec;
                    if (minSpace < 3600000) { //如果是一小时以内的时间
                        int minVal = (int) (minSpace / 60000);
                        if (minVal < 1) {
                            return "刚刚";
                        } else {
                            return minVal + "分钟前";
                        }
                    } else {
                        return time.format("%H:%M");
                    }
                } else if ((time.monthDay + 1) == today.monthDay) { //昨天
                    return hasTime ? "昨天 " + time.format("%H:%M") : "昨天";
                } else if ((time.monthDay + 1) < today.monthDay) {
                    return time.format(hasTime ? "%m-%d %H:%M" : "%m-%d");
                } else {
                    return time.format(hasTime ? "%Y-%m-%d %H:%M" : "%Y-%m-%d"); //此为未来时间，统一全显示格式
                }
            } else {
                return time.format(hasTime ? "%Y-%m-%d %H:%M" : "%Y-%m-%d"); //此为未来时间，统一全显示格式
            }
        } else {
            return time.format(hasTime ? "%Y-%m-%d %H:%M" : "%Y-%m-%d");//此为未来时间，统一全显示格式
        }
    }
}

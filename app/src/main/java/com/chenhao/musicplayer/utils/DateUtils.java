package com.chenhao.musicplayer.utils;

import java.text.SimpleDateFormat;

/**
 * Created by chenhao on 2016/11/7.
 */

public class DateUtils {
    public static String millisecondFormat(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        String format = simpleDateFormat.format(time);
        return format;
    }
}

package com.chenhao.musicplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chenhao on 2016/11/9.
 */

public class ObjectSaveUtil {
    private final static String FILENAME = "data_save";

    public static void saveInteger(Context context, String key, int obj) {
        SharedPreferences.Editor sharedata = context.getSharedPreferences(FILENAME, 0).edit();
        sharedata.putInt(key, obj);
        sharedata.commit();
    }

    public static int readInteger(Context context, String key, int defValue) {
        SharedPreferences sharedata = context.getSharedPreferences(FILENAME, 0);
        return sharedata.getInt(key, defValue);
    }
}

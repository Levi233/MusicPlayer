package com.chenhao.musicplayer;

import android.app.Application;
import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by chenhao on 2016/11/27.
 */

public class App extends Application {

    public static String mSecretKey = "";
    private static Handler mainThreadHandler	= new Handler();

    private static synchronized void initKsingSecretKey(){
        if (TextUtils.isEmpty(mSecretKey)){
            String millis = String.valueOf(System.currentTimeMillis()) + "12345678";
            mSecretKey = millis.substring(0,8);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initKsingSecretKey();
    }

    public static Handler getMainThreadHandler() {
        return mainThreadHandler;
    }
}

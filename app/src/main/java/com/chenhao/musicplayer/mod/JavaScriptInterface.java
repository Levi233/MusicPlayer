package com.chenhao.musicplayer.mod;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by chenhao on 2016/10/12.
 */
public class JavaScriptInterface {

    @JavascriptInterface
    public String get_dev_info(){
        Log.e("chenhaolog","JavaScriptInterface [get_dev_info]-----------");
        return "{}";
    }

    @JavascriptInterface
    public void jsCallNative(String json){
        Log.d("chenhaolog", "jsCallNative: " + json);
    }

}

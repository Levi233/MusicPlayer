package com.chenhao.musicplayer.utils;

import android.util.Log;

import com.chenhao.musicplayer.App;
import com.chenhao.musicplayer.utils.crypt.Base64Coder;
import com.chenhao.musicplayer.utils.crypt.IOUtils;
import com.chenhao.musicplayer.utils.crypt.KuwoDES;

import java.io.UnsupportedEncodingException;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by chenhao on 2016/11/16.
 */

public class MyUtils {
    public static String decode(byte[] data) {
        byte[] rawBytes = data;
        if (rawBytes == null || rawBytes.length <= 6) {
            Log.e("chenhaolog", "rawBytes is null or length <= 6");
            return null;
        }
        String datas = null;
        try {
            datas = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] lines = datas.split("\r\n");
        String firstLine = lines[0].trim();
        // String retSig = null;
        if (!firstLine.startsWith("sig=")) {
            if ("TP=none".equalsIgnoreCase(firstLine)) {
                return firstLine;
            }
            Log.e("chenhaolog", "firstLine not starsWith sig");
            return null;
        }
        // retSig = firstLine.substring(4).trim();
        int index = firstLine.getBytes().length + "\r\n".getBytes().length;
        if (index > rawBytes.length){
            return  null;
        }

        byte[] zip = new byte[4];
        zip[0] = rawBytes[0 + index];
        zip[1] = rawBytes[1 + index];
        zip[2] = rawBytes[2 + index];
        zip[3] = rawBytes[3 + index];

        int zipLength = IOUtils.parseInteger(zip, false);

        if (zipLength > rawBytes.length - index) {
            return null;
        }

        zip[0] = rawBytes[4 + index];
        zip[1] = rawBytes[5 + index];
        zip[2] = rawBytes[6 + index];
        zip[3] = rawBytes[7 + index];

        int rawLength = IOUtils.parseInteger(zip, false);
        zip = null;
        Inflater decompresser = new Inflater();
        decompresser.setInput(rawBytes, 8 + index, zipLength);
        byte[] byteResult = null;
        try {
            byteResult = new byte[rawLength];
        } catch (OutOfMemoryError e1) {
            Log.e("chenhaolog", "ys:handleQukuResult|oom");
        }
        try {
            decompresser.inflate(byteResult);
        } catch (Exception e) {
            Log.e("chenhaolog", "ys:handleQukuResult|数据解压失败");
            return null;
        } finally {
            decompresser.end();
        }
        String resultXml = null;
        try {
            String s[] = new String(byteResult).split("\n");
//			for (String string : s) {
//				LogMgr.d("xiaoniu", "ys:|" + string);
//			}
            resultXml = new String(byteResult).replaceAll("\r", "").replaceAll("\n", "");
        } catch (OutOfMemoryError e) {
            Log.e("chenhaolog", "ys:handleQukuResult|replace oom");
            return null;
        }

        try {
            byteResult = resultXml.getBytes();
        } catch (OutOfMemoryError e) {
        }

        rawBytes = null;
        byte[] validxmlprefix = { '<', '?' };
        index = IOUtils.indexOf(byteResult, 0, validxmlprefix);
        if (index == -1) {
            Log.e("chenhaolog", "ys:handleQukuResult|数据格式错误");
            return null;
        }
        if (index == 0) {
        } else {
            rawLength = byteResult.length - index;
            byte[] byteResultTrimed = null;
            try {
                byteResultTrimed = new byte[rawLength];
            } catch (OutOfMemoryError e1) {
                Log.e("chenhaolog", "ys:handleQukuResult|oom");
            }
            System.arraycopy(byteResult, index, byteResultTrimed, 0, rawLength);
            byteResult = byteResultTrimed;
        }
        return new String(byteResult);
    }

    public static String decodeKSing(String data) {
        String str = "";
        try {
            // 防止服务器秘钥错误,解密失败
            byte[] b64Data = Base64Coder.decode(data);
            str = new String(KuwoDES.decryptKSing(b64Data, App.mSecretKey.getBytes())).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String numFormat(int i){
        if(i < 100000){
            return i+"";
        }else{
            int j = i / 10000;
            return j+"万";
        }
    }

    public static void loadPic(String url ,Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }
}

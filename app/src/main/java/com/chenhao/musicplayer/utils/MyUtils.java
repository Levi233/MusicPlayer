package com.chenhao.musicplayer.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chenhao.musicplayer.App;
import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.MusicInfo;
import com.chenhao.musicplayer.utils.crypt.Base64Coder;
import com.chenhao.musicplayer.utils.crypt.IOUtils;
import com.chenhao.musicplayer.utils.crypt.KuwoDES;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenhao on 2016/11/16.
 */

public class MyUtils {

    private static int start = 0;
    private static boolean mTag;
    private static SharedPreferences mSp;


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
        if (index > rawBytes.length) {
            return null;
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
        byte[] validxmlprefix = {'<', '?'};
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

    public static String numFormat(int i) {
        if (i < 100000) {
            return i + "";
        } else {
            int j = i / 10000;
            return j + "万";
        }
    }

    public static void loadPic(String url, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void showBottomDialog(Context context, View contentView) {
        Dialog mCameraDialog = new Dialog(context, R.style.my_dialog);
        mCameraDialog.setContentView(contentView);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialog_anim_style);// 添加动画  
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();// 获取对话框当前的参数值  
        lp.x = 0;// 新位置X坐标  
        lp.y = -20;// 新位置Y坐标  
        lp.width = (int) context.getResources().getDisplayMetrics().widthPixels;// 宽度  
//      lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度  
//      lp.alpha = 9f; // 透明度  
        contentView.measure(0, 0);
        lp.height = contentView.getMeasuredHeight();
//        lp.alpha = 9f;// 透明度  
        dialogWindow.setAttributes(lp);
        mCameraDialog.setCanceledOnTouchOutside(true);
        mCameraDialog.setCancelable(true);
        mCameraDialog.show();
    }

    private static void Download(final Context context, String url, final String name) {
        mSp = context.getSharedPreferences("total", 0);
        long total = mSp.getLong("total", -1);
        final String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ChenHaoMusic";
        OkHttpClient okHttpClient = new OkHttpClient();
        File file = new File(SDPath + "/tag.txt");
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        Request request;
        if (file.exists() && file.length() > 0 && total != -1) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
                String lastPosition = bfr.readLine();   //线程上次下载的位置
                start = Integer.parseInt(lastPosition);
            } catch (Exception e) {
                e.printStackTrace();
            }
            request = new Request.Builder().url(url)
                    .addHeader("Range", "bytes=" + start + "-" + (total - 1)).build();
            mTag = true;
        } else {
            request = new Request.Builder().url(url)
                    .build();
        }
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("chenhaolog", "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[1024 * 1024];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    mSp = context.getSharedPreferences("total", 0);
                    mSp.edit().putLong("total", total).commit();
                    File file = new File(SDPath, name+".mp3");
                    fos = new FileOutputStream(file, mTag);
                    mTag = false;
                    long sum = 0;
                    RandomAccessFile raf = new RandomAccessFile(SDPath + "/tag.txt", "rwd");
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        raf.seek(0);
                        raf.write(String.valueOf(start + sum).getBytes());
                        final int progress = (int) ((start + sum) * 1.0f / (total + start) * 100);
                        Log.d("chenhaolog", "progress=" + progress);
                        App.getMainThreadHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(context,"已下载："+progress);
                            }
                        });
                    }
                    fos.flush();
                    File file1 = new File(SDPath + "/tag.txt");
                    file1.delete();
                    start = 0;
                    raf.close();
                    Log.i("chenhaolog", "文件下载成功");
                } catch (Exception e) {
                    Log.e("chenhaolog", "文件下载失败");
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    //根据rid获取真正的url
    public static void DownloadMusic(final Context context, final MusicInfo info) {
        String url = OnlineUrlUtil.getMusicUrl(info.getRid());
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                String s = new String(bytes);
                String[] split = s.split("\r\n");
                String str = split[2];
                String[] split2 = str.split("rl=");
                String url = split2[1];
                Download(context,url,info.getName());
            }
        });
    }
}

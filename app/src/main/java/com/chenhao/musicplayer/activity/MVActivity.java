package com.chenhao.musicplayer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chenhao.musicplayer.App;
import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.utils.OnlineUrlUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MVActivity extends AppCompatActivity {

    private SurfaceView mSurfaceView;
    private IjkMediaPlayer ijkMediaPlayer;
    private long mRid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_mv);
//        getSupportActionBar().hide();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mRid = (long) extras.get("rid");

        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.addCallback(new MyCallBack());

        ijkMediaPlayer = new IjkMediaPlayer();

        ijkMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                ijkMediaPlayer.start();
            }
        });
        playMV();
    }

    public void playMV() {
        String url = OnlineUrlUtil.getMVUrl(mRid, "mp4");
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
              String bytes = response.body().string();
                String[] items = bytes.split("\\n|\\r\\n");
                for(String item:items) {
                    if(item.startsWith("bitrate=")){
                        int pos = item.indexOf("=");
                        if (pos == -1) {
                            continue;
                        }
                    }
                    if (item.startsWith("url=")) {
                        int pos = item.indexOf("=");
                        if (pos == -1) {
                            continue;
                        }
                        final String url = item.substring(pos + 1).trim();
                        final Uri uri = Uri.parse(url);
                        App.getMainThreadHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ijkMediaPlayer.setDataSource(url);
                                    ijkMediaPlayer.prepareAsync();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private class MyCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            ijkMediaPlayer.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }


    @Override
    protected void onPause() {
        if(ijkMediaPlayer != null){
            ijkMediaPlayer.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(ijkMediaPlayer != null){
            ijkMediaPlayer.start();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if(ijkMediaPlayer != null){
            ijkMediaPlayer.stop();
        }
        super.onDestroy();
    }

    private void setFullScreen(){
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}

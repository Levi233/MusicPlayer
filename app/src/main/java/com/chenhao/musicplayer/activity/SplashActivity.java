package com.chenhao.musicplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.MusicInfo;
import com.chenhao.musicplayer.db.MusicDao;
import com.chenhao.musicplayer.messagemgr.MessageManager;
import com.chenhao.musicplayer.mod.MediaPlayerManager;
import com.chenhao.musicplayer.utils.ObjectSaveUtil;

import java.util.ArrayList;

public class SplashActivity extends Activity {

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        MediaPlayerManager.getInstance().init();
        int mode = ObjectSaveUtil.readInteger(this, "play_mode", 0);
        MediaPlayerManager.getInstance().setPlayMode(mode);
        new Thread(new MessageManager.Runner() {
            @Override
            public void call() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        SplashActivity.this.finish();
                    }
                },1500);
            }
        }).start();
        MusicDao dao = new MusicDao(this);
        Cursor cursor = dao.find();
        ArrayList<MusicInfo> musicInfos = new ArrayList<MusicInfo>();
        if(cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()) {
                MusicInfo musicInfo = new MusicInfo();
                long rid = cursor.getLong(0);
                String name = cursor.getString(1);
                String artist = cursor.getString(2);
                String url = cursor.getString(3);
                musicInfo.setRid(rid);
                musicInfo.setName(name);
                musicInfo.setArtist(artist);
                musicInfo.setUrl(url);
                musicInfos.add(musicInfo);
            }
        }
        MediaPlayerManager.getInstance().setInfos(musicInfos);
    }
}

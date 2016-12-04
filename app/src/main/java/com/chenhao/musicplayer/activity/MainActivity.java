package com.chenhao.musicplayer.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.MusicInfo;
import com.chenhao.musicplayer.fragment.PlayPageFragment;
import com.chenhao.musicplayer.local.MineMainFragment;
import com.chenhao.musicplayer.messagemgr.MediaPlayerObserver;
import com.chenhao.musicplayer.messagemgr.MessageID;
import com.chenhao.musicplayer.messagemgr.MessageManager;
import com.chenhao.musicplayer.mod.FragmentControl;
import com.chenhao.musicplayer.mod.MediaPlayerManager;
import com.chenhao.musicplayer.online.OnlineMusicMainFragment;
import com.chenhao.musicplayer.utils.ObjectSaveUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private TextView mMusicName;
    private TextView mSingerName;
    private ImageView mPlayList;
    private ImageView mPlayAndPause;
    private TextView mExit;
    private ImageView mNext;
    private ProgressBar mProgress;
    private ImageView mLateralDropdownImg;
    private RelativeLayout mPlayBar;
    private Call mCall = null;
    private Thread mThread = null;
    private String[] TITLE = new String[]{"本地音乐", "推荐"};
    private DrawerLayout mDrawerLayout;
    private static MainActivity instance;
    private ImageView mMyPage;
    private ImageView mRecommendedPage;
    private String mCurrentMusicName = "好音质 用MediaPlayer";
    private int mCurrentPosition = -1;

    private MediaPlayerObserver mObserver = new MediaPlayerObserver() {

        @Override
        public void startMusic(List<MusicInfo> infos, int position) {
            Log.e("chenhaolog", "MainActivity [startMusic] " + position);
            ObjectSaveUtil.saveInteger(MainActivity.this, "CurrentPosition", position);
            mPlayAndPause.setImageResource(R.drawable.playbar_btn_pause);
            mProgress.setMax(MediaPlayerManager.getInstance().getMusicDuration());
            if (mThread == null) {
                mCall = new Call();
                mThread = new Thread(mCall);
                mThread.start();
            }
        }

        @Override
        public void stopMusic() {
            mPlayAndPause.setImageResource(R.drawable.playbar_btn_play);
            mProgress.setProgress(0);
        }

        @Override
        public void onPause() {
            if (mCall != null) {
                mCall.cancel();
                mThread = null;
            }
            mPlayAndPause.setImageResource(R.drawable.playbar_btn_play);
        }

        @Override
        public void onBuffering(int i) {
        }

        @Override
        public void onPrepared(List<MusicInfo> infos, int position) {
        }

        @Override
        public void onRefreshUI(List<MusicInfo> infos, int position) {
            mCurrentPosition = position;
            mMusicName.setText(infos.get(position).getName());
            mCurrentMusicName = infos.get(position).getName();
            mMusicName.setText(mCurrentMusicName);
            if(!TextUtils.isEmpty(infos.get(position).getArtist())){
                mSingerName.setText(infos.get(position).getArtist());
                mSingerName.setVisibility(View.VISIBLE);
            }else{
                mSingerName.setVisibility(View.GONE);
            }
        }
    };

    public class Call implements Runnable {

        private boolean flag = true;

        public void cancel() {
            this.flag = false;
        }

        @Override
        public void run() {
            //设置当前进度
            while (flag) {
//                Log.e("chenhaolog","当前线程 ： "+Thread.currentThread().getId());
                int p = MediaPlayerManager.getInstance().getMusicCurrentPosition();
                mProgress.setProgress(p);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.w("chenhaolog", Thread.currentThread().getId() + "运行结束");
        }
    }

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        instance = this;
        MessageManager.getInstance().attachMessage(MessageID.OBSERVER_MEDIA_PLAYER, mObserver);
        initView();
        setAdapterAndListener();
        int position = ObjectSaveUtil.readInteger(this, "CurrentPosition", -1);
        if (position > -1) {
            MediaPlayerManager.getInstance().setMediaPlayerUrl(position, false);
        }
    }

    private void setAdapterAndListener() {
        mPlayList.setOnClickListener(this);
        mPlayAndPause.setOnClickListener(this);
        mNext.setOnClickListener(this);
        mPlayBar.setOnClickListener(this);
        mLateralDropdownImg.setOnClickListener(this);
        mMyPage.setOnClickListener(this);
        mRecommendedPage.setOnClickListener(this);
        mExit.setOnClickListener(this);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//        mIndicator.setViewPager(mViewPager);
//        mUnderlinePageIndicator.setViewPager(mViewPager);
//        mUnderlinePageIndicator.setFades(false);
//        mIndicator.setOnPageChangeListener(mUnderlinePageIndicator);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mRecommendedPage.setImageResource(R.drawable.actionbar_discover_normal);
                        mMyPage.setImageResource(R.drawable.actionbar_music_selected);
                        break;
                    case 1:
                        mRecommendedPage.setImageResource(R.drawable.actionbar_discover_selected);
                        mMyPage.setImageResource(R.drawable.actionbar_music_normal);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        mViewPager.setCurrentItem(1);
    }

    private void initView() {
        mPlayBar = (RelativeLayout) findViewById(R.id.playback_control_bar);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mMusicName = (TextView) findViewById(R.id.music_name);
        mSingerName = (TextView) findViewById(R.id.singer_name);
        mPlayList = (ImageView) findViewById(R.id.btn_playlist);
        mPlayAndPause = (ImageView) findViewById(R.id.btn_play_and_pause);
        mNext = (ImageView) findViewById(R.id.btn_next);
        mProgress = (ProgressBar) findViewById(R.id.my_progress);
        mExit = (TextView) findViewById(R.id.exit_text);
//        mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
//        mUnderlinePageIndicator = (UnderlinePageIndicatorEx) findViewById(R.id.underline_indicator);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mLateralDropdownImg = (ImageView) findViewById(R.id.Lateral_dropdown_img);
        mMyPage = (ImageView) findViewById(R.id.my_page);
        mRecommendedPage = (ImageView) findViewById(R.id.recommended_page);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.app_name,R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_playlist:

                break;
            case R.id.btn_play_and_pause:
                if(MediaPlayerManager.getInstance().getMediaPlayState() == MediaPlayerManager.MEDIA_PLAY_DEFAULT){
                    Toast.makeText(this,"当前无歌曲播放",Toast.LENGTH_LONG).show();
                    return;
                }
                MediaPlayerManager.getInstance().pauseOrPlay();
                break;
            case R.id.btn_next:
                if(MediaPlayerManager.getInstance().getMediaPlayState() == MediaPlayerManager.MEDIA_PLAY_DEFAULT){
                    Toast.makeText(this,"当前无歌曲播放",Toast.LENGTH_LONG).show();
                    return;
                }
                MediaPlayerManager.getInstance().playerNext();
                break;
            case R.id.playback_control_bar:
                FragmentControl.getInstance().showSubFrag(PlayPageFragment.getInstance(mCurrentMusicName,mSingerName.getText().toString().trim()), PlayPageFragment.class.getName());
                break;
            case R.id.Lateral_dropdown_img:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.recommended_page:
                mViewPager.setCurrentItem(1);
                mRecommendedPage.setImageResource(R.drawable.actionbar_discover_selected);
                mMyPage.setImageResource(R.drawable.actionbar_music_normal);
                break;
            case R.id.my_page:
                mViewPager.setCurrentItem(0);
                mRecommendedPage.setImageResource(R.drawable.actionbar_discover_normal);
                mMyPage.setImageResource(R.drawable.actionbar_music_selected);
                break;
            case R.id.exit_text:
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = MineMainFragment.newInstance();
            } else if (position == 1) {
                fragment = new OnlineMusicMainFragment();
            }
            return fragment;
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            return TITLE[position % TITLE.length];
//        }

        @Override
        public int getCount() {
            return TITLE.length;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
            if(drawer.isDrawerOpen(GravityCompat.START)){
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            } else {
                getSupportFragmentManager().popBackStack();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }


    @Override
    protected void onStop() {
        super.onStop();
        ObjectSaveUtil.saveInteger(this, "CurrentPosition", mCurrentPosition);
    }

    @Override
    protected void onDestroy() {
        MediaPlayerManager.getInstance().releaseMediaPlayer();
        MessageManager.getInstance().detachMessage(MessageID.OBSERVER_MEDIA_PLAYER, mObserver);
        super.onDestroy();
    }
}

package com.chenhao.musicplayer.mod;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.chenhao.musicplayer.bean.MusicInfo;
import com.chenhao.musicplayer.messagemgr.MediaPlayerObserver;
import com.chenhao.musicplayer.messagemgr.MessageID;
import com.chenhao.musicplayer.messagemgr.MessageManager;
import com.chenhao.musicplayer.utils.OnlineUrlUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenhao on 2016/11/4.
 */

public class MediaPlayerManager {
    public final static int MEDIA_PLAY_DEFAULT = 0;
    public final static int MEDIA_PLAY_PLAYING = 1;
    public final static int MEDIA_PLAY_PAUSE = 2;
    public final static int MEDIA_PLAY_STOP = 3;
    public final static int MEDIA_PLAY_PREPARE = 4;
    public final static int MEDIA_PLAY_RESET = 5;
    public final static int MEDIA_PLAY_RELEASE = 6;
    public final static int PLAY_MODE_LIST_CYCLE = 0;
    public final static int PLAY_MODE_SINGLE_CYCLE = 1;
    public final static int PLAY_MODE_RANDOM_CYCLE = 2;
    private List<MusicInfo> mInfos = new ArrayList<>();
    private int mPosition = -1;
    private static MediaPlayerManager mInstance = null;
    private static android.media.MediaPlayer mMediaPlayer = null;
    private boolean mBufferCompletePlay = true;
    private int mState = MEDIA_PLAY_DEFAULT;
    private int mPlayMode = PLAY_MODE_LIST_CYCLE;

    private static String mClassName;

    static {
        mClassName = MediaPlayerManager.class.getSimpleName();
    }

    private MediaPlayerManager() {
    }

    public static MediaPlayerManager getInstance() {
        if (null == mInstance) {
            synchronized (MediaPlayerManager.class) {
                if (null == mInstance) {
                    mInstance = new MediaPlayerManager();
                    Log.e("chenhaolog", mClassName + "[getInstance]" + mInstance.getClass().hashCode());
                }
            }
        }
        return mInstance;
    }

    public int getPlayMode() {
        return mPlayMode;
    }

    public void setPlayMode(int playMode) {
        this.mPlayMode = playMode;
    }

    public void setInfos(List<MusicInfo> infos) {
        this.mInfos = infos;
    }

    public List<MusicInfo> getInfos() {
        return mInfos;
    }

    public int getMediaPlayState() {
        return mState;
    }

    public void init() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new android.media.MediaPlayer();
            Log.i("chenhaolog", mClassName + "[new MediaPlayer]" + mMediaPlayer.getClass().hashCode());
        }
        mMediaPlayer.setOnBufferingUpdateListener(new android.media.MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(android.media.MediaPlayer mediaPlayer, final int i) {
                MessageManager.getInstance().asyncNotify(MessageID.OBSERVER_MEDIA_PLAYER, new MessageManager.Caller<MediaPlayerObserver>() {
                    @Override
                    public void call() {
                        ob.onBuffering(i);
                        ob.onPrepared(mInfos, mPosition);
                    }
                });
                Log.i("chenhaolog", "mMediaPlayer [onBufferingUpdate]" + mMediaPlayer.getClass().hashCode());
            }
        });
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mState = MEDIA_PLAY_PREPARE;
                if (mBufferCompletePlay) {
                    mInstance.startMediaPlayer();
                }
                MessageManager.getInstance().asyncNotify(MessageID.OBSERVER_MEDIA_PLAYER, new MessageManager.Caller<MediaPlayerObserver>() {
                    @Override
                    public void call() {
                        ob.onPrepared(mInfos, mPosition);
                    }
                });
                Log.i("chenhaolog", "mMediaPlayer [onPrepared]" + mMediaPlayer.getClass().hashCode());
            }
        });
        mMediaPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(android.media.MediaPlayer mediaPlayer) {
                Log.i("chenhaolog", "mMediaPlayer [onCompletion]" + mMediaPlayer.getClass().hashCode());
                mInstance.playerNext();
            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i("chenhaolog", "mMediaPlayer [onError]" + what + ":" + extra);
                return true;
            }
        });
    }

    public void setMediaPlayerUrlAndStart(List<MusicInfo> infos, final int position) {
        if(mInfos != null && !(mPosition < 0) &&infos.get(position).getRid() == mInfos.get(mPosition).getRid()){
            mMediaPlayer.seekTo(0);
            if(!isPlaying()){
                startMediaPlayer();
            }
            setInfos(infos);
        }else {
            setInfos(infos);
            resetMediaPlayer();
            Log.i("chenhaolog", mClassName + "[setMediaPlayerUrlAndStart]" + position);
            setMediaPlayerUrl(position, true);
        }
    }

    public void setMediaPlayerUrl(int position, boolean bufferCompletePlay) {
        try {
            MessageManager.getInstance().asyncNotify(MessageID.OBSERVER_MEDIA_PLAYER, new MessageManager.Caller<MediaPlayerObserver>() {
                @Override
                public void call() {
                    ob.onRefreshUI(mInfos, mPosition);
                }
            });
            mBufferCompletePlay = bufferCompletePlay;
            mPosition = position;
            long rid = mInfos.get(position).getRid();
            if (rid > 0) {
                String url = getUrl(rid);
            } else {
                mMediaPlayer.setDataSource(mInfos.get(position).getUrl());
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.prepareAsync();
                mState = MEDIA_PLAY_PREPARE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl(long rid) {
        final String[] musicUrl = new String[1];
        String url = OnlineUrlUtil.getMusicUrl(rid);
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
                String s1 = split2[1];
                musicUrl[0] = s1;
                mMediaPlayer.setDataSource(s1);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.prepareAsync();
            }
        });
        return musicUrl[0];
    }


    public void startMediaPlayer() {
        if (mMediaPlayer != null) {
            if (mPosition < 0 && mInfos.size() < 1) {
                return;
            }
            Log.i("chenhaolog", mClassName + "[startMediaPlayer]");
            mMediaPlayer.start();
            mState = MEDIA_PLAY_PLAYING;
            MessageManager.getInstance().asyncNotify(MessageID.OBSERVER_MEDIA_PLAYER, new MessageManager.Caller<MediaPlayerObserver>() {
                @Override
                public void call() {
                    ob.startMusic(mInfos, mPosition);
                }
            });
        }
    }

    public void playerNext() {
        switch (mPlayMode){
            case PLAY_MODE_LIST_CYCLE:
                if (mMediaPlayer != null && (mPosition + 1) > 0 && mPosition< mInfos.size()) {
                    if(mPosition == mInfos.size()-1){
                        setMediaPlayerUrlAndStart(mInfos,0);
                    }else{
                        setMediaPlayerUrlAndStart(mInfos,mPosition + 1);
                    }
                }
                break;
            case PLAY_MODE_SINGLE_CYCLE:
                if (mMediaPlayer != null && mPosition > -1 && mPosition < mInfos.size()) {
                    setMediaPlayerUrlAndStart(mInfos,mPosition);
                }
                break;
            case PLAY_MODE_RANDOM_CYCLE:
                if (mMediaPlayer != null && mPosition > -1 && mPosition < mInfos.size()) {
                    Random random = new Random();
                    int position = random.nextInt(mInfos.size());
                    setMediaPlayerUrlAndStart(mInfos,position);
                }
                break;
        }
    }

    public void playerFront() {
        if (mMediaPlayer != null && (mPosition - 1) > -1 && mPosition < mInfos.size()) {
            setMediaPlayerUrlAndStart(mInfos, mPosition - 1);
        }
    }

    public boolean isPlaying() {
        boolean playing = false;
        if (mMediaPlayer != null) {
            playing = mMediaPlayer.isPlaying();
        }
        return playing;
    }

    private void pauseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            mState = MEDIA_PLAY_PAUSE;
            MessageManager.getInstance().asyncNotify(MessageID.OBSERVER_MEDIA_PLAYER, new MessageManager.Caller<MediaPlayerObserver>() {
                @Override
                public void call() {
                    ob.onPause();
                }
            });

        }
    }

    public void pauseOrPlay() {
        if (isPlaying()) {
            pauseMediaPlayer();
        } else {
            startMediaPlayer();
        }
    }

    public void stopMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mState = MEDIA_PLAY_STOP;
        }
    }

    private void resetMediaPlayer() {
        if (mMediaPlayer != null) {
            Log.i("chenhaolog", mClassName + "[resetMediaPlayer]");
            mMediaPlayer.reset();
            mState = MEDIA_PLAY_RESET;
            MessageManager.getInstance().asyncNotify(MessageID.OBSERVER_MEDIA_PLAYER, new MessageManager.Caller<MediaPlayerObserver>() {
                @Override
                public void call() {
                    ob.stopMusic();
                }
            });
        }
    }

    public void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mState = MEDIA_PLAY_RELEASE;
        }
        mMediaPlayer = null;
        mInstance = null;
    }

    public void seekTo(int position) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(position);
        }
    }

    //获取歌曲长度
    public int getMusicDuration() {
        int rtn = 0;
        if (mMediaPlayer != null) {
            rtn = mMediaPlayer.getDuration();
        }
        return rtn;
    }

    //获取当前播放进度
    public int getMusicCurrentPosition() {
        int rtn = 0;
        if (mMediaPlayer != null) {
            rtn = mMediaPlayer.getCurrentPosition();

        }
        return rtn;
    }

}

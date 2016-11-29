package com.chenhao.musicplayer.messagemgr;

import com.chenhao.musicplayer.bean.MusicInfo;

import java.util.List;

/**
 * Created by chenhao on 2016/11/4.
 */

public interface MediaPlayerObserver extends IObserverBase {
    public void startMusic(List<MusicInfo> infos, int position);
    public void stopMusic();
    public void onPause();
    public void onBuffering(int i);
    public void onPrepared(List<MusicInfo> infos, int position);
    public void onRefreshUI(List<MusicInfo> infos, int position);
}

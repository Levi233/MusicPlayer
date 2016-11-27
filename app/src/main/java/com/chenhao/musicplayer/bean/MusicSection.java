package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/27.
 */

public class MusicSection extends Section {
    @Override
    public int getItemViewType() {
        return ItemViewType.TYPE_MUSIC.ordinal();
    }

    @Override
    public String toString() {
        return "MusicSection{} " + super.toString();
    }
}

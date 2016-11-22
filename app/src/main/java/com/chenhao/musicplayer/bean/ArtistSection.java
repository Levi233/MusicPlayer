package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/22.
 */

public class ArtistSection extends Section {
    @Override
    public int getItemViewType() {
        return ItemViewType.TYPE_ARTIST.ordinal();
    }

    @Override
    public String toString() {
        return "ArtistSection{} " + super.toString();
    }
}

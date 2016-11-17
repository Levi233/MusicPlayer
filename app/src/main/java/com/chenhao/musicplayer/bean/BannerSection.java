package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class BannerSection extends Section {
    @Override
    public int getItemViewType() {
        return ItemViewType.TYPE_BANNER.ordinal();
    }

    @Override
    public String toString() {
        return "BannerSection{} " + super.toString();
    }
}

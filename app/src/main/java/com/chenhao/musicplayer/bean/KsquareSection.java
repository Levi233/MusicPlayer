package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class KsquareSection extends Section {
    @Override
    public int getItemViewType() {
        return ItemViewType.TYPE_KSQUARE.ordinal();
    }

    @Override
    public String toString() {
        return "KsquareSection{} " + super.toString();
    }
}

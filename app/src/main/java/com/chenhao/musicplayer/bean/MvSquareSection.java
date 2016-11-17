package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class MvSquareSection extends Section {
    @Override
    public int getItemViewType() {
        return ItemViewType.TYPE_MVSQUARE.ordinal();
    }

    @Override
    public String toString() {
        return "MvSquareSection{} " + super.toString();
    }
}

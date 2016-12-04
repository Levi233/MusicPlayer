package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/12/4.
 */

public class MvSection extends Section {
    @Override
    public int getItemViewType() {
        return ItemViewType.TYPE_MV.ordinal();
    }

    @Override
    public String toString() {
        return "MvSection{} " + super.toString();
    }
}

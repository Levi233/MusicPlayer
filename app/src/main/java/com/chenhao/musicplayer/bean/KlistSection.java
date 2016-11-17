package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class KlistSection extends Section {
    @Override
    public int getItemViewType() {
        return ItemViewType.TYPE_KLIST.ordinal();
    }

    @Override
    public String toString() {
        return "KlistSection{} " + super.toString();
    }
}

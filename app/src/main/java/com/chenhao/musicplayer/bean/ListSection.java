package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class ListSection extends Section {
    @Override
    public int getItemViewType() {
        return ItemViewType.TYPE_LIST.ordinal();
    }

    @Override
    public String toString() {
        return "ListSection{} " + super.toString();
    }
}

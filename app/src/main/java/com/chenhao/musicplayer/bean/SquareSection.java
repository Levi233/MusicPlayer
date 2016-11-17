package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class SquareSection extends Section {
    @Override
    public int getItemViewType() {
        return ItemViewType.TYPE_SQUARE.ordinal();
    }

    @Override
    public String toString() {
        return "SquareSection{} " + super.toString();
    }
}

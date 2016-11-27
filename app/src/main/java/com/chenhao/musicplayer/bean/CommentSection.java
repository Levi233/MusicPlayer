package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/27.
 */

public class CommentSection extends Section {
    @Override
    public int getItemViewType() {
        return ItemViewType.TYPE_COMMENT.ordinal();
    }

    @Override
    public String toString() {
        return "CommentSection{} " + super.toString();
    }
}

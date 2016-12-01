package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/23.
 */

public class ListInfo extends OnlineInfo {
    private int child;
    private int isnew;

    public int getIsnew() {
        return isnew;
    }

    public void setIsnew(int isnew) {
        this.isnew = isnew;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "ListInfo{" +
                "child=" + child +
                ", isnew=" + isnew +
                "} " + super.toString();
    }
}

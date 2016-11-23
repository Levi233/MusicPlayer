package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/23.
 */

public class ListInfo extends OnlineInfo {
    private int isnew;

    public int getIsnew() {
        return isnew;
    }

    public void setIsnew(int isnew) {
        this.isnew = isnew;
    }

    @Override
    public String toString() {
        return "ListInfo{" +
                "isnew=" + isnew +
                "} " + super.toString();
    }
}

package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class BibiInfo extends OnlineInfo {
    private String purl;

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    @Override
    public String toString() {
        return "BibiInfo{" +
                "purl='" + purl + '\'' +
                '}';
    }
}

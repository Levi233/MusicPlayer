package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/21.
 */

public class HitbillboardInfo extends OnlineInfo {
    private String name1;
    private String url;

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "HitbillboardInfo{" +
                "name1='" + name1 + '\'' +
                ", url='" + url + '\'' +
                "} " + super.toString();
    }
}

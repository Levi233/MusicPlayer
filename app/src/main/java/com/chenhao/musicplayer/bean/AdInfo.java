package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class AdInfo extends OnlineInfo {
    private String small_img;
    private String version;
    private String provider;
    private String url;
    private boolean inapp;

    public String getSmall_img() {
        return small_img;
    }

    public void setSmall_img(String small_img) {
        this.small_img = small_img;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isInapp() {
        return inapp;
    }

    public void setInapp(boolean inapp) {
        this.inapp = inapp;
    }

    @Override
    public String toString() {
        return "AdInfo{" +
                "small_img='" + small_img + '\'' +
                ", version='" + version + '\'' +
                ", provider='" + provider + '\'' +
                ", url='" + url + '\'' +
                ", inapp=" + inapp +
                '}';
    }
}

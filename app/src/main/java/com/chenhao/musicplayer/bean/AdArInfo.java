package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class AdArInfo extends OnlineInfo {
    private String publish;
    private String version;
    private String provider;
    private boolean inapp;
    private String url;
    private String small_img;
    private int favorcnt;
    private int listencnt;
    private String statistics_id;

    @Override
    public String getPublish() {
        return publish;
    }

    @Override
    public void setPublish(String publish) {
        this.publish = publish;
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

    public boolean isInapp() {
        return inapp;
    }

    public void setInapp(boolean inapp) {
        this.inapp = inapp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSmall_img() {
        return small_img;
    }

    public void setSmall_img(String small_img) {
        this.small_img = small_img;
    }

    public int getFavorcnt() {
        return favorcnt;
    }

    public void setFavorcnt(int favorcnt) {
        this.favorcnt = favorcnt;
    }

    public int getListencnt() {
        return listencnt;
    }

    public void setListencnt(int listencnt) {
        this.listencnt = listencnt;
    }

    public String getStatistics_id() {
        return statistics_id;
    }

    public void setStatistics_id(String statistics_id) {
        this.statistics_id = statistics_id;
    }

    @Override
    public String toString() {
        return "AdArInfo{" +
                "publish='" + publish + '\'' +
                ", version='" + version + '\'' +
                ", provider='" + provider + '\'' +
                ", inapp=" + inapp +
                ", url='" + url + '\'' +
                ", small_img='" + small_img + '\'' +
                ", favorcnt=" + favorcnt +
                ", listencnt=" + listencnt +
                ", statistics_id='" + statistics_id + '\'' +
                '}';
    }
}

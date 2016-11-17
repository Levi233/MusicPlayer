package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class MvInfo extends OnlineInfo {
    private long rid;
    private int duration;
    private int hot;
    private String mvquality;
    private int album;
    private String format;
    private String disname;
    private String artist;
    private String res;
    private String statistics_id;
    private int listencnt;

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public String getMvquality() {
        return mvquality;
    }

    public void setMvquality(String mvquality) {
        this.mvquality = mvquality;
    }

    public int getAlbum() {
        return album;
    }

    public void setAlbum(int album) {
        this.album = album;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDisname() {
        return disname;
    }

    public void setDisname(String disname) {
        this.disname = disname;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getStatistics_id() {
        return statistics_id;
    }

    public void setStatistics_id(String statistics_id) {
        this.statistics_id = statistics_id;
    }

    public int getListencnt() {
        return listencnt;
    }

    public void setListencnt(int listencnt) {
        this.listencnt = listencnt;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "MvInfo{" +
                "rid=" + rid +
                ", duration=" + duration +
                ", hot=" + hot +
                ", mvquality='" + mvquality + '\'' +
                ", album=" + album +
                ", format='" + format + '\'' +
                ", disname='" + disname + '\'' +
                ", artist='" + artist + '\'' +
                ", res='" + res + '\'' +
                ", statistics_id='" + statistics_id + '\'' +
                ", listencnt=" + listencnt +
                '}';
    }
}

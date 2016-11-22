package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/22.
 */

public class ArtistInfo extends OnlineInfo {
    private int followers;
    private int musiccnt;
    private int albumcnt;
    private long radio_id;
    private int mvcnt;

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getMusiccnt() {
        return musiccnt;
    }

    public void setMusiccnt(int musiccnt) {
        this.musiccnt = musiccnt;
    }

    public int getAlbumcnt() {
        return albumcnt;
    }

    public void setAlbumcnt(int albumcnt) {
        this.albumcnt = albumcnt;
    }

    public long getRadio_id() {
        return radio_id;
    }

    public void setRadio_id(long radio_id) {
        this.radio_id = radio_id;
    }

    public int getMvcnt() {
        return mvcnt;
    }

    public void setMvcnt(int mvcnt) {
        this.mvcnt = mvcnt;
    }

    @Override
    public String toString() {
        return "ArtistInfo{" +
                "followers=" + followers +
                ", musiccnt=" + musiccnt +
                ", albumcnt=" + albumcnt +
                ", radio_id=" + radio_id +
                ", mvcnt=" + mvcnt +
                "} " + super.toString();
    }
}

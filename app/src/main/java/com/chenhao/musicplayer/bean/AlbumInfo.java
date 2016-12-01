package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/12/1.
 */

public class AlbumInfo extends OnlineInfo {
    private String artist;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "AlbumInfo{" +
                "artist='" + artist + '\'' +
                "} " + super.toString();
    }
}

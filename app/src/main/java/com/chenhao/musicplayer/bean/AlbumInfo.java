package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/12/1.
 */

public class AlbumInfo extends OnlineInfo {
    private String artist;
    private String big_pic;
    private int com_num;
    private int mcnum;

    public int getMcnum() {
        return mcnum;
    }

    public void setMcnum(int mcnum) {
        this.mcnum = mcnum;
    }

    public int getCom_num() {
        return com_num;
    }

    public void setCom_num(int com_num) {
        this.com_num = com_num;
    }

    public String getBig_pic() {
        return big_pic;
    }

    public void setBig_pic(String big_pic) {
        this.big_pic = big_pic;
    }

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
                ", big_pic='" + big_pic + '\'' +
                ", com_num=" + com_num +
                ", mcnum=" + mcnum +
                "} " + super.toString();
    }
}

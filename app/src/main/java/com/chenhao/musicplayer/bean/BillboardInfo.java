package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/21.
 */

public class BillboardInfo extends OnlineInfo {
    private long radio_id;
    private String name1;
    private String name2;
    private String name3;
    private String artist1;
    private String artist2;
    private String artist3;

    public long getRadio_id() {
        return radio_id;
    }

    public void setRadio_id(long radio_id) {
        this.radio_id = radio_id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getArtist1() {
        return artist1;
    }

    public void setArtist1(String artist1) {
        this.artist1 = artist1;
    }

    public String getArtist2() {
        return artist2;
    }

    public void setArtist2(String artist2) {
        this.artist2 = artist2;
    }

    public String getArtist3() {
        return artist3;
    }

    public void setArtist3(String artist3) {
        this.artist3 = artist3;
    }

    @Override
    public String toString() {
        return "BillboardInfo{" +
                "radio_id=" + radio_id +
                ", name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", name3='" + name3 + '\'' +
                ", artist1='" + artist1 + '\'' +
                ", artist2='" + artist2 + '\'' +
                ", artist3='" + artist3 + '\'' +
                "} " + super.toString();
    }
}

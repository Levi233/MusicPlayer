package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/21.
 */

public class TabInfo {
    private long id;
    private String name;
    private String desc;
    private String publish;
    private String img;
    private String url;
    private String name1;
    private String name2;
    private String name3;
    private String artist1;
    private String artist2;
    private String artist3;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
        return "TabInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", publish='" + publish + '\'' +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                ", name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", name3='" + name3 + '\'' +
                ", artist1='" + artist1 + '\'' +
                ", artist2='" + artist2 + '\'' +
                ", artist3='" + artist3 + '\'' +
                '}';
    }
}

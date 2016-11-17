package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class SongListInfo extends OnlineInfo {
    private int lossless_mark;
    private String small_img;
    private long radioid;
    private String type;
    private String imgscript;
    private String desc_url;
    private String radio_id;
    private int favorcnt;
    private int listencnt;
    private int flag;
    private String statistics_id;

    public int getLossless_mark() {
        return lossless_mark;
    }

    public void setLossless_mark(int lossless_mark) {
        this.lossless_mark = lossless_mark;
    }

    public String getSmall_img() {
        return small_img;
    }

    public void setSmall_img(String small_img) {
        this.small_img = small_img;
    }

    public long getRadioid() {
        return radioid;
    }

    public void setRadioid(long radioid) {
        this.radioid = radioid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgscript() {
        return imgscript;
    }

    public void setImgscript(String imgscript) {
        this.imgscript = imgscript;
    }

    public String getDesc_url() {
        return desc_url;
    }

    public void setDesc_url(String desc_url) {
        this.desc_url = desc_url;
    }

    public String getRadio_id() {
        return radio_id;
    }

    public void setRadio_id(String radio_id) {
        this.radio_id = radio_id;
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getStatistics_id() {
        return statistics_id;
    }

    public void setStatistics_id(String statistics_id) {
        this.statistics_id = statistics_id;
    }

    @Override
    public String toString() {
        return "SongListInfo{" +
                "lossless_mark=" + lossless_mark +
                ", small_img='" + small_img + '\'' +
                ", radioid='" + radioid + '\'' +
                ", type='" + type + '\'' +
                ", imgscript='" + imgscript + '\'' +
                ", desc_url='" + desc_url + '\'' +
                ", radio_id='" + radio_id + '\'' +
                ", favorcnt=" + favorcnt +
                ", listencnt=" + listencnt +
                ", flag=" + flag +
                ", statistics_id='" + statistics_id + '\'' +
                '}';
    }
}

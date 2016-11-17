package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class RadioInfo extends OnlineInfo {
    private String type;
    private String imgscript;
    private String desc_url;
    private long radio_id;
    private int favorcnt;
    private int listencnt;
    private int flag;

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

    public long getRadio_id() {
        return radio_id;
    }

    public void setRadio_id(long radio_id) {
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

    @Override
    public String toString() {
        return "RadioInfo{" +
                "type='" + type + '\'' +
                ", imgscript='" + imgscript + '\'' +
                ", desc_url='" + desc_url + '\'' +
                ", radio_id=" + radio_id +
                ", favorcnt=" + favorcnt +
                ", listencnt=" + listencnt +
                ", flag=" + flag +
                '}';
    }
}

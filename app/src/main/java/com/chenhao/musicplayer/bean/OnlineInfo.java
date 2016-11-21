package com.chenhao.musicplayer.bean;

import java.util.ArrayList;

/**
 * Created by chenhao on 2016/11/16.
 */
public class OnlineInfo {
    private long id;
    private String name;
    private int digest;
    private String desc;
    private String publish;
    private String extend;
    private String img;

    private ArrayList<TabInfo> tabInfos = new ArrayList<>();

    public void add(TabInfo info){
        tabInfos.add(info);
    }
    public ArrayList<TabInfo> getTabInfos() {
        return tabInfos;
    }

    public void setTabInfos(ArrayList<TabInfo> tabInfos) {
        this.tabInfos = tabInfos;
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

    public int getDigest() {
        return digest;
    }

    public void setDigest(int digest) {
        this.digest = digest;
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

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "OnlineInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", digest=" + digest +
                ", desc='" + desc + '\'' +
                ", publish='" + publish + '\'' +
                ", extend='" + extend + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}

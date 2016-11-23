package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/23.
 */

public class ClassifySection extends Section {

    private String img;
    private String name;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getItemViewType() {
        return ItemViewType.TYPE_CLASSIFY.ordinal();
    }

    @Override
    public String toString() {
        return "ClassifySection{" +
                "img='" + img + '\'' +
                ", name='" + name + '\'' +
                "} " + super.toString();
    }
}

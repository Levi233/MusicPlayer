package com.chenhao.musicplayer.bean;

import java.util.ArrayList;

/**
 * Created by chenhao on 2016/11/16.
 */

public abstract class Section {
    private String label;
    private long mid;
    private int mdigest;
    private int start;
    private String mtype;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public abstract int getItemViewType();
    private ArrayList<OnlineInfo> onlineInfos = new ArrayList<OnlineInfo>();

    public void add(OnlineInfo onlineInfo){
        onlineInfos.add(onlineInfo);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public int getMdigest() {
        return mdigest;
    }

    public void setMdigest(int mdigest) {
        this.mdigest = mdigest;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public ArrayList<OnlineInfo> getOnlineInfos() {
        return onlineInfos;
    }

    public void setOnlineInfos(ArrayList<OnlineInfo> onlineInfos) {
        this.onlineInfos = onlineInfos;
    }

    @Override
    public String toString() {
        return "Section{" +
                "onlineInfos=" + onlineInfos +
                ", label='" + label + '\'' +
                ", mid=" + mid +
                ", mdigest=" + mdigest +
                ", mtype='" + mtype + '\'' +
                '}';
    }
}

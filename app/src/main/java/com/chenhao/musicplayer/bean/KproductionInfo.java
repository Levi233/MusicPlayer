package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class KproductionInfo extends OnlineInfo {
    private int workType;

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    @Override
    public String toString() {
        return "KproductionInfo{" +
                "workType=" + workType +
                '}';
    }
}

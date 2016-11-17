package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/16.
 */

public class PictorialInfo extends OnlineInfo {
    private long statistics_id;

    public long getStatistics_id() {
        return statistics_id;
    }

    public void setStatistics_id(long statistics_id) {
        this.statistics_id = statistics_id;
    }

    @Override
    public String toString() {
        return "PictorialInfo{" +
                "statistics_id=" + statistics_id +
                '}';
    }
}

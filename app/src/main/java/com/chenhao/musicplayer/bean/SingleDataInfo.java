package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/26.
 */

public class SingleDataInfo {
    private String big_pic;
    private int com_num;
    private long ctime;
    private String desc;
    private String pic;
    private int play_num;
    private int share_num;
    private String tag;
    private String tagid;
    private int total;
    private long uid;
    private String uname;
    private String upic;

    public String getBig_pic() {
        return big_pic;
    }

    public void setBig_pic(String big_pic) {
        this.big_pic = big_pic;
    }

    public int getCom_num() {
        return com_num;
    }

    public void setCom_num(int com_num) {
        this.com_num = com_num;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getPlay_num() {
        return play_num;
    }

    public void setPlay_num(int play_num) {
        this.play_num = play_num;
    }

    public int getShare_num() {
        return share_num;
    }

    public void setShare_num(int share_num) {
        this.share_num = share_num;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpic() {
        return upic;
    }

    public void setUpic(String upic) {
        this.upic = upic;
    }

    @Override
    public String toString() {
        return "SingleDataInfo{" +
                "big_pic='" + big_pic + '\'' +
                ", com_num=" + com_num +
                ", ctime=" + ctime +
                ", desc='" + desc + '\'' +
                ", pic='" + pic + '\'' +
                ", play_num=" + play_num +
                ", share_num=" + share_num +
                ", tag='" + tag + '\'' +
                ", tagid='" + tagid + '\'' +
                ", total=" + total +
                ", uid=" + uid +
                ", uname='" + uname + '\'' +
                ", upic='" + upic + '\'' +
                '}';
    }
}

package com.chenhao.musicplayer.bean;

/**
 * Created by chenhao on 2016/11/27.
 */

public class CommentInfo extends OnlineInfo {
    private  boolean is_like;
    private int like_num;
    private String msg;
    private int r_num;
    private int state;
    private long time;
    private long u_id;
    private String u_name;
    private String u_pic;
    private CommentInfo reply;

    public boolean is_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getR_num() {
        return r_num;
    }

    public void setR_num(int r_num) {
        this.r_num = r_num;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getU_id() {
        return u_id;
    }

    public void setU_id(long u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_pic() {
        return u_pic;
    }

    public void setU_pic(String u_pic) {
        this.u_pic = u_pic;
    }

    public CommentInfo getReply() {
        return reply;
    }

    public void setReply(CommentInfo reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "is_like=" + is_like +
                ", like_num=" + like_num +
                ", msg='" + msg + '\'' +
                ", r_num=" + r_num +
                ", state=" + state +
                ", time=" + time +
                ", u_id=" + u_id +
                ", u_name='" + u_name + '\'' +
                ", u_pic='" + u_pic + '\'' +
                ", reply=" + reply +
                "} " + super.toString();
    }
}

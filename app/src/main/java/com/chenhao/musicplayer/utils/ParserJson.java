package com.chenhao.musicplayer.utils;

import com.chenhao.musicplayer.bean.CommentInfo;
import com.chenhao.musicplayer.bean.CommentSection;
import com.chenhao.musicplayer.bean.RootInfo;
import com.chenhao.musicplayer.bean.SingleDataInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by chenhao on 2016/11/26.
 */

public class ParserJson {
    public static SingleDataInfo parserSingleDataJson(String json) throws JSONException {
        SingleDataInfo songleDataInfo = new SingleDataInfo();
        JSONObject jsonObject;
        jsonObject = new JSONObject(json);
        JSONObject obj = jsonObject.getJSONObject("sl_data");
        String big_pic = getDefaultString(obj, "big_pic");
        int com_num = getDefaultInteger(obj, "com_num");
        long ctime = getDefaultLong(obj, "ctime");
        String desc = getDefaultString(obj, "desc");
        String pic = getDefaultString(obj, "pic");
        int play_num = getDefaultInteger(obj, "play_num");
        int share_num = getDefaultInteger(obj, "share_num");
        String tag = getDefaultString(obj, "tag");
        String tagid = getDefaultString(obj, "tagid");
        int total = getDefaultInteger(obj, "total");
        long uid = getDefaultLong(obj, "uid");
        String uname = getDefaultString(obj, "uname");
        String upic = getDefaultString(obj, "upic");
        songleDataInfo.setBig_pic(big_pic);
        songleDataInfo.setCom_num(com_num);
        songleDataInfo.setCtime(ctime);
        songleDataInfo.setDesc(desc);
        songleDataInfo.setPlay_num(play_num);
        songleDataInfo.setPic(pic);
        songleDataInfo.setShare_num(share_num);
        songleDataInfo.setTag(tag);
        songleDataInfo.setTagid(tagid);
        songleDataInfo.setTotal(total);
        songleDataInfo.setUid(uid);
        songleDataInfo.setUname(uname);
        songleDataInfo.setUpic(upic);
        return songleDataInfo;
    }

    public static RootInfo parserCommentJson(String json) throws JSONException, UnsupportedEncodingException {
        JSONObject jsonObject;
        jsonObject = new JSONObject(json);
        JSONObject obj = jsonObject.getJSONObject("data");
        RootInfo rootInfo = new RootInfo();
        CommentSection commentSection = new CommentSection();
        JSONArray info = obj.getJSONArray("info");
        for (int i = 0; i < info.length(); i++) {
            CommentInfo commentInfo = new CommentInfo();
            JSONObject obj1 = info.getJSONObject(i);
            setCommentInfo(obj1,commentInfo);
            JSONObject reply = obj1.optJSONObject("reply");
            if(reply != null){
                CommentInfo replyInfo = new CommentInfo();
                setCommentInfo(reply,replyInfo);
                commentInfo.setReply(replyInfo);
            }
            commentSection.add(commentInfo);
        }
        rootInfo.add(commentSection);
        return rootInfo;
    }

    private static void setCommentInfo(JSONObject obj,CommentInfo info) throws UnsupportedEncodingException {
        int id = getDefaultInteger(obj, "id");
        boolean is_like = getDefaultBoolean(obj, "is_like");
        int like_num = getDefaultInteger(obj, "like_num");
        String msg = getDefaultString(obj, "msg");
        int r_num = getDefaultInteger(obj, "r_num");
        int state = getDefaultInteger(obj, "state");
        long time = getDefaultLong(obj, "time");
        long u_id = getDefaultLong(obj, "u_id");
        String u_name = getDefaultString(obj, "u_name");
        String u_pic = getDefaultString(obj, "u_pic");
        info.setId(id);
        info.setIs_like(is_like);
        info.setLike_num(like_num);
        info.setMsg(msg);
        info.setR_num(r_num);
        info.setState(state);
        info.setTime(time);
        info.setU_id(u_id);
        info.setU_name(URLDecoder.decode(u_name, "UTF-8"));
        info.setU_pic(u_pic);
    }

    private static String getDefaultString(JSONObject obj, String key, String defaultValue) {
        String name = "";
        try {
            name = obj.getString(key);
        } catch (JSONException e) {
            name = defaultValue;
        }
        return name;
    }

    private static String getDefaultString(JSONObject obj, String key) {
        return getDefaultString(obj, key, "");
    }

    private static long getDefaultLong(JSONObject obj, String key) {
        long i = -1;
        try {
            i = obj.getLong(key);
        } catch (JSONException e) {
            i = -1;
        }
        return i;
    }

    private static int getDefaultInteger(JSONObject obj, String key) {
        int i = -1;
        try {
            i = obj.getInt(key);
        } catch (JSONException e) {
            i = -1;
        }
        return i;
    }

    private static boolean getDefaultBoolean(JSONObject obj, String key){
        boolean b = false;
        try{
            b = obj.getBoolean(key);
        }catch (JSONException e){
            return b;
        }
        return b;
    }
}

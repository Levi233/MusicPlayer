package com.chenhao.musicplayer.utils;

import com.chenhao.musicplayer.bean.SingleDataInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenhao on 2016/11/26.
 */

public class ParserJson {
    public static SingleDataInfo parserSingleDataJson(String json) throws JSONException{
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

    private static int getDefaultInteger(JSONObject obj, String key){
        int i = -1;
        try {
            i = obj.getInt(key);
        } catch (JSONException e) {
            i = -1;
        }
        return i;
    }
}

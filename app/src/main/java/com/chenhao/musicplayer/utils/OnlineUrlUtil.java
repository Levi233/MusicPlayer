package com.chenhao.musicplayer.utils;

import android.text.TextUtils;

import com.chenhao.musicplayer.utils.crypt.Base64Coder;
import com.chenhao.musicplayer.utils.crypt.KuwoDES;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by chenhao on 2016/11/24.
 */

public class OnlineUrlUtil {

    public static String HOST_SUB_LIST = "http://nmsublist.kuwo.cn/mobi.s?f=kuwo&q=";

    public static String getRecommendUrl(){
        return "http://nmobi.kuwo.cn/mobi.s?f=kuwo&q=2S5ec7LNX+pQWCHXaOs5nvnnWqyaughtHbUI3IEkHudHoPfxpFuJZY6OI9KVO/SJxQskru/aVoLIKjvXNwwUHjxFEOPVi1swkQHEb1KoQHYAYmUV/VSoO1HLcjTE+WA+W1D1fLBcpmuQ3pzdb8dh7RFQdfp5JZAUH/+jpWYq+jsxMiRDNVX8ufVa18zxSJFA8gZaVc/cq4SfU8+CbuTwXLhrFqI62sahOSpyH+57sg0eThhiFdco+1ebIdQOLFJ9WxC98fCtz/W2Rv9/BBkqhDo6CrYD5H1j2aHDplxND6/upuKIhFBKlzk4RFzONrTjG5u0CcvvkPhOHL+3bG4f9zk4RFzONrTjG5u0CcvvkPjfocDUxrRLcs5A+MNXow5iGwMo+VE+lbFFcflWKsKYFCosPy1nrcuRoqDappqcGE0QaL5+mMEluxjkKJeeQZ8Rk65c0A/A2Zav4ntNwywtnZv6VmjR/nKUVc8A3qqFevKsffRgan1mjE21o4AOAcII/o9vwYRbdKgi4Zhoht7jhssmGsVDg8ZLcPa9gmHvUkjsl7e5SuvcDVZ2VW0fG/gHP/M+dP90G8ImNIRKfKMRo3Sfno5HHcD9ZDxcmnFfv78xMMacAYTMstjJZkHHp6P54ifT81/xYEfMbtvSNsd5rYqF7Fh27z9cj3VZBKlps484Qczy3vUIpcfWAUjxUYJw";
    }
    public static String getSingerUrl(){
        return "http://nmsublist.kuwo.cn/mobi.s?f=kuwo&q=2S5ec7LNX+pQWCHXaOs5nvnnWqyaughtHbUI3IEkHudHoPfxpFuJZY6OI9KVO/SJxQskru/aVoLIKjvXNwwUHjxFEOPVi1swkQHEb1KoQHYAYmUV/VSoO1HLcjTE+WA+W1D1fLBcpmuQ3pzdb8dh7RFQdfp5JZAUKFYkIwf6k9bOn9AmweAsoXf8yCKF9v6VQN4iHq6T5o5MnqBCOxYj9mWA2cLeA9MkdIQrEeb7Bq8ncUbAt+D07iIGI/+72M0Cwb9fFhOT8tlcYZX+Bz4jkn20Sg/Tl9Cg60f8OA3MHkSiQ45IBfHkkVwp1sIyQeXY7/vk/Vi/eAiC1emsxSQCs9jJZkHHp6P54ifT81/xYEfMbtvSNsd5rYqF7Fh27z9cj3VZBKlps484Qczy3vUIpcfWAUjxUYJw";
    }
    public static String getRankRul(){
        return "http://nmsublist.kuwo.cn/mobi.s?f=kuwo&q=2S5ec7LNX+pQWCHXaOs5nvnnWqyaughtHbUI3IEkHudHoPfxpFuJZY6OI9KVO/SJxQskru/aVoLIKjvXNwwUHjxFEOPVi1swkQHEb1KoQHYAYmUV/VSoO1HLcjTE+WA+W1D1fLBcpmuQ3pzdb8dh7RFQdfp5JZAUKFYkIwf6k9bOn9AmweAsoXf8yCKF9v6VQN4iHq6T5o5MnqBCOxYj9mWA2cLeA9MkdIQrEeb7Bq8ncUbAt+D07hKTkXx081Ffwb9fFhOT8tlcYZX+Bz4jkpC4QSu7b1/I60f8OA3MHkSiQ45IBfHkkVwp1sIyQeXY7/vk/Vi/eAiC1emsxSQCs9jJZkHHp6P54ifT81/xYEfMbtvSNsd5rYqF7Fh27z9cj3VZBKlps484Qczy3vUIpcfWAUjxUYJw";
    }
    public static String getClassifyUrl(){
        return "http://nmobi.kuwo.cn/mobi.s?f=kuwo&q=2S5ec7LNX+pQWCHXaOs5nvnnWqyaughtHbUI3IEkHudHoPfxpFuJZY6OI9KVO/SJxQskru/aVoLIKjvXNwwUHjxFEOPVi1swkQHEb1KoQHYAYmUV/VSoO1HLcjTE+WA+W1D1fLBcpmuQ3pzdb8dh7RFQdfp5JZAU+vpLzsg1CWspJCU3OBc2beefATWgtiuRqPbmK0iphOWFIFBXIXk+wah+ySyvrWhNqmBVJgSRnuPsli8g40y8IzaFbuazOwwY8qAHJO8GnFQZNAgndIKyid6aBJASHLoXgB5dBs5yqzhpkkZsPYvKBGnBFYo33zYYKGrmRSHoCoLkrJ0twztVy0TEnGUXvPY+SjRKgtnzKsHviR2F6uCy5TckmQUZT7hCSlhutIPFo5c=";
    }

    public static String getRequest(String type,long id ,int start,int count,String digest){
        StringBuilder sb = new StringBuilder();
        sb.append("user=866479021253520&prod=kwplayer_ar_8.3.0.0&corp=kuwo&vipver=8.3.0.0&source=kwplayer_ar_8.3.0.0_kwdebug.apk&p2p=1&");
        sb.append("type=").append(type);//type加载更多 = sub_list
        sb.append("&uid=").append("78161491");
        sb.append("&hasrecgd=1");
        sb.append("&kubb=2");
        sb.append("&fs=1");
        sb.append("&showtype=1");
        sb.append("&id=").append(id);
        sb.append("&start=").append(start);
        sb.append("&count=").append(count);

        sb.append("&digest=").append(digest);

        sb.append("&hasmv=1");
        sb.append("&hasinner=1");

        sb.append("&hasad=1");
        sb.append("&hsy=1");
        sb.append("&isnew=2");
        sb.append("&newcate=1");
        sb.append("&supportfan=1");
        sb.append("&isg=1");
        String params = sb.toString();
        return createURL_SubList(params.getBytes());
    }

    public static String getSongListInfoUrl(String ids, String flagType, int position) {
        StringBuilder builder = new StringBuilder(
                "http://mobilebasedata.kuwo.cn/basedata.s?type=get_songlist_info2&");
        builder.append("user=866479021253520&prod=kwplayer_ar_8.3.0.0&corp=kuwo&vipver=8.3.0.0&source=kwplayer_ar_8.3.0.0_kwdebug.apk&p2p=1&")
                .append("id=")//不要加&，createCommonParams有
                .append(ids);

        if (!TextUtils.isEmpty(flagType)) {
            builder.append("&flagtype=").append(flagType);
        }
        builder.append("&pos=" + position);
        try {
            String city = "北京市";
            builder.append("&province=").append(URLEncoder.encode("北京市", "utf-8"));
            builder.append("&city=").append(URLEncoder.encode(city, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private static String createURL_SubList(byte[] paramsBytes) {
        return HOST_SUB_LIST + createB64Params(paramsBytes);
    }

    private static String createB64Params(byte[] paramsBytes) {
        byte[] bytes = KuwoDES.encrypt(paramsBytes, paramsBytes.length, KuwoDES.SECRET_KEY, KuwoDES.SECRET_KEY_LENGTH);
        return new String(Base64Coder.encode(bytes, bytes.length));
    }
}

package com.chenhao.musicplayer.utils;

import android.text.TextUtils;
import android.util.Xml;

import com.chenhao.musicplayer.bean.AdArInfo;
import com.chenhao.musicplayer.bean.AdInfo;
import com.chenhao.musicplayer.bean.BannerSection;
import com.chenhao.musicplayer.bean.BibiInfo;
import com.chenhao.musicplayer.bean.KlistSection;
import com.chenhao.musicplayer.bean.KomnibusInfo;
import com.chenhao.musicplayer.bean.KproductionInfo;
import com.chenhao.musicplayer.bean.KsquareSection;
import com.chenhao.musicplayer.bean.ListSection;
import com.chenhao.musicplayer.bean.MvInfo;
import com.chenhao.musicplayer.bean.MvSquareSection;
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.bean.PictorialInfo;
import com.chenhao.musicplayer.bean.QzListInfo;
import com.chenhao.musicplayer.bean.RadioInfo;
import com.chenhao.musicplayer.bean.RootInfo;
import com.chenhao.musicplayer.bean.Section;
import com.chenhao.musicplayer.bean.Show2Info;
import com.chenhao.musicplayer.bean.SongListInfo;
import com.chenhao.musicplayer.bean.SongListRcm2Info;
import com.chenhao.musicplayer.bean.SquareSection;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chenhao on 2016/11/16.
 */

public class XmlParse {

    public static final String ROOT = "root";
    public static final String SECTION = "section";
    public static final String TYPE = "type";
    public static final String LABEL = "label";
    public static final String AD = "ad";
    public static final String SONGLIST = "songlist";
    public static final String AD_AR = "ad_ar";
    public static final String RADIO = "radio";
    public static final String MV = "mv";
    public static final String KPRODUCTION = "kproduction";
    public static final String KOMNIBUS = "komnibus";
    public static final String QZ_LIST = "qz_list";
    public static final String BIBI = "bibi";

    public static final String KLIST = "klist";
    public static final String LIST = "list";
    public static final String BANNER = "banner";
    public static final String SQUARE = "square";
    public static final String MVSQUARE = "mvsquare";
    public static final String KSQUARE = "ksquare";

    private static Section mSection;
    private static RootInfo mRootInfo;
    private static OnlineInfo mOnlineInfo;

    public static RootInfo parseXml(String datas) {

        XmlPullParser parser = Xml.newPullParser();
        try {
            InputStream is = new ByteArrayInputStream(datas.getBytes());
            parser.setInput(is, "utf-8");
            int type = parser.getEventType();
            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if (ROOT.equals(parser.getName())) {
                            mRootInfo = new RootInfo();
                        } else if (SECTION.equals(parser.getName())) {
                            getSection(parser);
                        } else if (AD.equals(parser.getName())) {
                            AdInfo adInfo = new AdInfo();
                            parseOnlineInfo(parser, adInfo);
                            String url = getFormatAttributeValue(parser, "url");
                            adInfo.setUrl(url);
                            String small_img = getFormatAttributeValue(parser, "small_img");
                            adInfo.setUrl(small_img);
                            String version = getFormatAttributeValue(parser, "version");
                            adInfo.setVersion(version);
                            String provider = getFormatAttributeValue(parser, "provider");
                            adInfo.setProvider(provider);
                            boolean inapp = getBoolean(parser, "inapp");
                            adInfo.setInapp(inapp);
                            mOnlineInfo = adInfo;
                        } else if (SONGLIST.equals(parser.getName())) {
                            SongListInfo songListInfo = new SongListInfo();
                            parseOnlineInfo(parser, songListInfo);
                            int lossless_mark = getDefaultInteger(parser, "lossless_mark", 0);
                            songListInfo.setLossless_mark(lossless_mark);
                            mOnlineInfo = songListInfo;
                        } else if (AD_AR.equals(parser.getName())) {
                            AdArInfo adArInfo = new AdArInfo();
                            parseOnlineInfo(parser, adArInfo);
                            String url = getFormatAttributeValue(parser, "url");
                            adArInfo.setUrl(url);
                            int listencnt = getDefaultInteger(parser, "listencnt", 0);
                            adArInfo.setListencnt(listencnt);
                            boolean inapp = getBoolean(parser, "inapp");
                            adArInfo.setInapp(inapp);
                            mOnlineInfo = adArInfo;
                        } else if (RADIO.equals(parser.getName())) {
                            RadioInfo radioInfo = new RadioInfo();
                            parseOnlineInfo(parser, radioInfo);
                            long radio_id = getDefaultLong(parser, "radio_id", -1);
                            radioInfo.setRadio_id(radio_id);
                            int listencnt = getDefaultInteger(parser, "listencnt", 0);
                            radioInfo.setListencnt(listencnt);
                            mOnlineInfo = radioInfo;
                        } else if (MV.equals(parser.getName())) {
                            MvInfo mvInfo = new MvInfo();
                            parseOnlineInfo(parser, mvInfo);
                            long rid = getDefaultLong(parser, "rid", -1);
                            mvInfo.setRid(rid);
                            String artist = getFormatAttributeValue(parser, "artist");
                            mvInfo.setArtist(artist);
                            int album = getDefaultInteger(parser, "album", 0);
                            mvInfo.setAlbum(album);
                            int duration = getDefaultInteger(parser, "duration", -1);
                            mvInfo.setDuration(duration);
                            String format = getFormatAttributeValue(parser, "format");
                            mvInfo.setFormat(format);
                            String res = getFormatAttributeValue(parser, "res");
                            mvInfo.setRes(res);
                            String mvquality = getFormatAttributeValue(parser, "mvquality");
                            mvInfo.setMvquality(mvquality);
                            String disname = getFormatAttributeValue(parser, "disname");
                            mvInfo.setDisname(disname);
                            mOnlineInfo = mvInfo;
                        } else if (KPRODUCTION.equals(parser.getName())) {
                            KproductionInfo kproductionInfo = new KproductionInfo();
                            parseOnlineInfo(parser, kproductionInfo);
                            int workType = getDefaultInteger(parser, "workType", 0);
                            kproductionInfo.setWorkType(workType);
                            mOnlineInfo = kproductionInfo;
                        } else if (KOMNIBUS.equals(parser.getName())) {
                            KomnibusInfo komnibusInfo = new KomnibusInfo();
                            parseOnlineInfo(parser, komnibusInfo);
                            mOnlineInfo = komnibusInfo;
                        } else if (QZ_LIST.equals(parser.getName())) {
                            QzListInfo qzListInfo = new QzListInfo();
                            parseOnlineInfo(parser, qzListInfo);
                            long statistics_id = getDefaultLong(parser, "statistics_id", -1);
                            qzListInfo.setStatistics_id(statistics_id);
                            mOnlineInfo = qzListInfo;
                        } else if (BIBI.equals(parser.getName())) {
                            BibiInfo bibiInfo = new BibiInfo();
                            parseOnlineInfo(parser, bibiInfo);
                            String purl = getFormatAttributeValue(parser, "purl");
                            bibiInfo.setPurl(purl);
                            mOnlineInfo = bibiInfo;
                        } else if("show2".equals(parser.getName())){
                            Show2Info show2Info = new Show2Info();
                            parseOnlineInfo(parser, show2Info);
                            mOnlineInfo = show2Info;
                        } else if("songlist_rcm2".equals(parser.getName())){
                            SongListRcm2Info songlistRcm2Info = new SongListRcm2Info();
                            songlistRcm2Info.setName("发现好歌");
                            mOnlineInfo = songlistRcm2Info;
                        } else if("pictorial".equals(parser.getName())){
                            PictorialInfo pictorialInfo = new PictorialInfo();
                            parseOnlineInfo(parser, pictorialInfo);
                            mOnlineInfo = pictorialInfo;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (SECTION.equals(parser.getName())) {
                            if(mSection != null){
                                mRootInfo.add(mSection);
                            }
                        } else if (ROOT.equals(parser.getName())) {

                        }else {
                            if(mSection != null){
                                mSection.add(mOnlineInfo);
                            }
                        }
                        break;
                    default:
                        break;
                }
                type = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mRootInfo;
    }

    private static void getSection(XmlPullParser parser) {
        String type = getFormatAttributeValue(parser, TYPE);
        String label = getFormatAttributeValue(parser, LABEL);
        String mtype = getFormatAttributeValue(parser, "mtype");
        if (BANNER.equals(type)) {
            mSection = new BannerSection();
        } else if (SQUARE.equals(type)) {
            mSection = new SquareSection();
        } else if (MVSQUARE.equals(type)) {
            mSection = new MvSquareSection();
        } else if (KSQUARE.equals(type)) {
            mSection = new KsquareSection();
        } else if (KLIST.equals(type)) {
            mSection = new KlistSection();
        } else if (LIST.equals(type)) {
            mSection = new ListSection();
        } else if ("business".equals(type)) {
            mSection = null;
            return;
        }
        mSection.setLabel(label);
        mSection.setMtype(mtype);
    }

    private static void parseOnlineInfo(XmlPullParser parser, OnlineInfo onlineInfo) {
        int digest = getDefaultInteger(parser, "digest", -1);
        onlineInfo.setDigest(digest);
        int id = getDefaultInteger(parser, "id", -1);
        onlineInfo.setId(id);
        String name = getFormatAttributeValue(parser, "name");
        onlineInfo.setName(name);
        String desc = getFormatAttributeValue(parser, "desc");
        onlineInfo.setDesc(desc);
        String publish = getFormatAttributeValue(parser, "publish");
        onlineInfo.setPublish(publish);
        String img = getFormatAttributeValue(parser, "img");
        onlineInfo.setImg(img);
        String extend = getFormatAttributeValue(parser, "extend");
        onlineInfo.setExtend(extend);
    }

    private static int getDefaultInteger(XmlPullParser parser, String name, int value) {
        try {
            value = Integer.valueOf(getFormatAttributeValue(parser, name));
        } catch (Exception e) {
        }
        return value;
    }

    private static long getDefaultLong(XmlPullParser parser, String name, long value) {
        try {
            value = Long.valueOf(getFormatAttributeValue(parser, name));
        } catch (Exception e) {
        }
        return value;
    }

    private static boolean getBoolean(XmlPullParser parser, String name) {
        boolean bon = false;
        String str = parser.getAttributeValue(null, name);
        if ("true".equals(str)) {
            bon = true;
        }
        return bon;
    }

    private static String getFormatAttributeValue(XmlPullParser parser, String name) {
        String str = parser.getAttributeValue(null, name);
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        return str;
    }
}

package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;

import com.chenhao.musicplayer.bean.TabInfo;

import java.util.ArrayList;

/**
 * Created by chenhao on 2016/12/2.
 */

public class KubillboardMultiAdapter extends RecyclerAdapterFactory<ArrayList<TabInfo>> {

    public KubillboardMultiAdapter(Context context, ArrayList<TabInfo> tabInfos, Handler handler) {
        super(context, tabInfos, handler);
    }

    @Override
    protected void buildAdapters(ArrayList<TabInfo> tabInfos) {
        for (TabInfo info:tabInfos) {
            addAdapter(new TabAdapter(getContext(),info,1,getHandler()));
        }
    }
}

package com.chenhao.musicplayer.online;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.adapter.MultiAdapter;
import com.chenhao.musicplayer.bean.RootInfo;
import com.chenhao.musicplayer.fragment.BaseFragment;
import com.chenhao.musicplayer.utils.XmlParse;

/**
 * Created by chenhao on 2016/11/15.
 */

public class SortFragment extends BaseFragment<RootInfo> {
    private RecyclerView mRecyclerView;
    public static SortFragment newInstance(){
        return new SortFragment();
    }

    @Override
    protected String getRequestUrl() {
        return "http://nmobi.kuwo.cn/mobi.s?f=kuwo&q=2S5ec7LNX+pQWCHXaOs5nvnnWqyaughtHbUI3IEkHudHoPfxpFuJZY6OI9KVO/SJxQskru/aVoLIKjvXNwwUHjxFEOPVi1swkQHEb1KoQHYAYmUV/VSoO1HLcjTE+WA+W1D1fLBcpmuQ3pzdb8dh7RFQdfp5JZAU+vpLzsg1CWspJCU3OBc2beefATWgtiuRqPbmK0iphOWFIFBXIXk+wah+ySyvrWhNqmBVJgSRnuPsli8g40y8IzaFbuazOwwY8qAHJO8GnFQZNAgndIKyid6aBJASHLoXgB5dBs5yqzhpkkZsPYvKBGnBFYo33zYYKGrmRSHoCoLkrJ0twztVy0TEnGUXvPY+SjRKgtnzKsHviR2F6uCy5TckmQUZT7hCSlhutIPFo5c=";
    }

    @Override
    protected RootInfo onBackgroundParser(String datas) throws Exception {
        RootInfo rootInfo = XmlParse.parseClassifyXml(datas);
        return rootInfo;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, RootInfo infos) {
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        Log.e("chenhaolog","SortFragment ------- infos : "+ infos);
        initView(view);
        setAdatper(infos);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    public void setAdatper(RootInfo infos) {
        MultiAdapter adapter = new MultiAdapter(getContext(), infos, new Handler());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }
}

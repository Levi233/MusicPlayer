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

public class SingerFragment extends BaseFragment<RootInfo> {
    private RecyclerView mRecyclerView;
    public static SingerFragment newInstance(){
        return new SingerFragment();
    }

    @Override
    protected String getRequestUrl() {
        return "http://nmsublist.kuwo.cn/mobi.s?f=kuwo&q=2S5ec7LNX+pQWCHXaOs5nvnnWqyaughtHbUI3IEkHudHoPfxpFuJZY6OI9KVO/SJxQskru/aVoLIKjvXNwwUHjxFEOPVi1swkQHEb1KoQHYAYmUV/VSoO1HLcjTE+WA+W1D1fLBcpmuQ3pzdb8dh7RFQdfp5JZAUKFYkIwf6k9bOn9AmweAsoXf8yCKF9v6VQN4iHq6T5o5MnqBCOxYj9mWA2cLeA9MkdIQrEeb7Bq8ncUbAt+D07iIGI/+72M0Cwb9fFhOT8tlcYZX+Bz4jkn20Sg/Tl9Cg60f8OA3MHkSiQ45IBfHkkVwp1sIyQeXY7/vk/Vi/eAiC1emsxSQCs9jJZkHHp6P54ifT81/xYEfMbtvSNsd5rYqF7Fh27z9cj3VZBKlps484Qczy3vUIpcfWAUjxUYJw";
    }

    @Override
    protected RootInfo onBackgroundParser(String datas) throws Exception {
        RootInfo rootInfo = XmlParse.parseXml(datas);
        return rootInfo;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, RootInfo infos) {
        View view = inflater.inflate(R.layout.fragment_singer, container, false);
        initView(view);
        setAdapter(infos);
        Log.e("chenhaolog","SingerFragment---infos : "+ infos.toString());
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    public void setAdapter(RootInfo infos) {
        MultiAdapter adapter = new MultiAdapter(getContext(), infos, new Handler());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }
}

package com.chenhao.musicplayer.online;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.chenhao.musicplayer.utils.OnlineUrlUtil;
import com.chenhao.musicplayer.utils.XmlParse;

/**
 * Created by chenhao on 2016/11/27.
 */

public class SongListFragment extends BaseFragment<RootInfo> {

    private RecyclerView mRecyclerView;
    private long mId;
    private int mDigest;

    public static SongListFragment newInstance(long id,int digest) {
        SongListFragment f = new SongListFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        bundle.putInt("digest",digest);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mId = bundle.getLong("id");
            mDigest = bundle.getInt("digest");
        }
    }

    @Override
    protected String getRequestUrl() {
        String url =  OnlineUrlUtil.getRequest("sub_list",mId,0,30,String.valueOf(mDigest));
        Log.e("chenhaolog","songlist url:" + url);
        return url;
    }

    @Override
    protected RootInfo onBackgroundParser(String datas) throws Exception {
        Log.e("chenhaolog","songlist data : "+datas);
        RootInfo rootInfo = XmlParse.parseXml(datas);
        return rootInfo;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, RootInfo infos) {
        View view = inflater.inflate(R.layout.fragment_song_list, container, false);
        initView(view);
        setAdapter(infos);
        return view;
    }

    private void setAdapter(RootInfo infos) {
        MultiAdapter adapter = new MultiAdapter(getContext(), infos, new Handler());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }
}

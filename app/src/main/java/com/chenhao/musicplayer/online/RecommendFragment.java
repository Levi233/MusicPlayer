package com.chenhao.musicplayer.online;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
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
 * Created by chenhao on 2016/11/15.
 */

public class RecommendFragment extends BaseFragment<RootInfo> implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;

    public static RecommendFragment newInstance(){
        return new RecommendFragment();
    }

    @Override
    protected String getRequestUrl() {
        return OnlineUrlUtil.getRecommendUrl();
    }

    @Override
    protected RootInfo onBackgroundParser(String datas) throws Exception {
        Log.e("chenhaolog","resource : "+datas);
        RootInfo rootInfo = XmlParse.parseXml(datas);
        return rootInfo;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, RootInfo infos) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        Log.e("chenhaolog","infos : "+infos.toString());
        initView(view);
        setAdapterAndListener(infos);
        return view;
    }

    private void setAdapterAndListener(RootInfo infos) {
        MultiAdapter adapter = new MultiAdapter(getContext(), infos, new Handler());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onRefresh() {

    }
}

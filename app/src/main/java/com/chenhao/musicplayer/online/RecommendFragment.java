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
import com.chenhao.musicplayer.utils.XmlParse;

/**
 * Created by chenhao on 2016/11/15.
 */

public class RecommendFragment extends BaseFragment<RootInfo> implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;

    private String url = "http://nmobi.kuwo.cn/mobi.s?f=kuwo&q=2S5ec7LNX+pQWCHXaOs5nvnnWqyaughtHbUI3IEkHudHoPfxpFuJZY6OI9KVO/SJxQskru/aVoLIKjvXNwwUHjxFEOPVi1swkQHEb1KoQHYAYmUV/VSoO1HLcjTE+WA+W1D1fLBcpmuQ3pzdb8dh7RFQdfp5JZAUH/+jpWYq+jsxMiRDNVX8ufVa18zxSJFA8gZaVc/cq4SfU8+CbuTwXLhrFqI62sahOSpyH+57sg0eThhiFdco+1ebIdQOLFJ9WxC98fCtz/W2Rv9/BBkqhDo6CrYD5H1j2aHDplxND6/upuKIhFBKlzk4RFzONrTjG5u0CcvvkPhOHL+3bG4f9zk4RFzONrTjG5u0CcvvkPjfocDUxrRLcs5A+MNXow5iGwMo+VE+lbFFcflWKsKYFCosPy1nrcuRoqDappqcGE0QaL5+mMEluxjkKJeeQZ8Rk65c0A/A2Zav4ntNwywtnZv6VmjR/nKUVc8A3qqFevKsffRgan1mjE21o4AOAcII/o9vwYRbdKgi4Zhoht7jhssmGsVDg8ZLcPa9gmHvUkjsl7e5SuvcDVZ2VW0fG/gHP/M+dP90G8ImNIRKfKMRo3Sfno5HHcD9ZDxcmnFfv78xMMacAYTMstjJZkHHp6P54ifT81/xYEfMbtvSNsd5rYqF7Fh27z9cj3VZBKlps484Qczy3vUIpcfWAUjxUYJw";
    public static RecommendFragment newInstance(){
        return new RecommendFragment();
    }

    @Override
    protected String getRequestUrl() {
        return url;
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

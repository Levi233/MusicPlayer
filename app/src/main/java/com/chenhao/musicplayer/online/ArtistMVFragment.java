package com.chenhao.musicplayer.online;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.adapter.MultiAdapter;
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.bean.RootInfo;
import com.chenhao.musicplayer.bean.Section;
import com.chenhao.musicplayer.fragment.BaseFragment;
import com.chenhao.musicplayer.utils.MyUtils;
import com.chenhao.musicplayer.utils.OnlineUrlUtil;
import com.chenhao.musicplayer.utils.XmlParse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenhao on 2016/12/4.
 */

public class ArtistMVFragment extends BaseFragment<RootInfo> {

    private long mId;
    private String mType;
    private int mStart = 0;
    private boolean mLoadMore = true;
    private RecyclerView mRecyclerView;
    private MultiAdapter mAdapter;
    private Handler mHandler = new Handler();

    public static ArtistMVFragment newInstance(long id,String type){
        ArtistMVFragment f = new ArtistMVFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        bundle.putString("type",type);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            mId = bundle.getLong("id");
            mType = bundle.getString("type");
        }
    }

    @Override
    protected String getRequestUrl() {
        return OnlineUrlUtil.getRequest(mType,"mv",mId,0,30,"");
    }

    @Override
    protected RootInfo onBackgroundParser(String datas) throws Exception {
        if("TP=none".equals(datas)){
            throw new EmptyStateViewException();
        }
        RootInfo rootInfo = XmlParse.parseXml(datas);
        return rootInfo;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, RootInfo infos) {
        View view = inflater.inflate(R.layout.fragment_song_list, container, false);
        initView(view);
        initControl(infos);
        return view;
    }

    private void initControl(final RootInfo infos) {
        mAdapter = new MultiAdapter(getContext(), infos, mHandler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                        >= recyclerView.computeVerticalScrollRange() && mLoadMore) {
                    Section section = infos.getSections().get(0);
                    if (mStart+30 < section.getTotal()) {
                        loadMore(mStart+30,infos);
                    }
                    mLoadMore = false;
                }
            }
        });
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private void loadMore(int start, final RootInfo infos) {
        String url = OnlineUrlUtil.getRequest(mType,"mv", mId, start, 30, "");
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mLoadMore = true;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final byte[] bytes = response.body().bytes();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String datas = MyUtils.decode(bytes);
                        RootInfo rootInfo = XmlParse.parseXml(datas);
                        List<Section> sections = rootInfo.getSections();
                        mStart = sections.get(0).getStart();
                        ArrayList<OnlineInfo> onlines = sections.get(0).getOnlineInfos();
                        ArrayList<OnlineInfo> onlineInfos = infos.getSections().get(0).getOnlineInfos();
                        onlineInfos.addAll(onlines);
                        mAdapter.setData(rootInfo, onlineInfos);
                        mAdapter.notifyDataSetChanged();
                        mLoadMore = true;
                    }
                });
            }
        });
    }
}

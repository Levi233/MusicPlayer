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
import com.chenhao.musicplayer.bean.Section;
import com.chenhao.musicplayer.fragment.BaseFragment;
import com.chenhao.musicplayer.utils.MyUtils;
import com.chenhao.musicplayer.utils.OnlineUrlUtil;
import com.chenhao.musicplayer.utils.XmlParse;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenhao on 2016/11/15.
 */

public class SingerFragment extends BaseFragment<RootInfo> {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MultiAdapter mAdapter;
    private boolean mLoadMore = true;
    private RootInfo mRootInfo;
    private int mStart = 0;
    private Handler mHandler = new Handler();
    public static SingerFragment newInstance() {
        return new SingerFragment();
    }

    @Override
    protected String getRequestUrl() {
        return OnlineUrlUtil.getSingerUrl();
    }

    @Override
    protected RootInfo onBackgroundParser(String datas) throws Exception {
        mRootInfo = XmlParse.parseXml(datas);
        return mRootInfo;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, RootInfo infos) {
        View view = inflater.inflate(R.layout.fragment_singer, container, false);
        initView(view);
        setAdapter(infos);
        Log.e("chenhaolog", "SingerFragment---infos : " + infos.toString());
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    public void setAdapter(final RootInfo infos) {
        mAdapter = new MultiAdapter(getContext(), infos, new Handler());
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int itemCount = mAdapter.getItemCount();
                if(recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                        >= recyclerView.computeVerticalScrollRange() && mLoadMore){
                    Section section =infos.getSections().get(0);
                    if(mStart+30 < section.getTotal()){
                        loadMore(mStart+30);
                    }
                    mLoadMore = false;
                }
            }
        });
    }

    private void loadMore(int start) {
        String url = OnlineUrlUtil.getRequest("sub_list", 0, start, 30, "3");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
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
                        mAdapter.setData(rootInfo,null);
                        mAdapter.notifyDataSetChanged();
                        List<Section> sections = rootInfo.getSections();
                        Section section = sections.get(0);
                        mStart = section.getStart();
                        mLoadMore = true;
                    }
                });
            }
        });
    }
}

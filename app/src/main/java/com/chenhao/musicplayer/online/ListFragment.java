package com.chenhao.musicplayer.online;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by chenhao on 2016/12/6.
 */

public class ListFragment extends BaseFragment<RootInfo> {
    private long mId;
    private int mDigest;
    private String mType;
    private String mName;
    private int mStart;
    private MultiAdapter mAdapter;
    private boolean mLoadMore = true;
    private RecyclerView mRecyclerView;
    private Handler mHandler = new Handler();
    public static ListFragment newInstance(long id,int digest,String name,String type){
        ListFragment f = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        bundle.putInt("digest",digest);
        bundle.putString("type",type);
        bundle.putString("name",name);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVisible();
        Bundle bundle = getArguments();
        if(bundle != null){
            mId = bundle.getLong("id");
            mDigest = bundle.getInt("digest");
            mType = bundle.getString("type");
            mName = bundle.getString("name");
        }
    }

    @Override
    protected View onCreateTitleView(LayoutInflater inflater, ViewGroup container) {
        View view =  inflater.inflate(R.layout.fragment_base_head, container, false);
        ImageView btn_back = (ImageView) view.findViewById(R.id.btn_back);
        TextView title_name = (TextView) view.findViewById(R.id.title_name);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        title_name.setText(mName);
        return view;
    }

    @Override
    protected String getRequestUrl() {
        return OnlineUrlUtil.getRequest(mType,null,mId,0,30,String.valueOf(mDigest));
    }

    @Override
    protected RootInfo onBackgroundParser(String datas) throws Exception {
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
        mAdapter = new MultiAdapter(getContext(), infos,mHandler);
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
                        loadMore(mStart+30,null);
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
        String url = OnlineUrlUtil.getRequest(mType,null, mId, start, 30, String.valueOf(mDigest));
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
                        mAdapter.setData(rootInfo, null);
                        mAdapter.notifyDataSetChanged();
                        mLoadMore = true;
                    }
                });
            }
        });
    }
}

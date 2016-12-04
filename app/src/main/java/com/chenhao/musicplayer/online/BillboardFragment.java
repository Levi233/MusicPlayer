package com.chenhao.musicplayer.online;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.activity.MainActivity;
import com.chenhao.musicplayer.adapter.MultiAdapter;
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.bean.RootInfo;
import com.chenhao.musicplayer.bean.Section;
import com.chenhao.musicplayer.bean.TabInfo;
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
 * Created by chenhao on 2016/12/3.
 */

public class BillboardFragment extends BaseFragment<RootInfo> {
    private OnlineInfo mInfo;
    private long mId;
    private int mDigest;
    private int mStart = 0;
    private Handler mHandler = new Handler();
    private MultiAdapter mAdapter;
    private boolean mLoadMore = true;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private RelativeLayout head_layout;
    private TextView mDesc;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RecyclerView mRecyclerView;

    public static BillboardFragment newInstance(OnlineInfo info){
        BillboardFragment f = new BillboardFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info",info);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVisible();
        Bundle bundle = getArguments();
        if(bundle != null){
            mInfo = (OnlineInfo) bundle.getSerializable("info");
        }
    }

    @Override
    protected String getRequestUrl() {
        if(mInfo instanceof TabInfo){
            mId = mInfo.getId();
            mDigest = mInfo.getDigest();
        }else{
            mId = mInfo.getTabInfos().get(0).getId();
            mDigest = mInfo.getTabInfos().get(0).getDigest();
        }
        return OnlineUrlUtil.getRequest("sub_list",null,mId,0,30,String.valueOf(mDigest));
    }

    @Override
    protected RootInfo onBackgroundParser(String datas) throws Exception {
        RootInfo rootInfo = XmlParse.parseXml(datas);
        return rootInfo;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, RootInfo infos) {
        View view = inflater.inflate(R.layout.fragment_kubillboard, container, false);
        init(view);
        initControl(infos);
        return view;
    }

    private void initControl(final RootInfo infos) {
        Glide.with(getContext()).load(mInfo.getImg()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                head_layout.setBackgroundDrawable(new BitmapDrawable(resource));
                mCollapsingToolbarLayout.setContentScrim(new BitmapDrawable(resource));
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle(mInfo.getName());
                } else {
                    mCollapsingToolbarLayout.setTitle(" ");
                }
            }
        });
        if(mInfo instanceof TabInfo){
            mDesc.setText("");
        }else{
            mDesc.setText(mInfo.getPublish());
        }
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
                if(recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                        >= recyclerView.computeVerticalScrollRange() && mLoadMore){
                    Section section =infos.getSections().get(0);
                    if(mStart+30 < section.getTotal()){
                        loadMore(mStart+30,infos);
                    }
                    mLoadMore = false;
                }
            }
        });
    }

    private void init(View view) {
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        head_layout = (RelativeLayout) view.findViewById(R.id.login_layout);
        mDesc = (TextView) view.findViewById(R.id.desc);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        MainActivity.getInstance().setSupportActionBar(mToolbar);
        MainActivity.getInstance().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadMore(int start , final RootInfo infos) {
        String url = OnlineUrlUtil.getRequest("sub_list",null,mId , start, 30, String.valueOf(mDigest));
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
                        List<Section> sections = rootInfo.getSections();
                        mStart = sections.get(0).getStart();
                        ArrayList<OnlineInfo> onlines = sections.get(0).getOnlineInfos();
                        ArrayList<OnlineInfo> onlineInfos = infos.getSections().get(0).getOnlineInfos();
                        onlineInfos.addAll(onlines);
                        mAdapter.setData(rootInfo,onlineInfos);
                        mAdapter.notifyDataSetChanged();
                        mLoadMore = true;
                    }
                });
            }
        });
    }
}

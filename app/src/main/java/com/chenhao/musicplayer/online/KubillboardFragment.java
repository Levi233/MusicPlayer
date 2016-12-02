package com.chenhao.musicplayer.online;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import com.chenhao.musicplayer.adapter.KubillboardMultiAdapter;
import com.chenhao.musicplayer.bean.KubillboardInfo;
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.bean.TabInfo;

import java.util.ArrayList;

/**
 * Created by chenhao on 2016/12/2.
 */

public class KubillboardFragment extends Fragment {
    private OnlineInfo mInfo;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private RelativeLayout head_layout;
    private TabLayout mTabLayout;
    private TextView mDesc;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RecyclerView mRecyclerView;

    public static KubillboardFragment newInstance(OnlineInfo info){
        KubillboardFragment f = new KubillboardFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info",info);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            mInfo = (OnlineInfo) bundle.getSerializable("info");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kubillboard, container, false);
        initView(view);
        initControl();
        return view;
    }

    private void initControl() {
        Glide.with(getContext()).load(((KubillboardInfo)mInfo).getKuimg()).asBitmap().into(new SimpleTarget<Bitmap>() {
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
        mDesc.setText(mInfo.getDesc());
        ArrayList<TabInfo> tabInfos = ((KubillboardInfo) mInfo).getTabInfos();
        KubillboardMultiAdapter adapter = new KubillboardMultiAdapter(getContext(), tabInfos, new Handler());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    private void initView(View view) {
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        head_layout = (RelativeLayout) view.findViewById(R.id.login_layout);
        mTabLayout = (TabLayout) view.findViewById(R.id.toolbar_tab);
        mDesc = (TextView) view.findViewById(R.id.desc);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        MainActivity.getInstance().setSupportActionBar(mToolbar);
        MainActivity.getInstance().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

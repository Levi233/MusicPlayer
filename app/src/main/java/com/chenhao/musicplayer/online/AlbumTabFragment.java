package com.chenhao.musicplayer.online;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.activity.MainActivity;
import com.chenhao.musicplayer.bean.AlbumInfo;
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.fragment.BaseFragment;
import com.chenhao.musicplayer.utils.OnlineUrlUtil;
import com.chenhao.musicplayer.utils.ParserJson;

/**
 * Created by chenhao on 2016/12/4.
 */

public class AlbumTabFragment extends BaseFragment<AlbumInfo> {
    private AppBarLayout mAppBarLayout;
    private ImageView mHeadImg;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TextView mNickName;
    private TextView mPlayNum;
    private TextView mDesc;
    private RelativeLayout mHeadLayout;
    private TabLayout mTabLayout;
    private OnlineInfo mInfo;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private String[] mTitles = new String[]{"单曲","评论"};

    public static AlbumTabFragment newInstance(OnlineInfo info){
        AlbumTabFragment f = new AlbumTabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info",info);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disEnableSecondDecode();
        setVisible();
        Bundle bundle = getArguments();
        if(bundle != null){
            mInfo = (OnlineInfo) bundle.getSerializable("info");
        }
    }

    @Override
    protected String getRequestUrl() {
        return OnlineUrlUtil.getAlbumInfoUrl(String.valueOf(mInfo.getId()));
    }

    @Override
    protected AlbumInfo onBackgroundParser(String datas) throws Exception {
        AlbumInfo albumInfo = ParserJson.parserAlbumInfo(datas);
        return albumInfo;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, AlbumInfo info) {
        View view = inflater.inflate(R.layout.fragment_song_list_tab, container, false);
        mTitles[0] = "单曲 "+info.getMcnum();
        mTitles[1] = "评论 "+info.getCom_num();
        initView(view);
        initControl(info);
        return view;
    }

    private void initControl(AlbumInfo info) {
        Glide.with(getContext()).load(info.getBig_pic()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mHeadLayout.setBackgroundDrawable(new BitmapDrawable(resource));
                mCollapsingToolbarLayout.setContentScrim(new BitmapDrawable(resource));
            }
        });

        mHeadImg.setVisibility(View.GONE);
        mNickName.setVisibility(View.GONE);
        mPlayNum.setVisibility(View.GONE);
        if(!TextUtils.isEmpty(info.getPublish())){
            mDesc.setVisibility(View.VISIBLE);
            mDesc.setText("发行时间:"+info.getPublish());
        }

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initView(View view) {
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);
        mHeadImg = (ImageView) view.findViewById(R.id.head_img);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mViewPager=(ViewPager)view.findViewById(R.id.view_pager);
        mNickName = (TextView) view.findViewById(R.id.nick_name);
        mPlayNum = (TextView) view.findViewById(R.id.play_num);
        mDesc = (TextView) view.findViewById(R.id.desc);
        mHeadLayout = (RelativeLayout) view.findViewById(R.id.login_layout);
        mTabLayout = (TabLayout) view.findViewById(R.id.toolbar_tab);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);

        MainActivity.getInstance().setSupportActionBar(mToolbar);
        MainActivity.getInstance().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -mHeadLayout.getHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle(mInfo.getName());
                } else {
                    mCollapsingToolbarLayout.setTitle(" ");
                }
            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            switch (position){
                case 0:
                    f = SongListFragment.newInstance(mInfo.getId(),"album",-1,"music_list");
                    break;
                case 1:
                    f = CommentListFragment.newInstance(mInfo.getId(),13);
                    break;
            }
            return f;
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            if (mTitles != null){
                return mTitles[position];
            }
            return super.getPageTitle(position);
        }
    }

}

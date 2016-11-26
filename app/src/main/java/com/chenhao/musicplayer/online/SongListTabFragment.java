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
import android.util.Log;
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
import com.chenhao.musicplayer.bean.SingleDataInfo;
import com.chenhao.musicplayer.fragment.BaseFragment;
import com.chenhao.musicplayer.utils.MyUtils;
import com.chenhao.musicplayer.utils.OnlineUrlUtil;
import com.chenhao.musicplayer.utils.ParserJson;
import com.chenhao.musicplayer.view.GlideRoundTransform;

/**
 * Created by chenhao on 2016/11/24.
 */

public class SongListTabFragment extends BaseFragment<SingleDataInfo> {
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private RelativeLayout head_layout;
    private TabLayout mTabLayout;
    private ImageView mHeadImg;
    private TextView mNickName;
    private TextView mPlayNum;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private String[] mTitles = new String[]{"单曲","评论"};

    private long mId;
    private String mTitle;
    private String mImg;

    public static SongListTabFragment newInstance(long id,String name,String img){
        SongListTabFragment f = new SongListTabFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        bundle.putString("title",name);
        bundle.putString("img",img);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVisible();
        disEnableSecondDecode();
        Bundle bundle = getArguments();
        if (bundle != null) {
            mId = bundle.getLong("id");
            mTitle = bundle.getString("title");
            mImg = bundle.getString("img");
        }
    }

    @Override
    protected String getRequestUrl() {
        return OnlineUrlUtil.getSongListInfoUrl(String.valueOf(mId), "", -1);
    }

    @Override
    protected SingleDataInfo onBackgroundParser(String datas) throws Exception {
        Log.e("chenhaolog","datas : "+datas);
        SingleDataInfo singleDataInfo = ParserJson.parserSingleDataJson(datas);
        return singleDataInfo;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, SingleDataInfo infos) {
        View view = inflater.inflate(R.layout.fragment_song_list_tab, container, false);
        mTitles[0] = "单曲 "+infos.getTotal();
        mTitles[1] = "评论 "+infos.getCom_num();
        initView(view);
        setAdapterAndListener(infos);
        return view;
    }

    private void setAdapterAndListener(SingleDataInfo infos) {
        Glide.with(getContext()).load(mImg).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                head_layout.setBackgroundDrawable(new BitmapDrawable(resource));
                mCollapsingToolbarLayout.setContentScrim(new BitmapDrawable(resource));
            }
        });

        Glide.with(getContext())
                .load(infos.getUpic())
                .transform(new GlideRoundTransform(getContext()))
                .placeholder(R.mipmap.ic_launcher)
                .into(mHeadImg);

        mNickName.setText(infos.getUname());
        mPlayNum.setText(MyUtils.numFormat(infos.getPlay_num()));
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
        MainActivity.getInstance().setSupportActionBar(mToolbar);
        MainActivity.getInstance().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewPager.setOffscreenPageLimit(2);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        head_layout = (RelativeLayout) view.findViewById(R.id.login_layout);
        mTabLayout = (TabLayout) view.findViewById(R.id.toolbar_tab);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle(mTitle);
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
                    f = RecommendFragment.newInstance();
                    break;
                case 1:
                    f = RankFragment.newInstance();
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

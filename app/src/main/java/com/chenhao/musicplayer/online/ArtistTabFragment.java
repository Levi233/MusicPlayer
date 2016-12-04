package com.chenhao.musicplayer.online;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
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
import com.chenhao.musicplayer.bean.ArtistInfo;
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.utils.MyUtils;
import com.chenhao.musicplayer.utils.OnlineUrlUtil;
import com.chenhao.musicplayer.utils.ParserJson;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by chenhao on 2016/12/4.
 */

public class ArtistTabFragment extends Fragment{
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
    private Handler mHandler = new Handler();
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private String[] mTitles = new String[]{"单曲","专辑","MV","详情"};

    public static ArtistTabFragment newInstance(OnlineInfo info){
        ArtistTabFragment f = new ArtistTabFragment();
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
        View view = inflater.inflate(R.layout.fragment_song_list_tab, container, false);
        if(((ArtistInfo) mInfo).getMusiccnt() > 0){
            mTitles[0] = "单曲 "+((ArtistInfo) mInfo).getMusiccnt();
            mTitles[1] = "专辑 "+((ArtistInfo) mInfo).getAlbumcnt();
            mTitles[2] = "MV "+((ArtistInfo) mInfo).getMvcnt();
        }
        initView(view);
        loadHeadInfo();
        initControl();
        return view;
    }

    private void initControl() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mHeadLayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
        mCollapsingToolbarLayout.setContentScrim(new BitmapDrawable(bitmap));
        mHeadImg.setVisibility(View.GONE);
        mNickName.setVisibility(View.GONE);
        mPlayNum.setVisibility(View.GONE);
        mDesc.setVisibility(View.VISIBLE);
        mDesc.setText("粉丝:"+MyUtils.numFormat(((ArtistInfo)mInfo).getFollowers()));

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
        mViewPager.setOffscreenPageLimit(4);

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
                    f = SongListFragment.newInstance(mInfo.getId(),"artist",mInfo.getDigest(),"music_list");
                    break;
                case 1:
                    f = AlbumListFragment.newInstance(mInfo.getId(),mInfo.getDigest(),"album_list");
                    break;
                case 2:
                    f = ArtistMVFragment.newInstance(mInfo.getId(),"music_list");
                    break;
                case 3:
                    f = ArtistDescFragment.newInstance(mInfo.getId());
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

    private void loadHeadInfo(){

        MyUtils.loadPic(OnlineUrlUtil.getArtistInfoUrl(String.valueOf(mInfo.getId())),new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                try {
                    final String[] strings = ParserJson.parserDescJson(string);
                    final String img = strings[1];
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDesc.setText("粉丝:"+MyUtils.numFormat(Integer.parseInt(strings[2])));
                            Glide.with(getContext()).load(img).asBitmap().into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    mHeadLayout.setBackgroundDrawable(new BitmapDrawable(resource));
                                    mCollapsingToolbarLayout.setContentScrim(new BitmapDrawable(resource));
                                }
                            });
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

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
import com.chenhao.musicplayer.bean.SingleDataInfo;
import com.chenhao.musicplayer.utils.MyUtils;
import com.chenhao.musicplayer.utils.OnlineUrlUtil;
import com.chenhao.musicplayer.utils.ParserJson;
import com.chenhao.musicplayer.view.GlideRoundTransform;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by chenhao on 2016/11/24.
 */

public class SongListTabFragment extends Fragment{
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private RelativeLayout head_layout;
    private TabLayout mTabLayout;
    private ImageView mHeadImg;
    private TextView mNickName;
    private TextView mPlayNum;
    private Handler mHandler = new Handler();
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private String[] mTitles = new String[]{"单曲","评论"};

    private long mId;
    private String mTitle;
    private String mImg;
    private int mDigest;

    public static SongListTabFragment newInstance(long id,String name,String img,int digest){
        SongListTabFragment f = new SongListTabFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        bundle.putString("title",name);
        bundle.putString("img",img);
        bundle.putInt("digest",digest);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mId = bundle.getLong("id");
            mTitle = bundle.getString("title");
            mImg = bundle.getString("img");
            mDigest = bundle.getInt("digest");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_list_tab, container, false);
        initView(view);
        loadHeadInfo();
        setAdapterAndListener();
        return view;
    }

    private void setAdapterAndListener() {
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
                    f = SongListFragment.newInstance(mId,null,mDigest,"sub_list");
                    break;
                case 1:
                    f = CommentListFragment.newInstance(mId,mDigest);
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

        MyUtils.loadPic(OnlineUrlUtil.getSongListInfoUrl(String.valueOf(mId), "", -1),new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String datas = response.body().string();
                try {
                    final SingleDataInfo info = ParserJson.parserSingleDataJson(datas);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(isFragmentAlive()){
                                mHeadImg.setVisibility(View.VISIBLE);
                                mNickName.setVisibility(View.VISIBLE);
                                mPlayNum.setVisibility(View.VISIBLE);
                                String big_pic = info.getBig_pic();
                                Glide.with(getContext()).load(big_pic).asBitmap().into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        head_layout.setBackgroundDrawable(new BitmapDrawable(resource));
                                        mCollapsingToolbarLayout.setContentScrim(new BitmapDrawable(resource));
                                    }
                                });
                                Glide.with(getContext())
                                        .load(info.getUpic())
                                        .transform(new GlideRoundTransform(getContext()))
                                        .placeholder(R.mipmap.img_user_default)
                                        .into(mHeadImg);

                                mNickName.setText(info.getUname());
                                mPlayNum.setText(MyUtils.numFormat(info.getPlay_num()));
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected final boolean isFragmentAlive() {
        return (getActivity() != null && !getActivity().isFinishing() && !isDetached());
    }
}

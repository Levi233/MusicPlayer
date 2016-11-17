package com.chenhao.musicplayer.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.activity.MainActivity;
import com.chenhao.musicplayer.mod.FragmentControl;

/**
 * Created by chenhao on 2016/11/11.
 */

public class LocalMusicMainFragment extends Fragment implements View.OnClickListener{

    private ImageView mBack;
    private ViewPager mViewPager;
    private TabLayout mTableLayout;
    private ImageView mMore;

    private String[] mTitles = new String[]{"单曲","歌手","专辑"};

    public static LocalMusicMainFragment newInstance(){
        return new LocalMusicMainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music_base, container, false);
        initView(view);
        setAdapterAndListener();
        return view;
    }

    private void setAdapterAndListener() {
        ViewPagerFrameAdapter adapter = new ViewPagerFrameAdapter(getFragmentManager());
        mViewPager.setAdapter(adapter);
        mTableLayout.setupWithViewPager(mViewPager);
        mBack.setOnClickListener(this);
        mMore.setOnClickListener(this);
    }

    private void initView(View view) {
        mBack = (ImageView) view.findViewById(R.id.btn_back);
        mMore = (ImageView) view.findViewById(R.id.more_img);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mTableLayout = (TabLayout) view.findViewById(R.id.tab_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                MainActivity.getInstance().getSupportFragmentManager().popBackStack();
                break;
            case R.id.more_img:
                FragmentControl.getInstance().showSubFrag(ScanLocalMusicFragment.newInstance(),ScanLocalMusicFragment.class.getName());
                break;
        }
    }

    private class ViewPagerFrameAdapter extends FragmentStatePagerAdapter{

        public ViewPagerFrameAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
           switch (position){
               case 0:
                   fragment = LocalMusicSingleFragment.newInstance();
                   break;
               case 1:
                   fragment = LocalMusicSingerFragment.newInstance();
                   break;
               case 2:
                   fragment = LocalMusicAlbumFragment.newInstance();
                   break;
           }
            return fragment;
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

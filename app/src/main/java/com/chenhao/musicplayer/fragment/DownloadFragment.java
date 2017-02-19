package com.chenhao.musicplayer.fragment;

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
import android.widget.TextView;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.local.AlreadyDownloadFragment;
import com.chenhao.musicplayer.local.InDownloadFragment;

/**
 * Created by chenhao on 2016/12/10.
 */

public class DownloadFragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout mTableLayout;
    private TextView mTitle;
    private ImageView mBack;
    private String[] mTitles = new String[]{"已下载","下载中"};

    public static DownloadFragment newInstance(){
        DownloadFragment f = new DownloadFragment();
        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        initView(view);
        initControl();
        return view;
    }

    private void initControl() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        mTitle.setText("下载管理");
        ViewPagerFrameAdapter adapter = new ViewPagerFrameAdapter(getFragmentManager());
        mViewPager.setAdapter(adapter);
        mTableLayout.setupWithViewPager(mViewPager);
    }

    private void initView(View view) {
        mBack = (ImageView) view.findViewById(R.id.btn_back);
        mTitle = (TextView) view.findViewById(R.id.title_name);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mTableLayout = (TabLayout) view.findViewById(R.id.tab_layout);
    }

    private class ViewPagerFrameAdapter extends FragmentStatePagerAdapter {

        public ViewPagerFrameAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = AlreadyDownloadFragment.newInstance();
                    break;
                case 1:
                    fragment = InDownloadFragment.newInstance();
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

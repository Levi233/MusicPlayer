package com.chenhao.musicplayer.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.fragment.DownloadFragment;
import com.chenhao.musicplayer.mod.FragmentControl;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by chenhao on 2016/11/11.
 */

public class MineMainFragment extends Fragment implements View.OnClickListener{

    private LinearLayout mLocalMusic;
    private LinearLayout mDownloadManager;
    private Badge mBadge;
    public static MineMainFragment newInstance(){
        return new MineMainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_pager, container, false);
        initView(view);
        setAdapterAndListener();
        return view;

    }

    private void setAdapterAndListener() {
        mLocalMusic.setOnClickListener(this);
        mDownloadManager.setOnClickListener(this);
    }

    private void initView(View view) {
        mLocalMusic = (LinearLayout) view.findViewById(R.id.local_music_linearLayout);
        mDownloadManager = (LinearLayout) view.findViewById(R.id.local_music_download);
//        mBadge = new BadgeView(getContext(), mDownloadManager);
//        mBadge.setText("1");
//        mBadge.show();
        mBadge = new QBadgeView(getContext()).bindTarget(mDownloadManager).setBadgeNumber(100)
                .setBadgeGravity(Gravity.END |Gravity.CENTER).setBadgeNumberSize(13,true);
        mBadge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.local_music_linearLayout:
                FragmentControl.getInstance().showWithPlayBarSubFrag(LocalMusicMainFragment.newInstance(),LocalMusicMainFragment.class.getName());
                break;
            case R.id.local_music_download:
                FragmentControl.getInstance().showWithPlayBarSubFrag(DownloadFragment.newInstance(),DownloadFragment.class.getSimpleName());
                mBadge.hide(true);
                break;
        }
    }
}

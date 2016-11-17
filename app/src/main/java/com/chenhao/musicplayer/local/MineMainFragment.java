package com.chenhao.musicplayer.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.mod.FragmentControl;

/**
 * Created by chenhao on 2016/11/11.
 */

public class MineMainFragment extends Fragment implements View.OnClickListener{

    private LinearLayout mLocalMusic;
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
    }

    private void initView(View view) {
        mLocalMusic = (LinearLayout) view.findViewById(R.id.local_music_linearLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.local_music_linearLayout:
                FragmentControl.getInstance().showWithPlayBarSubFrag(LocalMusicMainFragment.newInstance(),LocalMusicMainFragment.class.getName());
                break;
        }
    }
}

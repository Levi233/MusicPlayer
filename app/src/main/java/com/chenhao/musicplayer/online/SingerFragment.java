package com.chenhao.musicplayer.online;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.fragment.BaseFragment;

/**
 * Created by chenhao on 2016/11/15.
 */

public class SingerFragment extends BaseFragment {
    public static SingerFragment newInstance(){
        return new SingerFragment();
    }

    @Override
    protected String getRequestUrl() {
        return "";
    }

    @Override
    protected Object onBackgroundParser(String datas) throws Exception {
        return "";
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Object o) {
        View view = inflater.inflate(R.layout.fragment_singer, container, false);
        return view;
    }
}

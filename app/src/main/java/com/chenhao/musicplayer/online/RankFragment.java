package com.chenhao.musicplayer.online;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.fragment.BaseFragment;

/**
 * Created by chenhao on 2016/11/15.
 */

public class RankFragment extends BaseFragment {

    public static RankFragment newInstance(){
        return new RankFragment();
    }

    @Override
    protected String getRequestUrl() {
        return "http://www.bilibili.com";
    }

    @Override
    protected Object onBackgroundParser(String datas) throws Exception {
        return datas;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disEnableSecondDecode();
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Object o) {
        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        return view;
    }
}

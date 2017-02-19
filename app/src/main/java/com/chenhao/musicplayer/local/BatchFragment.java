package com.chenhao.musicplayer.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.OnlineInfo;

import java.util.ArrayList;

/**
 * Created by chenhao on 2016/12/18.
 */

public class BatchFragment extends Fragment {

    private ArrayList<OnlineInfo> mInfos;

    public static BatchFragment newInstance(ArrayList<OnlineInfo> infos){
        BatchFragment f = new BatchFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info",infos);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            mInfos = (ArrayList<OnlineInfo>) bundle.getSerializable("info");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_batch, container, false);
        return view;
    }
}

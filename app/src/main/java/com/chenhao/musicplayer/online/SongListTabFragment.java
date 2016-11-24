package com.chenhao.musicplayer.online;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.musicplayer.R;

/**
 * Created by chenhao on 2016/11/24.
 */

public class SongListTabFragment extends Fragment{
    private RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_list_tab, container, false);
        initView(view);
        setAdapterAndListener();
        return view;

    }

    private void setAdapterAndListener() {

    }

    private void initView(View view) {
       mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private void createAdapter(RecyclerView recyclerView) {

    }
}

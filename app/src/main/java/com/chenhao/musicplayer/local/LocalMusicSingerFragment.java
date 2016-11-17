package com.chenhao.musicplayer.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.musicplayer.R;

/**
 * Created by chenhao on 2016/11/11.
 */

public class LocalMusicSingerFragment extends Fragment {

    public static LocalMusicSingerFragment newInstance(){
        return new LocalMusicSingerFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online_music, container, false);
        return view;
    }
}

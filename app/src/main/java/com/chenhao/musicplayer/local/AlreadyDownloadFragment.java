package com.chenhao.musicplayer.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.musicplayer.R;

/**
 * Created by chenhao on 2016/12/18.
 */

public class AlreadyDownloadFragment extends Fragment{

    public static AlreadyDownloadFragment newInstance(){
        AlreadyDownloadFragment f = new AlreadyDownloadFragment();
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music, container, false);
        return view;
    }
}

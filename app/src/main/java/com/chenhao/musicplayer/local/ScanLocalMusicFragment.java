package com.chenhao.musicplayer.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.activity.MainActivity;

/**
 * Created by chenhao on 2016/11/11.
 */

public class ScanLocalMusicFragment extends Fragment implements View.OnClickListener{

    private ImageView mBack;
    private TextView mScan;

    public static ScanLocalMusicFragment newInstance(){
        return new ScanLocalMusicFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan_music, container, false);
        initView(view);
        setListener();
        return view;
    }

    private void setListener() {
        mBack.setOnClickListener(this);
    }

    private void initView(View view) {
        mBack = (ImageView) view.findViewById(R.id.btn_back);
        mScan = (TextView) view.findViewById(R.id.scan_text_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                MainActivity.getInstance().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}

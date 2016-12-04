package com.chenhao.musicplayer.online;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.fragment.BaseFragment;
import com.chenhao.musicplayer.utils.OnlineUrlUtil;
import com.chenhao.musicplayer.utils.ParserJson;

/**
 * Created by chenhao on 2016/12/4.
 */

public class ArtistDescFragment extends BaseFragment<String> {
    private long mId;
    public static ArtistDescFragment newInstance(long id){
        ArtistDescFragment f = new ArtistDescFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disEnableSecondDecode();
        Bundle bundle = getArguments();
        if(bundle != null){
            mId = bundle.getLong("id");
        }
    }

    @Override
    protected String getRequestUrl() {
        return OnlineUrlUtil.getArtistInfoUrl(String.valueOf(mId));
    }

    @Override
    protected String onBackgroundParser(String datas) throws Exception {
        String[] strings = ParserJson.parserDescJson(datas);
        String desc = strings[0];
        if(TextUtils.isEmpty(desc)){
            throw new EmptyStateViewException();
        }
        return desc;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, String desc) {
        View view = inflater.inflate(R.layout.fragment_artist_desc, container, false);
        TextView descTextView = (TextView) view.findViewById(R.id.desc_tv);
        descTextView.setText(desc);
        return view;
    }
}

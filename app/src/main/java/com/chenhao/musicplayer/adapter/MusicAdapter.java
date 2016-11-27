package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.MusicInfo;

/**
 * Created by chenhao on 2016/11/27.
 */

public class MusicAdapter extends SingleRecyclerAdapter<MusicInfo> {

    public MusicAdapter(Context context, MusicInfo musicInfo, int type, Handler handler) {
        super(context, musicInfo, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MusicHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_song_list_adapter_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MusicHolder)holder).music_name_tv.setText(getItem().getName());
        if(TextUtils.isEmpty(getItem().getAlbum())){
            ((MusicHolder)holder).music_artist_tv.setText(getItem().getArtist());
        }else {
            ((MusicHolder)holder).music_artist_tv.setText(getItem().getArtist()+" - "+getItem().getAlbum());
        }
    }

    private static class MusicHolder extends RecyclerView.ViewHolder{
        TextView music_name_tv;
        TextView music_artist_tv;
        public MusicHolder(View itemView) {
            super(itemView);
            music_artist_tv = (TextView) itemView.findViewById(R.id.music_artist_tv);
            music_name_tv = (TextView) itemView.findViewById(R.id.music_name_tv);
        }
    }
}

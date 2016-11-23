package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenhao.musicplayer.R;

/**
 * Created by chenhao on 2016/11/23.
 */

public class ArtistHeadAdapter extends SingleRecyclerAdapter {

    public ArtistHeadAdapter(Context context, Object o, int type, Handler handler) {
        super(context, o, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArtistHeadHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_artist_head_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    private static class ArtistHeadHolder extends RecyclerView.ViewHolder{
        TextView chinese_tv;
        TextView occident_tv;
        TextView japan_korea_tv;

        public ArtistHeadHolder(View itemView) {
            super(itemView);
            chinese_tv = (TextView) itemView.findViewById(R.id.chinese_tv);
            occident_tv = (TextView) itemView.findViewById(R.id.occident_tv);
            japan_korea_tv = (TextView) itemView.findViewById(R.id.japan_korea_tv);
        }
    }
}

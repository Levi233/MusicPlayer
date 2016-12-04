package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.MvInfo;
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.utils.JumpUtils;

/**
 * Created by chenhao on 2016/12/4.
 */

public class MvAdapter extends SingleRecyclerAdapter<OnlineInfo> {

    public MvAdapter(Context context, OnlineInfo onlineInfo, int type, Handler handler) {
        super(context, onlineInfo, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MvHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_mv_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MvInfo info = (MvInfo) getItem();
        Glide.with(getContext())
                .load(info.getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((MvHolder)holder).mv_img);
        ((MvHolder)holder).mv_name.setText(info.getName());
        ((MvHolder)holder).mv_artist.setText(info.getArtist());
        ((MvHolder)holder).rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.jumpFragment(getItem());
            }
        });
    }

    private static class MvHolder extends RecyclerView.ViewHolder{


        ImageView mv_img;
        TextView mv_name;
        TextView mv_artist;
        RelativeLayout rl_layout;

        public MvHolder(View itemView) {
            super(itemView);
            mv_img = (ImageView) itemView.findViewById(R.id.mv_img);
            mv_name = (TextView) itemView.findViewById(R.id.mv_name);
            mv_artist = (TextView) itemView.findViewById(R.id.mv_artist);
            rl_layout = (RelativeLayout) itemView.findViewById(R.id.rl_layout);
        }

    }
}

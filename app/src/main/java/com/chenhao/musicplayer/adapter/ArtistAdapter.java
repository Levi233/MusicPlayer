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
import com.chenhao.musicplayer.bean.ArtistInfo;
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.utils.JumpUtils;
import com.chenhao.musicplayer.utils.MyUtils;
import com.chenhao.musicplayer.view.GlideRoundTransform;

/**
 * Created by chenhao on 2016/11/22.
 */

public class ArtistAdapter extends SingleRecyclerAdapter<OnlineInfo> {

    public ArtistAdapter(Context context, OnlineInfo onlineInfo, int type, Handler handler) {
        super(context, onlineInfo, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArtistHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_main_artist_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ArtistInfo info = (ArtistInfo) getItem();
        Glide.with(getContext())
                .load(info.getImg())
                .transform(new GlideRoundTransform(getContext()))
                .placeholder(R.drawable.img_user_default)
                .into(((ArtistHolder)holder).artistImg);
        ((ArtistHolder)holder).artistName.setText(getItem().getName());
        ((ArtistHolder)holder).artistFollowers.setText("粉丝："+ MyUtils.numFormat(info.getFollowers()));
        ((ArtistHolder)holder).rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.jumpFragment(getItem());
            }
        });
    }

    private static class ArtistHolder extends RecyclerView.ViewHolder{
        ImageView artistImg;
        TextView artistName;
        TextView artistFollowers;
        RelativeLayout rl_layout;
        public ArtistHolder(View itemView) {
            super(itemView);
            rl_layout = (RelativeLayout) itemView.findViewById(R.id.rl_layout);
            artistImg = (ImageView) itemView.findViewById(R.id.artist_img);
            artistName = (TextView) itemView.findViewById(R.id.artist_name);
            artistFollowers = (TextView) itemView.findViewById(R.id.artist_followers);
        }
    }
}

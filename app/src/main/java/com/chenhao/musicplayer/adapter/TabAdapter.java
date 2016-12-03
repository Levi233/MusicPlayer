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
import com.chenhao.musicplayer.bean.TabInfo;
import com.chenhao.musicplayer.utils.JumpUtils;

/**
 * Created by chenhao on 2016/12/2.
 */

public class TabAdapter extends SingleRecyclerAdapter<TabInfo> {

    public TabAdapter(Context context, TabInfo info, int type, Handler handler) {
        super(context, info, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TabHolder(LayoutInflater.from(getContext()).inflate(R.layout.main_billboard_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(getContext())
                .load(getItem().getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((TabHolder)holder).billboardImg);
        ((TabHolder)holder).name.setText(getItem().getName());
        ((TabHolder)holder).desc.setVisibility(View.GONE);
        ((TabHolder)holder).name1.setText("1."+getItem().getName1()+" - "+getItem().getArtist1());
        ((TabHolder)holder).name2.setText("2."+getItem().getName2()+" - "+getItem().getArtist2());
        ((TabHolder)holder).name3.setText("3."+getItem().getName3()+" - "+getItem().getArtist3());
        ((TabHolder)holder).rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.jumpFragment(getItem());
            }
        });
    }
    private static class TabHolder extends RecyclerView.ViewHolder{
        ImageView billboardImg;
        TextView name;
        TextView name1;
        TextView name2;
        TextView name3;
        TextView desc;
        RelativeLayout rl_layout;
        public TabHolder(View itemView) {
            super(itemView);
            rl_layout = (RelativeLayout) itemView.findViewById(R.id.rl_layout);
            billboardImg = (ImageView) itemView.findViewById(R.id.billboard_img);
            name = (TextView) itemView.findViewById(R.id.name);
            name1 = (TextView) itemView.findViewById(R.id.name1);
            name2 = (TextView) itemView.findViewById(R.id.name2);
            name3 = (TextView) itemView.findViewById(R.id.name3);
            desc = (TextView) itemView.findViewById(R.id.desc);
        }
    }
}

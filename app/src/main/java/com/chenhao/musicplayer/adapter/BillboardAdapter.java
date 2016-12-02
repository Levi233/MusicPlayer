package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.chenhao.musicplayer.bean.BillboardInfo;
import com.chenhao.musicplayer.bean.HitbillboardInfo;
import com.chenhao.musicplayer.bean.KubillboardInfo;
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.utils.JumpUtils;

/**
 * Created by chenhao on 2016/11/21.
 */

public class BillboardAdapter extends SingleRecyclerAdapter<OnlineInfo> {

    public BillboardAdapter(Context context, OnlineInfo onlineInfos, int type, Handler handler) {
        super(context, onlineInfos, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BillboardHolder(LayoutInflater.from(getContext()).inflate(R.layout.main_billboard_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OnlineInfo info = getItem();
        Glide.with(getContext())
                .load(info.getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((BillboardHolder)holder).billboardImg);
        ((BillboardHolder)holder).name.setText(getItem().getName());
        if(info instanceof KubillboardInfo){
            ((BillboardHolder)holder).desc.setVisibility(View.GONE);
            ((BillboardHolder)holder).name1.setText(((KubillboardInfo)info).getName1());
            ((BillboardHolder)holder).name2.setText(((KubillboardInfo)info).getName2());
            ((BillboardHolder)holder).name3.setText(((KubillboardInfo)info).getName3());
            ((BillboardHolder)holder).name3.setTextColor(Color.GRAY);
        }else if(info instanceof BillboardInfo){
            ((BillboardHolder)holder).desc.setVisibility(View.GONE);
            ((BillboardHolder)holder).name1.setText("1."+((BillboardInfo)info).getName1()+" - "+((BillboardInfo)info).getArtist1());
            ((BillboardHolder)holder).name2.setText("2."+((BillboardInfo)info).getName2()+" - "+((BillboardInfo)info).getArtist2());
            ((BillboardHolder)holder).name3.setText("3."+((BillboardInfo)info).getName3()+" - "+((BillboardInfo)info).getArtist3());
            ((BillboardHolder)holder).name3.setTextColor(Color.GRAY);
        }else if(info instanceof HitbillboardInfo){
            ((BillboardHolder)holder).desc.setVisibility(View.VISIBLE);
            ((BillboardHolder)holder).desc.setText(((HitbillboardInfo)info).getDesc());
            ((BillboardHolder)holder).name1.setText("");
            ((BillboardHolder)holder).name2.setText("");
            ((BillboardHolder)holder).name3.setText(((HitbillboardInfo)info).getName1());
            ((BillboardHolder)holder).name3.setTextColor(Color.BLUE);
        }
        ((BillboardHolder)holder).rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.jumpFragment(getItem());
            }
        });
    }

    private static class BillboardHolder extends RecyclerView.ViewHolder{
        ImageView billboardImg;
        TextView name;
        TextView name1;
        TextView name2;
        TextView name3;
        TextView desc;
        RelativeLayout rl_layout;
        public BillboardHolder(View itemView) {
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

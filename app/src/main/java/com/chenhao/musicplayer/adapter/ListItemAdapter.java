package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.utils.JumpUtils;

/**
 * Created by chenhao on 2016/11/17.
 */

public class ListItemAdapter extends SingleRecyclerAdapter<OnlineInfo> {
    public ListItemAdapter(Context context, OnlineInfo onlineInfo, int type, Handler handler) {
        super(context, onlineInfo, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        long start = System.currentTimeMillis();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.multi_list_item, parent, false);
        ListItemHolder holder = new ListItemHolder(view);
        long end = System.currentTimeMillis();
        Log.e("chenhaolog", mSimpleName + " [onCreateViewHolder] viewType ::: " + viewType+" cost :: "+(end - start));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        long start = System.currentTimeMillis();
        Glide.with(getContext())
                .load(getItem().getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((ListItemHolder)holder).klistImg);
        ((ListItemHolder)holder).klistName.setText(getItem().getName());
        ((ListItemHolder)holder).klistDesc.setText(getItem().getDesc());
        ((ListItemHolder)holder).rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.jumpFragment(getItem());
            }
        });
        long end = System.currentTimeMillis();
        Log.i("chenhaolog", mSimpleName + " [onBindViewHolder] cost  " + (end - start) + " :::position : " + position);
    }

    private static class ListItemHolder extends RecyclerView.ViewHolder{

        ImageView klistImg;
        TextView klistName;
        TextView klistDesc;
        RelativeLayout rl_layout;
        public ListItemHolder(View itemView) {
            super(itemView);
            rl_layout = (RelativeLayout) itemView.findViewById(R.id.rl_layout);
            klistImg = (ImageView) itemView.findViewById(R.id.klist_img);
            klistName = (TextView) itemView.findViewById(R.id.klist_name);
            klistDesc = (TextView) itemView.findViewById(R.id.klist_desc);
        }

    }
}

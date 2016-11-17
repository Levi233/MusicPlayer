package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.OnlineInfo;

/**
 * Created by chenhao on 2016/11/17.
 */

public class ListItemAdapter extends SingleRecyclerAdapter<OnlineInfo> {

    public ListItemAdapter(Context context, OnlineInfo onlineInfo, int type, Handler handler) {
        super(context, onlineInfo, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListItemHolder(LayoutInflater.from(getContext()).inflate(R.layout.multi_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(getContext())
                .load(getItem().getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((ListItemHolder)holder).klistImg);
        ((ListItemHolder)holder).klistName.setText(getItem().getName());
        ((ListItemHolder)holder).klistDesc.setText(getItem().getDesc());
    }

    private static class ListItemHolder extends RecyclerView.ViewHolder{

        ImageView klistImg;
        TextView klistName;
        TextView klistDesc;
        public ListItemHolder(View itemView) {
            super(itemView);
            klistImg = (ImageView) itemView.findViewById(R.id.klist_img);
            klistName = (TextView) itemView.findViewById(R.id.klist_name);
            klistDesc = (TextView) itemView.findViewById(R.id.klist_desc);
        }

    }
}

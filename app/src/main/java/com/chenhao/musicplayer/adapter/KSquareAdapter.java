package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.OnlineInfo;

import java.util.ArrayList;

/**
 * Created by chenhao on 2016/11/17.
 */

public class KSquareAdapter extends SingleRecyclerAdapter<ArrayList<OnlineInfo>> {

    public KSquareAdapter(Context context, ArrayList<OnlineInfo> onlineInfos, int type, Handler handler) {
        super(context, onlineInfos, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("chenhaolog", mSimpleName + " [onCreateViewHolder] viewType ::: " + viewType);
        return new KSquareHolder(LayoutInflater.from(getContext()).inflate(R.layout.square_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        long start = System.currentTimeMillis();
        Glide.with(getContext())
                .load(getItem().get(0).getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((KSquareHolder)holder).picImg1);
        Glide.with(getContext())
                .load(getItem().get(1).getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((KSquareHolder)holder).picImg2);
        Glide.with(getContext())
                .load(getItem().get(2).getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((KSquareHolder)holder).picImg3);
        ((KSquareHolder)holder).nameTextView1.setText(getItem().get(0).getName());
        ((KSquareHolder)holder).nameTextView2.setText(getItem().get(1).getName());
        ((KSquareHolder)holder).nameTextView3.setText(getItem().get(2).getName());
        long end = System.currentTimeMillis();
        Log.i("chenhaolog", mSimpleName + " [onBindViewHolder] cost  " + (end - start) + " :::position : " + position);

    }

    private static class KSquareHolder extends RecyclerView.ViewHolder{
        ImageView picImg1;
        ImageView picImg2;
        ImageView picImg3;
        TextView nameTextView1;
        TextView nameTextView2;
        TextView nameTextView3;
        public KSquareHolder(View itemView) {
            super(itemView);
            picImg1 = (ImageView) itemView.findViewById(R.id.pic_img_1);
            picImg2 = (ImageView) itemView.findViewById(R.id.pic_img_2);
            picImg3 = (ImageView) itemView.findViewById(R.id.pic_img_3);
            nameTextView1 = (TextView) itemView.findViewById(R.id.name_text_1);
            nameTextView2 = (TextView) itemView.findViewById(R.id.name_text_2);
            nameTextView3 = (TextView) itemView.findViewById(R.id.name_text_3);
        }
    }
}

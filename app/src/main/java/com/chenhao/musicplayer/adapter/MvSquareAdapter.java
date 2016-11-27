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
import com.chenhao.musicplayer.utils.JumpUtils;

import java.util.ArrayList;

/**
 * Created by chenhao on 2016/11/17.
 */

public class MvSquareAdapter extends SingleRecyclerAdapter<ArrayList<OnlineInfo>> implements View.OnClickListener{

    public MvSquareAdapter(Context context, ArrayList<OnlineInfo> onlineInfos, int type, Handler handler) {
        super(context, onlineInfos, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("chenhaolog", mSimpleName + " [onCreateViewHolder] viewType ::: " + viewType);
        return new MvSquareHolder(LayoutInflater.from(getContext()).inflate(R.layout.mv_square_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        long start = System.currentTimeMillis();
        Glide.with(getContext())
                .load(getItem().get(0).getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((MvSquareHolder) holder).mv_pic_1);
        Glide.with(getContext())
                .load(getItem().get(1).getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((MvSquareHolder) holder).mv_pic_2);
        ((MvSquareHolder) holder).mv_name_1.setText(getItem().get(0).getName());
        ((MvSquareHolder) holder).mv_name_2.setText(getItem().get(1).getName());
        ((MvSquareHolder) holder).mv_pic_1.setOnClickListener(this);
        ((MvSquareHolder) holder).mv_pic_2.setOnClickListener(this);

        long end = System.currentTimeMillis();
        Log.i("chenhaolog", mSimpleName + " [onBindViewHolder] cost  " + (end - start) + " :::position : " + position);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mv_pic_1:
                JumpUtils.jumpFragment(getItem().get(0));
                break;
            case R.id.mv_pic_2:
                JumpUtils.jumpFragment(getItem().get(1));
                break;
        }
    }

    private static class MvSquareHolder extends RecyclerView.ViewHolder {
        ImageView mv_pic_2;
        ImageView mv_pic_1;
        TextView mv_name_1;
        TextView mv_name_2;

        public MvSquareHolder(View itemView) {
            super(itemView);
            mv_pic_1 = (ImageView) itemView.findViewById(R.id.mv_pic_1);
            mv_pic_2 = (ImageView) itemView.findViewById(R.id.mv_pic_2);
            mv_name_1 = (TextView) itemView.findViewById(R.id.mv_name_1);
            mv_name_2 = (TextView) itemView.findViewById(R.id.mv_name_2);
        }
    }
}

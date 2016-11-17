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

import java.util.ArrayList;

/**
 * Created by chenhao on 2016/11/17.
 */

public class MvSquareAdapter extends SingleRecyclerAdapter<ArrayList<OnlineInfo>> {

    public MvSquareAdapter(Context context, ArrayList<OnlineInfo> onlineInfos, int type, Handler handler) {
        super(context, onlineInfos, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MvSquareHolder(LayoutInflater.from(getContext()).inflate(R.layout.mv_square_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(getContext())
                .load(getItem().get(0).getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((MvSquareHolder)holder).mv_pic_1);
        Glide.with(getContext())
                .load(getItem().get(1).getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((MvSquareHolder)holder).mv_pic_2);
        ((MvSquareHolder)holder).mv_name_1.setText(getItem().get(0).getName());
        ((MvSquareHolder)holder).mv_name_2.setText(getItem().get(1).getName());
    }

    private static class MvSquareHolder extends RecyclerView.ViewHolder{
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

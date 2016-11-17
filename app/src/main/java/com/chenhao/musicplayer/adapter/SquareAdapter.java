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
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.bean.RadioInfo;
import com.chenhao.musicplayer.utils.MyUtils;

import java.util.ArrayList;

/**
 * Created by chenhao on 2016/11/17.
 */

public class SquareAdapter extends SingleRecyclerAdapter<ArrayList<OnlineInfo>> {

    public SquareAdapter(Context context, ArrayList<OnlineInfo> onlineInfos, int type, Handler handler) {
        super(context, onlineInfos, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SquareHolder(LayoutInflater.from(getContext()).inflate(R.layout.square_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(getContext())
                .load(getItem().get(0).getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((SquareHolder)holder).picImg1);
        Glide.with(getContext())
                .load(getItem().get(1).getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((SquareHolder)holder).picImg2);
        Glide.with(getContext())
                .load(getItem().get(2).getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(((SquareHolder)holder).picImg3);
        ((SquareHolder)holder).nameTextView1.setText(getItem().get(0).getName());
        ((SquareHolder)holder).nameTextView2.setText(getItem().get(1).getName());
        ((SquareHolder)holder).nameTextView3.setText(getItem().get(2).getName());

        OnlineInfo onlineInfo = getItem().get(1);
        if(onlineInfo instanceof RadioInfo){
            ((SquareHolder)holder).listencnt_rl_1.setVisibility(View.VISIBLE);
            ((SquareHolder)holder).listencnt_rl_2.setVisibility(View.VISIBLE);
            ((SquareHolder)holder).listencnt_rl_3.setVisibility(View.VISIBLE);
            ((SquareHolder)holder).listencnt_1.setText(MyUtils.numFormat(((RadioInfo)getItem().get(0)).getListencnt()));
            ((SquareHolder)holder).listencnt_2.setText(MyUtils.numFormat(((RadioInfo)getItem().get(1)).getListencnt()));
            ((SquareHolder)holder).listencnt_3.setText(MyUtils.numFormat(((RadioInfo)getItem().get(2)).getListencnt()));
        }else{
            ((SquareHolder)holder).listencnt_rl_1.setVisibility(View.GONE);
            ((SquareHolder)holder).listencnt_rl_2.setVisibility(View.GONE);
            ((SquareHolder)holder).listencnt_rl_3.setVisibility(View.GONE);
        }
    }

    private static class SquareHolder extends RecyclerView.ViewHolder{

        ImageView picImg1;
        ImageView picImg2;
        ImageView picImg3;
        TextView nameTextView1;
        TextView nameTextView2;
        TextView nameTextView3;
        RelativeLayout listencnt_rl_1;
        RelativeLayout listencnt_rl_2;
        RelativeLayout listencnt_rl_3;
        TextView listencnt_1;
        TextView listencnt_2;
        TextView listencnt_3;

        public SquareHolder(View itemView) {
            super(itemView);
            picImg1 = (ImageView) itemView.findViewById(R.id.pic_img_1);
            picImg2 = (ImageView) itemView.findViewById(R.id.pic_img_2);
            picImg3 = (ImageView) itemView.findViewById(R.id.pic_img_3);
            nameTextView1 = (TextView) itemView.findViewById(R.id.name_text_1);
            nameTextView2 = (TextView) itemView.findViewById(R.id.name_text_2);
            nameTextView3 = (TextView) itemView.findViewById(R.id.name_text_3);
            listencnt_rl_1 = (RelativeLayout) itemView.findViewById(R.id.listencnt_rl_1);
            listencnt_rl_2 = (RelativeLayout) itemView.findViewById(R.id.listencnt_rl_2);
            listencnt_rl_3 = (RelativeLayout) itemView.findViewById(R.id.listencnt_rl_3);
            listencnt_1 = (TextView) itemView.findViewById(R.id.listencnt_1);
            listencnt_2 = (TextView) itemView.findViewById(R.id.listencnt_2);
            listencnt_3 = (TextView) itemView.findViewById(R.id.listencnt_3);
        }
    }
}

package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.ClassifySection;
import com.chenhao.musicplayer.utils.BItmapUtil;

/**
 * Created by chenhao on 2016/11/23.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ClassifySection mSection;
    private static int num = 0;
    public RecyclerViewAdapter(Context context, ClassifySection section){
        this.mContext = context;
        this.mSection = section;
    }

    public void setData(ClassifySection section){
        this.mSection = section;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("chenhaolog","------------>RecyclerViewAdapter  [onCreateViewHolder] viewType ::: " + ++num);
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.classify_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        long start = System.currentTimeMillis();
        String name ="";
        if(position == 0){
            Glide.with(mContext)
                    .load(mSection.getImg())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Bitmap alphaBitmap = BItmapUtil.getAlphaBitmap(resource, mContext.getResources().getColor(R.color.seekBarProgress));
                            ((ViewHolder)holder).section_img.setImageBitmap(alphaBitmap);
                        }
                    });
            ((ViewHolder)holder).class_linearLayout.setVisibility(View.VISIBLE);
            ((ViewHolder)holder).list_name.setVisibility(View.GONE);
            ((ViewHolder)holder).section_name.setText(mSection.getName());
            name = mSection.getName();
        }else{
            ((ViewHolder)holder).class_linearLayout.setVisibility(View.GONE);
            ((ViewHolder)holder).list_name.setVisibility(View.VISIBLE);
            ((ViewHolder)holder).list_name.setText(mSection.getOnlineInfos().get(position-1).getName());
            name =mSection.getOnlineInfos().get(position-1).getName();
        }
        long end = System.currentTimeMillis();
        Log.i("chenhaolog", "name"+name+"--------------->RecyclerViewAdapter  [onBindViewHolder] cost  " + (end - start) + " :::position : " + position);
    }

    @Override
    public int getItemCount() {
        return mSection.getOnlineInfos().size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder{
        TextView section_name;
        TextView list_name;
        LinearLayout class_linearLayout;
        ImageView section_img;

        public ViewHolder(View itemView) {
            super(itemView);
            section_name = (TextView) itemView.findViewById(R.id.section_name);
            list_name = (TextView) itemView.findViewById(R.id.list_name);
            class_linearLayout = (LinearLayout) itemView.findViewById(R.id.class_linearLayout);
            section_img = (ImageView) itemView.findViewById(R.id.section_img);
        }
    }
}

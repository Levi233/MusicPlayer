package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.BannerSection;

/**
 * Created by chenhao on 2016/11/16.
 */

public class BannerAdapter extends SingleRecyclerAdapter<BannerSection> {


    public BannerAdapter(Context context, BannerSection bannerSection, int type, Handler handler) {
        super(context, bannerSection, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BannerViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.banner_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(((BannerViewHolder)holder).adapter == null){
            ((BannerViewHolder)holder).adapter = new ViewPagerAdapter(getContext(), getItem().getOnlineInfos());
            ((BannerViewHolder)holder).viewPager.setAdapter(((BannerViewHolder)holder).adapter);
        }else {
            ((BannerViewHolder)holder).adapter.setInfos(getItem().getOnlineInfos());
            ((BannerViewHolder)holder).adapter.notifyDataSetChanged();
        }
    }

    private static class BannerViewHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        ViewPagerAdapter adapter;
        public BannerViewHolder(View view) {
            super(view);
            viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        }
    }
}

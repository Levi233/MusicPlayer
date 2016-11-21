package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.BannerSection;

import java.util.ArrayList;

/**
 * Created by chenhao on 2016/11/16.
 */

public class BannerAdapter extends SingleRecyclerAdapter<BannerSection> {

    private static ArrayList<View> dotsList = new ArrayList<>();

    public BannerAdapter(Context context, BannerSection bannerSection, int type, Handler handler) {
        super(context, bannerSection, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        long start = System.currentTimeMillis();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.banner_item, parent, false);
        BannerViewHolder holder = new BannerViewHolder(view,getHandler());
        long end = System.currentTimeMillis();
        Log.e("chenhaolog", mSimpleName + " [onCreateViewHolder] viewType ::: " + viewType+" cost :: "+(end - start));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        long start = System.currentTimeMillis();
        if(((BannerViewHolder)holder).adapter == null){
            ((BannerViewHolder)holder).adapter = new ViewPagerAdapter(getContext(), getItem().getOnlineInfos());
            ((BannerViewHolder)holder).viewPager.setAdapter(((BannerViewHolder)holder).adapter);
            initDot(((BannerViewHolder)holder).dotsLayout);
            ((BannerViewHolder)holder).viewPager.setCurrentItem(100000*getItem().getOnlineInfos().size());
        }else {
            ((BannerViewHolder)holder).adapter.setInfos(getItem().getOnlineInfos());
            ((BannerViewHolder)holder).adapter.notifyDataSetChanged();
        }
        ((BannerViewHolder)holder).autoRunningTask.start();
        long end = System.currentTimeMillis();
        Log.i("chenhaolog", mSimpleName + " [onBindViewHolder] cost  " + (end - start) + " :::position : " + position);
    }

    private static class BannerViewHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        ViewPagerAdapter adapter;
        AutoRunningTask autoRunningTask;
        LinearLayout dotsLayout;
        public BannerViewHolder(View view ,Handler handler) {
            super(view);
            viewPager = (ViewPager) view.findViewById(R.id.view_pager);
            dotsLayout = (LinearLayout) view.findViewById(R.id.dots_ll);
            autoRunningTask = new AutoRunningTask(viewPager, handler);
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0;i < dotsList.size();i++){
                        View view = dotsList.get(i);
                        if(i == position%dotsList.size()){
                            view.setBackgroundResource(R.mipmap.dot_focus);
                        }else{
                            view.setBackgroundResource(R.mipmap.dot_normal);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            viewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            autoRunningTask.stop();
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            autoRunningTask.start();
                            break;
                        case MotionEvent.ACTION_UP:
                            autoRunningTask.start();
                            break;
                    }
                    return false;
                }
            });
        }
    }

    private void initDot(LinearLayout dots){
        dots.removeAllViews();
        dotsList.clear();
        for(int i = 0;i < getItem().getOnlineInfos().size();i++){
            View view = new View(getContext());
            if(i == 0){
                view.setBackgroundResource(R.mipmap.dot_focus);
            }else{
                view.setBackgroundResource(R.mipmap.dot_normal);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(18, 18);
            layoutParams.setMargins(0, 0, 25, 0);
            dots.addView(view,layoutParams);
            dotsList.add(view);
        }
    }

    private static class AutoRunningTask implements Runnable{
        boolean flag;
        ViewPager viewPager;
        Handler handler;
        private AutoRunningTask(ViewPager viewPager,Handler handler){
            this.viewPager = viewPager;
            this.handler = handler;
        }
        @Override
        public void run() {
          if(flag){
              handler.removeCallbacks(this);
              int currentItem = viewPager.getCurrentItem();
              currentItem++;
              viewPager.setCurrentItem(currentItem);
              handler.postDelayed(this,3000);
          }
        }

        public void start(){
            if(!flag){
                flag = true;
                handler.postDelayed(this,3000);
            }
        }

        public void stop(){
            if(flag){
                handler.removeCallbacks(this);
                flag = false;
            }
        }
    }
}

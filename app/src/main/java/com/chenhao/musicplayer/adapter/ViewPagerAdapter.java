package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.OnlineInfo;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by chenhao on 2016/11/16.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<OnlineInfo> mInfos;
    private LinkedList<ImageView> mImgs = new LinkedList<ImageView>();
    public ViewPagerAdapter(Context context, ArrayList<OnlineInfo> infos){
        this.mContext = context;
        this.mInfos = infos;
    }

    public void setInfos(ArrayList<OnlineInfo> infos){
        this.mInfos = infos;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView;
        if(mImgs.size() > 0){
            imageView = mImgs.removeFirst();
        }else{
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        Glide.with(mContext)
                .load(mInfos.get(position%mInfos.size()).getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mImgs.add((ImageView) object);
        container.removeView((View)object);
    }
}

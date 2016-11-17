package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by chenhao on 2016/9/18.
 */
public abstract class SingleRecyclerAdapter<T>  {

    private Context mContext;
    private T mItem;
    private Handler mHandler;
    private int mItemType;

    public SingleRecyclerAdapter(Context context, T t, int type, Handler handler){
        this.mContext = context;
        this.mItem = t;
        this.mItemType = type;
        this.mHandler = handler;
    }

    public T getItem() {
        return mItem;
    }

    public Context getContext(){
        return mContext;
    }

    public Handler getHandler(){
        return mHandler;
    }


    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    public int getItemViewType() {
        return mItemType;
    }
}

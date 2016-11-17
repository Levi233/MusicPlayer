package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhao on 2016/9/18.
 */
public abstract class RecyclerAdapterFactory<T> extends RecyclerView.Adapter {

    private Handler mHandler;
    private Context mContext;
    private List<SingleRecyclerAdapter<?>> mAdapters;

    public RecyclerAdapterFactory(Context context, T t, Handler handler){
        this.mContext = context;
        this.mHandler = handler;
        this.mAdapters = new ArrayList<SingleRecyclerAdapter<?>>();
        buildAdapters(t);
    }

    protected abstract void buildAdapters(T t);

    protected  final Handler getHandler(){
        return mHandler;
    }

    protected final Context getContext(){
        return mContext;
    }

    protected final void addAdapter(SingleRecyclerAdapter<?> adapter){
        if (adapter != null){
            mAdapters.add(adapter);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        for (SingleRecyclerAdapter<?> adapter:mAdapters) {
            if(adapter.getItemViewType() == viewType){
                holder = adapter.onCreateViewHolder(parent, viewType);
            }
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mAdapters.get(position).onBindViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        Log.e("chenhaolog","size : "+mAdapters.size());
        return mAdapters.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mAdapters.get(position).getItemViewType();
    }

}

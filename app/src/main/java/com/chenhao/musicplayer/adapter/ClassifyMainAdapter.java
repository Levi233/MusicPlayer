package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.ClassifySection;

/**
 * Created by chenhao on 2016/11/23.
 */

public class ClassifyMainAdapter extends SingleRecyclerAdapter<ClassifySection> {

    public ClassifyMainAdapter(Context context, ClassifySection section, int type, Handler handler) {
        super(context, section, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("chenhaolog", mSimpleName + " [onCreateViewHolder] viewType ::: " + viewType);
        return new ClassifyHolder(LayoutInflater.from(getContext()).inflate(R.layout.classify_main_fragment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        long start = System.currentTimeMillis();
        if(((ClassifyHolder)holder).adapter == null){
            ((ClassifyHolder)holder).adapter = new RecyclerViewAdapter(getContext(), getItem());
            ((ClassifyHolder)holder).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

            RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool(){
                @Override
                public void putRecycledView(RecyclerView.ViewHolder scrap) {
                    super.putRecycledView(scrap);
                }

                @Override
                public RecyclerView.ViewHolder getRecycledView(int viewType) {
                    return super.getRecycledView(viewType);
                }
            };
            pool.setMaxRecycledViews(((ClassifyHolder)holder).adapter.getItemViewType(0),19);
            ((ClassifyHolder)holder).recyclerview.setRecycledViewPool(pool);
            ((ClassifyHolder)holder).recyclerview.setAdapter(((ClassifyHolder)holder).adapter);
        }else{
            ((ClassifyHolder)holder).adapter.setData(getItem());
            ((ClassifyHolder)holder).adapter.notifyDataSetChanged();
        }
        long end = System.currentTimeMillis();
        Log.e("chenhaolog", mSimpleName + getItem().getName()+" [onBindViewHolder] cost  " + (end - start) + " :::position : " + position);
    }

    private static class ClassifyHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerview;
        RecyclerViewAdapter adapter;
        public ClassifyHolder(View itemView) {
            super(itemView);
            recyclerview = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        }
    }
}

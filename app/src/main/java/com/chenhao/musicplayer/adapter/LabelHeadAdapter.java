package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenhao.musicplayer.R;

import java.util.ArrayList;

/**
 * Created by chenhao on 2016/11/17.
 */

public class LabelHeadAdapter extends SingleRecyclerAdapter<ArrayList<String>> {

    public LabelHeadAdapter(Context context, ArrayList<String> s, int type, Handler handler) {
        super(context, s, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LabelHolder(LayoutInflater.from(getContext()).inflate(R.layout.label_head_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItem().size() != 2){
            ((LabelHolder)holder).moreTextView.setVisibility(View.GONE);
        }else{
            ((LabelHolder)holder).moreTextView.setVisibility(View.VISIBLE);
            ((LabelHolder)holder).moreTextView.setText(getItem().get(1));
        }
        ((LabelHolder)holder).labelTextView.setText(getItem().get(0));
    }

    private static class LabelHolder extends RecyclerView.ViewHolder{
        TextView moreTextView;
        TextView labelTextView;
        public LabelHolder(View itemView) {
            super(itemView);
            labelTextView = (TextView) itemView.findViewById(R.id.label_text);
            moreTextView = (TextView) itemView.findViewById(R.id.more_text);
        }
    }
}

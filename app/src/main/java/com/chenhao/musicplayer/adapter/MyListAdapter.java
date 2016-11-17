package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.MusicInfo;

import java.util.List;

/**
 * Created by chenhao on 2016/11/3.
 */

public class MyListAdapter extends BaseAdapter{
    private Context mContext;
    private List<MusicInfo> mInfos;
    private int mSelected = -1;
    public MyListAdapter(Context context, List<MusicInfo> infos){
        this.mContext = context;
        this.mInfos = infos;
    }

    public void setInfos(List<MusicInfo> infos){
        this.mInfos = infos;
    }
    public void setSelected(int selected){
        this.mSelected = selected;
    }

    @Override
    public int getCount() {
        return mInfos.size();
    }

    @Override
    public MusicInfo getItem(int position) {
        return mInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View view = convertView;
        if(view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
            holder.textView = (TextView) view.findViewById(R.id.text);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        if(mSelected == position){
            holder.textView.setTextColor(Color.RED);
        }else{
            holder.textView.setTextColor(Color.BLACK);
        }
        holder.textView.setText(getItem(position).getName());
        return view;
    }

    private static class ViewHolder{
        TextView textView;
    }
}

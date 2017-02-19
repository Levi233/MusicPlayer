package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.MusicInfo;
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.db.MusicDao;
import com.chenhao.musicplayer.local.BatchFragment;
import com.chenhao.musicplayer.mod.FragmentControl;
import com.chenhao.musicplayer.mod.MediaPlayerManager;
import com.chenhao.musicplayer.utils.MyUtils;
import com.chenhao.musicplayer.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhao on 2016/11/27.
 */

public class MusicAdapter extends SingleRecyclerAdapter<ArrayList<OnlineInfo>> implements View.OnClickListener{

    public MusicAdapter(Context context, ArrayList<OnlineInfo> onlineInfos, int type, Handler handler) {
        super(context, onlineInfos, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MusicHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_song_list_adapter_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.e("chenhaolog","onBindViewHolder-----position : "+position);
        MusicInfo infos = (MusicInfo) getItem().get(position);

        if(!(getItem().get(position) instanceof MusicInfo) || position != 0){
            ((MusicHolder)holder).batch_layout.setVisibility(View.GONE);
        }else{
            ((MusicHolder)holder).batch_layout.setVisibility(View.VISIBLE);
        }
        ((MusicHolder)holder).num_tv.setText(String.valueOf(position+1));
        ((MusicHolder)holder).music_name_tv.setText(infos.getName());
        if(TextUtils.isEmpty(infos.getAlbum())){
            ((MusicHolder)holder).music_artist_tv.setText(infos.getArtist());
        }else {
            ((MusicHolder)holder).music_artist_tv.setText(infos.getArtist()+" - "+infos.getAlbum());
        }
        ((MusicHolder)holder).batch_layout.setOnClickListener(this);
        ((MusicHolder)holder).batch_operate.setOnClickListener(this);
        ((MusicHolder)holder).more_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_content, null);
                TextView singerName = (TextView) view.findViewById(R.id.singer_name);
                singerName.setText("歌曲："+((MusicInfo) getItem().get(position)).getName());

                view.findViewById(R.id.single_download).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUtils.DownloadMusic(getContext(), (MusicInfo) getItem().get(position));
                        ToastUtil.showToast(getContext(),"已加入下载队列");
                    }
                });
                MyUtils.showBottomDialog(getContext(),view);
            }
        });
        ((MusicHolder)holder).music_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               playAll(position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.batch_layout:
                playAll(0);
                break;
            case R.id.batch_operate:
                FragmentControl.getInstance().showSubFrag(BatchFragment.newInstance(getItem()),BatchFragment.class.getSimpleName());
                break;
        }
    }

    private void playAll(int position) {
        MusicDao dao = new MusicDao(getContext());
        dao.del();
        ArrayList<OnlineInfo> item = getItem();
        List<MusicInfo> musicInfos = new ArrayList<MusicInfo>();
        for (OnlineInfo onlineinfo:item) {
            musicInfos.add((MusicInfo)onlineinfo);
            long rid = ((MusicInfo) onlineinfo).getRid();
            String artist = ((MusicInfo) onlineinfo).getArtist();
            String name = ((MusicInfo) onlineinfo).getName();
            dao.add(rid,name,artist,"");
        }
        MediaPlayerManager.getInstance().setMediaPlayerUrlAndStart(musicInfos,position);
    }

    private static class MusicHolder extends RecyclerView.ViewHolder{
        TextView music_name_tv;
        TextView music_artist_tv;
        TextView num_tv;
        ImageView batch_operate;
        ImageView more_iv;
        RelativeLayout music_layout;
        RelativeLayout batch_layout;
        public MusicHolder(View itemView) {
            super(itemView);
            music_artist_tv = (TextView) itemView.findViewById(R.id.music_artist_tv);
            music_name_tv = (TextView) itemView.findViewById(R.id.music_name_tv);
            batch_operate = (ImageView) itemView.findViewById(R.id.batch_operate);
            more_iv = (ImageView) itemView.findViewById(R.id.more_iv);
            num_tv = (TextView) itemView.findViewById(R.id.num_tv);
            music_layout = (RelativeLayout) itemView.findViewById(R.id.music_layout);
            batch_layout = (RelativeLayout) itemView.findViewById(R.id.batch_layout);
        }
    }
}

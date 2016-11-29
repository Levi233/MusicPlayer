package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.MusicInfo;
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.db.MusicDao;
import com.chenhao.musicplayer.mod.MediaPlayerManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhao on 2016/11/27.
 */

public class MusicAdapter extends SingleRecyclerAdapter<ArrayList<OnlineInfo>> {

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
        ((MusicHolder)holder).music_name_tv.setText(infos.getName());
        if(TextUtils.isEmpty(infos.getAlbum())){
            ((MusicHolder)holder).music_artist_tv.setText(infos.getArtist());
        }else {
            ((MusicHolder)holder).music_artist_tv.setText(infos.getArtist()+" - "+infos.getAlbum());
        }
        ((MusicHolder)holder).music_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    private static class MusicHolder extends RecyclerView.ViewHolder{
        TextView music_name_tv;
        TextView music_artist_tv;
        LinearLayout music_layout;
        public MusicHolder(View itemView) {
            super(itemView);
            music_artist_tv = (TextView) itemView.findViewById(R.id.music_artist_tv);
            music_name_tv = (TextView) itemView.findViewById(R.id.music_name_tv);
            music_layout = (LinearLayout) itemView.findViewById(R.id.music_layout);
        }
    }
}

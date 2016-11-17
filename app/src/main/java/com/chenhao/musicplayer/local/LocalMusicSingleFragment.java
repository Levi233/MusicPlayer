package com.chenhao.musicplayer.local;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.adapter.MyListAdapter;
import com.chenhao.musicplayer.bean.MusicInfo;
import com.chenhao.musicplayer.db.MusicDao;
import com.chenhao.musicplayer.messagemgr.MediaPlayerObserver;
import com.chenhao.musicplayer.messagemgr.MessageID;
import com.chenhao.musicplayer.messagemgr.MessageManager;
import com.chenhao.musicplayer.mod.MediaPlayerManager;
import com.chenhao.musicplayer.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhao on 2016/11/4.
 */

public class LocalMusicSingleFragment extends Fragment {

    private ListView mListView;
    private MyListAdapter mAdapter;
    private List<MusicInfo> mInfos = new ArrayList<>();
    private android.os.Handler mHandler = new android.os.Handler();

    public static LocalMusicSingleFragment newInstance() {
        LocalMusicSingleFragment fragment = new LocalMusicSingleFragment();
        return fragment;
    }

    private MediaPlayerObserver mObserver = new MediaPlayerObserver() {

        @Override
        public void startMusic(List<MusicInfo> infos, int position) {
        }

        @Override
        public void stopMusic() {
        }

        @Override
        public void onPause() {
        }

        @Override
        public void onBuffering(int i) {
        }

        @Override
        public void onPrepared(List<MusicInfo> infos, int position) {
            mAdapter.setSelected(position);
            mAdapter.notifyDataSetChanged();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music, container, false);
        initView(view);
        MessageManager.getInstance().attachMessage(MessageID.OBSERVER_MEDIA_PLAYER, mObserver);
        mInfos = MediaPlayerManager.getInstance().getInfos();
        setAdapterAndListener();
        return view;
    }

    private void setAdapterAndListener() {
        mAdapter = new MyListAdapter(getContext(), mInfos);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("chenhaolog", "OnItemClickListener position " + position);
                MediaPlayerManager.getInstance().setMediaPlayerUrlAndStart(mInfos, position);
                mAdapter.setSelected(position);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.listView);
    }

    public void getMusic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = Environment.getExternalStorageDirectory().getPath();
                String path = str + "//netease//cloudmusic//Music//";
                String path2 = str + "//";
                mInfos = FileUtils.getFileList(path);
                MusicDao dao = new MusicDao(getContext());
                dao.del();
                for (MusicInfo info:mInfos) {
                    String name = info.getName();
                    String artist = info.getArtist();
                    String url = info.getUrl();
                    dao.add(-1,name,artist,url);
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setInfos(mInfos);
                        mAdapter.notifyDataSetChanged();
                        mListView.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MessageManager.getInstance().detachMessage(MessageID.OBSERVER_MEDIA_PLAYER, mObserver);
    }

}

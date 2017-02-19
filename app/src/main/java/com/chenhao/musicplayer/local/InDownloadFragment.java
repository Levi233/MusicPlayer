package com.chenhao.musicplayer.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.chenhao.musicplayer.R;

/**
 * Created by chenhao on 2016/12/18.
 */

public class InDownloadFragment extends Fragment {

    private ListView mListView;

    public static InDownloadFragment newInstance() {
        InDownloadFragment f = new InDownloadFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloading, container, false);
        initView(view);
        initControl();
        return view;
    }

    private void initControl() {

    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.listView);
    }

    private class InDownloadListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return null;
        }
    }
}

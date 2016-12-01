package com.chenhao.musicplayer.online;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.adapter.MultiAdapter;
import com.chenhao.musicplayer.bean.RootInfo;
import com.chenhao.musicplayer.fragment.BaseFragment;
import com.chenhao.musicplayer.utils.OnlineUrlUtil;
import com.chenhao.musicplayer.utils.XmlParse;

/**
 * Created by chenhao on 2016/12/1.
 */

public class QzListFragment extends BaseFragment<RootInfo> implements View.OnClickListener{
    private long mId;
    private int mDigest;
    private String mName;
    private RecyclerView mRecyclerView;

    public static QzListFragment newInstance(long id,int digest,String name){
        QzListFragment f = new QzListFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        bundle.putInt("digest",digest);
        bundle.putString("name",name);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVisible();
        Bundle bundle = getArguments();
        if(bundle != null){
            mId = bundle.getLong("id");
            mDigest = bundle.getInt("digest");
            mName = bundle.getString("name");
        }
    }

    @Override
    protected String getRequestUrl() {
        return OnlineUrlUtil.getRequest("get_qz_data", mId, 0, 30, String.valueOf(mDigest));
    }

    @Override
    protected RootInfo onBackgroundParser(String datas) throws Exception {
        RootInfo rootInfo = XmlParse.parseXml(datas);
        return rootInfo;
    }

    @Override
    protected View onCreateTitleView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_base_head, container, false);
        ImageView btn_back = (ImageView) view.findViewById(R.id.btn_back);
        TextView title_name = (TextView) view.findViewById(R.id.title_name);
        btn_back.setOnClickListener(this);
        title_name.setText(mName);
        return view;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, RootInfo infos) {
        View view = inflater.inflate(R.layout.fragment_qz_list, container, false);
        initView(view);
        initControl(infos);
        return view;
    }

    private void initControl(RootInfo infos) {
        MultiAdapter adapter = new MultiAdapter(getContext(), infos, new Handler());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}

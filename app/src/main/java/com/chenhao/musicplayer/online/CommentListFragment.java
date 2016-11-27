package com.chenhao.musicplayer.online;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.adapter.MultiAdapter;
import com.chenhao.musicplayer.bean.RootInfo;
import com.chenhao.musicplayer.fragment.BaseFragment;
import com.chenhao.musicplayer.utils.MyUtils;
import com.chenhao.musicplayer.utils.OnlineUrlUtil;
import com.chenhao.musicplayer.utils.ParserJson;

/**
 * Created by chenhao on 2016/11/27.
 */

public class CommentListFragment extends BaseFragment<RootInfo> {

    private RecyclerView mRecyclerView;

    private int mDigest;
    private long mId;
    public static CommentListFragment newInstance(long id,int digest){
        CommentListFragment f = new CommentListFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        bundle.putInt("digest",digest);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disEnableSecondDecode();
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDigest = bundle.getInt("digest");
            mId = bundle.getLong("id");
        }
    }

    @Override
    protected String getRequestUrl() {
        return OnlineUrlUtil.getCommentListUrl(null,0,String.valueOf(mDigest),mId,0,10);
    }

    @Override
    protected RootInfo onBackgroundParser(String datas) throws Exception {
        String s = MyUtils.decodeKSing(datas);
        Log.d("chenhaolog", "CommentListFragment [datas]: "+s);
        RootInfo rootInfo = ParserJson.parserCommentJson(s);
        return rootInfo;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, RootInfo infos) {
        View view = inflater.inflate(R.layout.fragment_comment_list, container, false);
        initView(view);
        setAdapter(infos);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    public void setAdapter(RootInfo infos) {
        MultiAdapter adapter = new MultiAdapter(getContext(), infos, new Handler());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }
}

package com.chenhao.musicplayer.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.mod.OnlineTask;

/**
 * Created by chenhao on 2016/11/7.
 */

public abstract class BaseFragment<T> extends Fragment implements View.OnTouchListener {

    private FrameLayout mTitleContainer;
    private FrameLayout mContentContainer;
    private FragmentState mInitFragmentState;
    private FragmentState mLastFragmentState;
    private boolean mSecondDecode = true;
    private LayoutInflater mInflater;
    private Handler mHandler = new Handler();
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.setClickable(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
    protected abstract String getRequestUrl();
    protected abstract T onBackgroundParser(String datas) throws Exception;

    private OnlineTask.OnStateViewListener<T> mStateViewListener = new OnlineTask.OnStateViewListener<T>(){

        @Override
        public void onRefresh(FragmentState state, T t) {
            showOnlineView(state,t);
        }

        @Override
        public T onBackgroundParser(String datas) throws Exception {
            return BaseFragment.this.onBackgroundParser(datas);
        }
    };

    protected final void disEnableSecondDecode(){
        this.mSecondDecode = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        View view = inflater.inflate(R.layout.base_fragment, container, false);
        mTitleContainer = (FrameLayout) view.findViewById(R.id.base_title_container);
        mContentContainer = (FrameLayout) view.findViewById(R.id.base_content_container);
        View titleViewGroup = onCreateTitleView(inflater, mTitleContainer);
        if(titleViewGroup != null && isFragmentAlive()){
            mTitleContainer.addView(titleViewGroup);
        }
        executeInOnCreateView();
        return view;
    }

    protected View onCreateTitleView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    protected final boolean isFragmentAlive() {
        return (getActivity() != null && !getActivity().isFinishing() && !isDetached());
    }

    public View onCreateEmptyView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_base_empty, container, false);
        return view;
    }
    public View onCreateErrorView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_base_error,container,false);
        return view;
    }
    public View onCreateFailureView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_base_failure,container,false);
        TextView retry = (TextView) view.findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNetData();
            }
        });
        return view;
    }
    public View onCreateLoadingView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_base_loading,container,false);
        return view;
    }

    public View onCreateNoNetworkView(LayoutInflater inflater, ViewGroup container){
        View view = inflater.inflate(R.layout.fragment_base_no_network,container,false);
        return view;
    }

    public abstract View onCreateContentView(LayoutInflater inflater, ViewGroup container,T t);


    protected void executeInOnCreateView(){
        if(mInitFragmentState == FragmentState.EMPTY){
            View child = onCreateEmptyView(mInflater, mContentContainer);
            showContentView(child, mInitFragmentState);
        }else if(mInitFragmentState == FragmentState.ERROR){
            View child = onCreateErrorView(mInflater, mContentContainer);
            showContentView(child, mInitFragmentState);
        }else if(mInitFragmentState == FragmentState.SUCCESS){
            View child = onCreateContentView(mInflater, mContentContainer,null);
            showContentView(child, mInitFragmentState);
        }else {
            if(isNetworkAvailable()){
                requestNetData();
            }else{
                showOnlineView(FragmentState.NET_UNAVAILABLE,null);
            }
        }
    }

    private void requestNetData(){
        String url = getRequestUrl();
        if (TextUtils.isEmpty(url)) {
            showOnlineView(FragmentState.FAILURE,null);
            return;
        }else{
            showOnlineView(FragmentState.LOADING,null);
            OnlineTask<T> task = new OnlineTask<>(url, mStateViewListener);
            task.setDecode(mSecondDecode);
        }
    }

    protected final void showContentView(View child, FragmentState fragmentState) {
        if (child != null && isFragmentAlive()) {
                mContentContainer.removeAllViews();
                mContentContainer.addView(child);
                mLastFragmentState = fragmentState;
        }
    }

    private void showOnlineView(final FragmentState fragmentState , final T t) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (fragmentState != null && isFragmentAlive()) {
                    switch (fragmentState) {
                        case SUCCESS:
                            View successView = onCreateContentView(mInflater, mContentContainer,t);
                            showContentView(successView, fragmentState);
                            break;
                        case FAILURE:
                            View failureView = onCreateFailureView(mInflater, mContentContainer);
                            showContentView(failureView, fragmentState);
                            break;
                        case LOADING:
                            View loadingView = onCreateLoadingView(mInflater, mContentContainer);
                            showContentView(loadingView, fragmentState);
                            break;
                        case EMPTY:
                            View emptyView = onCreateEmptyView(mInflater, mContentContainer);
                            showContentView(emptyView, fragmentState);
                            break;
                        case NET_UNAVAILABLE:
                            View noNetworkView = onCreateNoNetworkView(mInflater, mContentContainer);
                            showContentView(noNetworkView, fragmentState);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }
    /**
     * 在onBackgroundParser抛出该异常,页面会显示为空页面
     */
    public static class EmptyStateViewException extends Exception {
        private static final long serialVersionUID = 1L;
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isAvailable());
    }
}

package com.chenhao.musicplayer.mod;

import com.chenhao.musicplayer.fragment.BaseFragment;
import com.chenhao.musicplayer.fragment.FragmentState;
import com.chenhao.musicplayer.utils.MyUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenhao on 2016/11/15.
 */

public class OnlineTask<T> {
    private String mUrl;
    private boolean mDecode = true;
    private OnStateViewListener<T> mListener;

    public OnlineTask(String url,OnStateViewListener<T> listener){
        this.mUrl = url;
        this.mListener = listener;
        okHttp();
    }

    private void okHttp(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(mUrl)
                .build();
        try {
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mListener.onRefresh(FragmentState.FAILURE, null);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    byte[] bytes = response.body().bytes();
                    String datas = new String(bytes);
                    if(mDecode){
                        datas = MyUtils.decode(bytes);
                    }
                    try {
                        T t = null;
                        t = (T) mListener.onBackgroundParser(datas);
                        if (t == null) {
                            mListener.onRefresh(FragmentState.FAILURE, null);
                            throw new Exception("onBackgroundParser return null");
                        } else {
                            mListener.onRefresh(FragmentState.SUCCESS, t);
                        }
                    } catch (BaseFragment.EmptyStateViewException e) {
                        e.printStackTrace();
                        mListener.onRefresh(FragmentState.EMPTY,null);
                    } catch (Exception e){
                        e.printStackTrace();
                        mListener.onRefresh(FragmentState.FAILURE, null);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            mListener.onRefresh(FragmentState.ERROR, null);
        }
    }




    public final void setDecode(boolean flag){
        this.mDecode = flag;
    }

    public static interface OnUserStateViewListener<T> {
        public void onRefresh(FragmentState state, T t);
    }

    public static interface OnStateViewListener<T> extends OnUserStateViewListener<T> {

        public T onBackgroundParser(String datas) throws Exception;
    }
}

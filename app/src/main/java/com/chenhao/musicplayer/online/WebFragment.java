package com.chenhao.musicplayer.online;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.mod.JavaScriptInterface;

/**
 * Created by chenhao on 2016/11/30.
 */

public class WebFragment extends Fragment implements View.OnClickListener{

    private String mUrl;
    private String mDesc;
    private WebView mWebView;
    private ImageView mBackImg;
    private TextView mDescTextView;
    private LinearLayout mLogin;
    public static WebFragment newInstance(String url,String desc){
        WebFragment f = new WebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        bundle.putString("desc",desc);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUrl = bundle.getString("url");
            mDesc = bundle.getString("desc");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        initView(view);
        initControl();
        return view;
    }

    private void initControl() {
        Log.e("chenhaolog","web ---url : "+mUrl);
        mWebView.loadUrl(mUrl);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.addJavascriptInterface(new JavaScriptInterface(),"KuwoInterface");
        mDescTextView.setText(mDesc);
        mBackImg.setOnClickListener(this);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLogin.setVisibility(View.GONE);
            }
        });
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
                    mWebView.goBack();
                    return true;
                }
                return false;
            }
        });
    }

    private void initView(View view) {
        mWebView = (WebView) view.findViewById(R.id.webView);
        mBackImg = (ImageView) view.findViewById(R.id.btn_back);
        mDescTextView = (TextView) view.findViewById(R.id.desc);
        mLogin = (LinearLayout) view.findViewById(R.id.login_layout);
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

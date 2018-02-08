package com.sk.uudc.module.home.activity;

import android.view.View;
import android.webkit.WebView;

import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.module.near.Constant;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/2/8.
 */

public class WebActivity extends BaseActivity {
    @BindView(R.id.webview)
    WebView webview;
    @Override
    protected int getContentView() {
        setAppTitle(getResources().getString(R.string.app_name));
        return R.layout.act_web;
    }

    @Override
    protected void initView() {
        String content = getIntent().getStringExtra(Constant.IParam.htmlContent);
        if (content != null) {
            initWebViewForContent(webview,content);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}

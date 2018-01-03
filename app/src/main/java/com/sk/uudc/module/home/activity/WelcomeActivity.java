package com.sk.uudc.module.home.activity;

import android.os.Handler;
import android.view.View;

import com.sk.uudc.base.BaseActivity;

/**
 * Created by Administrator on 2018/1/3.
 */

public class WelcomeActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        setNoTheme(true);
        return 0;
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                STActivity(MainActivity.class);
                finish();
            }
        },1500);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}

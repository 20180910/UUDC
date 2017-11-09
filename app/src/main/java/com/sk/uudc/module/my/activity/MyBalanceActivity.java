package com.sk.uudc.module.my.activity;

import android.support.v4.content.ContextCompat;
import android.view.View;

import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;

/**
 * Created by Administrator on 2017/11/6.
 */

public class MyBalanceActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        setAppTitle("我的账户");
        setAppRightTitleColor(ContextCompat.getColor(mContext,R.color.home_green));
        return R.layout.act_my_balance;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}

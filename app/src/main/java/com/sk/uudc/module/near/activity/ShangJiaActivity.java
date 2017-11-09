package com.sk.uudc.module.near.activity;

import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/8.
 */

public class ShangJiaActivity extends BaseActivity {
    @BindView(R.id.ctl_shang_jia)
    CommonTabLayout ctl_shang_jia;

    @Override
    protected int getContentView() {
        return R.layout.act_shang_jia;
    }

    @Override
    protected void initView() {
        ctl_shang_jia.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}

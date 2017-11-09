package com.sk.uudc.module.my.activity;

import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.module.my.bean.UpdateBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/7.
 */

public class UpdatePWDActivity extends BaseActivity {
    @BindView(R.id.ctl_update_pwd)
    CommonTabLayout ctl_update_pwd;

    @BindView(R.id.fl_update_pwd)
    FrameLayout fl_update_pwd;

    @Override
    protected int getContentView() {
        setAppTitle("修改密码");
        return R.layout.act_update_pwd;
    }

    @Override
    protected void initView() {
        ArrayList<CustomTabEntity> list=new ArrayList<>();
        list.add(new UpdateBean("原密码验证"));
        list.add(new UpdateBean("手机验证"));

        ctl_update_pwd.setTabData(list);
        ctl_update_pwd.setOnTabSelectListener(new OnTabSelectListener() {
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

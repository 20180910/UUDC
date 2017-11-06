package com.sk.uudc.module.my.fragment;

import android.view.View;

import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;
import com.sk.uudc.module.my.activity.MyCollectActivity;
import com.sk.uudc.module.my.activity.MyFenXiaoActivity;
import com.sk.uudc.module.my.activity.MyShouYiActivity;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MyFragment extends BaseFragment {
    @Override
    protected int getContentView() {
        return R.layout.frag_my;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_my_shouyi,R.id.ll_my_fenxiao,R.id.ll_my_collect})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.ll_my_shouyi:
                STActivity(MyShouYiActivity.class);
            break;
            case R.id.ll_my_fenxiao:
                STActivity(MyFenXiaoActivity.class);
            break;
            case R.id.ll_my_collect:
                STActivity(MyCollectActivity.class);
            break;
        }
    }
}

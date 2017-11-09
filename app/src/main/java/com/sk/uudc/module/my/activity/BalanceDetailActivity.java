package com.sk.uudc.module.my.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.module.my.adapter.BalanceDetailAdapter;
import com.sk.uudc.module.my.fragment.BalanceDetailFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/7.
 */

public class BalanceDetailActivity extends BaseActivity {
    @BindView(R.id.stl_balance_detail)
    SlidingTabLayout stl_balance_detail;

    @BindView(R.id.vp_balance_detail)
    ViewPager vp_balance_detail;

    BalanceDetailAdapter adapter;
    List<Fragment> list=new ArrayList<>();

    @Override
    protected int getContentView() {
        setAppTitle("余额明细");
        return R.layout.act_balance_detail;
    }

    @Override
    protected void initView() {
        list.add(BalanceDetailFragment.newInstance());
        list.add(BalanceDetailFragment.newInstance());
        list.add(BalanceDetailFragment.newInstance());

        adapter=new BalanceDetailAdapter(getSupportFragmentManager());
        adapter.setList(list);
        vp_balance_detail.setAdapter(adapter);
        vp_balance_detail.setOffscreenPageLimit(list.size()-1);

        stl_balance_detail.setViewPager(vp_balance_detail);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}

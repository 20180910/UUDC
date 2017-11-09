package com.sk.uudc.module.near.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/9.
 */

public class ShangJiaShangPinFragment extends BaseFragment {
    @BindView(R.id.rv_shang_pin_type)
    RecyclerView rv_shang_pin_type;

    @BindView(R.id.rv_shang_pin_cai)
    RecyclerView rv_shang_pin_cai;



    @Override
    protected int getContentView() {
        return R.layout.frag_shang_jia_shang_pin;
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

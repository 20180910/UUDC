package com.sk.uudc.module.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/7.
 */

public class PayActivity extends BaseActivity {
    @BindView(R.id.rv_pay_cai)
    RecyclerView rv_pay_cai;
    BaseRecyclerAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("支付");
        return R.layout.act_pay;
    }

    @Override
    protected void initView() {
        adapter=new BaseRecyclerAdapter(mContext,R.layout.item_pay_cai) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, Object o) {

            }
        };
        rv_pay_cai.setLayoutManager(new LinearLayoutManager(mContext));
        rv_pay_cai.setAdapter(adapter);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}

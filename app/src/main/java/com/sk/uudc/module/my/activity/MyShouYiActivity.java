package com.sk.uudc.module.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MyShouYiActivity extends BaseActivity {
    @BindView(R.id.rv_my_shouyi)
    RecyclerView rv_my_shouyi;

    LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("我的收益");
        return R.layout.act_my_shouyi;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter(mContext,R.layout.item_my_shou_yi,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, Object bean) {

            }
        };
        adapter.setOnLoadMoreListener(this);
        adapter.setTestListSize(10);

        rv_my_shouyi.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_shouyi.addItemDecoration(getItemDivider());
        rv_my_shouyi.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}

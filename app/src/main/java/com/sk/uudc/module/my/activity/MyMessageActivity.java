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
 * Created by Administrator on 2017/11/6.
 */

public class MyMessageActivity extends BaseActivity {
    @BindView(R.id.rv_my_message)
    RecyclerView rv_my_message;
    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("我的消息");
        return R.layout.act_my_message;
    }

    @Override
    protected void initView() {

        adapter=new LoadMoreAdapter(mContext,R.layout.item_my_message,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, Object bean) {

            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_my_message.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_message.addItemDecoration(getItemDivider());
        rv_my_message.setAdapter(adapter);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);

    }

    @Override
    protected void onViewClick(View v) {

    }
}

package com.sk.uudc.module.my.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MyCollectActivity extends BaseActivity {
    @BindView(R.id.rv_my_collect)
    RecyclerView rv_my_collect;
    private LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("我的收藏");
        setAppRightTitle("编辑");
        setAppRightTitleColor(ContextCompat.getColor(mContext,R.color.home_green));
        return R.layout.act_my_collect;
    }

    @Override
    protected void initView() {
        adapter =new LoadMoreAdapter(mContext,R.layout.item_my_collect,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, Object bean) {

            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_my_collect.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_collect.addItemDecoration(getItemDivider());
        rv_my_collect.setAdapter(adapter);


    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
    }

    @OnClick({R.id.app_right_tv})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_tv:

            break;
        }
    }
}

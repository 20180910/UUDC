package com.sk.uudc.module.my.activity;

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

public class MyEvaluateActivity extends BaseActivity {
    @BindView(R.id.rv_dai_evaluate)
    RecyclerView rv_dai_evaluate;
    private LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("待评价");
        return R.layout.act_dai_evaluate;
    }

    @Override
    protected void initView() {
        adapter =new LoadMoreAdapter(mContext,R.layout.item_my_evaluate,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, Object bean) {

            }
        };
        adapter.setTestListSize(10);
        adapter.setOnLoadMoreListener(this);

        rv_dai_evaluate.setLayoutManager(new LinearLayoutManager(mContext));
        rv_dai_evaluate.addItemDecoration(getItemDivider());
        rv_dai_evaluate.setAdapter(adapter);


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

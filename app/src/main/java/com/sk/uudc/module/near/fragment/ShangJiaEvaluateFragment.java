package com.sk.uudc.module.near.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/9.
 */

public class ShangJiaEvaluateFragment extends BaseFragment {
    @BindView(R.id.rv_shang_jia_evaluate)
    RecyclerView rv_shang_jia_evaluate;

    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        return R.layout.frag_shang_jia_evaluate;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter(mContext,R.layout.item_shang_jia_evaluate,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, Object bean) {

            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_shang_jia_evaluate.setLayoutManager(new LinearLayoutManager(mContext));
        rv_shang_jia_evaluate.addItemDecoration(getItemDivider());
        rv_shang_jia_evaluate.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}

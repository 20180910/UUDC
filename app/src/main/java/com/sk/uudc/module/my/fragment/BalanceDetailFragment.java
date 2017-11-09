package com.sk.uudc.module.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/7.
 */

public class BalanceDetailFragment extends BaseFragment {
    @BindView(R.id.rv_balance_detail)
    RecyclerView rv_balance_detail;
    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        return R.layout.frag_balance_detail;
    }

    public static BalanceDetailFragment newInstance() {

        Bundle args = new Bundle();

        BalanceDetailFragment fragment = new BalanceDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter(mContext,R.layout.item_balance_detail,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, Object bean) {

            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_balance_detail.setLayoutManager(new LinearLayoutManager(mContext));
        rv_balance_detail.addItemDecoration(getItemDivider());
        rv_balance_detail.setAdapter(adapter);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}

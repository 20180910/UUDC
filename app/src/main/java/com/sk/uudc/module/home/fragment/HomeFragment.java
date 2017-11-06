package com.sk.uudc.module.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;
import com.sk.uudc.tools.SpaceItemDecoration;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/4.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.rv_home_cai)
    RecyclerView rv_home_cai;

    @BindView(R.id.rv_home_can_ting)
    RecyclerView rv_home_can_ting;

    @BindView(R.id.rv_home_like)
    RecyclerView rv_home_like;

    BaseRecyclerAdapter caiAdapter,canTingAdapter,likeAdapter;

    @Override
    protected int getContentView() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView() {
        caiAdapter=new BaseRecyclerAdapter(mContext,R.layout.item_home_cai) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, Object o) {

            }
        };
        caiAdapter.setTestListSize(8);
        rv_home_cai.setNestedScrollingEnabled(false);
        rv_home_cai.setLayoutManager(new GridLayoutManager(mContext,4));
        rv_home_cai.addItemDecoration(new SpaceItemDecoration(10));
        rv_home_cai.setAdapter(caiAdapter);


        canTingAdapter=new BaseRecyclerAdapter(mContext,R.layout.item_home_can_ting) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, Object o) {
                ImageView iv_home_can_ting = holder.getImageView(R.id.iv_home_can_ting);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height= (PhoneUtils.getScreenWidth(mContext)-PhoneUtils.dip2px(mContext,20))/3;
                iv_home_can_ting.setLayoutParams(layoutParams);

            }
        };
        canTingAdapter.setTestListSize(5);
        rv_home_can_ting.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 1){
                    return 3;
                }else{
                    return 2;
                }
            }
        });
        rv_home_can_ting.setLayoutManager(gridLayoutManager);
        rv_home_can_ting.addItemDecoration(new SpaceItemDecoration(5));
        rv_home_can_ting.setAdapter(canTingAdapter);

        likeAdapter=new BaseRecyclerAdapter(mContext,R.layout.item_home_like) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, Object o) {

            }
        };
        likeAdapter.setTestListSize(3);
        rv_home_like.setNestedScrollingEnabled(false);
        rv_home_like.setLayoutManager(new LinearLayoutManager(mContext));
        rv_home_like.addItemDecoration(getItemDivider());
        rv_home_like.setAdapter(likeAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }


}

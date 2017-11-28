package com.sk.uudc.module.near.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.response.ShangJiaInfoObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/9.
 */

public class ShangJiaJieShaoFragment extends BaseFragment {
    @BindView(R.id.rv_shang_jia_jie_shao)
    RecyclerView rv_shang_jia_jie_shao;
    @BindView(R.id.rv_shang_jia_info)
    RecyclerView rv_shang_jia_info;
    LoadMoreAdapter adapter;
    BaseRecyclerAdapter huoDongAdapter;
    String merchantId;
    @BindView(R.id.tv_shangjia_info_address)
    TextView tv_shangjia_info_address;
    @BindView(R.id.tv_shangjia_info_time)
    TextView tv_shangjia_info_time;
    @BindView(R.id.tv_shangjia_info_remark)
    TextView tv_shangjia_info_remark;


    public static ShangJiaJieShaoFragment newInstance(String merchantId) {
        ShangJiaJieShaoFragment newFragment = new ShangJiaJieShaoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.merchantId, merchantId);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_shang_jia_jie_shao;
    }

    @Override
    protected void initView() {
        merchantId = getArguments().getString(Constant.merchantId);

        adapter = new LoadMoreAdapter<String>(mContext, R.layout.item_shang_jia_evaluate, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, String bean) {
                ImageView imageView = (ImageView) holder.itemView;
                Glide.with(mContext).load(bean).error(R.color.c_press).into(imageView);
            }

            @Override
            public LoadMoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ImageView imageView=new ImageView(mContext);
//                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int w = (PhoneUtils.getScreenWidth(mContext) - PhoneUtils.dip2px(mContext, 10) * 4) / 3;
                layoutParams.setMargins(0,0, PhoneUtils.dip2px(mContext, 10) ,0);
                layoutParams.width= w;
                layoutParams.height=w*5/7;
                imageView.setLayoutParams(layoutParams);
                LoadMoreViewHolder holder=new LoadMoreViewHolder(mContext,imageView);
                return holder;
            }
        };
        rv_shang_jia_jie_shao.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
//        rv_shang_jia_jie_shao.addItemDecoration(getItemDivider());
        rv_shang_jia_jie_shao.setAdapter(adapter);



        huoDongAdapter=new BaseRecyclerAdapter<ShangJiaInfoObj.ActivityBean>(mContext,R.layout.item_shang_jia_info) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, ShangJiaInfoObj.ActivityBean bean) {
                holder.setText(R.id.tv_shangjia_info_huodong,"满"+bean.getFull_amount()+"减"+bean.getSubtract_amount());
            }
        };
        rv_shang_jia_info.setLayoutManager(new LinearLayoutManager(mContext));
        rv_shang_jia_info.setAdapter(huoDongAdapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("merchant_id",merchantId);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getShangJiaInfo(map, new MyCallBack<ShangJiaInfoObj>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(ShangJiaInfoObj obj) {
                tv_shangjia_info_address.setText(obj.getMerchant_address());
                tv_shangjia_info_time.setText("可预订时间:"+obj.getScheduled_time());
                tv_shangjia_info_remark.setText(obj.getMerchant_remark());
                adapter.setList(obj.getMerchant_photo_list(),true);
                huoDongAdapter.setList(obj.getActivity(),true);
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
}

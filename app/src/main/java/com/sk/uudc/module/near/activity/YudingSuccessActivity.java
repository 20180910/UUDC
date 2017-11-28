package com.sk.uudc.module.near.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.response.PaySuccessObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/16.
 */

public class YudingSuccessActivity extends BaseActivity {
    @BindView(R.id.tvYudingSuccessHexiaoma)
    TextView tvYudingSuccessHexiaoma;
    @BindView(R.id.rvYudingSuccess)
    RecyclerView rvYudingSuccess;
    BaseRecyclerAdapter mAdapter;
    private String orderId;

    @Override
    protected int getContentView() {
        setAppTitle("预订成功");
        setTitleBackgroud(R.color.white);
        return R.layout.act_yuding_success;
    }

    @Override
    protected void initView() {
        orderId =  getIntent().getStringExtra(Constant.IParam.order_id);

        mAdapter=new BaseRecyclerAdapter<PaySuccessObj.ListBean>(mContext,R.layout.item_yuding_success) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, PaySuccessObj.ListBean bean) {
                ImageView imageView = holder.getImageView(R.id.ivItemYudingIcon);
                Glide.with(mContext).load(bean.getMerchant_avatar()).error(R.color.c_press).into(imageView);
                holder.setText(R.id.tvItemYudingName,bean.getMerchant_name())
                        .setText(R.id.tvItemYudingAddress,bean.getDistance());

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.merchant_id,bean.getMerchant_id()+"");
                        STActivity(intent,ShangJiaActivity.class);
                    }
                });
            }
        };
        rvYudingSuccess.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        rvYudingSuccess.setLayoutManager(gridLayoutManager);
        rvYudingSuccess.setAdapter(mAdapter);

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
        map.put("user_id",getUserId());
        map.put("order_id",orderId);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.payTuiJianShangJia(map, new MyCallBack<PaySuccessObj>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(PaySuccessObj obj) {
                tvYudingSuccessHexiaoma.setText(obj.getVerification_code());
                mAdapter.setList(obj.getList(),true);
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
}

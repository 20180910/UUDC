package com.sk.uudc.module.near.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.response.ShangJiaYouHuiQuanObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/2.
 */

public class GetYouHuiQuanActivity extends BaseActivity {
    @BindView(R.id.rv_shangjia_youhuiquan)
    RecyclerView rv_shangjia_youhuiquan;

    LoadMoreAdapter adapter;
    private ShangJiaYouHuiQuanObj shangJiaYouHuiQuanObj;

    @Override
    protected int getContentView() {
        setAppTitle("领取优惠券");
        return R.layout.act_get_youhuiquan;
    }

    @Override
    protected void initView() {
        shangJiaYouHuiQuanObj = (ShangJiaYouHuiQuanObj) getIntent().getSerializableExtra(Constant.IParam.youHuiQuan);

        adapter = new LoadMoreAdapter<ShangJiaYouHuiQuanObj.CouponRedemptionListBean>(mContext, R.layout.item_get_youhuiquan, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, ShangJiaYouHuiQuanObj.CouponRedemptionListBean bean) {
                holder.setText(R.id.tv_youhuiquan_price, bean.getFace_value() + "")
                        .setText(R.id.tv_youhuiquan_tiaojian, bean.getTitle())
                        .setText(R.id.tv_youhuiquan_time, "有限期至" + bean.getEnd_time());


                ImageView iv_youhuiquan_status = holder.getImageView(R.id.iv_youhuiquan_status);
                TextView tv_youhuiquan_status = holder.getTextView(R.id.tv_youhuiquan_status);
                if (bean.getIs_status() == 0) {
                    iv_youhuiquan_status.setVisibility(View.GONE);
                    tv_youhuiquan_status.setVisibility(View.VISIBLE);
                } else {
                    iv_youhuiquan_status.setVisibility(View.VISIBLE);
                    tv_youhuiquan_status.setVisibility(View.GONE);
                }
                tv_youhuiquan_status.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        getYouHuiQuan();
                    }
                    private void getYouHuiQuan() {
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("coupon_redemption_id",bean.getCoupon_redemption_id()+"");
                        map.put("merchant_id",shangJiaYouHuiQuanObj.getMerchant_id());
                        map.put("user_id",getUserId());
                        map.put("sign", GetSign.getSign(map));
                        ApiRequest.getShangJiaYouHuiQuan(map, new MyCallBack<BaseObj>(mContext) {
                            @Override
                            public void onSuccess(BaseObj obj) {
                                bean.setIs_status(1);
                                notifyDataSetChanged();
                                showMsg(obj.getMsg());
                            }
                        });

                    }
                });
            }
        };
        adapter.setList(shangJiaYouHuiQuanObj.getCoupon_redemption_list());
//        adapter.setOnLoadMoreListener(this);

        rv_shangjia_youhuiquan.setLayoutManager(new LinearLayoutManager(mContext));
        rv_shangjia_youhuiquan.setNestedScrollingEnabled(false);
        rv_shangjia_youhuiquan.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_get_quxiadan})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_quxiadan:
                finish();
                break;
        }
    }
}

package com.sk.uudc.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.DaiEvaluateObj;
import com.sk.uudc.module.order.Constant;
import com.sk.uudc.module.order.activity.BusinessEvaluationActivity;
import com.sk.uudc.module.order.activity.OrderDetailsActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/4.
 */

public class DaiEvaluateActivity extends BaseActivity {

    @BindView(R.id.rv_dai_ping_jia)
    RecyclerView rv_dai_ping_jia;
    private LoadMoreAdapter adapter;
//    String order_id,avatar,name;

    @Override
    protected int getContentView() {
        setAppTitle("待评价");
        return R.layout.act_dai_ping_jia;
    }

    @Override
    protected void initView() {
        adapter = new LoadMoreAdapter<DaiEvaluateObj.DaiPingJiaBean>(mContext, R.layout.item_dai_evaluate, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, DaiEvaluateObj.DaiPingJiaBean bean) {
                ImageView iv_item_daipingjia_icon = holder.getImageView(R.id.iv_item_daipingjia_icon);
                TextView tv_item_daipingjia_name = holder.getTextView(R.id.tv_item_daipingjia_name);
                TextView tv_item_daipingjia_time = holder.getTextView(R.id.tv_item_daipingjia_time);
                MyTextView tv_item_daipingjia_shaidan = (MyTextView) holder.getTextView(R.id.tv_item_daipingjia_shaidan);
                Glide.with(mContext).load(bean.getMerchant_avatar()).error(R.color.c_press).into(iv_item_daipingjia_icon);
                tv_item_daipingjia_name.setText(bean.getMerchant_name());
                tv_item_daipingjia_time.setText(bean.getDine_time()+"消费未评价");
                tv_item_daipingjia_shaidan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        order_id=bean.getOrder_id();
//                        avatar=bean.getMerchant_avatar();
//                        name=bean.getMerchant_name();


                        Intent pingjia=new Intent();
                        pingjia.putExtra(Constant.IParam.orderId,bean.getOrder_id());
                        pingjia.putExtra(Constant.IParam.shangjiaImg,bean.getMerchant_avatar());
                        pingjia.putExtra(Constant.IParam.shangjiaName,bean.getMerchant_name());
                        STActivity(pingjia,BusinessEvaluationActivity.class);


                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.orderId,bean.getOrder_id());
                        STActivity(intent,OrderDetailsActivity.class);

                    }
                });



            }

        };

        rv_dai_ping_jia.setLayoutManager(new LinearLayoutManager(mContext));
        rv_dai_ping_jia.setNestedScrollingEnabled(false);
        rv_dai_ping_jia.addItemDecoration(getItemDivider());
        rv_dai_ping_jia.setAdapter(adapter);


    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("pagesize",pageSize+"");
        map.put("page",page+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getDaiPingJia(map, new MyCallBack<DaiEvaluateObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(DaiEvaluateObj obj) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getDaiPingJia(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getDaiPingJia(),true);
                }

            }
        });

    }


    protected void onViewClick(View v) {
    }
}

package com.sk.uudc.module.near.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.response.HongBaoObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/29.
 */

public class UseHongBaoActivity extends BaseActivity {
    @BindView(R.id.rv_hongbao)
    RecyclerView rv_hongbao;
    @BindView(R.id.tv_hongbao_nouser)
    TextView tv_hongbao_nouser;
    @BindView(R.id.tv_look_shixiao_hongbao)
    TextView tv_look_shixiao_hongbao;
    @BindView(R.id.ll_hongbao_cancel)
    LinearLayout ll_hongbao_cancel;

    LoadMoreAdapter adapter;

    private int youHuiType;
    private String title="";
    private String action;
    private String merchantId;
    private String amount;
    private int hongBaoId;
    String titleType="";
    @Override
    protected int getContentView() {
        return R.layout.act_use_hongbao;
    }

    @Override
    protected void initView() {
        action = getIntent().getAction();
        if(TextUtils.equals(Constant.IParam.tiJiaoOrder,action)){

            hongBaoId =getIntent().getIntExtra(Constant.IParam.hongBaoId,-1);
            merchantId =getIntent().getStringExtra(Constant.IParam.merchantId);
            amount =getIntent().getStringExtra(Constant.IParam.amount);

            ll_hongbao_cancel.setVisibility(View.VISIBLE);
            titleType="使用";

            tv_look_shixiao_hongbao.setVisibility(View.GONE);
        }else{
            ll_hongbao_cancel.setVisibility(View.GONE);
            titleType="我的";

            tv_look_shixiao_hongbao.setVisibility(View.VISIBLE);
        }

        youHuiType=getIntent().getIntExtra(Constant.IParam.youHuiType,Constant.IParam.youHuiType_1);
        int layoutView;
        if(youHuiType==Constant.IParam.youHuiType_1){
            title="红包";
            layoutView=R.layout.item_hongbao;
            tv_look_shixiao_hongbao.setText("查看已失效红包");
        }else{
            title="优惠券";
            layoutView=R.layout.item_youhuiquan;
            tv_look_shixiao_hongbao.setText("查看已失效优惠券");
        }
        setAppTitle(titleType+title);
        tv_hongbao_nouser.setText("不使用"+title);

        adapter=new LoadMoreAdapter<HongBaoObj>(mContext,layoutView,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, HongBaoObj bean) {
                holder.setText(R.id.tv_hongbao_title,bean.getTitle())
                        .setText(R.id.tv_hongbao_jine,bean.getFace_value()+"")
                        .setText(R.id.tv_hongbao_content,"满"+bean.getAvailable()+"使用")
                        .setText(R.id.tv_hongbao_time,"有效期至"+bean.getEnd_time());
                if(youHuiType==Constant.IParam.youHuiType_1){//红包

                }else{//优惠券
                    ImageView imageView = holder.getImageView(R.id.iv_hongbao_img);
                    Glide.with(mContext).load(bean.getPhoto()).error(R.color.c_press).into(imageView);
                }

                ImageView iv_hongbao_flag = holder.getImageView(R.id.iv_hongbao_flag);
                if(TextUtils.equals(Constant.IParam.tiJiaoOrder,action)){
                    holder.itemView.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            Intent intent=new Intent();
                            intent.putExtra(Constant.IParam.youHuiId,bean.getCoupons_id()+"");
                            intent.putExtra(Constant.IParam.youHuiTotal,bean.getFace_value());
                            intent.putExtra(Constant.IParam.hongBaoId,bean.getCoupons_id());
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    });
                    if(hongBaoId==bean.getCoupons_id()){
                        iv_hongbao_flag.setVisibility(View.VISIBLE);
                    }else{
                        iv_hongbao_flag.setVisibility(View.INVISIBLE);
                    }
                }else {
                    iv_hongbao_flag.setVisibility(View.INVISIBLE);
                }

            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_hongbao.setLayoutManager(new LinearLayoutManager(mContext));
        rv_hongbao.addItemDecoration(getItemDivider());
        rv_hongbao.setAdapter(adapter);


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
        //提交订单
        if(TextUtils.equals(Constant.IParam.tiJiaoOrder,action)){
            map.put("user_id",getUserId());
            map.put("type",youHuiType+"");
            map.put("merchant_id", merchantId);
            map.put("amount", amount);
            map.put("pagesize",pageSize+"");
            map.put("page",page+"");
            map.put("sign", GetSign.getSign(map));

            ApiRequest.getHongBaoYouHuiQuanForOrder(map, new MyCallBack<List<HongBaoObj>>(mContext,pcfl,pl_load) {
                @Override
                public void onSuccess(List<HongBaoObj> list) {
                    if(isLoad){
                        pageNum++;
                        adapter.addList(list,true);
                    }else{
                        pageNum=2;
                        adapter.setList(list,true);
                    }
                }
            });

        }else{//个人信息
            map.put("user_id",getUserId());
            map.put("type",youHuiType+"");
            map.put("status","1");
            map.put("pagesize",pageSize+"");
            map.put("page",page+"");
            map.put("sign", GetSign.getSign(map));
            ApiRequest.getHongBaoYouHuiQuan(map, new MyCallBack<List<HongBaoObj>>(mContext,pcfl,pl_load) {
                @Override
                public void onSuccess(List<HongBaoObj> list) {
                    if(isLoad){
                        pageNum++;
                        adapter.addList(list,true);
                    }else{
                        pageNum=2;
                        adapter.setList(list,true);
                    }
                }
            });
        }



    }

    @OnClick({R.id.tv_hongbao_nouser,R.id.tv_look_shixiao_hongbao})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_hongbao_nouser:
                setResult(RESULT_OK);
                finish();
            break;
            case R.id.tv_look_shixiao_hongbao:
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.youHuiType,youHuiType);
                STActivity(intent,ShiXiaoHongBaoActivity.class);
            break;
        }
    }
}

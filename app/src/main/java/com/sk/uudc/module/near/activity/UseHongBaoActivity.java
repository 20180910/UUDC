package com.sk.uudc.module.near.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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

    LoadMoreAdapter adapter;

    private int youHuiType;
    private String title="";
    @Override
    protected int getContentView() {
        return R.layout.act_use_hongbao;
    }

    @Override
    protected void initView() {
        youHuiType=getIntent().getIntExtra(Constant.IParam.youHuiType,Constant.IParam.youHuiType_1);
        int layoutView;
        if(youHuiType==Constant.IParam.youHuiType_1){
            title="红包";
            layoutView=R.layout.item_hongbao;
        }else{
            title="优惠券";
            layoutView=R.layout.item_youhuiquan;
        }
        setAppTitle("使用"+title);
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

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.hongBaoId,bean.getCoupons_id()+"");
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
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
        map.put("user_id",getUserId());
        map.put("type",youHuiType+"");
        map.put("status","1");
        map.put("pagesize",pageSize+"");
        map.put("page",page+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getHongBaoYouHuiQuan(map, new MyCallBack<List<HongBaoObj>>(mContext) {
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

    @OnClick({R.id.tv_hongbao_nouser})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_hongbao_nouser:
                setResult(RESULT_OK);
                finish();
            break;
        }
    }
}

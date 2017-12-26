package com.sk.uudc.module.near.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.response.ShangJiaEvaluateListObj;
import com.sk.uudc.module.near.network.response.ShangJiaEvaluateNumObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/9.
 */

public class ShangJiaEvaluateFragment extends BaseFragment {
    @BindView(R.id.rv_shang_jia_evaluate)
    RecyclerView rv_shang_jia_evaluate;

    LoadMoreAdapter adapter;
    String merchantId;
    @BindView(R.id.tv_shangjia_evaluate_all)
    MyTextView tv_shangjia_evaluate_all;
    @BindView(R.id.tv_shangjia_evaluate_good)
    MyTextView tv_shangjia_evaluate_good;
    @BindView(R.id.tv_shangjia_evaluate_bad)
    MyTextView tv_shangjia_evaluate_bad;
    @BindView(R.id.tv_shangjia_evaluate_pingfen)
    TextView tv_shangjia_evaluate_pingfen;

    MyTextView selectView;
    private String type="0";


    public static ShangJiaEvaluateFragment newInstance(String merchantId) {
        ShangJiaEvaluateFragment newFragment = new ShangJiaEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.merchantId, merchantId);
        newFragment.setArguments(bundle);
        return newFragment;

    }


    @Override
    protected int getContentView() {
        return R.layout.frag_shang_jia_evaluate;
    }

    @Override
    protected void initView() {
        selectView=tv_shangjia_evaluate_all;
        merchantId = getArguments().getString(Constant.merchantId);
        adapter = new LoadMoreAdapter<ShangJiaEvaluateListObj.ScoringListBean>(mContext, R.layout.item_shang_jia_evaluate, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, ShangJiaEvaluateListObj.ScoringListBean bean) {
                ImageView iv_shangjia_evaluate_img = holder.getImageView(R.id.iv_shangjia_evaluate_img);
                Glide.with(mContext).load(bean.getUserimg()).error(R.drawable.people).into(iv_shangjia_evaluate_img);
                holder.setText(R.id.tv_shangjia_evaluate_name,bean.getNickname())
                        .setText(R.id.tv_shangjia_evaluate_content,bean.getContent())
                        .setText(R.id.tv_shangjia_evaluate_fen,bean.getScoring()+"")
                        .setText(R.id.tv_shangjia_evaluate_time,bean.getDeadline());
                
                
                ImageView iv_shangjia_star1 = holder.getImageView(R.id.iv_shangjia_evaluate_star1);
                ImageView iv_shangjia_star2 = holder.getImageView(R.id.iv_shangjia_evaluate_star2);
                ImageView iv_shangjia_star3 = holder.getImageView(R.id.iv_shangjia_evaluate_star3);
                ImageView iv_shangjia_star4 = holder.getImageView(R.id.iv_shangjia_evaluate_star4);
                ImageView iv_shangjia_star5 = holder.getImageView(R.id.iv_shangjia_evaluate_star5);

                if(bean.getScoring()==1){
                    Log.d("=====","bean.getScoring()="+bean.getScoring());
                    iv_shangjia_star1.setVisibility(View.VISIBLE);
                    iv_shangjia_star2.setVisibility(View.GONE);
                    iv_shangjia_star3.setVisibility(View.GONE);
                    iv_shangjia_star4.setVisibility(View.GONE);
                    iv_shangjia_star5.setVisibility(View.GONE);
                }else if(bean.getScoring()==2){
                    Log.d("=====","bean.getScoring()="+bean.getScoring());
                    iv_shangjia_star1.setVisibility(View.VISIBLE);
                    iv_shangjia_star2.setVisibility(View.VISIBLE);
                    iv_shangjia_star3.setVisibility(View.GONE);
                    iv_shangjia_star4.setVisibility(View.GONE);
                    iv_shangjia_star5.setVisibility(View.GONE);
                }else if(bean.getScoring()==3){
                    Log.d("=====","bean.getScoring()="+bean.getScoring());
                    iv_shangjia_star1.setVisibility(View.VISIBLE);
                    iv_shangjia_star2.setVisibility(View.VISIBLE);
                    iv_shangjia_star3.setVisibility(View.VISIBLE);
                    iv_shangjia_star4.setVisibility(View.GONE);
                    iv_shangjia_star5.setVisibility(View.GONE);
                }else if(bean.getScoring()==4){
                    Log.d("=====","bean.getScoring()="+bean.getScoring());
                    iv_shangjia_star1.setVisibility(View.VISIBLE);
                    iv_shangjia_star2.setVisibility(View.VISIBLE);
                    iv_shangjia_star3.setVisibility(View.VISIBLE);
                    iv_shangjia_star4.setVisibility(View.VISIBLE);
                    iv_shangjia_star5.setVisibility(View.GONE);
                }else if(bean.getScoring()==5){
                    Log.d("=====","bean.getScoring()="+bean.getScoring());
                    iv_shangjia_star1.setVisibility(View.VISIBLE);
                    iv_shangjia_star2.setVisibility(View.VISIBLE);
                    iv_shangjia_star3.setVisibility(View.VISIBLE);
                    iv_shangjia_star4.setVisibility(View.VISIBLE);
                    iv_shangjia_star5.setVisibility(View.VISIBLE);
                }else {
                    Log.d("=====","bean.getScoring()="+bean.getScoring());
                    iv_shangjia_star1.setVisibility(View.GONE);
                    iv_shangjia_star2.setVisibility(View.GONE);
                    iv_shangjia_star3.setVisibility(View.GONE);
                    iv_shangjia_star4.setVisibility(View.GONE);
                    iv_shangjia_star5.setVisibility(View.GONE);

                }
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_shang_jia_evaluate.setLayoutManager(new LinearLayoutManager(mContext));
        rv_shang_jia_evaluate.addItemDecoration(getItemDivider());
        rv_shang_jia_evaluate.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getEvaluateNum();
        getData(1, false);
    }

    private void getEvaluateNum() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("merchant_id", merchantId);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getShangJiaEvaluateNum(map, new MyCallBack<ShangJiaEvaluateNumObj>(mContext) {
            @Override
            public void onSuccess(ShangJiaEvaluateNumObj obj) {
                tv_shangjia_evaluate_pingfen.setText(obj.getScoring()+"分");
                tv_shangjia_evaluate_all.setText("全部("+obj.getReputation_count()+")");
                tv_shangjia_evaluate_good.setText("好评("+obj.getGood_reputation()+")");
                tv_shangjia_evaluate_bad.setText("差评("+obj.getReview_reputation()+")");
            }
        });

    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String>map=new HashMap<String,String>();
        map.put("merchant_id",merchantId);
        map.put("type", type);
        map.put("page", page+"");
        map.put("pagesize", pageSize+"");
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getShangJiaEvaluateList(map, new MyCallBack<ShangJiaEvaluateListObj>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(ShangJiaEvaluateListObj obj) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getScoringList(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getScoringList(),true);
                }
            }
        });
    }

    @OnClick({R.id.tv_shangjia_evaluate_all, R.id.tv_shangjia_evaluate_good, R.id.tv_shangjia_evaluate_bad})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shangjia_evaluate_all:
                if(checkView(tv_shangjia_evaluate_all)){
                    showLoading();
                    selectData(0);
                }
                break;
            case R.id.tv_shangjia_evaluate_good:
                if(checkView(tv_shangjia_evaluate_good)){
                    showLoading();
                    selectData(1);
                }
                break;
            case R.id.tv_shangjia_evaluate_bad:
                if(checkView(tv_shangjia_evaluate_bad)){
                    showLoading();
                    selectData(2);
                }
                break;
        }
    }

    //0全部，1好评，2,差评
    private void selectData(int flag) {
        type=flag+"";
        getData(1,false);
    }

    private boolean checkView(MyTextView textView) {
        if(selectView!=textView){
            selectView.setBorderColor(ContextCompat.getColor(mContext, R.color.c_press));
            selectView.setTextColor(ContextCompat.getColor(mContext,R.color.gray_33));
            selectView.complete();

            selectView=textView;
            selectView.setBorderColor(ContextCompat.getColor(mContext,R.color.orange));
            selectView.setTextColor(ContextCompat.getColor(mContext,R.color.orange));
            selectView.complete();
            return true;
        }
        return false;
    }
}

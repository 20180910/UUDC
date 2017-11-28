package com.sk.uudc.module.near.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.uudc.GetSign;
import com.sk.uudc.MyApplication;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.activity.ShangJiaActivity;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.request.NearShangJiaBody;
import com.sk.uudc.module.near.network.response.NearListObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/15.
 */

public class NearFramentVP extends BaseFragment {
    @BindView(R.id.rv_near)
    RecyclerView rv_near;
    LoadMoreAdapter adapter;


    public static NearFramentVP newInstance(String typeId) {
        NearFramentVP newFragment = new NearFramentVP();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.typeId, typeId);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_near_vp;
    }

    @Override
    protected void initView() {
        adapter = new LoadMoreAdapter<NearListObj.MerchantListBean>(mContext, R.layout.item_near, pageSize) {
            public String listToString(List list) {
                if(isEmpty(list)){
                    return "";
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i)).append(",");
                }
                return sb.toString().substring(0, sb.toString().length() - 1);
            }

            @Override
            public void bindData(LoadMoreViewHolder holder, int position, NearListObj.MerchantListBean bean) {
                ImageView iv_near_icon = holder.getImageView(R.id.iv_near_icon);
                Glide.with(mContext).load(bean.getMerchant_avatar()).error(R.color.c_press).into(iv_near_icon);

                holder.setText(R.id.tv_near_name, bean.getMerchant_name())
                        .setText(R.id.tv_near_star_num, bean.getScoring() + "")
                        .setText(R.id.tv_near_price, "¥ " + bean.getMoney_people() + "/人")
                        .setText(R.id.tv_near_address, bean.getMerchant_address())
                        .setText(R.id.tv_near_type, bean.getCuisine())
                        .setText(R.id.tv_near_type2, listToString(bean.getLable()))
                        .setText(R.id.tv_near_huodong2, listObjToString(bean.getActivity()))
                        .setText(R.id.tv_near_distance, bean.getDistance());

                LinearLayout ll_near_list_huodong = (LinearLayout) holder.getView(R.id.ll_near_list_huodong);
                if (notEmpty(bean.getActivity())) {
                    ll_near_list_huodong.setVisibility(View.VISIBLE);
                } else {
                    ll_near_list_huodong.setVisibility(View.GONE);
                }
                if(bean.getScoring()>=5){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.VISIBLE);
                }else if(bean.getScoring()==4){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }else if(bean.getScoring()==3){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }else if(bean.getScoring()==2){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }else if(bean.getScoring()==1){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }else if(bean.getScoring()==0){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }

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
        adapter.setOnLoadMoreListener(this);
        rv_near.setLayoutManager(new LinearLayoutManager(mContext));
        rv_near.addItemDecoration(getItemDivider());
        rv_near.setAdapter(adapter);
    }

    @Override
    protected void initData() {

        showProgress();
        getData(1, false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", page + "");
        map.put("pagesize", pageSize + "");
        map.put("sign", GetSign.getSign(map));
        NearShangJiaBody body = new NearShangJiaBody();
        body.setType_id(getArguments().getString(Constant.typeId));
        body.setLat(MyApplication.getWeiDu(mContext));
        body.setLng(MyApplication.getJingDu(mContext));
        ApiRequest.getNearShangJiaList(map, body, new MyCallBack<NearListObj>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(NearListObj obj) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(obj.getMerchantList(), true);
                } else {
                    pageNum = 2;
                    adapter.setList(obj.getMerchantList(), true);
                }
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
    public String listObjToString(List<NearListObj.MerchantListBean.ActivityBean> list) {
        if(isEmpty(list)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append("满"+list.get(i).getFull_amount()+"减"+list.get(i).getSubtract_amount()).append(",");
            if(i%2!=0){
                sb.append("\n");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}

package com.sk.uudc.module.home.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.uudc.GetSign;
import com.sk.uudc.MyApplication;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.home.Constant;
import com.sk.uudc.module.home.network.ApiRequest;
import com.sk.uudc.module.home.network.request.SearchResultBody;
import com.sk.uudc.module.home.network.response.SearchResultObj;
import com.sk.uudc.module.near.activity.ShangJiaActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/27.
 */

public class SearchResultActivity extends BaseActivity {
    @BindView(R.id.rv_search_result)
    RecyclerView rv_search_result;
    //LoadMoreAdapter
    LoadMoreAdapter adapter;
    String search_term;

    @Override
    protected int getContentView() {
        setAppTitle("商家列表");
        return R.layout.act_search_result;
    }

    @Override
    protected void initView() {
        getValue();
        adapter=new LoadMoreAdapter<SearchResultObj.MerchantListBean>(mContext,R.layout.item_near_type,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, SearchResultObj.MerchantListBean bean) {
                ImageView iv_item_search_resout_type_icon = holder.getImageView(R.id.iv_item_near_type_icon);
                TextView tv_item_search_resout_type_name = holder.getTextView(R.id.tv_item_near_type_name);
                ImageView iv_item_search_resout_type_star1 = holder.getImageView(R.id.iv_item_near_type_star1);
                ImageView iv_item_search_resout_type_star2 = holder.getImageView(R.id.iv_item_near_type_star2);
                ImageView iv_item_search_resout_type_star3 = holder.getImageView(R.id.iv_item_near_type_star3);
                ImageView iv_item_search_resout_type_star4 = holder.getImageView(R.id.iv_item_near_type_star4);
                ImageView iv_item_search_resout_type_star5 = holder.getImageView(R.id.iv_item_near_type_star5);
                TextView tv_item_search_resout_type_star_num = holder.getTextView(R.id.tv_item_near_type_star_num);
                TextView tv_item_search_resout_type_price = holder.getTextView(R.id.tv_item_near_type_price);
                TextView tv_item_search_resout_type_address = holder.getTextView(R.id.tv_item_near_type_address);
                TextView tv_item_search_resout_type_type2 = holder.getTextView(R.id.tv_item_near_type_type2);
                TextView tv_item_search_resout_type_type = holder.getTextView(R.id.tv_item_near_type_type);
                TextView tv_item_search_resout_type_distance = holder.getTextView(R.id.tv_item_near_type_distance);
                TextView tv_item_search_resout_type_huodong2 = holder.getTextView(R.id.tv_item_near_type_huodong2);
                TextView tv_item_search_resout_type_huodong = holder.getTextView(R.id.tv_item_near_type_huodong);
                Glide.with(mContext).
                        load(bean.getMerchant_avatar()).
                        error(R.color.c_press).
                        into(iv_item_search_resout_type_icon);
                tv_item_search_resout_type_name.setText(bean.getMerchant_name());
                tv_item_search_resout_type_star_num.setText(bean.getScoring()+"");
                tv_item_search_resout_type_price.setText("¥"+bean.getMoney_people()+"/人");
                tv_item_search_resout_type_address.setText(bean.getMerchant_address());
                tv_item_search_resout_type_type.setText(bean.getCuisine());
                tv_item_search_resout_type_distance.setText(bean.getDistance());
                if (bean.getScoring()==0) {
                    iv_item_search_resout_type_star1.setVisibility(View.GONE);
                    iv_item_search_resout_type_star2.setVisibility(View.GONE);
                    iv_item_search_resout_type_star3.setVisibility(View.GONE);
                    iv_item_search_resout_type_star4.setVisibility(View.GONE);
                    iv_item_search_resout_type_star5.setVisibility(View.GONE);
                }else if (bean.getScoring()==1){
                    iv_item_search_resout_type_star1.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star2.setVisibility(View.GONE);
                    iv_item_search_resout_type_star3.setVisibility(View.GONE);
                    iv_item_search_resout_type_star4.setVisibility(View.GONE);
                    iv_item_search_resout_type_star5.setVisibility(View.GONE);
                }else if (bean.getScoring()==2){
                    iv_item_search_resout_type_star1.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star2.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star3.setVisibility(View.GONE);
                    iv_item_search_resout_type_star4.setVisibility(View.GONE);
                    iv_item_search_resout_type_star5.setVisibility(View.GONE);
                }else if (bean.getScoring()==3){
                    iv_item_search_resout_type_star1.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star2.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star3.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star4.setVisibility(View.GONE);
                    iv_item_search_resout_type_star5.setVisibility(View.GONE);
                }else if (bean.getScoring()==4){
                    iv_item_search_resout_type_star1.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star2.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star3.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star4.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star5.setVisibility(View.GONE);
                }else if (bean.getScoring()==5){
                    iv_item_search_resout_type_star1.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star2.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star3.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star4.setVisibility(View.VISIBLE);
                    iv_item_search_resout_type_star5.setVisibility(View.VISIBLE);
                }
                String type="";
                if (bean.getLable().size()==0) {
                    tv_item_search_resout_type_type2.setVisibility(View.GONE);
                }else {
                    tv_item_search_resout_type_type2.setVisibility(View.VISIBLE);
                    for (int i1 = 0; i1 < bean.getLable().size(); i1++) {
                        String type2=bean.getLable().get(i1);
                        type=type+","+type2;
                    }
                    tv_item_search_resout_type_type2.setText(type.substring(1,type.length()));
                }
                if (bean.getActivity().size()==0) {
                    tv_item_search_resout_type_huodong.setVisibility(View.GONE);
                    tv_item_search_resout_type_huodong2.setVisibility(View.GONE);
                }else {
                    tv_item_search_resout_type_huodong.setVisibility(View.VISIBLE);
                    tv_item_search_resout_type_huodong2.setVisibility(View.VISIBLE);
                    String man="满";
                    String jian="减";
                    String yuan="元";
                    String youhui="";
                    for (int i2 = 0; i2 < bean.getActivity().size(); i2++) {
                        String youhui2=man+bean.getActivity().get(i2).getFull_amount()+jian+bean.getActivity().get(i2).getSubtract_amount()+yuan;
                        youhui=youhui+","+youhui2;
                    }
                    tv_item_search_resout_type_huodong2.setText(youhui.substring(1,youhui.length()));
                }



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.merchant_id,bean.getMerchant_id()+"");
                        STActivity(intent,ShangJiaActivity.class);


                    }
                });
            }

        };
        adapter.setOnLoadMoreListener(this);
        rv_search_result.setLayoutManager(new LinearLayoutManager(mContext));
        rv_search_result.setNestedScrollingEnabled(false);
        rv_search_result.setAdapter(adapter);

    }

    private void getValue() {
        search_term=getIntent().getStringExtra(Constant.IParam.searchTerm);
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
        String user_id=getUserId();
        if (TextUtils.isEmpty(user_id)) {
            user_id="0";
        }

        map.put("pagesize",pageSize+"");
        map.put("page",page+"");
        map.put("sign", GetSign.getSign(map));
        SearchResultBody body=new SearchResultBody();
        body.setSearch_term(search_term);
        body.setLat(MyApplication.getWeiDu(mContext)+"");
        body.setLng(MyApplication.getJingDu(mContext)+"");
        body.setUser_id(user_id);
        ApiRequest.postSearchMerchant(map, body, new MyCallBack<SearchResultObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(SearchResultObj obj) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getMerchantList(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getMerchantList(),true);
                }


            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
}

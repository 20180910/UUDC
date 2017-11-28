package com.sk.uudc.module.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.YueMingxiObj;
import com.sk.uudc.tools.TabEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/7.
 */

public class BalanceDetailActivity extends BaseActivity {


    @BindView(R.id.ctl_balance_detail)
    CommonTabLayout ctl_balance_detail;
    @BindView(R.id.rv_balance_detail)
    RecyclerView rv_balance_detail;
    private String[] titles = {"全部", "收入", "支付"};
    private ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
    int type = 0;
    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("余额明细");
        return R.layout.act_balance_detail;
    }

    @Override
    protected void initView() {
        initTabLayout();
        adapter=new LoadMoreAdapter<YueMingxiObj>(mContext,R.layout.item_balance_detail,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, YueMingxiObj bean) {
                TextView tv_item_balance_detail_title = holder.getTextView(R.id.tv_item_balance_detail_title);
                TextView tv_item_balance_detail_time = holder.getTextView(R.id.tv_item_balance_detail_time);
                TextView tv_item_balance_detail_value = holder.getTextView(R.id.tv_item_balance_detail_value);
                tv_item_balance_detail_title.setText(bean.getRemark());
                tv_item_balance_detail_time.setText(bean.getAdd_time());
                tv_item_balance_detail_value.setText(bean.getValue());
            }
        };
        rv_balance_detail.setLayoutManager(new LinearLayoutManager(mContext));
        rv_balance_detail.setNestedScrollingEnabled(false);
        rv_balance_detail.setAdapter(adapter);


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
        map.put("type",type+"");
        map.put("pagesize",pageSize+"");
        map.put("page",page+"");
        map.put("sign", GetSign.getSign(map));
        //getMyBalance
        ApiRequest.getMyBalance(map, new MyCallBack<List<YueMingxiObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<YueMingxiObj> obj) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj,true);
                }else{
                    pageNum=2;
                    adapter.setList(obj,true);
                }

            }
        });

    }



    @Override
    protected void onViewClick(View v) {

    }
    private void initTabLayout() {
        for (int i = 0; i < titles.length; i++) {
            tabEntities.add(new TabEntity(titles[i], 0, 0));
        }
        ctl_balance_detail.setTabData(tabEntities);

        ctl_balance_detail.setCurrentTab(type);
        ctl_balance_detail.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Log.d("=====", "position===onTabSelect===" + position);
                type = position;
                initData();
            }

            @Override
            public void onTabReselect(int position) {
            }
        });


    }

}

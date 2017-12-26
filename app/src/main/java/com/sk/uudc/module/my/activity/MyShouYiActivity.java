package com.sk.uudc.module.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyTextView;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.MyShouyiObj;
import com.sk.uudc.module.my.network.response.ShouyiObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MyShouYiActivity extends BaseActivity {
    @BindView(R.id.rv_my_shouyi)
    RecyclerView rv_my_shouyi;
    @BindView(R.id.tv_my_shouyi)
    TextView tv_my_shouyi;
    @BindView(R.id.tv_my_shouyi_lishi)
    TextView tv_my_shouyi_lishi;
    @BindView(R.id.tv_my_shouyi_zuotian)
    TextView tv_my_shouyi_zuotian;
    @BindView(R.id.tv_my_shouyi_zhuanchuyue)
    MyTextView tv_my_shouyi_zhuanchuyue;
    LoadMoreAdapter adapter;
    double amount;

    @Override
    protected int getContentView() {
        setAppTitle("我的收益");
//        setAppTitleColor(this.getResources().getColor(R.color.gray_33));
        setTitleBackgroud(R.color.home_green);
        setBackIcon(R.drawable.app_back);
        hiddenBottomLine();

        return R.layout.act_my_shouyi;
    }

    @Override
    protected void initView() {
        adapter = new LoadMoreAdapter<MyShouyiObj.CommissiondetailBean>(mContext, R.layout.item_my_shou_yi, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, MyShouyiObj.CommissiondetailBean bean) {
                TextView tv_item_my_shouyi_title = holder.getTextView(R.id.tv_item_my_shouyi_title);
                TextView tv_item_my_shouyi_time = holder.getTextView(R.id.tv_item_my_shouyi_time);
                TextView tv_item_my_shouyi_value = holder.getTextView(R.id.tv_item_my_shouyi_value);
                tv_item_my_shouyi_title.setText(bean.getRemark());
                tv_item_my_shouyi_time.setText(bean.getAdd_time());
                tv_item_my_shouyi_value.setText(bean.getValue()+"");


            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_my_shouyi.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_shouyi.addItemDecoration(getItemDivider());
        rv_my_shouyi.setAdapter(adapter);

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
        map.put("pagesize",pageSize+"");
        map.put("page",page+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getMyShouyi(map, new MyCallBack<MyShouyiObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(MyShouyiObj obj) {


                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getCommissiondetail(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getCommissiondetail(),true);
                    amount=obj.getCommission();
                    tv_my_shouyi.setText(obj.getCommission()+"");
                    tv_my_shouyi_lishi.setText(obj.getHistory_commission()+"");
                    tv_my_shouyi_zuotian.setText(obj.getZuori_commission()+"");
                }


            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }

    @OnClick(R.id.tv_my_shouyi_zhuanchuyue)
    public void onClick() {
        if (amount==0) {
           showMsg("收益为0.0元不能转出到余额");
            return;
        }


        getCommissionWithdrawals();


    }

    private void getCommissionWithdrawals() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("amount",amount+"");
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getCommissionWithdrawals(map, new MyCallBack<ShouyiObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(ShouyiObj obj) {
                showMsg(obj.getMsg());
                showLoading();
                getData(1,false);
                SPUtils.setPrefString(mContext, Config.amount, obj.getAmount() + "");


            }
        });

    }
}

package com.sk.uudc.module.my.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.customview.MyTextView;
import com.sk.uudc.Config;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/6.
 */

public class MyBalanceActivity extends BaseActivity {
    @BindView(R.id.tv_my_balance_yue)
    TextView tv_my_balance_yue;
    @BindView(R.id.tv_my_balance_chongzhi)
    MyTextView tv_my_balance_chongzhi;
    @BindView(R.id.tv_my_balance_tixiang)
    MyTextView tv_my_balance_tixiang;
    @BindView(R.id.iv_my_balance_yinhangka)
    ImageView iv_my_balance_yinhangka;
    String yue;

    @Override
    protected int getContentView() {
        setAppTitle("我的余额");
        setAppRightTitle("余额明细");

        setAppTitleColor(this.getResources().getColor(R.color.gray_33));
        setTitleBackgroud(R.color.white);
        setAppRightTitleColor(ContextCompat.getColor(mContext, R.color.home_green));
        return R.layout.act_my_balance;
    }

    @Override
    protected void initView() {
        yue= SPUtils.getPrefString(mContext, Config.amount,"0.00");
        tv_my_balance_yue.setText("¥"+yue);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }

    @OnClick({R.id.tv_my_balance_chongzhi, R.id.tv_my_balance_tixiang, R.id.iv_my_balance_yinhangka,R.id.app_right_tv})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_my_balance_chongzhi:
                STActivity(ChongZhiActivity.class);
                break;
            case R.id.tv_my_balance_tixiang:
                STActivity(TiXianActivity.class);

                break;
            case R.id.iv_my_balance_yinhangka:
                Intent intent=new Intent();
                intent.putExtra("type","1");
                STActivity(intent,BankManageActivity.class);
                break;
            case R.id.app_right_tv:
                Intent BalanceDetail=new Intent();
                STActivity(BalanceDetail,BalanceDetailActivity.class);
                break;
        }
    }
}

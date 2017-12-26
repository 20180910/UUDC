package com.sk.uudc.module.my.activity;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.Constant;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.ChongzhiSuccessObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/7.
 */

public class ChongZhiSuccessActivity extends BaseActivity {
    @BindView(R.id.tv_chongzhi_success_money)
    TextView tv_chongzhi_success_money;
    @BindView(R.id.tv_chongzhi_success_type)
    TextView tv_chongzhi_success_type;
    @BindView(R.id.tv_chongzhi_success_money2)
    TextView tv_chongzhi_success_money2;
    String order_no;

    @Override
    protected int getContentView() {
        setAppTitle("充值");
        setAppRightTitle("完成");
        setAppRightTitleColor(ContextCompat.getColor(mContext, R.color.home_green));
        return R.layout.act_chong_zhi_success;
        //    intent.putExtra(Constant.IParam.order_no,orderNo);
    }

    @Override
    protected void initView() {
        getValue();

    }

    private void getValue() {
        order_no=getIntent().getStringExtra(Constant.IParam.order_no);
    }

    @Override
    protected void initData() {
        showProgress();
        getPayRecharge();

    }

    private void getPayRecharge() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_no",order_no);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getPayRecharge(map, new MyCallBack<ChongzhiSuccessObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(ChongzhiSuccessObj obj) {
                tv_chongzhi_success_money.setText(obj.getMoney()+"元");
                tv_chongzhi_success_type.setText(obj.getContent());
                tv_chongzhi_success_money2.setText("+"+obj.getMoney());

                SPUtils.setPrefString(mContext, Config.amount, obj.getBalance());

            }
        });
    }


    @OnClick({R.id.app_right_tv})
    @Override
    protected void onViewClick(View v) {
        switch (v.getId()) {

            case R.id.app_right_tv:
               finish();
                break;
        }

    }
}

package com.sk.uudc.module.my.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.github.customview.MyLinearLayout;
import com.github.customview.MyTextView;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.AccountDefaultObj;
import com.sk.uudc.module.my.network.response.WithdrawalsObj;
import com.sk.uudc.tools.AndroidUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/7.
 */

public class TiXianActivity extends BaseActivity {
    @BindView(R.id.iv_tixian_icon)
    ImageView iv_tixian_icon;
    @BindView(R.id.tv_tixian_name)
    TextView tv_tixian_name;
    @BindView(R.id.tv_tixian_type)
    TextView tv_tixian_type;
    @BindView(R.id.tv_tixian_weihao)
    TextView tv_tixian_weihao;
    @BindView(R.id.ll_tiixan)
    MyLinearLayout ll_tiixan;
    @BindView(R.id.et_tixian_jine)
    EditText et_tixian_jine;
    @BindView(R.id.tv_tixian_yue)
    TextView tv_tixian_yue;
    @BindView(R.id.tv_tixian_liji)
    MyTextView tv_tixian_liji;
    @BindView(R.id.ll_tixian_no)
    MyLinearLayout ll_tixian_no;
    String yueStr, amountStr, account_id ,codeId;
    double amount, yue;


    @Override
    protected int getContentView() {
        setAppTitle("提现");
        return R.layout.act_ti_xian;
    }

    @Override
    protected void initView() {
        yueStr = SPUtils.getPrefString(mContext, Config.amount, "0.00");
        tv_tixian_yue.setText(yueStr);
        yue = AndroidUtils.round(Double.parseDouble(yueStr));
    }


    @Override
    protected void myReStart() {
        super.myReStart();
        showLoading();
        getAccountDefault();
    }

    @Override
    protected void initData() {
        showProgress();
        getAccountDefault();

    }

    private void getAccountDefault() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAccountDefault(map, new MyCallBack<AccountDefaultObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(AccountDefaultObj obj) {
                codeId=obj.getId();
                if (obj.getId().equals("0")) {
                    ll_tixian_no.setVisibility(View.VISIBLE);
                    ll_tiixan.setVisibility(View.GONE);
                    account_id = "";
                } else {
                    ll_tixian_no.setVisibility(View.GONE);
                    ll_tiixan.setVisibility(View.VISIBLE);
                    Glide.with(mContext).
                            load(obj.getBank_image()).
                            error(R.color.c_press).
                            into(iv_tixian_icon);
                    tv_tixian_name.setText(obj.getBank_name());
                    tv_tixian_type.setText(obj.getCard_type());
                    tv_tixian_weihao.setText(obj.getBank_card());
                    account_id = obj.getId();

                }
            }
        });
    }


    @OnClick({R.id.ll_tiixan, R.id.tv_tixian_liji, R.id.ll_tixian_no})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tiixan:
                Intent intent = new Intent();
                intent.putExtra("type", "2");
                STActivity(intent, BankManageActivity.class);
                break;
            case R.id.tv_tixian_liji:
                amountStr = getSStr(et_tixian_jine);
                if (codeId.equals("0")) {
                    showMsg("请先选择银行卡！");
                    return;
                }
                if (yue == 0) {
                    showMsg("余额为0.00元，不能提现");
                    return;
                }
                if (TextUtils.isEmpty(amountStr)) {
                    showMsg("请输入提现金额！");
                    return;
                }

                amount = AndroidUtils.round(Double.parseDouble(amountStr));
                if (amount > yue) {
                    showMsg("输入提现金额有误！");
                    return;
                }

                getWithdrawals();
                break;
            case R.id.ll_tixian_no:
                Intent tixian_no = new Intent();
                tixian_no.putExtra("type", "2");
                STActivity(tixian_no, BankManageActivity.class);
                break;
        }
    }

    private void getWithdrawals() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("account_id", account_id);
        map.put("amount", amount + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getWithdrawals(map, new MyCallBack<WithdrawalsObj>(mContext) {
            @Override
            public void onSuccess(WithdrawalsObj obj) {
                showMsg(obj.getMsg());
                SPUtils.setPrefString(mContext, Config.amount, obj.getAmount() + "");
                finish();

            }


        });

    }
}

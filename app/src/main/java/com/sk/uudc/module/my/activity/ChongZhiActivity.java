package com.sk.uudc.module.my.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.alipay.sdk.app.PayTask;
import com.github.androidtools.SPUtils;
import com.github.baseclass.rx.IOCallBack;
import com.github.customview.MyRadioButton;
import com.github.customview.MyTextView;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.Constant;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.ChongzhiCreateOrderObj;
import com.sk.uudc.tools.alipay.AliPay;
import com.sk.uudc.tools.alipay.OrderInfoUtil2_0;
import com.sk.uudc.tools.alipay.PayResult;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/11/7.
 */

public class ChongZhiActivity extends BaseActivity {
    @BindView(R.id.rb_pay_zhifubao)
    MyRadioButton rb_pay_zhifubao;
    @BindView(R.id.rb_pay_weixin)
    MyRadioButton rb_pay_weixin;
    @BindView(R.id.rg_select_pay)
    RadioGroup rg_select_pay;
    @BindView(R.id.tv_changzhi)
    MyTextView tv_changzhi;
    @BindView(R.id.et_changzhi)
    EditText et_changzhi;
    String moneyStr, order_no, payment_url;
    double money;

    int payment_type = 1;


    @Override
    protected int getContentView() {
        setAppTitle("充值");
        return R.layout.act_chong_zhi;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }

    @OnClick(R.id.tv_changzhi)
    public void onClick() {
        moneyStr = getSStr(et_changzhi);
        if (TextUtils.isEmpty(moneyStr)) {
            showMsg("请输入充值金额！");
            return;
        }

        showProgress();
        if (rb_pay_zhifubao.isChecked()) {
            payment_type = 1;
            getCreateOrder();


        } else {
            showMsg("开发中");
//            payment_type=2;
//            getCreateOrder();


        }


    }

    private void getCreateOrder() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("money", moneyStr);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getCreateOrder(map, new MyCallBack<ChongzhiCreateOrderObj>(mContext) {
            @Override
            public void onSuccess(ChongzhiCreateOrderObj obj) {
                order_no = obj.getOrder_no();
                money = obj.getMoney();
                if (payment_type == 1) {
                    zhiFuBaoPay(order_no, money);
                } else {

                }
            }
        });
    }

    private void zhiFuBaoPay(String orderNo, double totalPrice) {
        showLoading();
        double total = totalPrice;
        AliPay bean = new AliPay();
        bean.setTotal_amount(total);
        bean.setOut_trade_no(orderNo);
        bean.setSubject(orderNo + "余额充值");
        bean.setBody("优优点餐余额充值");
        String notifyUrl = SPUtils.getPrefString(mContext, Config.payType_ZFB, null);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(bean, notifyUrl);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, Config.zhifubao_rsa2, true);
        final String orderInfo = orderParam + "&" + sign;
        RXStart(new IOCallBack<Map>() {
            @Override
            public void call(Subscriber<? super Map> subscriber) {
                PayTask payTask = new PayTask(mContext);
                Map<String, String> result = payTask.payV2(orderInfo, true);
                Log.i("msp=====", result.toString());
                subscriber.onNext(result);
                subscriber.onCompleted();
            }

            @Override
            public void onMyNext(Map map) {
                dismissLoading();
                PayResult payResult = new PayResult(map);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Intent intent = new Intent();
                    intent.putExtra(Constant.IParam.order_no, orderNo);
                    STActivity(intent, ChongZhiSuccessActivity.class);
                    finish();


                } else if (TextUtils.equals(resultStatus, "6001")) {
                    showMsg("支付取消");
                } else {
                    showMsg("支付失败");
                }
            }
        });
    }

}

package com.sk.uudc.module.near.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.github.androidtools.SPUtils;
import com.github.baseclass.rx.IOCallBack;
import com.github.customview.MyRadioButton;
import com.github.customview.MyTextView;
import com.sk.uudc.BuildConfig;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.response.CommitOrderResultObj;
import com.sk.uudc.module.near.network.response.PaySuccessObj;
import com.sk.uudc.tools.alipay.AliPay;
import com.sk.uudc.tools.alipay.OrderInfoUtil2_0;
import com.sk.uudc.tools.alipay.PayResult;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/11/16.
 */

public class OrderPayActivity extends BaseActivity {
    @BindView(R.id.tvOrderPayPrice)
    TextView tvOrderPayPrice;
    @BindView(R.id.rbOrderPayYue)
    MyRadioButton rbOrderPayYue;
    @BindView(R.id.rbOrderPayZhifubao)
    MyRadioButton rbOrderPayZhifubao;
    @BindView(R.id.rbOrderPayWeixin)
    MyRadioButton rbOrderPayWeixin;
    @BindView(R.id.rgOrderPay)
    RadioGroup rgOrderPay;
    @BindView(R.id.tvOrderPaySure)
    MyTextView tvOrderPaySure;
    private CommitOrderResultObj payInfo;

    @Override
    protected int getContentView() {
        setAppTitle("支付");
        setTitleBackgroud(R.color.white);
        return R.layout.act_order_pay;
    }

    @Override
    protected void initView() {
        payInfo = (CommitOrderResultObj) getIntent().getSerializableExtra(Constant.IParam.orderPayInfo);
        tvOrderPayPrice.setText(payInfo.getCombined()+"元");
    }

    @Override
    protected void initData() {

    }
    @OnClick({R.id.tvOrderPaySure})
    protected void onViewClick(View v) {
        if(rbOrderPayYue.isChecked()){//余额支付
            yuePay();
        }else if(rbOrderPayZhifubao.isChecked()){//支付宝支付
            zhiFuBaoPay(payInfo.getOrder_no(),payInfo.getCombined());
        }else if(rbOrderPayWeixin.isChecked()){//微信支付
            showMsg("开发中");
        }
    }
    private void zhiFuBaoPay(String orderNo,double totalPrice) {
        showLoading();
        double total;
        if(BuildConfig.DEBUG){
            total=0.01;
        }else{
            total =  totalPrice;
        }
        AliPay bean = new AliPay();
        bean.setTotal_amount(total );
        bean.setOut_trade_no(orderNo);
        bean.setSubject(orderNo + "订单支付");
        bean.setBody("优优点餐订单支付");
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
//                    paySuccess(null);//////////////////////////////////////////////////////////////
                    Intent intent=new Intent();
                    intent.putExtra(Constant.IParam.order_id,payInfo.getOrder_id()+"");
                    STActivity(intent,YudingSuccessActivity.class);
                    finish();
                } else if (TextUtils.equals(resultStatus, "6001")) {
                    showMsg("支付取消");
                } else {
                    showMsg("支付失败");
                }
            }
        });
    }
    private void yuePay() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_id",payInfo.getOrder_id()+"");
        map.put("order_no", payInfo.getOrder_no()+"");
        map.put("amount", payInfo.getCombined()+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.yuePay(map, new MyCallBack<PaySuccessObj>(mContext) {
            @Override
            public void onSuccess(PaySuccessObj obj) {
                paySuccess(obj);
            }
        });
    }

    private void paySuccess(PaySuccessObj obj) {
        Intent intent=new Intent();
        intent.putExtra(Constant.IParam.order_id,payInfo.getOrder_id()+"");
        STActivity(intent,YudingSuccessActivity.class);
        finish();
    }
}

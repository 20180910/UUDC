package com.sk.uudc.module.near.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.rx.IOCallBack;
import com.github.baseclass.rx.RxBus;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyRadioButton;
import com.github.customview.MyTextView;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.broadcast.WXPayBro;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.request.CommitOrderBody;
import com.sk.uudc.module.near.network.request.ShowOrderBody;
import com.sk.uudc.module.near.network.response.CommitOrderResultObj;
import com.sk.uudc.module.near.network.response.PaySuccessObj;
import com.sk.uudc.module.near.network.response.TiJiaoOrderObj;
import com.sk.uudc.module.order.event.OrdersEvent;
import com.sk.uudc.tools.AndroidUtils;
import com.sk.uudc.tools.alipay.AliPay;
import com.sk.uudc.tools.alipay.OrderInfoUtil2_0;
import com.sk.uudc.tools.alipay.PayResult;
import com.sk.uudc.wxapi.OrderBean;
import com.sk.uudc.wxapi.WXPay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @BindView(R.id.ll_pay_nojiacai)
    LinearLayout ll_pay_nojiacai;
    @BindView(R.id.ll_pay_jiacai)
    LinearLayout ll_pay_jiacai;
    @BindView(R.id.iv_place_order)
    ImageView iv_place_order;
    @BindView(R.id.tv_place_order_name)
    TextView tv_place_order_name;
    @BindView(R.id.ll_place_order_shangdian)
    LinearLayout ll_place_order_shangdian;
    @BindView(R.id.rv_price_order)
    RecyclerView rv_price_order;
    @BindView(R.id.tv_price_order_youhui)
    TextView tv_price_order_youhui;
    @BindView(R.id.tv_price_order_dingdan_price)
    TextView tv_price_order_dingdan_price;
    @BindView(R.id.tv_price_order_daizhifu_price)
    TextView tv_price_order_daizhifu_price;
    private CommitOrderResultObj payInfo;
    private ShowOrderBody orderBody;

    BaseRecyclerAdapter adapter;

    private String orderNo;
    private double totalPrice;
    private String orderId;

    WXPayBro wxPayBro;

    @Override
    protected int getContentView() {
        setAppTitle("支付");
        setTitleBackgroud(R.color.white);
        return R.layout.act_order_pay;
    }

    @Override
    protected void initView() {
        wxPayBro=new WXPayBro(new WXPayBro.WXPayInter() {
            @Override
            public void payResult(boolean isJiaCai) {
                if(isJiaCai){
                    MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                    mDialog.setMessage("加菜成功");
                    mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    mDialog.setDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
                    mDialog.create().show();
                }else{
                    finish();
                }
            }
        });
        LocalBroadcastManager.getInstance(mContext).registerReceiver(wxPayBro,new IntentFilter(Config.WXPAY));
        orderBody = (ShowOrderBody) getIntent().getSerializableExtra(Constant.IParam.jiaCaiBody);
        if (orderBody == null) {
            ll_pay_nojiacai.setVisibility(View.VISIBLE);
            ll_pay_jiacai.setVisibility(View.GONE);
            payInfo = (CommitOrderResultObj) getIntent().getSerializableExtra(Constant.IParam.orderPayInfo);
            tvOrderPayPrice.setText(payInfo.getCombined() + "元");

              orderId = payInfo.getOrder_id()+"";
            orderNo=payInfo.getOrder_no();
            totalPrice =payInfo.getCombined();
        } else {//加菜
            ll_pay_nojiacai.setVisibility(View.GONE);
            ll_pay_jiacai.setVisibility(View.VISIBLE);


            adapter = new BaseRecyclerAdapter<TiJiaoOrderObj.GoodsListBean>(mContext, R.layout.item_pay_cai) {
                @Override
                public void bindData(RecyclerViewHolder holder, int i, TiJiaoOrderObj.GoodsListBean bean) {
                    holder.setText(R.id.tvItemPayName,bean.getGoods_name())
                            .setText(R.id.tvItemPayNum,"X"+bean.getNumber())
                            .setText(R.id.tvItemPayPrice,"¥"+bean.getGoods_price());
                }
            };
            rv_price_order.setLayoutManager(new LinearLayoutManager(mContext));
            rv_price_order.setNestedScrollingEnabled(false);
            rv_price_order.setAdapter(adapter);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(wxPayBro);
    }

    @Override
    protected void initData() {
        if (orderBody != null) {//加菜
            showProgress();
            getData(1, false);
        }
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", GetSign.getSign(map));
        CommitOrderBody body = new CommitOrderBody();
        List<TiJiaoOrderObj.GoodsListBean> orderList = new ArrayList<>();
        for (int i = 0; i < orderBody.getShowOrder().size(); i++) {
            TiJiaoOrderObj.GoodsListBean bean = new TiJiaoOrderObj.GoodsListBean();
            bean.setGoods_id(orderBody.getShowOrder().get(i).getGoods_id());
            bean.setNumber(orderBody.getShowOrder().get(i).getNumber());
            orderList.add(bean);
        }
        body.setShowOrder(orderList);
        body.setOrder_id(orderBody.getOrder_id());
        body.setMerchant_id(orderBody.getMerchant_id() + "");
        ApiRequest.commitJiaCaiOrder(map, body, new MyCallBack<TiJiaoOrderObj>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(TiJiaoOrderObj obj) {
                Glide.with(mContext).load(obj.getMerchant_avatar()).error(R.color.c_press).into(iv_place_order);
                tv_place_order_name.setText(obj.getMerchant_name());
                tv_price_order_youhui.setText("-¥" + obj.getMerchants_preferential());
                tv_price_order_dingdan_price.setText("订单¥" + obj.getTo_pay());
                tv_price_order_daizhifu_price.setText("待支付¥" + obj.getTo_pay());
                adapter.setList(obj.getGoods_list(), true);

                orderNo=obj.getOrder_no();
                totalPrice=obj.getTo_pay();
                orderId=obj.getOrder_id();
            }
        });
    }

    @OnClick({R.id.tvOrderPaySure})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tvOrderPaySure:
                if (rbOrderPayYue.isChecked()) {//余额支付
                    yuePay();
                } else if (rbOrderPayZhifubao.isChecked()) {//支付宝支付
                    zhiFuBaoPay( );
                } else if (rbOrderPayWeixin.isChecked()) {//微信支付
                    SPUtils.setPrefString(mContext,Constant.IParam.order_id,orderId);
                    SPUtils.setPrefString(mContext,Constant.IParam.orderNo,orderNo);
                    OrderBean orderBean =new OrderBean();
                    orderBean.body="优优点餐订单";
                    orderBean.nonceStr=getRnd();
                    orderBean.out_trade_no=orderNo;
                    orderBean.totalFee= AndroidUtils.mul(totalPrice,100);
                    orderBean.IP= AndroidUtils.getIP(mContext);
                    weiXinPay(orderBean);
                }
            break;
        }

    }
    private void weiXinPay(OrderBean orderBean) {
        if(orderBody==null){
            SPUtils.setPrefBoolean(mContext, Config.jiaCai, false);
        }else{//加菜
            SPUtils.setPrefBoolean(mContext, Config.jiaCai, true);
        }
        new WXPay(mContext).pay(orderBean);
    }
    private void zhiFuBaoPay( ) {
        showLoading();
        double total = totalPrice;
        AliPay bean = new AliPay();
        bean.setTotal_amount(total);
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

                    RxBus.getInstance().post(new OrdersEvent(Config.refresh));
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Intent intent = new Intent();
                    intent.putExtra(Constant.IParam.order_id, orderId + "");
                    intent.putExtra(Constant.IParam.orderNo, orderNo + "");
                    payResult(intent);
                } else if (TextUtils.equals(resultStatus, "6001")) {
                    showMsg("支付取消");
                } else {
                    showMsg(payResult.getMemo());
                }
            }
        });
    }
    public void payResult(Intent intent){
        if(orderBody==null){
            STActivity(intent, YudingSuccessActivity.class);
            finish();
        }else{//加菜
            MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
            mDialog.setMessage("加菜成功");
            mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    setResult(RESULT_OK);
                    finish();
                }
            });
            mDialog.create().show();
        }
    }
    private void yuePay() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("order_id",orderId+ "");
        map.put("order_no", orderNo + "");
        map.put("amount", totalPrice + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.yuePay(map, new MyCallBack<PaySuccessObj>(mContext) {
            @Override
            public void onSuccess(PaySuccessObj obj) {

                paySuccess(obj);
                RxBus.getInstance().post(new OrdersEvent(Config.refresh));
            }
        });
    }

    private void paySuccess(PaySuccessObj obj) {
        Intent intent = new Intent();
        intent.putExtra(Constant.IParam.order_id,orderId+ "");
        intent.putExtra(Constant.IParam.orderNo,orderNo+ "");
        payResult(intent);
    }
}

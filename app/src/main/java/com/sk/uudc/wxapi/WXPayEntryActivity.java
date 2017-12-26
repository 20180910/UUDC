package com.sk.uudc.wxapi;


import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.github.androidtools.SPUtils;
import com.github.androidtools.ToastUtils;
import com.github.baseclass.rx.RxBus;
import com.sk.uudc.Config;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.activity.YudingSuccessActivity;
import com.sk.uudc.module.order.event.OrdersEvent;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    private Intent intent;


    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected void initView() {
        api = WXAPIFactory.createWXAPI(this, Config.weixing_id);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void initData() {

    }

//    @OnClick({R.id.tv_pay_lookorder, R.id.tv_pay_back})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            /*case R.id.tv_pay_lookorder:
                finish();
                break;
            case R.id.tv_pay_back:
                intent = new Intent(Config.backHome);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                STActivity(intent,MainActivity.class);
                break;*/
        }
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Log.e("tag", "支付结果:" + resp.errCode);
            boolean isChongZhi = SPUtils.getPrefBoolean(mContext, Config.accountChongZhi, false);
            if(isChongZhi){
                SPUtils.removeKey(mContext, Config.accountChongZhi);
                if (resp.errCode == 0) {// 支付成功

                } else if (resp.errCode == -2) {
                    ToastUtils.showToast(this, "充值已取消");
                } else if (resp.errCode == -1) {
                    ToastUtils.showToast(this, "充值失败");
                }
            }else{
                String orderId = SPUtils.getPrefString(mContext, Constant.IParam.order_id,null);
                String orderNo = SPUtils.getPrefString(mContext, Constant.IParam.orderNo, null);
                RxBus.getInstance().post(new OrdersEvent(Config.refresh));
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                Intent intent = new Intent();
                intent.putExtra(Constant.IParam.order_id, orderId + "");
                intent.putExtra(Constant.IParam.orderNo, orderNo + "");
                payResult(intent);
            }
        }
        finish();
    }

    public void payResult(Intent intent){
        boolean jiaCai = SPUtils.getPrefBoolean(mContext, Config.jiaCai, false);
        if(jiaCai){
            Intent payIntent = new Intent(Config.WXPAY);
            payIntent.putExtra(Config.isJiaCai,true);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(payIntent);
        }else{//加菜
            STActivity(intent, YudingSuccessActivity.class);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Config.WXPAY));
        }
    }
}


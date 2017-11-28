package com.sk.uudc.module.order.network;

import com.github.retrofitutil.NoNetworkException;
import com.sk.uudc.Config;
import com.sk.uudc.base.BaseApiRequest;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.order.network.request.BusinessEvaluationBody;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest extends BaseApiRequest {

    /*public static Observable getRegisterXieYi(String rnd, String sign){
        return getCommonClient(com.sk.yangyu.module.home.network.IRequest.class).getPayNotifyUrl(rnd,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }*/

    public static void tuanGouSureOrder(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(com.sk.uudc.network.IRequest.class).tuanGouSureOrder(map).enqueue(callBack);
    }
    //订单
    public static void getOrder(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getOrder(map).enqueue(callBack);
    }

    //我的订单
    public static void getMyOrder(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getMyOrder(map).enqueue(callBack);
    }

    //订单详情
    public static void getOrderDetail(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getOrderDetail(map).enqueue(callBack);
    }

    //取消订单原因
    public static void getCancelReason(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getCancelReason(map).enqueue(callBack);
    }

    //提交-取消订单
    public static void getCancelOrder(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getCancelOrder(map).enqueue(callBack);
    }
    //发表商家评价
    public static void postPublishComment(Map map , BusinessEvaluationBody body,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).postPublishComment(map,body).enqueue(callBack);
    }
}

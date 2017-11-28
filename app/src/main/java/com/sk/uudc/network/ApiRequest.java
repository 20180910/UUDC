package com.sk.uudc.network;

import com.github.retrofitutil.NoNetworkException;
import com.sk.uudc.Config;
import com.sk.uudc.base.BaseApiRequest;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.request.UploadImgBody;

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
        getGeneralClient(IRequest.class).tuanGouSureOrder(map).enqueue(callBack);
    }

    public static void getSMSCode(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getSMSCode(map).enqueue(callBack);
    }


    //base64图片上传
    public static void uploadImg(Map map, UploadImgBody body, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).uploadImg(map, body).enqueue(callBack);
    }

    public static void paymentURL(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).paymentURL(map).enqueue(callBack);
    }

    //获取省份
    public static void getProvince(Map map,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getProvince(map).enqueue(callBack);
    }

    //获取城市
    public static void getCity(Map map,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getCity(map).enqueue(callBack);
    }

    //获取区/县
    public static void getArea(Map map,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getArea(map).enqueue(callBack);
    }

    //申请合伙人
    public static void getApplyForPartner(Map map,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getApplyForPartner(map).enqueue(callBack);
    }

    //获取支付信息
    public static void PayInfo(Map map,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).PayInfo(map).enqueue(callBack);
    }

}

package com.sk.uudc.module.my.network;

import com.github.retrofitutil.NoNetworkException;
import com.sk.uudc.Config;
import com.sk.uudc.base.BaseApiRequest;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.request.AddAccountBody;
import com.sk.uudc.module.my.network.request.DelMyCollectionBody;
import com.sk.uudc.module.my.network.request.EditUserInfoBody;
import com.sk.uudc.module.my.network.request.RegisterBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest extends BaseApiRequest {

    /*public static Observable getRegisterXieYi(String rnd, String sign){
        return getCommonClient(com.sk.yangyu.module.home.network.IRequest.class).getPayNotifyUrl(rnd,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }*/

    public static void tuanGouSureOrder(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(com.sk.uudc.network.IRequest.class).tuanGouSureOrder(map).enqueue(callBack);
    }


    //注册
    public static void register(Map map, RegisterBody body, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).register(map, body).enqueue(callBack);
    }
    //登录
    public static void userLogin(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).userLogin(map).enqueue(callBack);
    }

    //重置密码(忘记密码)
    public static void forgetPWD(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).forgetPWD(map).enqueue(callBack);
    }

    //获取用户资料
    public static void getUserInfo(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getUserInfo(map).enqueue(callBack);
    }



    //单独修改用户头像
    public static void updateUserImg(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).updateUserImg(map).enqueue(callBack);
    }

    //修改信息（昵称，姓名，生日，性别，头像）
    public static void editUserInfo(Map map, EditUserInfoBody body, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).editUserInfo(map, body).enqueue(callBack);
    }
    //修改密码
    public static void setNewPassword(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).setNewPassword(map).enqueue(callBack);
    }

    //我的消息列表
    public static void getNewsList(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getNewsList(map).enqueue(callBack);
    }

    //我的消息详情
    public static void getNewsDetail(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getNewsDetail(map).enqueue(callBack);
    }

    //我的分销码
    public static void getMyFenxiao(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getMyFenxiao(map).enqueue(callBack);
    }

    //是否接受消息推送设置
    public static void getMessageSink(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getMessageSink(map).enqueue(callBack);
    }

    //我的收藏
    public static void getMyCollection(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getMyCollection(map).enqueue(callBack);
    }
    //删除我的收藏
    public static void delMyCollection(Map map, List<DelMyCollectionBody> body, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).delMyCollection(map, body).enqueue(callBack);
    }
    //关于平台信息
    public static void getAboutPlatform(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getAboutPlatform(map).enqueue(callBack);
    }

    //我的收益
    public static void getMyShouyi(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getMyShouyi(map).enqueue(callBack);
    }

    //我要合作页面
    public static void getComperation(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getComperation(map).enqueue(callBack);
    }

    //提现选择账户列表
    public static void getAccount(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getAccount(map).enqueue(callBack);
    }
    //绑定 银行卡
    public static void postAddAccount(Map map, AddAccountBody body, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).postAddAccount(map,body).enqueue(callBack);
    }
    public static void editAccount(Map map, AddAccountBody body, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).editAccount(map,body).enqueue(callBack);
    }

    //获取默认账户
    public static void getAccountDefault(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getAccountDefault(map).enqueue(callBack);
    }

    //设置默认-提现账户
    public static void getEditDefault(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getEditDefault(map).enqueue(callBack);
    }

    //账户余额-提现申请
    public static void getWithdrawals(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getWithdrawals(map).enqueue(callBack);
    }

    //我的评价
    public static void getMyAppraise(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getMyAppraise(map).enqueue(callBack);
    }

    //待评价列表
    public static void getDaiPingJia(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getDaiPingJia(map).enqueue(callBack);
    }
    //账户充值-生成订单
    public static void getCreateOrder(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getCreateOrder(map).enqueue(callBack);
    }

    //账户余额-明细
    public static void getMyBalance(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getMyBalance(map).enqueue(callBack);
    }

    //删除-我的账户(银行卡)列表
    public static void getDelAccount(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getDelAccount(map).enqueue(callBack);
    }
    //账户充值结束界面数据
    public static void getPayRecharge(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getPayRecharge(map).enqueue(callBack);
    }

    //收益-转入余额
    public static void getCommissionWithdrawals(Map map,  MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(IRequest.class).getCommissionWithdrawals(map).enqueue(callBack);
    }


}

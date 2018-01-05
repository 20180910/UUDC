package com.sk.uudc.module.home.network;

import com.github.retrofitutil.NoNetworkException;
import com.sk.uudc.Config;
import com.sk.uudc.base.BaseApiRequest;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.home.network.request.HomeRoastingChartBody;
import com.sk.uudc.module.home.network.request.HomeTypeMerchantListBody;
import com.sk.uudc.module.home.network.request.SearchResultBody;

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

    //首页轮播图信息
    public static void postRoastingChart(Map map, HomeRoastingChartBody body, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).postRoastingChart(map, body).enqueue(callBack);
    }

    //首页类别集合信息
    public static void getTypeAssemblage(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getTypeAssemblage(map).enqueue(callBack);
    }

    //首页中部图片信息
    public static void getHomePageImage(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getHomePageImage(map).enqueue(callBack);
    }

    //首页每日精选
    public static void getDailybest(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getDailybest(map).enqueue(callBack);
    }

    //根据城市名获取ID
    public static void getCityId(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getCityId(map).enqueue(callBack);
    }

    //获取全部区/县商业圈
    public static void getAreaBusinessCircle(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getAreaBusinessCircle(map).enqueue(callBack);
    }
    //商家列表(分类)
    public static void postMerchantList(Map map, HomeTypeMerchantListBody body, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).postMerchantList(map,body).enqueue(callBack);
    }

    //首页公告信息
    public static void getAnnouncement(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getAnnouncement(map).enqueue(callBack);
    }

    //获取首页右上角未读消息状态(红点)
    public static void getUnreadNews(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getUnreadNews(map).enqueue(callBack);
    }

    //热门搜索词、历史搜索词
    public static void getHottestSearch(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getHottestSearch(map).enqueue(callBack);
    }

    //删除历史搜索词
    public static void getDelRecentlySearch(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getDelRecentlySearch(map).enqueue(callBack);
    }
    //搜索商家
    public static void postSearchMerchant(Map map, SearchResultBody body, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).postSearchMerchant(map,body).enqueue(callBack);
    }

    //猜你喜欢
    public static void getGuessYouLike(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getGuessYouLike(map).enqueue(callBack);
    }
    //获取公告
    public static void getGongGao(Map map,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return; }
        getGeneralClient(com.sk.uudc.network.IRequest.class).getGongGao(map).enqueue(callBack);
    }
}

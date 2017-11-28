package com.sk.uudc.module.near.network;

import com.sk.uudc.base.BaseBody;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.ResponseObj;
import com.sk.uudc.module.near.network.request.CommitOrderBody;
import com.sk.uudc.module.near.network.request.NearShangJiaBody;
import com.sk.uudc.module.near.network.request.ShowOrderBody;
import com.sk.uudc.module.near.network.request.YuYueBody;
import com.sk.uudc.module.near.network.response.CommitOrderResultObj;
import com.sk.uudc.module.near.network.response.NearListObj;
import com.sk.uudc.module.near.network.response.NearShangJiaObj;
import com.sk.uudc.module.near.network.response.PaySuccessObj;
import com.sk.uudc.module.near.network.response.ShangJiaEvaluateListObj;
import com.sk.uudc.module.near.network.response.ShangJiaEvaluateNumObj;
import com.sk.uudc.module.near.network.response.ShangJiaInfoObj;
import com.sk.uudc.module.near.network.response.ShangJiaObj;
import com.sk.uudc.module.near.network.response.ShangJiaShangPingObj;
import com.sk.uudc.module.near.network.response.TiJiaoOrderObj;
import com.sk.uudc.module.near.network.response.YuYueTimeObj;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    @GET("api/HomePage/GetProductGroupOrderShow")
    Call<ResponseObj<BaseObj>> tuanGouSureOrder(@QueryMap Map<String, String> map);

    //商家列表类别
    @GET("api/Information/GetTypeAssemblage")
    Call<ResponseObj<NearShangJiaObj>> getNearShangJiaType(@QueryMap Map<String, String> map);


    //商家列表
    @POST("api/MerchantCenter/PostVicinityMerchantList")
    Call<ResponseObj<NearListObj>> getNearShangJiaList(@QueryMap Map<String, String> map, @Body NearShangJiaBody body);


    //商家详情
    @POST("api/MerchantCenter/PostMerchantDetail")
    Call<ResponseObj<ShangJiaObj>> getShangJiaDetail(@QueryMap Map<String, String> map, @Body BaseBody body);

    //商家信息
    @GET("api/MerchantCenter/GetMerchantInformation")
    Call<ResponseObj<ShangJiaInfoObj>> getShangJiaInfo(@QueryMap Map<String, String> map );

    //商家评价数量
    @GET("api/MerchantCenter/GetScoringNum")
    Call<ResponseObj<ShangJiaEvaluateNumObj>> getShangJiaEvaluateNum(@QueryMap Map<String, String> map );

    //商家评价数据
    @GET("api/MerchantCenter/GetScoringList")
    Call<ResponseObj<ShangJiaEvaluateListObj>> getShangJiaEvaluateList(@QueryMap Map<String, String> map );

    //商家商品
    @GET("api/MerchantCenter/GetMerchantGoods")
    Call<ResponseObj<ShangJiaShangPingObj>> getShangJiaGoods(@QueryMap Map<String, String> map );

    //商家预约时间段
    @GET("api/MerchantCenter/GetAppointmentSchedule")
    Call<ResponseObj<List<YuYueTimeObj>>> getShangJiaYuYue(@QueryMap Map<String, String> map );

    //立即预约
    @POST("api/MerchantCenter/PostMakeAppointment")
    Call<ResponseObj<BaseObj>> startYuYue(@QueryMap Map<String, String> map,@Body YuYueBody body );

    //订单显示
    @POST("api/Order/PostShowOrder")
    Call<ResponseObj<TiJiaoOrderObj>> showOrder(@QueryMap Map<String, String> map, @Body ShowOrderBody body );

    //提交订单
    @POST("api/Order/PostSaveOrder")
    Call<ResponseObj<CommitOrderResultObj>> commitOrder(@QueryMap Map<String, String> map, @Body CommitOrderBody body );

    //余额支付订单
    @GET("api/Order/GetBalancePayment")
    Call<ResponseObj<PaySuccessObj>> yuePay(@QueryMap Map<String, String> map);

    //支付完成推荐商家
    @GET("api/Order/GetPayRecommend")
    Call<ResponseObj<PaySuccessObj>> payTuiJianShangJia(@QueryMap Map<String, String> map);
}

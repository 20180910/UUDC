package com.sk.uudc.module.order.network;

import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.ResponseObj;
import com.sk.uudc.module.order.network.request.BusinessEvaluationBody;
import com.sk.uudc.module.order.network.response.MyOrderObj;
import com.sk.uudc.module.order.network.response.OrderCancelReasonObj;
import com.sk.uudc.module.order.network.response.OrderDetailsObj;
import com.sk.uudc.module.order.network.response.OrderObj;

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
    //订单
    @GET("api/Order/GetOrder")
    Call<ResponseObj<OrderObj>> getOrder(@QueryMap Map<String, String> map);
    //我的订单
    @GET("api/Order/GetMyOrder")
    Call<ResponseObj<MyOrderObj>> getMyOrder(@QueryMap Map<String, String> map);

    //订单详情
    @GET("api/Order/GetOrderDetail")
    Call<ResponseObj<OrderDetailsObj>> getOrderDetail(@QueryMap Map<String, String> map);

    //取消订单原因
    @GET(" api/Order/GetCancelReason")
    Call<ResponseObj<List<OrderCancelReasonObj>>> getCancelReason(@QueryMap Map<String, String> map);

    //提交-取消订单
    @GET("api/Order/GetCancelOrder")
    Call<ResponseObj<BaseObj>> getCancelOrder(@QueryMap Map<String, String> map);

    //发表商家评价
    @POST("api/Order/PostPublishComment")
    Call<ResponseObj<BaseObj>>postPublishComment(@QueryMap Map<String, String> map, @Body BusinessEvaluationBody body);

}

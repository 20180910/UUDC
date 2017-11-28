package com.sk.uudc.network;

import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.ResponseObj;
import com.sk.uudc.module.my.network.request.UploadImgBody;
import com.sk.uudc.module.my.network.response.ProvinceObj;

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
    //获取验证码
    @GET("api/Lib/GetSMSCode")
    Call<ResponseObj<BaseObj>> getSMSCode(@QueryMap Map<String, String> map);
    //图片上传
    @POST("api/Lib/PostUploadFileBase64")
    Call<ResponseObj<BaseObj>> uploadImg(@QueryMap Map<String, String> map, @Body UploadImgBody body);
    //支付回传地址
    @GET("api/Lib/GetPayInfo")
    Call<ResponseObj<BaseObj>> paymentURL(@QueryMap Map<String, String> map );

    //获取省份
    @GET("api/Lib/GetProvince")
    Call<ResponseObj<List<ProvinceObj>>> getProvince(@QueryMap Map<String, String> map);

    //获取市
    @GET("api/Lib/GetCity")
    Call<ResponseObj<List<ProvinceObj>>> getCity(@QueryMap Map<String, String> map);

    //获取区/县
    @GET("api/Lib/GetArea")
    Call<ResponseObj<List<ProvinceObj>>> getArea(@QueryMap Map<String, String> map);

    //申请合伙人
    @GET("api/UserBase/GetApplyForPartner")
    Call<ResponseObj<BaseObj>> getApplyForPartner(@QueryMap Map<String, String> map);

    //获取支付信息
    @GET("api/Lib/GetPayInfo")
    Call<ResponseObj<BaseObj>> PayInfo(@QueryMap Map<String, String> map);





}

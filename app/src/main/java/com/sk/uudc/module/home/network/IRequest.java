package com.sk.uudc.module.home.network;

import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.ResponseObj;
import com.sk.uudc.module.home.network.request.HomeRoastingChartBody;
import com.sk.uudc.module.home.network.request.HomeTypeMerchantListBody;
import com.sk.uudc.module.home.network.request.SearchResultBody;
import com.sk.uudc.module.home.network.response.AreaBusinessCircleObj;
import com.sk.uudc.module.home.network.response.CityIdObj;
import com.sk.uudc.module.home.network.response.GongGaoObj;
import com.sk.uudc.module.home.network.response.HomeAnnouncementObj;
import com.sk.uudc.module.home.network.response.HomeDailybestObj;
import com.sk.uudc.module.home.network.response.HomeLikeObj;
import com.sk.uudc.module.home.network.response.HomePageImageObj;
import com.sk.uudc.module.home.network.response.HomeRoastingChartObj;
import com.sk.uudc.module.home.network.response.HomeTypeAssemblageObj;
import com.sk.uudc.module.home.network.response.HomeTypeMerchantListObj;
import com.sk.uudc.module.home.network.response.HomeUnreadNews;
import com.sk.uudc.module.home.network.response.SearchObj;
import com.sk.uudc.module.home.network.response.SearchResultObj;
import com.sk.uudc.module.home.network.response.ShowHongBaoObj;

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

    //首页轮播图信息
    @POST("api/Information/PostRoastingChart")
    Call<ResponseObj<HomeRoastingChartObj>> postRoastingChart(@QueryMap Map<String, String> map, @Body HomeRoastingChartBody body);
    //首页类别集合信息
    @GET("api/Information/GetTypeAssemblage")
    Call<ResponseObj<HomeTypeAssemblageObj>> getTypeAssemblage(@QueryMap Map<String, String> map);

    //首页中部图片信息
    @GET("api/Information/GetHomePageImage")
    Call<ResponseObj<HomePageImageObj>> getHomePageImage(@QueryMap Map<String, String> map);

    //首页每日精选
    @GET("api/Information/GetDailybest")
    Call<ResponseObj<HomeDailybestObj>> getDailybest(@QueryMap Map<String, String> map);

    //根据城市名获取ID
    @GET("api/Lib/GetCityID")
    Call<ResponseObj<CityIdObj>> getCityId(@QueryMap Map<String, String> map);

    //获取全部区/县商业圈
    @GET("api/Lib/GetAreaBusinessCircle")
    Call<ResponseObj<List<AreaBusinessCircleObj>>> getAreaBusinessCircle(@QueryMap Map<String, String> map);
    //商家列表(分类)
    @POST("api/MerchantCenter/PostMerchantList")
    Call<ResponseObj<HomeTypeMerchantListObj>> postMerchantList(@QueryMap Map<String, String> map, @Body HomeTypeMerchantListBody body);

    //首页公告信息
    @GET("api/Information/GetAnnouncement")
    Call<ResponseObj<List<HomeAnnouncementObj>>> getAnnouncement(@QueryMap Map<String, String> map);


    //获取首页右上角未读消息状态(红点)
    @GET("api/Information/GetUnreadNews")
    Call<ResponseObj<HomeUnreadNews>> getUnreadNews(@QueryMap Map<String, String> map);

    //热门搜索词、历史搜索词
    @GET("api/MerchantCenter/GetHottestSearch")
    Call<ResponseObj<SearchObj>> getHottestSearch(@QueryMap Map<String, String> map);

    //删除历史搜索词
    @GET("api/MerchantCenter/GetDelRecentlySearch")
    Call<ResponseObj<BaseObj>> getDelRecentlySearch(@QueryMap Map<String, String> map);

    //搜索商家
    @POST("api/MerchantCenter/PostSearchMerchant")
    Call<ResponseObj<SearchResultObj>>postSearchMerchant(@QueryMap Map<String, String> map, @Body SearchResultBody body);

    //猜你喜欢
    @GET("api/Information/GetGuessYouLike")
    Call<ResponseObj<HomeLikeObj>>getGuessYouLike(@QueryMap Map<String, String> map);

    //获取公告列表
    @GET("api/Information/GetAnnouncementList")
    Call<ResponseObj<List<GongGaoObj>>> getGongGao(@QueryMap Map<String, String> map);

    //首页获取红包列表
    @GET("api/Information/GetMerchantCoupon")
    Call<ResponseObj<ShowHongBaoObj>> getHongBaoList(@QueryMap Map<String, String> map);



}

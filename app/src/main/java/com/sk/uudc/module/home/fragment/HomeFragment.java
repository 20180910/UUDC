package com.sk.uudc.module.home.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.customview.MyLinearLayout;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.MyApplication;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.home.activity.CitySearchActivity;
import com.sk.uudc.module.home.activity.GoodsTypeActivity;
import com.sk.uudc.module.home.activity.SearchActivity;
import com.sk.uudc.module.home.network.ApiRequest;
import com.sk.uudc.module.home.network.request.HomeRoastingChartBody;
import com.sk.uudc.module.home.network.response.CityIdObj;
import com.sk.uudc.module.home.network.response.HomeAnnouncementObj;
import com.sk.uudc.module.home.network.response.HomeDailybestObj;
import com.sk.uudc.module.home.network.response.HomePageImageObj;
import com.sk.uudc.module.home.network.response.HomeRoastingChartObj;
import com.sk.uudc.module.home.network.response.HomeTypeAssemblageObj;
import com.sk.uudc.module.home.network.response.HomeUnreadNews;
import com.sk.uudc.tools.GlideLoader;
import com.sk.uudc.tools.SpaceItemDecoration;
import com.sk.uudc.view.AutoVerticalScrollTextView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

import static android.os.Looper.getMainLooper;

/**
 * Created by Administrator on 2017/11/4.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.rv_home_cai)
    RecyclerView rv_home_cai;

    @BindView(R.id.rv_home_can_ting)
    RecyclerView rv_home_can_ting;

    @BindView(R.id.rv_home_like)
    RecyclerView rv_home_like;

    @BindView(R.id.tv_home_city)
    TextView tv_home_city;

    @BindView(R.id.tv_home_search)
    TextView tv_home_search;

    @BindView(R.id.ll_home_search)
    MyLinearLayout ll_home_search;

    @BindView(R.id.iv_home_msg)
    ImageView iv_home_msg;

    @BindView(R.id.ll_home_top)
    MyLinearLayout ll_home_top;

    @BindView(R.id.bn_home)
    Banner bn_home;

    @BindView(R.id.iv_home_icon)
    ImageView iv_home_icon;

    @BindView(R.id.avstv_home1)
    AutoVerticalScrollTextView avstv_home1;

    @BindView(R.id.avstv_home2)
    AutoVerticalScrollTextView avstv_home2;

    private List<String> bannerList;
    ArrayList<String> arrayList = new ArrayList<>();
    BaseRecyclerAdapter caiAdapter, canTingAdapter, likeAdapter;
    String city = "上海市", city_id;
    private String area="";
    int number = 0;

    Timer timer = new Timer();
    TimerTask task;
    private boolean isFirstLoc=true;
    @Override
    protected int getContentView() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView() {
        baiDuMap();

        caiAdapter = new BaseRecyclerAdapter<HomeTypeAssemblageObj.TypeListBean>(mContext, R.layout.item_home_cai) {

            @Override
            public void bindData(RecyclerViewHolder holder, int i, HomeTypeAssemblageObj.TypeListBean bean) {
                ImageView iv_item_home_cai_icon = holder.getImageView(R.id.iv_item_home_cai_icon);
                TextView tv_item_home_cai = holder.getTextView(R.id.tv_item_home_cai);
                Glide.with(mContext).
                        load(bean.getType_image()).
                        error(R.color.c_press).
                        into(iv_item_home_cai_icon);
                tv_item_home_cai.setText(bean.getType_name());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCityId(bean.getType_name(), bean.getType_id());


                    }
                });

            }
        };
        rv_home_cai.setNestedScrollingEnabled(false);
        rv_home_cai.setLayoutManager(new GridLayoutManager(mContext, 4));
        rv_home_cai.addItemDecoration(new SpaceItemDecoration(10));
        rv_home_cai.setAdapter(caiAdapter);


        canTingAdapter = new BaseRecyclerAdapter<HomeDailybestObj.DailybestListBean>(mContext, R.layout.item_home_can_ting) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, HomeDailybestObj.DailybestListBean bean) {
                ImageView iv_home_can_ting = holder.getImageView(R.id.iv_home_can_ting);
                TextView tv_item_home_canting_pingfen = holder.getTextView(R.id.tv_item_home_canting_pingfen);
                TextView tv_item_home_canting_name = holder.getTextView(R.id.tv_item_home_canting_name);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = (PhoneUtils.getScreenWidth(mContext) - PhoneUtils.dip2px(mContext, 20)) / 3;
                iv_home_can_ting.setLayoutParams(layoutParams);
                Glide.with(mContext).
                        load(bean.getMerchant_avatar()).
                        error(R.color.c_press).
                        into(iv_home_can_ting);
                tv_item_home_canting_pingfen.setText(bean.getScoring() + "分");
                tv_item_home_canting_name.setText(bean.getMerchant_name());
            }
        };
        rv_home_can_ting.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 1) {
                    return 3;
                } else {
                    return 2;
                }
            }
        });
        rv_home_can_ting.setLayoutManager(gridLayoutManager);
        rv_home_can_ting.addItemDecoration(new SpaceItemDecoration(5));
        rv_home_can_ting.setAdapter(canTingAdapter);

        likeAdapter = new BaseRecyclerAdapter(mContext, R.layout.item_home_like) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, Object o) {

            }
        };
        likeAdapter.setTestListSize(3);
        rv_home_like.setNestedScrollingEnabled(false);
        rv_home_like.setLayoutManager(new LinearLayoutManager(mContext));
        rv_home_like.addItemDecoration(getItemDivider());
        rv_home_like.setAdapter(likeAdapter);


    }

    private void gunDong() {
        task = new TimerTask() {
            @Override
            public void run() {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        avstv_home1.next();
                        avstv_home2.next();
                        number++;
                        avstv_home1.setText(arrayList.get(number % arrayList.size()));
                        number++;
                        avstv_home2.setText(arrayList.get(number % arrayList.size()));
                    }
                });


            }
        };
        timer.schedule(task, 0, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (task != null) {
            task.cancel();
            timer.cancel();
            task = null;
            timer = null;
        }

    }

    @Override
    protected void initData() {
        showProgress();

        //首页类别集合信息
        getTypeAssemblage();
        //轮播图
        postRoastingChart();
//        首页中部图片信息
        getHomePageImage();
        //首页每日精选
        getDailybest();
        //公告
        getAnnouncement();
        if (!TextUtils.isEmpty(getUserId())) {
            getUnreadNews();
        }

    }

    private void getUnreadNews() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getUnreadNews(map, new MyCallBack<HomeUnreadNews>(mContext) {
            @Override
            public void onSuccess(HomeUnreadNews obj) {
                if (obj.getIs_check().equals("1")) {
                    iv_home_msg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.message));

                } else {
                    iv_home_msg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.message2));


                }

            }
        });

    }

    private void getAnnouncement() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAnnouncement(map, new MyCallBack<List<HomeAnnouncementObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<HomeAnnouncementObj> obj) {
                for (int i = 0; i < obj.size(); i++) {
                    arrayList.add(obj.get(i).getTitle());
                }
                gunDong();
            }
        });
    }

    private void getCityId(String name, String typeId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("city", city);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getCityId(map, new MyCallBack<CityIdObj>(mContext) {
            @Override
            public void onSuccess(CityIdObj obj) {
                city_id = obj.getCity_id();
                Intent goodsType = new Intent();
                goodsType.putExtra("name", name);
                goodsType.putExtra("typeId", typeId);
                goodsType.putExtra("city_id", city_id);
                STActivity(goodsType, GoodsTypeActivity.class);


            }
        });
    }

    private void getDailybest() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("lat", MyApplication.getWeiDu(mContext) + "");
        map.put("lng", MyApplication.getJingDu(mContext) + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getDailybest(map, new MyCallBack<HomeDailybestObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(HomeDailybestObj obj) {
                canTingAdapter.setList(obj.getDailybest_list(), true);


            }
        });
    }

    private void getHomePageImage() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getHomePageImage(map, new MyCallBack<HomePageImageObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(HomePageImageObj obj) {


                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = (int) (PhoneUtils.getScreenWidth(mContext) / 2.1);
                layoutParams.width = PhoneUtils.getScreenWidth(mContext);

                iv_home_icon.setLayoutParams(layoutParams);

                Glide.with(mContext).
                        load(obj.getImg_url()).
                        error(R.color.c_press).
                        into(iv_home_icon);


            }
        });

    }

    private void getTypeAssemblage() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getTypeAssemblage(map, new MyCallBack<HomeTypeAssemblageObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(HomeTypeAssemblageObj obj) {
                caiAdapter.setList(obj.getType_list(), true);
            }
        });

    }

    private void postRoastingChart() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", GetSign.getSign(map));
        HomeRoastingChartBody body = new HomeRoastingChartBody();
        body.setLat(MyApplication.getWeiDu(mContext) + "");
        body.setLng(MyApplication.getJingDu(mContext) + "");
        ApiRequest.postRoastingChart(map, body, new MyCallBack<HomeRoastingChartObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(HomeRoastingChartObj obj) {
                bannerList = new ArrayList<String>();
                for (int i = 0; i < obj.getRoasting_list().size(); i++) {
                    bannerList.add(obj.getRoasting_list().get(i).getImg_url());

                }
                bn_home.setImages(bannerList);
                bn_home.setImageLoader(new GlideLoader());
                bn_home.start();


            }
        });

    }

    @Override
    protected void getData(int page, boolean isLoad) {

    }


    @Override
    public void onStop() {
        super.onStop();
        if (bn_home != null && bannerList != null) {
            bn_home.stopAutoPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bn_home != null && bannerList != null) {
            bn_home.startAutoPlay();
        }
    }


    @OnClick({R.id.tv_home_city, R.id.tv_home_search, R.id.iv_home_msg})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home_city:
                STActivity(CitySearchActivity.class);
                break;
            case R.id.tv_home_search:
                STActivity(SearchActivity.class);
                break;
            case R.id.iv_home_msg:
                break;
        }
    }

    public MyLocationListenner myListener = new MyLocationListenner();

    private void baiDuMap() {
        // 定位初始化
        LocationClient mLocClient = new LocationClient(mContext);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocClient.setLocOption(option);
        mLocClient.start();
    }
    public class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null  )
                return;

//            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
            if (isFirstLoc) {
                /*MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();*/
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());

                Log.i("===",location.getAddrStr()+"=="+location.getCity()+"==="+location.getDistrict());
                city=location.getCity();
                area=location.getDistrict();
                SPUtils.setPrefString(mContext, Config.city,city);
                SPUtils.setPrefString(mContext, Config.area,area);
                tv_home_city.setText(location.getCity());
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}

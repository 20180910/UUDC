package com.sk.uudc.module.near.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyEditText;
import com.sk.uudc.GetSign;
import com.sk.uudc.MyApplication;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.request.NearShangJiaBody;
import com.sk.uudc.module.near.network.response.NearListObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/27.
 */

public class NearMapActivity extends BaseActivity {
    @BindView(R.id.rv_near_map)
    RecyclerView rv_near_map;
    LoadMoreAdapter adapter;
    @BindView(R.id.mv_near)
    MapView mv_near;
    @BindView(R.id.et_near_map_search)
    MyEditText et_near_map_search;
    private String searchContent="";

    public BDLocationListener myListener = new MyLocationListenner();
    BaiduMap mBaiduMap;
    private boolean isFirstLoc=true;
    private List<OverlayOptions> optionsList= new ArrayList<>();;

    @Override
    protected int getContentView() {
        return R.layout.act_near_map;
    }

    @Override
    protected void initView() {
        setBaiDuMap();
        adapter = new LoadMoreAdapter<NearListObj.MerchantListBean>(mContext, R.layout.item_near, pageSize) {
            public String listToString(List list) {
                if(isEmpty(list)){
                    return "";
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i)).append(",");
                }
                return sb.toString().substring(0, sb.toString().length() - 1);
            }

            @Override
            public void bindData(LoadMoreViewHolder holder, int position, NearListObj.MerchantListBean bean) {
                ImageView iv_near_icon = holder.getImageView(R.id.iv_near_icon);
                Glide.with(mContext).load(bean.getMerchant_avatar()).error(R.color.c_press).into(iv_near_icon);

                holder.setText(R.id.tv_near_name, bean.getMerchant_name())
                        .setText(R.id.tv_near_star_num, bean.getScoring() + "")
                        .setText(R.id.tv_near_price, "¥ " + bean.getMoney_people() + "/人")
                        .setText(R.id.tv_near_address, bean.getMerchant_address())
                        .setText(R.id.tv_near_type, bean.getCuisine())
                        .setText(R.id.tv_near_type2, listToString(bean.getLable()))
                        .setText(R.id.tv_near_huodong2, listObjToString(bean.getActivity()))
                        .setText(R.id.tv_near_distance, bean.getDistance());

                LinearLayout ll_near_list_huodong = (LinearLayout) holder.getView(R.id.ll_near_list_huodong);
                if (notEmpty(bean.getActivity())) {
                    ll_near_list_huodong.setVisibility(View.VISIBLE);
                } else {
                    ll_near_list_huodong.setVisibility(View.GONE);
                }
                if(bean.getScoring()>=5){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.VISIBLE);
                }else if(bean.getScoring()==4){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }else if(bean.getScoring()==3){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }else if(bean.getScoring()==2){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }else if(bean.getScoring()==1){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }else if(bean.getScoring()==0){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.merchant_id,bean.getMerchant_id()+"");
                        STActivity(intent,ShangJiaActivity.class);
                    }
                });

            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_near_map.setLayoutManager(new LinearLayoutManager(mContext));
        rv_near_map.addItemDecoration(getItemDivider());
        rv_near_map.setNestedScrollingEnabled(false);
        rv_near_map.setAdapter(adapter);


        et_near_map_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    searchData();
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }
    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", page + "");
        map.put("pagesize", pageSize + "");
        map.put("sign", GetSign.getSign(map));
        NearShangJiaBody body = new NearShangJiaBody();
        body.setType_id("0");//0查询所有
        body.setSearch_term(searchContent);
        body.setLat(MyApplication.getWeiDu(mContext));
        body.setLng(MyApplication.getJingDu(mContext));
        ApiRequest.getNearShangJiaList(map, body, new MyCallBack<NearListObj>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(NearListObj obj) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(obj.getMerchantList(), true);
                } else {
                    optionsList.clear();
                    pageNum = 2;
                    adapter.setList(obj.getMerchantList(), true);
                }
                addMark(obj.getMerchantList());
            }
        });

    }

    private void addMark(List<NearListObj.MerchantListBean> merchantList) {
        if(isEmpty(merchantList)){
            return;
        }
        for (int i = 0; i < merchantList.size(); i++) {
            NearListObj.MerchantListBean bean = merchantList.get(i);
            //获取地理编码结果
            final LatLng point = new LatLng( bean.getLat(), bean.getLng());
            //加载自定义marker
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.map_shangjia);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmapDescriptor);
            //在地图上添加Marker，并显示
            optionsList.add(option);
        }
        mBaiduMap.addOverlays(optionsList);
    }

    @OnClick({R.id.iv_near_map_search})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.iv_near_map_search:
                searchData();
            break;
        }
    }
    private void searchData() {
        searchContent=getSStr(et_near_map_search);
        showLoading();
        getData(1,false);
    }

    public String listObjToString(List<NearListObj.MerchantListBean.ActivityBean> list) {
        if(isEmpty(list)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append("满"+list.get(i).getFull_amount()+"减"+list.get(i).getSubtract_amount()).append(",");
            if(i%2!=0){
                sb.append("\n");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mv_near.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mv_near.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mv_near.onDestroy();
    }
    private void setBaiDuMap() {

        mBaiduMap = mv_near.getMap();
// 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        /*BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.back);*/
        MyLocationConfiguration config = new MyLocationConfiguration( MyLocationConfiguration.LocationMode.NORMAL, true, null);
        mBaiduMap.setMyLocationConfiguration(config);

// 定位初始化
        LocationClient mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }
    public void setMapBiaoZhu(String city,String address){
        GeoCoder geoCoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                }else {
                }
            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                }
                //获取反向地理编码结果
            }
        };
//        第三步，设置地理编码检索监听者；
        geoCoder.setOnGetGeoCodeResultListener(listener);
//        第四步，发起地理编码检索；
        geoCoder.geocode(new GeoCodeOption()
                .city(city)
                .address(address));//百度地图上少一个括号
    }
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mv_near == null)
                return;

//            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
            if (isFirstLoc) {
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}

package com.sk.uudc.module.order.activity;

import android.view.View;

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
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.module.near.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/18.
 */

public class ShangJiaMapAddress extends BaseActivity {
    @BindView(R.id.mv_shangjia_address)
    MapView mv_shangjia_address;

    public BDLocationListener myListener = new MyLocationListenner();
    BaiduMap mBaiduMap;
    private boolean isFirstLoc=true;
    private List<OverlayOptions> optionsList= new ArrayList<>();;
    private double weiDu;
    private double jingDu;
    @Override
    protected int getContentView() {
        return R.layout.act_shangjia_map_address;
    }

    @Override
    protected void initView() {
        setBaiDuMap();
        weiDu =getIntent().getDoubleExtra(Constant.IParam.weiDu,0.0);
        jingDu =getIntent().getDoubleExtra(Constant.IParam.jingDu,0.0);
        if(weiDu>0){
            dingWeiShangJia(weiDu, jingDu);
        }
    }
    private void dingWeiShangJia(double weiDu, double jingDu) {
//        MapStatus.Builder builder = new MapStatus.Builder();
//        builder.target(new LatLng(weiDu,jingDu));
//        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        //获取地理编码结果
        final LatLng point = new LatLng( weiDu, jingDu);
        //加载自定义marker
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.map_shangjia);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmapDescriptor);
        //在地图上添加Marker，并显示
        optionsList.add(option);

        mBaiduMap.addOverlays(optionsList);


        LatLng ll = new LatLng(weiDu,jingDu);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);


        mBaiduMap.animateMapStatus(u);
    }
    @Override
    protected void initData() {

    }

    @OnClick({R.id.cv_back})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.cv_back:
                finish();
            break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mv_shangjia_address.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mv_shangjia_address.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mv_shangjia_address.onDestroy();
    }
    private void setBaiDuMap() {

        mBaiduMap = mv_shangjia_address.getMap();
// 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        /*BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.back);*/
        MyLocationConfiguration config = new MyLocationConfiguration( MyLocationConfiguration.LocationMode.NORMAL, true, null);

// 设置定位数据

        mBaiduMap.setMyLocationConfiguration(config);

// 定位初始化
        LocationClient mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(30);


        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mv_shangjia_address == null)
                return;

//            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
            if (isFirstLoc) {
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(20).build()));
                isFirstLoc = false;

            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}

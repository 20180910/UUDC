package com.sk.uudc.module.home.activity;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.github.androidtools.SPUtils;
import com.github.androidtools.StatusBarUtils;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyRadioButton;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.broadcast.MyOperationBro;
import com.sk.uudc.module.home.fragment.HomeFragment;
import com.sk.uudc.module.my.activity.LoginActivity;
import com.sk.uudc.module.my.entity.AppInfo;
import com.sk.uudc.module.my.fragment.MyFragment;
import com.sk.uudc.module.near.fragment.NearFragment;
import com.sk.uudc.module.order.fragment.OrderFragment;
import com.sk.uudc.network.ApiRequest;
import com.sk.uudc.network.response.UpdateAppObj;
import com.sk.uudc.service.APPDownloadService;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.status_bar)
    View status_bar;

    HomeFragment homeFragment;
    NearFragment nearFragment;
    OrderFragment orderFragment;
    MyFragment myFragment;

    @BindView(R.id.layout_main_content)
    FrameLayout layout_main_content;
    @BindView(R.id.rb_home)
    MyRadioButton rb_home;

    @BindView(R.id.rb_home_near)
    MyRadioButton rb_home_near;

    @BindView(R.id.rb_home_order)
    MyRadioButton rb_home_order;

    @BindView(R.id.rb_home_my)
    MyRadioButton rb_home_my;

    private MyRadioButton selectButton;
    private LocalBroadcastManager localBroadcastManager;
    private MyOperationBro myOperationBro;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        int statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = statusBarHeight;
        status_bar.setLayoutParams(layoutParams);
        status_bar.setBackgroundColor(getResources().getColor(R.color.white));
        setBroadcast();
        selectButton = rb_home;
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, homeFragment).commitAllowingStateLoss();
    }

    @Override
    protected void initData() {
        updateApp();
        String registrationID = JPushInterface.getRegistrationID(mContext);
        Log.i("registrationID","registrationID====="+registrationID);
        if(!TextUtils.isEmpty(registrationID)){
            SPUtils.setPrefString(mContext,Config.jiguangRegistrationId,registrationID);
        }
        getPaymentURL(1);//获取支付宝回传地址
        getPaymentURL(2);//获取微信回传地址
    }

    private void updateApp() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.updateApp(map, new MyCallBack<UpdateAppObj>(mContext) {
            @Override
            public void onSuccess(UpdateAppObj obj) {
                if(obj.getAndroid_version()>getAppVersionCode()){
                    SPUtils.setPrefString(mContext,Config.appDownloadUrl,obj.getAndroid_vs_url());
                    SPUtils.setPrefBoolean(mContext,Config.appHasNewVersion,true);
                    MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                    mDialog.setMessage("检测到app有新版本是否更新?");
                    mDialog.setNegativeButton((dialog, which) -> dialog.dismiss());
                    mDialog.setPositiveButton((dialog, which) -> {
                        dialog.dismiss();
                        AppInfo info=new AppInfo();
                        info.setUrl(obj.getAndroid_vs_url());
                        info.setHouZhui(".apk");
                        info.setFileName("uudc");
                        info.setId(obj.getAndroid_version()+"");
                        downloadApp(info);
                    });
                    mDialog.create().show();
                }else{
                    SPUtils.setPrefBoolean(mContext,Config.appHasNewVersion,false);
                }
            }
        });
    }

    private void downloadApp(AppInfo info) {
        APPDownloadService.intentDownload(mContext, info);
    }


    private void getPaymentURL(int type) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("payment_type",type+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.paymentURL(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                if(obj.getPayment_type()==1){
                    SPUtils.setPrefString(mContext,Config.payType_ZFB,obj.getPayment_url());
                }else{
                    SPUtils.setPrefString(mContext,Config.payType_WX,obj.getPayment_url());
                }
            }
        });

    }


    @OnClick({R.id.rb_home, R.id.rb_home_near, R.id.rb_home_order, R.id.rb_home_my})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.rb_home:
                status_bar.setBackgroundColor(getResources().getColor(R.color.white));
                selectHome();
                break;
            case R.id.rb_home_near:
                status_bar.setBackgroundColor(getResources().getColor(R.color.white));
                selectNear();
                break;
            case R.id.rb_home_order:
                if ("0".equals(getUserId())) {
                    selectButton.setChecked(true);
                    STActivity(LoginActivity.class);
                    return;
                }
                status_bar.setBackgroundColor(getResources().getColor(R.color.white));
                selectOrder();
                break;
            case R.id.rb_home_my:
                if ("0".equals(getUserId())) {
                    selectButton.setChecked(true);
                    STActivity(LoginActivity.class);
                    return;
                }
                status_bar.setBackgroundColor(getResources().getColor(R.color.home_green));
                selectMy();
                break;
        }
    }

    private void selectMy() {
        selectButton = rb_home_my;
        if (myFragment == null) {
            myFragment = new MyFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, myFragment).commitAllowingStateLoss();
        } else {
            showFragment(myFragment);
        }
        hideFragment(homeFragment);
        hideFragment(nearFragment);
        hideFragment(orderFragment);
    }

    private void selectOrder() {
        selectButton = rb_home_order;
        if (orderFragment == null) {
            orderFragment = new OrderFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, orderFragment).commitAllowingStateLoss();
        } else {
            showFragment(orderFragment);
        }
        hideFragment(myFragment);
        hideFragment(nearFragment);
        hideFragment(homeFragment);
    }

    private void selectNear() {
        selectButton = rb_home_near;
        if (nearFragment == null) {
            nearFragment = new NearFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, nearFragment).commitAllowingStateLoss();
        } else {
            showFragment(nearFragment);
        }
        hideFragment(homeFragment);
        hideFragment(myFragment);
        hideFragment(orderFragment);
    }

    private void selectHome() {
        selectButton = rb_home;
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, homeFragment).commitAllowingStateLoss();
        } else {
            showFragment(homeFragment);
        }
        hideFragment(nearFragment);
        hideFragment(orderFragment);
        hideFragment(myFragment);
    }

    private void setBroadcast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        myOperationBro = new MyOperationBro(new MyOperationBro.LoginBroInter() {
            @Override
            public void loginSuccess() {
                selectMy();
                selectButton.setChecked(true);

//                registerHuanXin();
            }

            @Override
            public void exitLogin() {
                selectHome();
                selectButton.setChecked(true);
                myFragment=null;
            }
        });
        localBroadcastManager.registerReceiver(myOperationBro, new IntentFilter(Config.Bro.operation));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (localBroadcastManager != null) {
            localBroadcastManager.unregisterReceiver(myOperationBro);
        }
    }


    private long mExitTime;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 1500) {
            showToastS("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}

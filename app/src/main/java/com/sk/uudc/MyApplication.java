package com.sk.uudc;


import android.app.Application;

import com.github.retrofitutil.NetWorkManager;

/**
 * Created by administartor on 2017/8/8.
 */

public class MyApplication extends Application {
 /*   @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/
    @Override
    public void onCreate() {
//        SpeechUtility.createUtility(this, "appid=" + Config.xunfei_app_id);
        super.onCreate();

        NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:5008/",BuildConfig.DEBUG).complete();

//        ZXingLibrary.initDisplayOpinion(this);
//        SDKInitializer.initialize(getApplicationContext());

//        PlatformConfig.setWeixin(Config.weixing_id, Config.weixing_AppSecret);
//        PlatformConfig.setQQZone(Config.qq_id, Config.qq_key);
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");

//        UMShareAPI.get(this);
//        huanXin();
    }

   /* private void huanXin() {
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey(Config.hx_appKey);//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
        options.setTenantId(Config.hx_tenantId);//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”

        // Kefu SDK 初始化
        if (!ChatClient.getInstance().init(this, options)){
            return;
        }
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(this);
        //后面可以设置其他属性
    }*/

}

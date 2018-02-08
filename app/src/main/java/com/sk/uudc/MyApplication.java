package com.sk.uudc;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.aspsine.multithreaddownload.DownloadConfiguration;
import com.aspsine.multithreaddownload.DownloadManager;
import com.baidu.mapapi.SDKInitializer;
import com.github.androidtools.SPUtils;
import com.github.retrofitutil.NetWorkManager;
import com.umeng.socialize.PlatformConfig;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by administartor on 2017/8/8.
 */

public class MyApplication extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
//        SpeechUtility.createUtility(this, "appid=" + Config.xunfei_app_id);
        super.onCreate();
        String baseURL="http://106.14.12.235:5008/";
        if(true&&BuildConfig.DEBUG){
            //测试环境
            baseURL="http://121.40.186.118:5119/";
        }
        NetWorkManager.getInstance(getApplicationContext(),baseURL,BuildConfig.DEBUG).complete();
        //二维码
        ZXingLibrary.initDisplayOpinion(this);
//        ZXingLibrary.initDisplayOpinion(this);
        SDKInitializer.initialize(getApplicationContext());

        PlatformConfig.setWeixin(Config.weixing_id, Config.weixing_AppSecret);
        PlatformConfig.setQQZone(Config.qq_id, Config.qq_key);
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");

//        UMShareAPI.get(this);
        JPushInterface.setDebugMode(BuildConfig.DEBUG); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        //清除本地经纬度
        SPUtils.removeKey(getApplicationContext(),Config.longitude);
        SPUtils.removeKey(getApplicationContext(),Config.latitude);
        initDownloader();
    }
    private void initDownloader() {
        DownloadConfiguration configuration = new DownloadConfiguration();
        configuration.setMaxThreadNum(10);
        configuration.setThreadNum(3);
        DownloadManager.getInstance().init(getApplicationContext(), configuration);
    }
   //经度
   public static double longitude;//=121.432986;
    //纬度
   public static double latitude;//=31.229504;

    /**
     * 经度
     * @param context
     * @return
     */
    public static double getJingDu(Context context){
        if(longitude==0){
            longitude=SPUtils.getPrefFloat(context,Config.longitude,0);
            return longitude;
        }else{
            return longitude;
        }
    }

    /**
     * 纬度
     * @param context
     * @return
     */
    public static double getWeiDu(Context context){
        if(latitude==0){
            latitude=SPUtils.getPrefFloat(context,Config.latitude,0);
            return latitude;
        }else{
            return latitude;
        }
    }
    /**
     * 经度
     * @param context
     * @return
     */
    public static double Lng(Context context){
        if(longitude==0){
            longitude=SPUtils.getPrefFloat(context,Config.longitude,0);
            return longitude;
        }else{
            return longitude;
        }
    }
    /**
     * 纬度
     * @param context
     * @return
     */
    public static double Lat(Context context){
        if(latitude==0){
            latitude=SPUtils.getPrefFloat(context,Config.latitude,0);
            return latitude;
        }else{
            return latitude;
        }
    }
}

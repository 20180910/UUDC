package com.sk.uudc.module.my.activity;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.baseclass.rx.IOCallBack;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyTextView;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.entity.AppInfo;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.network.response.UpdateAppObj;
import com.sk.uudc.service.APPDownloadService;
import com.sk.uudc.tools.CleanMessageUtil;
import com.suke.widget.SwitchButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/11/7.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.ll_setting_gengxin)
    LinearLayout ll_setting_gengxin;
    @BindView(R.id.ll_setting_qinglihuancun)
    LinearLayout ll_setting_qinglihuancun;
    @BindView(R.id.sb_setting)
    SwitchButton sb_setting;
    @BindView(R.id.ll_setting_pingtai)
    LinearLayout ll_setting_pingtai;
    @BindView(R.id.tv_setting_tuichu)
    MyTextView tv_setting_tuichu;
    @BindView(R.id.tv_setting_cache_size)
    TextView tv_setting_cache_size;
    @BindView(R.id.tv_setting_new_version)
    TextView tv_setting_new_version;
    String message_sink;

    @Override
    protected int getContentView() {
        setAppTitle("设置");

        setAppTitleColor(this.getResources().getColor(R.color.gray_33));
        setTitleBackgroud(R.color.white);



        return R.layout.act_setting;
    }

    @Override
    protected void initView() {
        //   SPUtils.setPrefString(mContext, Config.message_sink,obj.getMessage_sink());
        message_sink=SPUtils.getPrefString(mContext,Config.message_sink,"0");
        if (message_sink.equals("0")) {
            sb_setting.setChecked(false);
        }else {
            sb_setting.setChecked(true);
        }
        sb_setting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()== MotionEvent.ACTION_UP){
                    setSwitch();
                }
                return true;
            }
        });


        if(SPUtils.getPrefBoolean(mContext,Config.appHasNewVersion,false)){
            tv_setting_new_version.setVisibility(View.VISIBLE);
        }else{
            tv_setting_new_version.setVisibility(View.INVISIBLE);
        }
    }
    private void setSwitch() {
        boolean checked = sb_setting.isChecked();
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("message_sink",!checked?"1":"0");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getMessageSink(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                sb_setting.setChecked(!checked);
                SPUtils.setPrefBoolean(mContext, Config.user_switch, !checked);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                sb_setting.setChecked(checked);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }

    @OnClick({R.id.ll_setting_gengxin, R.id.ll_setting_qinglihuancun, R.id.ll_setting_pingtai, R.id.tv_setting_tuichu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_setting_gengxin:
                updateApp();
                break;
            case R.id.ll_setting_qinglihuancun:
                deleteCache();
                break;
            case R.id.ll_setting_pingtai:
                STActivity(AboutPlatformActivity.class);
                break;
            case R.id.tv_setting_tuichu:
                mDialog = new MyDialog.Builder(mContext);
                mDialog.setMessage("是否确认退出登录?")
                        .setNegativeButton((dialog, which) -> dialog.dismiss())
                        .setPositiveButton((dialog, which) -> {
                            dialog.dismiss();
                            startExit();
                            exitLogin();

                        });
                mDialog.create().show();
                break;
        }
    }
    private void updateApp() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",GetSign.getSign(map));
        com.sk.uudc.network.ApiRequest.updateApp(map, new MyCallBack<UpdateAppObj>(mContext) {
            @Override
            public void onSuccess(UpdateAppObj obj) {
                if(obj.getAndroid_version()>getAppVersionCode()){
                    tv_setting_new_version.setVisibility(View.VISIBLE);
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
                        info.setId(obj.getAndroid_version()+"");
                        info.setFileName("uudc");
                        downloadApp(info);
                    });
                    mDialog.create().show();
                }else{
                    showMsg("当前app已是最新版本");
                    tv_setting_new_version.setVisibility(View.INVISIBLE);
                    SPUtils.setPrefBoolean(mContext,Config.appHasNewVersion,false);
                }
            }
        });
    }

    private void downloadApp(AppInfo info) {
        APPDownloadService.intentDownload(mContext, info);
    }
    private void deleteCache() {
        RXStart(new IOCallBack<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                CleanMessageUtil.clearAllCache(getApplicationContext());
                try {
                    String totalCacheSize = CleanMessageUtil.getTotalCacheSize(getApplicationContext());
                    subscriber.onNext(totalCacheSize);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMyNext(String totalCacheSize) {
                showMsg("清除成功");
                tv_setting_cache_size.setText(totalCacheSize);
            }
        });

    }

    private void exitLogin() {
        SPUtils.removeKey(mContext, Config.user_id);
        Intent intent = new Intent(Config.Bro.operation);
        intent.putExtra(Config.Bro.flag, Config.Bro.exit_login);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        STActivity(LoginActivity.class);
        finish();
    }
    private void startExit() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",GetSign.getSign(map));
        com.sk.uudc.network.ApiRequest.getLogOut(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {

            }
        });

    }
}

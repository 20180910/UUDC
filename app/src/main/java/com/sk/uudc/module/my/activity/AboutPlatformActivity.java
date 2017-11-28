package com.sk.uudc.module.my.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.AboutPlatformObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/7.
 */

public class AboutPlatformActivity extends BaseActivity {
    @BindView(R.id.iv_about_platform_icon)
    ImageView iv_about_platform_icon;
    @BindView(R.id.tv_about_platform_title)
    TextView tv_about_platform_title;
    @BindView(R.id.tv_about_platform_edition)
    TextView tv_about_platform_edition;

    @Override
    protected int getContentView() {
        setAppTitle("关于平台");
        setAppTitleColor(this.getResources().getColor(R.color.gray_33));
        setTitleBackgroud(R.color.white);
        return R.layout.act_about_platform;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {
        showProgress();
        getAboutPlatform();

    }

    private void getAboutPlatform() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAboutPlatform(map, new MyCallBack<AboutPlatformObj>(mContext) {
            @Override
            public void onSuccess(AboutPlatformObj obj) {
                Glide.with(mContext).
                        load(obj.getPayment_url()).
                        error(R.color.gray).
                        into(iv_about_platform_icon);
                tv_about_platform_edition.setText(obj.getEdition());


            }
        });


    }

    @Override
    protected void onViewClick(View v) {

    }
}

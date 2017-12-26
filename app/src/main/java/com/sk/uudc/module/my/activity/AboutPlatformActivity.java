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
import butterknife.OnClick;

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
    @BindView(R.id.tv_about_platform_phone)
    TextView tv_about_platform_phone;
    @BindView(R.id.tv_about_platform_qq)
    TextView tv_about_platform_qq;
    @BindView(R.id.tv_about_platform_email)
    TextView tv_about_platform_email;

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
        ApiRequest.getAboutPlatform(map, new MyCallBack<AboutPlatformObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(AboutPlatformObj obj) {
                Glide.with(mContext).
                        load(obj.getImage()).
                        error(R.color.gray).
                        into(iv_about_platform_icon);
                tv_about_platform_edition.setText(obj.getEdition());
                if (obj.getTel_wechat().equals("")) {
                    tv_about_platform_phone.setVisibility(View.GONE);
                }else {
                    tv_about_platform_phone.setVisibility(View.VISIBLE);
                    tv_about_platform_phone.setText("联系电话："+obj.getTel_wechat());
                }
                if (obj.getQQ().equals("")) {
                    tv_about_platform_qq.setVisibility(View.GONE);
                }else {
                    tv_about_platform_qq.setVisibility(View.VISIBLE);
                    tv_about_platform_qq.setText("QQ："+obj.getQQ());
                }
                if (obj.getEmail().equals("")) {
                    tv_about_platform_email.setVisibility(View.GONE);
                }else {
                    tv_about_platform_email.setVisibility(View.VISIBLE);
                    tv_about_platform_email.setText("email："+obj.getEmail());
                }



            }
        });


    }

    @Override
    protected void onViewClick(View v) {

    }

    @OnClick({R.id.tv_about_platform_phone, R.id.tv_about_platform_qq, R.id.tv_about_platform_email})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_about_platform_phone:
                break;
            case R.id.tv_about_platform_qq:
                break;
            case R.id.tv_about_platform_email:
                break;
        }
    }
}

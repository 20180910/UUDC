package com.sk.uudc.module.my.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.FenxiaoObj;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MyFenXiaoActivity extends BaseActivity {
    @BindView(R.id.tv_my_fenxiao_ma)
    TextView tv_my_fenxiao_ma;
    @BindView(R.id.iv_my_fenxiao_icon)
    ImageView iv_my_fenxiao_icon;
    @BindView(R.id.tv_my_fenxiao_conten)
    TextView tv_my_fenxiao_conten;
    @BindView(R.id.tv_my_fenxiao_yaoqing)
    MyTextView tv_my_fenxiao_yaoqing;
    String yaoqingma;

    @Override
    protected int getContentView() {
        setAppTitle("我的分销码");
        setAppTitleColor(this.getResources().getColor(R.color.gray_33));
        setTitleBackgroud(R.color.white);
        return R.layout.act_my_fenxiao;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        showProgress();
        getMyFenxiao();


    }

    private void getMyFenxiao() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getMyFenxiao(map, new MyCallBack<FenxiaoObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(FenxiaoObj obj) {
                tv_my_fenxiao_ma.setText(obj.getDistribution_yard());
                tv_my_fenxiao_conten.setText(obj.getContent());
                yaoqingma=obj.getDistribution_yard();
               Bitmap mBitmap = CodeUtils.createImage(yaoqingma, 300, 300, null);
                iv_my_fenxiao_icon.setImageBitmap(mBitmap);
            }
        });
    }


    @Override
    protected void onViewClick(View v) {

    }

    @OnClick(R.id.tv_my_fenxiao_yaoqing)
    public void onClick() {
    }
}

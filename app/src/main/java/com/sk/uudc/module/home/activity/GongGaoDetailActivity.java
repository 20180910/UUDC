package com.sk.uudc.module.home.activity;

import android.view.View;
import android.widget.TextView;

import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.module.home.Constant;
import com.sk.uudc.module.home.network.response.GongGaoObj;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/5.
 */

public class GongGaoDetailActivity extends BaseActivity {
    @BindView(R.id.tv_gonggao_detail_title)
    TextView tv_gonggao_detail_title;
    @BindView(R.id.tv_gonggao_detail_content)
    TextView tv_gonggao_detail_content;
    @BindView(R.id.tv_gonggao_detail_time)
    TextView tv_gonggao_detail_time;
    private GongGaoObj gongGaoObj;

    @Override
    protected int getContentView() {
        setAppTitle("公告详情");
        return R.layout.act_gong_gao_detail;
    }

    @Override
    protected void initView() {
        gongGaoObj = (GongGaoObj) getIntent().getSerializableExtra(Constant.IParam.gongGao);
        tv_gonggao_detail_title.setText(gongGaoObj.getTitle());
        tv_gonggao_detail_content.setText(gongGaoObj.getContent());
        tv_gonggao_detail_time.setText(gongGaoObj.getAdd_time());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}

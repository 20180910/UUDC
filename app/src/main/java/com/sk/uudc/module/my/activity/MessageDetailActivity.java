package com.sk.uudc.module.my.activity;

import android.view.View;
import android.widget.TextView;

import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.Constant;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.MessageDetailObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/6.
 */

public class MessageDetailActivity extends BaseActivity {
    @BindView(R.id.tv_message_details_title)
    TextView tv_message_details_title;
    @BindView(R.id.tv_message_details_content)
    TextView tv_message_details_content;
    private String msgId;

    @Override
    protected int getContentView() {
        setAppTitle("消息详情");
        setAppTitleColor(this.getResources().getColor(R.color.gray_33));
        setTitleBackgroud(R.color.white);
        return R.layout.act_message_details;
    }

    @Override
    protected void initView() {
        msgId = getIntent().getStringExtra(Constant.IParam.msgId);




    }

    @Override
    protected void initData() {
        showProgress();
        getNewsDetail();

    }

    private void getNewsDetail() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("news_id",msgId);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getNewsDetail(map, new MyCallBack<MessageDetailObj >(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(MessageDetailObj obj) {
                tv_message_details_title.setText(obj.getTitle());
                tv_message_details_content.setText(obj.getContent());
            }
        });
    }

    @Override
    protected void onViewClick(View v) {

    }
}

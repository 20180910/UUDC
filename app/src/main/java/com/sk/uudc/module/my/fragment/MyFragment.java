package com.sk.uudc.module.my.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.github.customview.MyImageView;
import com.github.customview.MyLinearLayout;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.activity.MyBalanceActivity;
import com.sk.uudc.module.my.activity.MyCollectActivity;
import com.sk.uudc.module.my.activity.MyDataActivity;
import com.sk.uudc.module.my.activity.MyEvaluateActivity;
import com.sk.uudc.module.my.activity.MyFenXiaoActivity;
import com.sk.uudc.module.my.activity.MyMessageActivity;
import com.sk.uudc.module.my.activity.MyShouYiActivity;
import com.sk.uudc.module.my.activity.SettingActivity;
import com.sk.uudc.module.my.activity.WoYaoHeZuoActivity;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.UserInfoObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.iv_my_icon)
    MyImageView iv_my_icon;
    @BindView(R.id.tv_my_name)
    TextView tv_my_name;
    @BindView(R.id.tv_my_yue)
    TextView tv_my_yue;
    @BindView(R.id.tv_my_message)
    TextView tv_my_message;
    @BindView(R.id.tv_my_shouyi)
    TextView tv_my_shouyi;
    @BindView(R.id.ll_my_zhanghu)
    MyLinearLayout ll_my_zhanghu;
    @BindView(R.id.ll_my_shouyi)
    MyLinearLayout ll_my_shouyi;
    @BindView(R.id.ll_my_fenxiao)
    MyLinearLayout ll_my_fenxiao;
    @BindView(R.id.ll_my_collect)
    MyLinearLayout ll_my_collect;
    @BindView(R.id.ll_my_pingjia)
    MyLinearLayout ll_my_pingjia;
    @BindView(R.id.ll_my_setting)
    MyLinearLayout ll_my_setting;
    @BindView(R.id.ll_my_hezuo)
    MyLinearLayout ll_my_hezuo;

    @Override
    protected int getContentView() {
        return R.layout.frag_my;
    }

    @Override
    protected void initView() {


    }



    @Override
    protected void initData() {
        showProgress();
        getUserInfo();

    }

    @Override
    protected void getData(int page, boolean isLoad) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id,null))){
            return;
        }
        getUserInfo();
    }
    private void getUserInfo() {
        if(TextUtils.isEmpty(SPUtils.getPrefString(mContext,Config.user_id,null))){
            return;
        }
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getUserInfo(map, new MyCallBack<UserInfoObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(UserInfoObj obj) {
                SPUtils.setPrefString(mContext, Config.mobile,obj.getMobile());
                SPUtils.setPrefString(mContext, Config.sex,obj.getSex());
                SPUtils.setPrefString(mContext, Config.avatar,obj.getAvatar());
                SPUtils.setPrefString(mContext, Config.birthday,obj.getBirthday());
                SPUtils.setPrefString(mContext, Config.nick_name,obj.getNick_name());
                SPUtils.setPrefString(mContext, Config.user_name,obj.getUser_name());
                SPUtils.setPrefString(mContext, Config.name,obj.getName());
                SPUtils.setPrefString(mContext, Config.amount,obj.getAmount()+"");
                SPUtils.setPrefString(mContext, Config.message_sink,obj.getMessage_sink());
                getInfo(obj);
            }
        });
    }

    private void getInfo(UserInfoObj obj) {

        if (obj.getAvatar() != null) {
            Glide.with(mContext).
                    load(obj.getAvatar()).
                    error(R.drawable.people).
                    into(iv_my_icon);
        }
        tv_my_name.setText(obj.getNick_name());
        tv_my_yue.setText(obj.getAmount());
        tv_my_shouyi.setText(obj.getCommission());

    }

    @OnClick({R.id.iv_my_icon, R.id.tv_my_yue, R.id.tv_my_message, R.id.tv_my_shouyi, R.id.ll_my_zhanghu, R.id.ll_my_shouyi, R.id.ll_my_fenxiao, R.id.ll_my_collect, R.id.ll_my_pingjia, R.id.ll_my_setting, R.id.ll_my_hezuo})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_my_icon:
                STActivity(MyDataActivity.class);
                break;
            case R.id.tv_my_yue:
                break;
            case R.id.tv_my_message:
                STActivity(MyMessageActivity.class);
                break;
            case R.id.tv_my_shouyi:
                break;
            case R.id.ll_my_zhanghu:
                STActivity(MyBalanceActivity.class);
                break;
            case R.id.ll_my_shouyi:
                STActivity(MyShouYiActivity.class);
                break;
            case R.id.ll_my_fenxiao:
                STActivity(MyFenXiaoActivity.class);
                break;
            case R.id.ll_my_collect:
                STActivity(MyCollectActivity.class);
                break;
            case R.id.ll_my_pingjia:
                STActivity(MyEvaluateActivity.class);

                break;
            case R.id.ll_my_setting:
                STActivity(SettingActivity.class);
                break;
            case R.id.ll_my_hezuo:
                STActivity(WoYaoHeZuoActivity.class);
                break;
        }
    }
}

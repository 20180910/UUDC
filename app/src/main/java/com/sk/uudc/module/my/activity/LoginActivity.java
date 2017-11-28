package com.sk.uudc.module.my.activity;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.LoginObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/4.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_login_phone)
    MyEditText et_login_phone;
    @BindView(R.id.et_login_password)
    MyEditText et_login_password;
    @BindView(R.id.tv_login)
    MyTextView tv_login;
    @BindView(R.id.tv_login_wangji)
    TextView tv_login_wangji;
    @BindView(R.id.tv_login_register)
    TextView tv_login_register;
    String phone,password;

    @Override
    protected int getContentView() {
        setTitleBackgroud(R.color.transparent);
        setBackIcon(R.drawable.app_back);
        hiddenBottomLine();
        return R.layout.act_login;
    }

    @Override
    protected void initView() {
        phone=SPUtils.getPrefString(mContext, Config.phone,null);
        if (!TextUtils.isEmpty(phone)) {
            et_login_phone.setText(phone);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        phone=SPUtils.getPrefString(mContext, Config.phone,null);
        if (!TextUtils.isEmpty(phone)) {
            et_login_phone.setText(phone);
        }
    }

    @OnClick({R.id.tv_login, R.id.tv_login_wangji, R.id.tv_login_register})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                phone=getSStr(et_login_phone);
                password=getSStr(et_login_password);

                if(TextUtils.isEmpty(phone)){
                    showToastS("请输入手机号");
                    return;
                }else if(TextUtils.isEmpty(password)){
                    showToastS("请输入密码");
                    return;
                }
                login();




                break;
            case R.id.tv_login_wangji:
                STActivity(ForgetPWDActivity.class);
                break;
            case R.id.tv_login_register:
                STActivity(RegisterActivity.class);
                break;
        }
    }

    private void login() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("username",phone);
        map.put("password",password);
        map.put("RegistrationID",SPUtils.getPrefString(mContext,Config.jiguangRegistrationId,""));
        map.put("sign", GetSign.getSign(map));
        ApiRequest.userLogin(map, new MyCallBack<LoginObj>(mContext) {
            @Override
            public void onSuccess(LoginObj obj) {
                loginResult(obj);

            }
        });


    }
    private void loginResult(LoginObj obj) {
        SPUtils.setPrefString(mContext,Config.phone,phone);
        SPUtils.setPrefString(mContext, Config.user_id,obj.getUser_id());
        SPUtils.setPrefString(mContext, Config.mobile,obj.getMobile());
        SPUtils.setPrefString(mContext, Config.sex,obj.getSex());
        SPUtils.setPrefString(mContext, Config.avatar,obj.getAvatar());
        SPUtils.setPrefString(mContext, Config.birthday,obj.getBirthday());
        SPUtils.setPrefString(mContext, Config.nick_name,obj.getNick_name());
        SPUtils.setPrefString(mContext, Config.user_name,obj.getUser_name());
        SPUtils.setPrefString(mContext, Config.amount,obj.getAmount()+"");

//        SPUtils.setPrefBoolean(mContext, Config.user_switch, obj.getMessage_sink()==1?true:false);

        LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Config.Bro.operation));

        finish();
    }
}

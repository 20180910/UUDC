package com.sk.uudc.module.my.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.customview.MyTextView;
import com.github.rxjava.rxbus.RxUtils;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Administrator on 2017/11/7.
 */

public class ForgetPWDActivity extends BaseActivity {
    @BindView(R.id.et_forget_ped_phone)
    EditText et_forget_ped_phone;
    @BindView(R.id.et_forget_ped_code)
    EditText et_forget_ped_code;
    @BindView(R.id.tv_forget_ped_huoqu)
    TextView tv_forget_ped_huoqu;
    @BindView(R.id.et_forget_ped_password)
    EditText et_forget_ped_password;
    @BindView(R.id.tv_forget_ped_sure)
    MyTextView tv_forget_ped_sure;
    String phone,smsCode,password,code;

    @Override
    protected int getContentView() {
        setAppTitle("忘记密码");
        setAppTitleColor(this.getResources().getColor(R.color.gray_33));
        setTitleBackgroud(R.color.white);

        return R.layout.act_forget_pwd;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_forget_ped_huoqu, R.id.tv_forget_ped_sure})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_ped_huoqu:
                phone=getSStr(et_forget_ped_phone);

                if(TextUtils.isEmpty(phone)){
                    showMsg("手机号不能为空");
                    return;
                }else if(!GetSign.isMobile(phone)){
                    showMsg("请输入正确手机号");
                    return;
                }
                getMsgCode();


                break;
            case R.id.tv_forget_ped_sure:
                phone=getSStr(et_forget_ped_phone);
                code=getSStr(et_forget_ped_code);
                password=getSStr(et_forget_ped_password);

                if(TextUtils.isEmpty(phone)){
                    showMsg("手机号不能为空");
                    return;
                }else if(!GetSign.isMobile(phone)){
                    showMsg("请输入正确手机号");
                    return;
                }else if(TextUtils.isEmpty(smsCode)||TextUtils.isEmpty(code)||!code.equals(smsCode)){
                    showMsg("请输入正确验证码");
                    return;
                }else if(TextUtils.isEmpty(password)){
                    showMsg("密码不能为空");
                    return;
                }
                resetPwd();








                break;
        }
    }
    private void getMsgCode() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile",phone);
        map.put("rnd",getRnd());
        String sign = GetSign.getSign(map);
        map.put("sign", sign);
        showLoading();
        ApiRequest.getSMSCode(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                smsCode = obj.getSMSCode();
                countDown();
            }
        });

    }
    private void countDown() {
        tv_forget_ped_huoqu.setEnabled(false);
        final long count = 30;
        Subscription subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .take(31)//计时次数
                .map(integer -> count - integer)
                .compose(RxUtils.appSchedulers())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        tv_forget_ped_huoqu.setEnabled(true);
                        tv_forget_ped_huoqu.setText("获取验证码");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        tv_forget_ped_huoqu.setText("剩下" + aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
        addSubscription(subscribe);
    }
    private void resetPwd() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("username",phone);
        map.put("newPassword",password);
        map.put("sign",GetSign.getSign(map));
        com.sk.uudc.module.my.network.ApiRequest.forgetPWD(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                finish();

            }
        });



    }
}

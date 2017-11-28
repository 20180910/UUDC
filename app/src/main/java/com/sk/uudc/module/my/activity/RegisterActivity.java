package com.sk.uudc.module.my.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.customview.MyTextView;
import com.github.rxjava.rxbus.RxUtils;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.request.RegisterBody;
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
 * Created by Administrator on 2017/11/4.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.et_register_phone)
    EditText et_register_phone;
    @BindView(R.id.et_register_code)
    EditText et_register_code;
    @BindView(R.id.tv_register_huoqu)
    TextView tv_register_huoqu;
    @BindView(R.id.et_register_password1)
    EditText et_register_password1;
    @BindView(R.id.et_register_password2)
    EditText et_register_password2;
    @BindView(R.id.et_register_yaoqingma)
    EditText et_register_yaoqingma;
    @BindView(R.id.tv_register)
    MyTextView tv_register;

    String phone,code,pwd,rePwd,smsCode,yaoQingMa;

    @Override
    protected int getContentView() {
        setAppTitle("注册");
        setAppTitleColor(this.getResources().getColor(R.color.white));
        setTitleBackgroud(R.color.transparent);
        setBackIcon(R.drawable.app_back);
        hiddenBottomLine();
        return R.layout.act_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
    private void getMsgCode() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile",getSStr(et_register_phone));
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
        tv_register_huoqu.setEnabled(false);
        final long count = 30;
        Subscription subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .take(31)//计时次数
                .map(integer -> count - integer)
                .compose(RxUtils.appSchedulers())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        tv_register_huoqu.setEnabled(true);
                        tv_register_huoqu.setText("获取验证码");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        tv_register_huoqu.setText("剩下" + aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
        addSubscription(subscribe);
    }

    private void register() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",GetSign.getSign(map));
        RegisterBody body=new RegisterBody();
         body.setUsername(phone);
        body.setPassword(rePwd);
        body.setDistribution_yard(yaoQingMa);


        com.sk.uudc.module.my.network.ApiRequest.register(map, body, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                SPUtils.setPrefString(mContext, Config.phone,phone);

                finish();

            }
        });


    }




    @OnClick({R.id.tv_register_huoqu, R.id.tv_register})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register_huoqu:
                if(TextUtils.isEmpty(getSStr(et_register_phone))){
                    showMsg("手机号不能为空");
                    return;
                }else if(!GetSign.isMobile(getSStr(et_register_phone))){
                    showMsg("请输入正确手机号");
                    return;
                }
                getMsgCode();
                break;
            case R.id.tv_register:
                 phone=getSStr(et_register_phone);
                 code=getSStr(et_register_code);
                 pwd=getSStr(et_register_password1);
                 rePwd=getSStr(et_register_password2);

                if(TextUtils.isEmpty(getSStr(et_register_phone))){
                    showMsg("手机号不能为空");
                    return;
                }else if(!GetSign.isMobile(getSStr(et_register_phone))){
                    showMsg("请输入正确手机号");
                    return;
                }/*else if(TextUtils.isEmpty(smsCode)||TextUtils.isEmpty(code)||!code.equals(smsCode)){
                    showMsg("请输入正确验证码");
                    return;
                }*/else if(TextUtils.isEmpty(pwd)){
                    showMsg("密码不能为空");
                    return;
                }else if(!pwd.equals(rePwd)){
                    showMsg("两次密码不一样");
                    return;
                }
                yaoQingMa=getSStr(et_register_yaoqingma);
                register();


                break;
        }
    }
}

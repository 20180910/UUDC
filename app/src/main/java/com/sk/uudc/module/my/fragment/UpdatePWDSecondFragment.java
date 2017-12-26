package com.sk.uudc.module.my.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.github.rxjava.rxbus.RxUtils;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;
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

public class UpdatePWDSecondFragment extends BaseFragment {
//    @BindView(R.id.et_update_pwd_second_phone)
//    MyEditText et_update_pwd_second_phone;

    @BindView(R.id.et_update_pwd_second_code)
    MyEditText et_update_pwd_second_code;
    @BindView(R.id.tv_update_pwd_second_huoqu)
    MyTextView tv_update_pwd_second_huoqu;
    @BindView(R.id.tv_update_pwd_second_next)
    MyTextView tv_update_pwd_second_next;
    @BindView(R.id.et_update_pwd_second_reset)
    MyEditText et_update_pwd_second_reset;
    @BindView(R.id.et_update_pwd_second_reset2)
    MyEditText et_update_pwd_second_reset2;
    @BindView(R.id.tv_update_pwd_second_sure)
    MyTextView tv_update_pwd_second_sure;
    @BindView(R.id.tv_update_pwd_second_phone)
    TextView tv_update_pwd_second_phone;
    @BindView(R.id.ll_update_pwd_second_next)
    LinearLayout ll_update_pwd_second_next;
    @BindView(R.id.ll_update_pwd_second_sure)
    LinearLayout ll_update_pwd_second_sure;
    String phone, resetPassword, resetPassword2, smsCode, code;

    @Override
    protected int getContentView() {
        return R.layout.frag_update_pwd_second;
    }

    @Override
    protected void initView() {
        ll_update_pwd_second_sure.setVisibility(View.GONE);
        tv_update_pwd_second_phone.setText(SPUtils.getPrefString(mContext, Config.phone,null));

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }

    @OnClick({R.id.tv_update_pwd_second_huoqu, R.id.tv_update_pwd_second_next, R.id.tv_update_pwd_second_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_update_pwd_second_huoqu:
                phone = getSStr(tv_update_pwd_second_phone);
//                if (TextUtils.isEmpty(phone)) {
//                    showMsg("手机号不能为空");
//                    return;
//                } else if (!GetSign.isMobile(phone)) {
//                    showMsg("请输入正确手机号");
//                    return;
//                }
                getMsgCode();
                break;
            case R.id.tv_update_pwd_second_next:
                code = getSStr(et_update_pwd_second_code);

//                if (TextUtils.isEmpty(phone)) {
//                    showMsg("手机号不能为空");
//                    return;
//                } else if (!GetSign.isMobile(phone)) {
//                    showMsg("请输入正确手机号");
//                    return;
//                } else
                    if (TextUtils.isEmpty(smsCode) || TextUtils.isEmpty(code) || !code.equals(smsCode)) {
                    showMsg("请输入正确验证码");
                    return;
                }


                ll_update_pwd_second_next.setVisibility(View.GONE);
                ll_update_pwd_second_sure.setVisibility(View.VISIBLE);


                break;
            case R.id.tv_update_pwd_second_sure:
                resetPassword = getSStr(et_update_pwd_second_reset);
                resetPassword2 = getSStr(et_update_pwd_second_reset2);
                if (TextUtils.isEmpty(resetPassword)) {
                    showMsg("密码不能为空");
                    return;
                } else if (!resetPassword.equals(resetPassword2)) {
                    showMsg("两次密码不一样");
                    return;
                }
                setResetPwd();

                break;
        }
    }

    private void setResetPwd() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", phone);
        map.put("newPassword", resetPassword);
        map.put("sign", GetSign.getSign(map));
        com.sk.uudc.module.my.network.ApiRequest.forgetPWD(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                getActivity().finish();

            }
        });


    }

    private void getMsgCode() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", phone);
        map.put("rnd", getRnd());
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
        tv_update_pwd_second_huoqu.setEnabled(false);
        final long count = 30;
        Subscription subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .take(31)//计时次数
                .map(integer -> count - integer)
                .compose(RxUtils.appSchedulers())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        tv_update_pwd_second_huoqu.setEnabled(true);
                        tv_update_pwd_second_huoqu.setText("获取验证码");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        tv_update_pwd_second_huoqu.setText("剩下" + aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
        addSubscription(subscribe);
    }
}

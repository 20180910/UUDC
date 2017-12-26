package com.sk.uudc.module.my.fragment;

import android.text.TextUtils;
import android.view.View;

import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/7.
 */

public class UpdatePWDOneFragment extends BaseFragment {
    @BindView(R.id.et_update_pwd_old)
    MyEditText et_update_pwd_old;
    @BindView(R.id.et_update_pwd_new)
    MyEditText et_update_pwd_new;
    @BindView(R.id.et_update_pwd_new2)
    MyEditText et_update_pwd_new2;
    @BindView(R.id.tv_update_pwd_sure)
    MyTextView tv_update_pwd_sure;
    String oldPwd,newPwd,newPwd2;

    @Override
    protected int getContentView() {

        return R.layout.frag_update_pwd;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }


    @OnClick(R.id.tv_update_pwd_sure)
    public void onClick() {
        oldPwd = getSStr(et_update_pwd_old);
        newPwd = getSStr(et_update_pwd_new);
        newPwd2 = getSStr(et_update_pwd_new2);
        if (TextUtils.isEmpty(oldPwd)) {
            showMsg("原始密码不能为空");
            return;
        }
        if(!newPwd.equals(newPwd2)){
            showMsg("两次密码不一样");
            return;
        }
        setNewPassword();







    }

    private void setNewPassword() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("oldPassword",oldPwd);
        map.put("newPassword",newPwd);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.setNewPassword(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                getActivity().finish();

            }
        });
    }
}

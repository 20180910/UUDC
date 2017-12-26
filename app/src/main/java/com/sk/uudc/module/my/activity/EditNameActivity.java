package com.sk.uudc.module.my.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.request.EditUserInfoBody;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/7.
 */

public class EditNameActivity extends BaseActivity {
    @BindView(R.id.et_edit_name)
    MyEditText et_edit_name;
    @BindView(R.id.tv_edit_baocun)
    MyTextView tv_edit_baocun;
    String type,name;

    @Override
    protected int getContentView() {
        setAppTitleColor(this.getResources().getColor(R.color.gray_33));
        setTitleBackgroud(R.color.white);

        return R.layout.act_edit_name;
    }

    @Override
    protected void initView() {
        getValue();

    }

    private void getValue() {
        type=getIntent().getStringExtra("type");
        name=getIntent().getStringExtra("name");
        //  Name.putExtra("name",name);
        if (type.equals("name")) {
            setAppTitle("编辑姓名");
            if (TextUtils.isEmpty(name)) {
                et_edit_name.setHint("请输入姓名");
            }else {
                et_edit_name.setText(name);
            }

        }else {
            setAppTitle("编辑昵称");
            if (TextUtils.isEmpty(name)) {
                et_edit_name.setHint("请输入昵称");
            }else {
                et_edit_name.setText(name);
            }


        }


    }
    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }

    @OnClick(R.id.tv_edit_baocun)
    public void onClick() {
        name=getSStr(et_edit_name);
        if (TextUtils.isEmpty(name)) {
            showMsg("内容不能为空");
            return;
        }
        editName();


    }

    private void editName() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign", GetSign.getSign(map));
        EditUserInfoBody body=new EditUserInfoBody();
        body.setUser_id(getUserId());


        if (type.equals("name")) {
            body.setName(name);
        }else {
            body.setNickname(name);

        }
        ApiRequest.editUserInfo(map, body, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {

                    Intent intent=new Intent();
                    intent.putExtra("name",name);
                    setResult(RESULT_OK,intent);
                showMsg(obj.getMsg());
                finish();


            }
        });


    }

}

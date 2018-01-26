package com.sk.uudc.module.my.activity;

import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.Constant;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.request.AddAccountBody;
import com.sk.uudc.module.my.network.response.AccountObj;
import com.sk.uudc.tools.AndroidUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/7.
 */

public class BangDingBankActivity extends BaseActivity {
    @BindView(R.id.et_bangding_bank_name)
    MyEditText et_bangding_bank_name;
    @BindView(R.id.et_bangding_bank_num)
    MyEditText et_bangding_bank_num;
    @BindView(R.id.tv_bangding_bank_type)
    TextView tv_bangding_bank_type;
    @BindView(R.id.et_bangding_bank_card_num)
    MyEditText et_bangding_bank_card_num;
    @BindView(R.id.tv_bangding_bank_save)
    MyTextView tv_bangding_bank_save;
    BottomSheetDialog bankTypeDialog;
    @BindView(R.id.et_bangding_bank_kaihuhang_name)
    MyEditText et_bangding_bank_kaihuhang_name;

    String  realname,//姓名
            bank_card_num,//银行卡号
            id_number,//身份证号
            card_type,//卡类型(1储蓄卡 2信用卡)
            opening_bank;//开户行名称
    private Serializable serializableExtra;

    @Override
    protected int getContentView() {
        setAppTitle("绑定银行卡");
        return R.layout.act_bang_ding_bank;
    }

    @Override
    protected void initView() {
        serializableExtra = getIntent().getSerializableExtra(Constant.IParam.accountBank);
        if(serializableExtra !=null){
            AccountObj obj= (AccountObj) serializableExtra;
            //姓名
            realname=obj.getReal_name();
            //银行卡号
            bank_card_num=obj.getId_number();
            //身份证号
            id_number=obj.getCard_type();
            //卡类型(1储蓄卡 2信用卡)
            card_type=obj.getBank_card_number();
            //开户行名称
            opening_bank=obj.getOpening_bank();

            et_bangding_bank_name.setText(realname);
            et_bangding_bank_num.setText(bank_card_num);
            tv_bangding_bank_type.setText(id_number);
            et_bangding_bank_card_num.setText(card_type);
            et_bangding_bank_kaihuhang_name.setText(opening_bank);
        }
    }

    @Override
    protected void initData() {

    }

    private void showBankTypeDialog() {
        if (bankTypeDialog == null) {
            View bankTypeView = LayoutInflater.from(mContext).inflate(R.layout.popu_bank_type, null);
            bankTypeView.findViewById(R.id.tv_popu_bank_type_chuxu).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    bankTypeDialog.dismiss();
                    tv_bangding_bank_type.setText("储蓄卡");
                    card_type="1";
                }
            });
            bankTypeView.findViewById(R.id.tv_popu_bank_type_xinyong).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    bankTypeDialog.dismiss();
                    tv_bangding_bank_type.setText("信用卡");
                    card_type="2";
                }
            });
            bankTypeView.findViewById(R.id.tv_popu_bank_type_cancel).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    bankTypeDialog.dismiss();
                }
            });
            bankTypeDialog = new BottomSheetDialog(mContext);
            bankTypeDialog.setCanceledOnTouchOutside(true);
            bankTypeDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            bankTypeDialog.setContentView(bankTypeView);
        }
        bankTypeDialog.show();
    }

    @OnClick({R.id.tv_bangding_bank_type, R.id.tv_bangding_bank_save})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bangding_bank_type:
                showBankTypeDialog();
                break;
            case R.id.tv_bangding_bank_save:
                realname = getSStr(et_bangding_bank_name);
                bank_card_num = getSStr(et_bangding_bank_card_num);
                id_number = getSStr(et_bangding_bank_num);
                opening_bank=getSStr(et_bangding_bank_kaihuhang_name);
                if (TextUtils.isEmpty(realname)) {
                    showMsg("请输入姓名！");
                    return;
                }
                if (TextUtils.isEmpty(id_number)) {
                    showMsg("请输入身份证号！");
                    return;
                }
                if (!AndroidUtils.personIdValidation(id_number)) {
                    showMsg("请输入正确的身份证号！");
                    return;
                }
                if (TextUtils.isEmpty(card_type)) {
                    showMsg("请选择卡类型！");
                    return;
                }
                if (TextUtils.isEmpty(bank_card_num)) {
                    showMsg("请输入银行卡号！");
                    return;
                }
                if (TextUtils.isEmpty(opening_bank)) {
                    showMsg("请输入开户行名称！");
                    return;
                }
                if(serializableExtra!=null){//编辑
                    editAccount();
                }else{
                    postAddAccount();
                }
                break;
        }
    }

    private void editAccount() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign", GetSign.getSign(map));
        AddAccountBody body=new AddAccountBody();
        body.setRealname(realname);
        body.setBank_card_num(bank_card_num);
        body.setId_number(id_number);
        body.setCard_type(card_type);
        body.setOpening_bank(opening_bank);
        ApiRequest.editAccount(map, body, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void postAddAccount() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign", GetSign.getSign(map));
        AddAccountBody body=new AddAccountBody();
        body.setRealname(realname);
        body.setBank_card_num(bank_card_num);
        body.setId_number(id_number);
        body.setCard_type(card_type);
        body.setOpening_bank(opening_bank);
        ApiRequest.postAddAccount(map, body, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                finish();

            }
        });
    }
}

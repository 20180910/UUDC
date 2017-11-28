package com.sk.uudc.module.my.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.customview.MyEditText;
import com.github.customview.MyLinearLayout;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.response.ProvinceObj;
import com.sk.uudc.network.ApiRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.SinglePicker;

/**
 * Created by Administrator on 2017/11/6.
 */

public class ShenQingHeHuoActivity extends BaseActivity {
    @BindView(R.id.tv_shenqinghezuo_province)
    TextView tv_shenqinghezuo_province;
    @BindView(R.id.ll_shenqinghehuo_province)
    MyLinearLayout ll_shenqinghehuo_province;
    @BindView(R.id.tv_shenqinghezuo_city)
    TextView tv_shenqinghezuo_city;
    @BindView(R.id.ll_shenqinghehuo_city)
    MyLinearLayout ll_shenqinghehuo_city;
    @BindView(R.id.tv_shenqinghezuo_area)
    TextView tv_shenqinghezuo_area;
    @BindView(R.id.ll_shenqinghehuo_area)
    MyLinearLayout ll_shenqinghehuo_area;
    @BindView(R.id.et_shenqinghezuo_zijin)
    MyEditText et_shenqinghezuo_zijin;
    @BindView(R.id.et_shenqinghezuo_name)
    MyEditText et_shenqinghezuo_name;
    @BindView(R.id.et_shenqinghezuo_phone)
    MyEditText et_shenqinghezuo_phone;
    @BindView(R.id.et_shenqinghezuo_youxiang)
    MyEditText et_shenqinghezuo_youxiang;
    @BindView(R.id.et_shenqinghezuo_liyou)
    MyEditText et_shenqinghezuo_liyou;
    @BindView(R.id.tv_shenqinghezuo_tijiao)
    MyTextView tv_shenqinghezuo_tijiao;
    String parent_id,city_id;
    //省,市,区/县,流动资金,姓名,联系电话,联系邮箱,申请理由;
    String province,city,area_county,circulating_fund,name,contact_number,contact_email,applyfor_reason;

    @Override
    protected int getContentView() {
        setAppTitle("申请成为合伙人");
        return R.layout.act_shen_qing_he_huo;
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

    @OnClick({R.id.ll_shenqinghehuo_province, R.id.ll_shenqinghehuo_city, R.id.ll_shenqinghehuo_area, R.id.tv_shenqinghezuo_tijiao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_shenqinghehuo_province:
                showProgress();
                getProvince();
                break;
            case R.id.ll_shenqinghehuo_city:
                if (TextUtils.isEmpty(parent_id)) {
                    showMsg("请先选择省份！");
                    return;
                }
                showProgress();
                getCity();
                break;
            case R.id.ll_shenqinghehuo_area:
                if (TextUtils.isEmpty(city_id)) {
                    showMsg("请先选择城市！");
                    return;
                }
                showProgress();
                getArea();
                break;
            case R.id.tv_shenqinghezuo_tijiao:
                circulating_fund=getSStr(et_shenqinghezuo_zijin);
                   name=getSStr(et_shenqinghezuo_name);
                contact_number=getSStr(et_shenqinghezuo_phone);
                contact_email=getSStr(et_shenqinghezuo_youxiang);
                applyfor_reason=getSStr(et_shenqinghezuo_liyou);
                Log.d("=====","circulating_fund="+circulating_fund);
                Log.d("=====","name="+name);
                Log.d("=====","contact_number="+contact_number);
                Log.d("=====","contact_email="+contact_email);
                Log.d("=====","applyfor_reason="+applyfor_reason);
                Log.d("=====","province="+province);
                Log.d("=====","city="+city);
                Log.d("=====","area_county="+area_county);
                if (TextUtils.isEmpty(province)) {
                    showMsg("请选取意向省份");
                    return;
                }
                if (TextUtils.isEmpty(city)) {
                    showMsg("请选取意向城市");
                    return;
                }
                if (TextUtils.isEmpty(area_county)) {
                    showMsg("请选取意向区县");
                    return;
                }
                if (TextUtils.isEmpty(circulating_fund)) {
                    showMsg("请输入可支配流动资金");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    showMsg("请输入负责人姓名");
                    return;
                }
                if (TextUtils.isEmpty(contact_number)) {
                    showMsg("请输入联系电话");
                    return;
                }
                if (TextUtils.isEmpty(contact_email)) {
                    showMsg("请输入联系邮箱");
                    return;
                }
                if (TextUtils.isEmpty(applyfor_reason)) {
                    showMsg("请输入申请理由");
                    return;
                }
                showProgress();
                getApplyForPartner();


                break;
        }
    }

    private void getApplyForPartner() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("province",province);
        map.put("city",city);
        map.put("area_county",area_county);
        map.put("circulating_fund",circulating_fund);
        map.put("name",name);
        map.put("contact_number",contact_number);
        map.put("contact_email",contact_email);
        map.put("applyfor_reason",applyfor_reason);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getApplyForPartner(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                finish();

            }
        });
    }

    private void getArea() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("city_id",city_id);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getArea(map, new MyCallBack<List<ProvinceObj>>(mContext) {
            @Override
            public void onSuccess(List<ProvinceObj> obj) {
                getProvincePicker(obj,"area");

            }
        });

    }

    private void getCity() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("parent_id",parent_id);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getCity(map, new MyCallBack<List<ProvinceObj>>(mContext) {
            @Override
            public void onSuccess(List<ProvinceObj> obj) {
                getProvincePicker(obj,"city");

            }
        });

    }

    private void getProvince() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getProvince(map, new MyCallBack<List<ProvinceObj>>(mContext) {
            @Override
            public void onSuccess(List<ProvinceObj> obj) {
                getProvincePicker(obj,"province");
            }
        });
    }


    private void getProvincePicker(List<ProvinceObj> obj,String type) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < obj.size(); i++) {
            list.add(obj.get(i).getTitle());
        }
        SinglePicker<String> picker = new SinglePicker<>(this, list);
        picker.setCanLoop(false);//不禁用循环
        picker.setLineVisible(true);
        picker.setShadowVisible(true);
        picker.setTextSize(18);
        picker.setSelectedIndex(1);
        picker.setWheelModeEnable(true);
        picker.setWeightEnable(true);
        picker.setItemWidth(PhoneUtils.getScreenWidth(mContext));
        picker.setSelectedTextColor(0xFF279BAA);//前四位值是透明度
        picker.setUnSelectedTextColor(0xFF999999);
        picker.setOnSingleWheelListener(new OnSingleWheelListener() {
            @Override
            public void onWheeled(int index, String item) {

            }
        });
        //确定
        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                if (type.equals("province")) {
                    province=list.get(index);
                    tv_shenqinghezuo_province.setText(list.get(index)+"");
                    tv_shenqinghezuo_city.setText("");
                    tv_shenqinghezuo_area.setText("");
                    parent_id=obj.get(index).getId();
                    Log.d("===","obj.get(index).getId()==="+obj.get(index).getId());
                }else if (type.equals("city")){
                    city=list.get(index);
                    city_id=obj.get(index).getId();
                    tv_shenqinghezuo_city.setText(list.get(index)+"");
                    tv_shenqinghezuo_area.setText("");
                }else {
                    tv_shenqinghezuo_area.setText(list.get(index));
                    area_county=list.get(index);

                }

            }
        });
        picker.show();

    }


}

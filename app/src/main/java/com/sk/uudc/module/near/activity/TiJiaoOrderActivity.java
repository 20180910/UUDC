package com.sk.uudc.module.near.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.rx.RxBus;
import com.github.customview.MyCheckBox;
import com.github.customview.MyEditText;
import com.github.customview.MyRadioButton;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.request.CommitOrderBody;
import com.sk.uudc.module.near.network.request.ShowOrderBody;
import com.sk.uudc.module.near.network.response.CommitOrderResultObj;
import com.sk.uudc.module.near.network.response.TiJiaoOrderObj;
import com.sk.uudc.module.order.event.OrdersEvent;
import com.sk.uudc.tools.AndroidUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/16.
 */

public class TiJiaoOrderActivity extends BaseActivity {
    @BindView(R.id.iv_place_order)
    ImageView iv_place_order;
    @BindView(R.id.tv_place_order_name)
    TextView tv_place_order_name;
    @BindView(R.id.ll_place_order_shangdian)
    LinearLayout ll_place_order_shangdian;
    @BindView(R.id.ll_tijiao_order_fapiao)
    LinearLayout ll_tijiao_order_fapiao;
    @BindView(R.id.rv_price_order)
    RecyclerView rv_price_order;
    @BindView(R.id.tv_price_order_youhui)
    TextView tv_price_order_youhui;
    @BindView(R.id.tv_price_order_dingdan_price)
    TextView tv_price_order_dingdan_price;
    @BindView(R.id.tv_price_order_daizhifu_price)
    TextView tv_price_order_daizhifu_price;
    @BindView(R.id.tv_price_order_time)
    TextView tv_price_order_time;
    @BindView(R.id.tv_price_order_num)
    TextView tv_price_order_num;
    @BindView(R.id.cb_place_order_baojian)
    MyCheckBox cb_place_order_baojian;
    @BindView(R.id.cb_place_order_fapiao)
    MyCheckBox cb_place_order_fapiao;
    @BindView(R.id.rb_place_order_fapiao_geren)
    MyRadioButton rb_place_order_fapiao_geren;
    @BindView(R.id.rb_place_order_fapiao_gongsi)
    MyRadioButton rb_place_order_fapiao_gongsi;
    @BindView(R.id.et_place_order_fapiao_danwei)
    EditText et_place_order_fapiao_danwei;
    @BindView(R.id.et_place_order_fapiao_shibiehao)
    EditText et_place_order_fapiao_shibiehao;
    @BindView(R.id.et_place_order_beizhu)
    EditText et_place_order_beizhu;
    @BindView(R.id.et_place_order_name)
    MyEditText et_place_order_name;
    @BindView(R.id.et_place_order_lianxi)
    MyEditText et_place_order_lianxi;
    @BindView(R.id.tv_place_order_price)
    TextView tv_place_order_price;
    @BindView(R.id.tv_place_order)
    TextView tv_place_order;
    @BindView(R.id.tv_youhui_name)
    TextView tv_youhui_name;
    BaseRecyclerAdapter adapter;
    private ShowOrderBody orderBody;
    private TiJiaoOrderObj orderObj;
    private BottomSheetDialog youHuiTypeDialog;
    private String youHuiType="0";//(0商家优惠 1红包 2商家优惠券)
    private String youHuiId="0";
    private double youHuiTotal=0;

    @Override
    protected int getContentView() {
        setAppTitle("提交订单");
        setTitleBackgroud(R.color.white);
        return R.layout.act_place_order;
    }

    @Override
    protected void initView() {

        orderBody = (ShowOrderBody) getIntent().getSerializableExtra(Constant.IParam.orderBody);

        adapter = new BaseRecyclerAdapter<TiJiaoOrderObj.GoodsListBean>(mContext, R.layout.item_pay_cai) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, TiJiaoOrderObj.GoodsListBean bean) {
                holder.setText(R.id.tvItemPayName,bean.getGoods_name())
                        .setText(R.id.tvItemPayNum,"X"+bean.getNumber())
                        .setText(R.id.tvItemPayPrice,"¥"+bean.getGoods_price());
            }
        };
        rv_price_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_price_order.setNestedScrollingEnabled(false);
        rv_price_order.setAdapter(adapter);


    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.showOrder(map,orderBody, new MyCallBack<TiJiaoOrderObj>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(TiJiaoOrderObj obj) {
                orderObj = obj;
                Glide.with(mContext).load(obj.getMerchant_avatar()).error(R.color.c_press).into(iv_place_order);
                tv_place_order_name.setText(obj.getMerchant_name());
                youHuiTotal=obj.getMerchants_preferential();
                orderPrice(obj.getMerchants_preferential(),obj.getTo_pay());
                tv_price_order_time.setText(obj.getDine_time());
                tv_price_order_num.setText(obj.getDine_num_people()+"");
                cb_place_order_baojian.setChecked(obj.getIs_require_rooms()==1);

                adapter.setList(obj.getGoods_list(),true);
            }
        });

    }

    private void orderPrice(double preferential,double orderPrice) {
        tv_price_order_youhui.setText("-¥"+preferential);
        tv_price_order_dingdan_price.setText("订单¥"+orderPrice);
        tv_price_order_daizhifu_price.setText("待支付¥"+orderPrice);
        tv_place_order_price.setText(orderPrice+"");
    }

    @OnClick({R.id.tv_place_order,R.id.rb_place_order_fapiao_geren,R.id.rb_place_order_fapiao_gongsi,R.id.cb_place_order_fapiao,R.id.ll_youhui_type})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_youhui_type:
                if(youHuiTypeDialog==null){
                    youHuiTypeDialog = new BottomSheetDialog(mContext);
                    youHuiTypeDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    View youhuiTypeView = LayoutInflater.from(mContext).inflate(R.layout.popu_youhui_type, null);
                    TextView tv_youhui_type_youhui = youhuiTypeView.findViewById(R.id.tv_youhui_type_youhui);
                    TextView tv_youhui_type_hongbao =youhuiTypeView.findViewById(R.id.tv_youhui_type_hongbao);
                    TextView tv_youhui_type_manjian =youhuiTypeView.findViewById(R.id.tv_youhui_type_manjian);
                    tv_youhui_type_youhui.setOnClickListener(getYouHuiClickListener(1,tv_youhui_type_youhui.getText().toString()));
                    tv_youhui_type_hongbao.setOnClickListener(getYouHuiClickListener(2,tv_youhui_type_hongbao.getText().toString()));
                    tv_youhui_type_manjian.setOnClickListener(getYouHuiClickListener(3,tv_youhui_type_manjian.getText().toString()));
                    youHuiTypeDialog.setContentView(youhuiTypeView);
                }
                youHuiTypeDialog.show();

                break;
            case R.id.cb_place_order_fapiao:
                if(cb_place_order_fapiao.isChecked()){
                    ll_tijiao_order_fapiao.setVisibility(View.VISIBLE);
                }else{
                    ll_tijiao_order_fapiao.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_place_order_fapiao_geren:
                et_place_order_fapiao_shibiehao.setVisibility(View.GONE);
                break;
            case R.id.rb_place_order_fapiao_gongsi:
                et_place_order_fapiao_shibiehao.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_place_order:
                String faPiaoName = getSStr(et_place_order_fapiao_danwei);
                String faPiaoCode = getSStr(et_place_order_fapiao_shibiehao);
                String peopleName = getSStr(et_place_order_name);
                String peoplePhone = getSStr(et_place_order_lianxi);
                if(cb_place_order_fapiao.isChecked()&&TextUtils.isEmpty(faPiaoName)){
                    showMsg("请输入发票名称");
                    return;
                }else if(cb_place_order_fapiao.isChecked()&&TextUtils.isEmpty(faPiaoCode)&&rb_place_order_fapiao_gongsi.isChecked()){
                    showMsg("请输入纳税人识别号");
                    return;
                }else if(TextUtils.isEmpty(peopleName)){
                    showMsg("请输入联系人姓名");
                    return;
                }else if(TextUtils.isEmpty(peoplePhone)){
                    showMsg("请输入联系方式");
                    return;
                }else if(!GetSign.isMobile(peoplePhone)){
                    showMsg("联系方式格式不正确");
                    return;
                }
                commitOrder(faPiaoName,
                        faPiaoCode,
                        peopleName,
                        peoplePhone);

                break;

        }
    }

    @NonNull
    private MyOnClickListener getYouHuiClickListener(int flag,String youHuiText) {
        return new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                youHuiTypeDialog.dismiss();
                Intent intent=new Intent(Constant.IParam.tiJiaoOrder);
                intent.putExtra(Constant.IParam.merchantId,orderBody.getMerchant_id());
                intent.putExtra(Constant.IParam.amount,AndroidUtils.jiaFa(orderObj.getTo_pay(),orderObj.getMerchants_preferential())+"");
                switch (flag){
                    case 1://优惠券
                        intent.putExtra(Constant.IParam.youHuiType,Constant.IParam.youHuiType_2);
                        STActivityForResult(intent,UseHongBaoActivity.class,100);
                    break;
                    case 2://红包
                        intent.putExtra(Constant.IParam.youHuiType,Constant.IParam.youHuiType_1);
                        STActivityForResult(intent,UseHongBaoActivity.class,200);
                    break;
                    case 3://满减活动

                    break;
                }

            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100://优惠券

                    if(data==null&&youHuiType.equals("2")){
                        youHuiType="0";
                        youHuiId="0";
                        tv_youhui_name.setText("满减活动");
                        youHuiTotal=orderObj.getMerchants_preferential();
                        orderPrice(youHuiTotal, orderObj.getTo_pay() );
                    }else if(data!=null){
                        youHuiType="2";//(0商家优惠 1红包 2商家优惠券)
                        tv_youhui_name.setText("优惠券");
                        youHuiId=data.getStringExtra(Constant.IParam.youHuiId);
                        youHuiTotal=data.getDoubleExtra(Constant.IParam.youHuiTotal,0);
                        orderPrice(youHuiTotal,AndroidUtils.jianFa(AndroidUtils.jiaFa(orderObj.getTo_pay(),orderObj.getMerchants_preferential()),youHuiTotal));
                    }
                    break;
                case 200://红包

                    if(data==null&&youHuiType.equals("1")){
                        youHuiType="0";
                        youHuiId="0";
                        tv_youhui_name.setText("满减活动");
                        youHuiTotal=orderObj.getMerchants_preferential();
                        orderPrice(youHuiTotal, orderObj.getTo_pay() );
                    }else if(data!=null){
                        youHuiType="1";//(0商家优惠 1红包 2商家优惠券)
                        tv_youhui_name.setText("红包");
                        youHuiId=data.getStringExtra(Constant.IParam.youHuiId);
                        youHuiTotal=data.getDoubleExtra(Constant.IParam.youHuiTotal,0);
                        orderPrice(youHuiTotal,AndroidUtils.jianFa(AndroidUtils.jiaFa(orderObj.getTo_pay(),orderObj.getMerchants_preferential()),youHuiTotal));
                    }
                    break;
            }


        }
    }

    private void commitOrder(String faPiaoName, String faPiaoCode, String peopleName, String peoplePhone) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",GetSign.getSign(map));
        CommitOrderBody body=new CommitOrderBody();
        body.setShowOrder(orderObj.getGoods_list());
        body.setUser_id(getUserId());
        body.setType(youHuiType);
        body.setCoupons_id(youHuiId);
        body.setMerchant_id(orderObj.getMerchant_id()+"");
        body.setDine_time(orderObj.getDine_time());
        body.setTime_id(orderObj.getTime_id());
        body.setDine_num_people(orderObj.getDine_num_people());
        body.setIs_require_rooms(cb_place_order_baojian.isChecked()?1:0);
        if(cb_place_order_fapiao.isChecked()){
            body.setInvoice_type(rb_place_order_fapiao_geren.isChecked()?"个人":"单位");
            body.setInvoice_name(faPiaoName);
            body.setInvoice_tax_number(faPiaoCode==null?"":faPiaoCode);
        }
        body.setRemark(getSStr(et_place_order_beizhu));
        body.setContact_person_recipient(peopleName);
        body.setContact_person_phone(peoplePhone);

        ApiRequest.commitOrder(map,body,new MyCallBack<CommitOrderResultObj>(mContext) {
            @Override
            public void onSuccess(CommitOrderResultObj obj) {
                RxBus.getInstance().post(new OrdersEvent(Config.refresh));
                showMsg(obj.getMsg());
//                Intent place=new Intent(mContext,OrderPayActivity.class);
//                place.putExtra(Constant.IParam.orderPayInfo,obj);
//                startActivity(place);
                finish();
            }
        });
    }
}

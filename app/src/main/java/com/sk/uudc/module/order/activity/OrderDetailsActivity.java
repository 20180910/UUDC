package com.sk.uudc.module.order.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.rx.RxBus;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyTextView;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.activity.OrderPayActivity;
import com.sk.uudc.module.near.network.response.CommitOrderResultObj;
import com.sk.uudc.module.order.Constant;
import com.sk.uudc.module.order.event.OrdersEvent;
import com.sk.uudc.module.order.network.ApiRequest;
import com.sk.uudc.module.order.network.response.OrderDetailsObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/14.
 */

public class OrderDetailsActivity extends BaseActivity {


    @BindView(R.id.tv_order_details_type)
    TextView tv_order_details_type;
    @BindView(R.id.tv_order_details_hexiaoma)
    TextView tv_order_details_hexiaoma;
    @BindView(R.id.ll_order_details_hexiaoma)
    LinearLayout ll_order_details_hexiaoma;
    @BindView(R.id.tv_order_details_icon)
    ImageView tv_order_details_icon;
    @BindView(R.id.tv_order_details_name)
    TextView tv_order_details_name;
    @BindView(R.id.rv_order_details_shangpin)
    RecyclerView rv_order_details_shangpin;
    @BindView(R.id.tv_order_details_youhui)
    TextView tv_order_details_youhui;
    @BindView(R.id.tv_order_details_type2)
    TextView tv_order_details_type2;
    @BindView(R.id.tv_order_details_price)
    TextView tv_order_details_price;
    @BindView(R.id.rv_order_details_jiacai_shangpin)
    RecyclerView rv_order_details_jiacai_shangpin;
    @BindView(R.id.jtv_order_details_iacai_youhui)
    TextView jtv_order_details_iacai_youhui;
    @BindView(R.id.tv_order_details_jiacai_type2)
    TextView tv_order_details_jiacai_type2;
    @BindView(R.id.tv_order_details_jiacai_price)
    TextView tv_order_details_jiacai_price;
    @BindView(R.id.ll_order_details_jiacai_info)
    LinearLayout ll_order_details_jiacai_info;
    @BindView(R.id.tv_order_details_map_tv)
    TextView tv_order_details_map_tv;
    @BindView(R.id.tv_order_details_name2)
    TextView tv_order_details_name2;
    @BindView(R.id.iv_order_details_star1)
    ImageView iv_order_details_star1;
    @BindView(R.id.iv_order_details_star2)
    ImageView iv_order_details_star2;
    @BindView(R.id.iv_order_details_star3)
    ImageView iv_order_details_star3;
    @BindView(R.id.iv_order_details_star4)
    ImageView iv_order_details_star4;
    @BindView(R.id.iv_order_details_star5)
    ImageView iv_order_details_star5;
    @BindView(R.id.tv_order_details_address_tv)
    TextView tv_order_details_address_tv;
    @BindView(R.id.iv_order_details_pingfen_tv)
    ImageView iv_order_details_pingfen_tv;
    @BindView(R.id.tv_order_details_dinner_time_tv)
    TextView tv_order_details_dinner_time_tv;
    @BindView(R.id.tv_order_details_dinner_num_tv)
    TextView tv_order_details_dinner_num_tv;
    @BindView(R.id.tv_order_details_baojian_tv)
    TextView tv_order_details_baojian_tv;
    @BindView(R.id.tv_order_details_invoice_info_tv)
    TextView tv_order_details_invoice_info_tv;
    @BindView(R.id.tv_order_details_remarks_tv)
    TextView tv_order_details_remarks_tv;
    @BindView(R.id.tv_order_details_contacts_tv)
    TextView tv_order_details_contacts_tv;
    @BindView(R.id.tv_order_details_phone_tv)
    TextView tv_order_details_phone_tv;
    @BindView(R.id.tv_order_details_dingdanhao)
    TextView tv_order_details_dingdanhao;
    @BindView(R.id.tv_order_details_time)
    TextView tv_order_details_time;
    @BindView(R.id.tv_order_details_lijifukuan)
    MyTextView tv_order_details_lijifukuan;

    @BindView(R.id.tv_order_details_tuikuan)
    MyTextView tv_order_details_tuikuan;

    @BindView(R.id.tv_order_details_quxiao)
    MyTextView tv_order_details_quxiao;
    @BindView(R.id.tv_order_details_pingjia)
    MyTextView tv_order_details_pingjia;
    @BindView(R.id.tv_order_details_chakan)
    MyTextView tv_order_details_chakan;
    @BindView(R.id.tv_order_details_jiacai)
    MyTextView tv_order_details_jiacai;
    @BindView(R.id.tv_order_details_num)
    TextView tv_order_details_num;
    String order_id, avatar, name,order_no;
    double combined;
    //    OrdersDetailsAdapter mAdapter;
    BaseRecyclerAdapter adapter,jacaiAdapter;
    private double weiDu=0;
    private double jingDu=0;

    private String merchantId;

    @Override
    protected int getContentView() {
        setAppTitle("订单详情");
        setAppTitleColor(this.getResources().getColor(R.color.gray_33));
        setTitleBackgroud(R.color.white);
        return R.layout.act_order_details;
    }

    @Override
    protected void initView() {
        getValue();
//        initRcv();
        adapter = new BaseRecyclerAdapter<OrderDetailsObj.GoodsListBean>(mContext, R.layout.item_order_details) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, OrderDetailsObj.GoodsListBean bean) {
                TextView tv_order_details_item_name = holder.getTextView(R.id.tv_order_details_item_name);
                TextView tv_order_details_item_num = holder.getTextView(R.id.tv_order_details_item_num);
                TextView tv_order_details_item_price = holder.getTextView(R.id.tv_order_details_item_price);
                tv_order_details_item_name.setText(bean.getGoods_name());
                tv_order_details_item_num.setText("X" + bean.getGoods_number());
                tv_order_details_item_price.setText("¥" + bean.getGoods_price());
            }
        };
        rv_order_details_shangpin.setLayoutManager(new LinearLayoutManager(mContext));
        rv_order_details_shangpin.setNestedScrollingEnabled(false);
        rv_order_details_shangpin.setAdapter(adapter);

        jacaiAdapter=new BaseRecyclerAdapter<OrderDetailsObj.AddfoodgoodsListBean.GoodsListBeanX>(mContext, R.layout.item_order_details) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, OrderDetailsObj.AddfoodgoodsListBean.GoodsListBeanX bean) {
                TextView tv_order_details_item_name = holder.getTextView(R.id.tv_order_details_item_name);
                TextView tv_order_details_item_num = holder.getTextView(R.id.tv_order_details_item_num);
                TextView tv_order_details_item_price = holder.getTextView(R.id.tv_order_details_item_price);
                tv_order_details_item_name.setText(bean.getGoods_name());
                tv_order_details_item_num.setText("X" + bean.getGoods_number());
                tv_order_details_item_price.setText("¥" + bean.getGoods_price());
            }


        };

        rv_order_details_jiacai_shangpin.setLayoutManager(new LinearLayoutManager(mContext));
        rv_order_details_jiacai_shangpin.setNestedScrollingEnabled(false);
        rv_order_details_jiacai_shangpin.setAdapter(jacaiAdapter);

    }


    private void getValue() {
        order_id = getIntent().getStringExtra(Constant.IParam.orderId);
        Log.d("====", "order_id===" + order_id);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();

    }

    private void getData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("order_id", order_id);
        map.put("user_id", getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getOrderDetail(map, new MyCallBack<OrderDetailsObj>(mContext, pcfl, pl_load) {


            @Override
            public void onSuccess(OrderDetailsObj obj) {
                merchantId = obj.getMerchant_id();
                if (notEmpty(obj.getMerchant_list())) {
                    weiDu  =obj.getMerchant_list().get(0).getLat();
                    jingDu =obj.getMerchant_list().get(0).getLng();
                }

                if (obj.getGoods_list().size() == 0) {
                    rv_order_details_shangpin.setVisibility(View.GONE);

                } else {
                    rv_order_details_shangpin.setVisibility(View.VISIBLE);

                }
                if (obj.getAddfoodgoods_list().size()!=0) {
                    ll_order_details_jiacai_info.setVisibility(View.VISIBLE);

                    jacaiAdapter.setList( obj.getAddfoodgoods_list().get(0).getGoods_list(),true);
                    jtv_order_details_iacai_youhui.setText("-¥"+obj.getAddfoodgoods_list().get(0).getYouhui_money());
                    tv_order_details_jiacai_type2.setText(obj.getAddfoodgoods_list().get(0).getPay_status()+"");
                    tv_order_details_jiacai_price.setText("¥" + obj.getAddfoodgoods_list().get(0).getCombined()+"");

                }else {
                    ll_order_details_jiacai_info.setVisibility(View.GONE);
                }


                adapter.setList(obj.getGoods_list(), true);


                setValue(obj);


            }
        });

    }

    private void setValue(OrderDetailsObj obj) {





        if(obj.getOrder_status() == 0){
            ll_order_details_hexiaoma.setVisibility(View.GONE);
//            ll_order_details_jiacai_info.setVisibility(View.GONE);
            tv_order_details_type.setText("等待商家确认");
            tv_order_details_type2.setText("等待商家确认");
            tv_order_details_type.setCompoundDrawablesRelativeWithIntrinsicBounds(null,
                    null,
                    mContext.getResources().getDrawable(R.drawable.daifukuan_c),
                    null);
            tv_order_details_quxiao.setVisibility(View.GONE);
            tv_order_details_tuikuan.setVisibility(View.GONE);
            tv_order_details_lijifukuan.setVisibility(View.GONE);
            tv_order_details_pingjia.setVisibility(View.GONE);
            tv_order_details_chakan.setVisibility(View.GONE);
            tv_order_details_jiacai.setVisibility(View.GONE);
        }else if (obj.getOrder_status() == 1) {
            ll_order_details_hexiaoma.setVisibility(View.GONE);
//            ll_order_details_jiacai_info.setVisibility(View.GONE);
            tv_order_details_type.setText("待付款");
            tv_order_details_type2.setText("待付款");
            tv_order_details_type.setCompoundDrawablesRelativeWithIntrinsicBounds(null,
                    null,
                    mContext.getResources().getDrawable(R.drawable.daifukuan_c),
                    null);
            tv_order_details_quxiao.setVisibility(View.VISIBLE);
            tv_order_details_tuikuan.setVisibility(View.GONE);
            tv_order_details_lijifukuan.setVisibility(View.VISIBLE);
            tv_order_details_pingjia.setVisibility(View.GONE);
            tv_order_details_chakan.setVisibility(View.GONE);
            tv_order_details_jiacai.setVisibility(View.GONE);

        } else if (obj.getOrder_status() == 2) {
            ll_order_details_hexiaoma.setVisibility(View.VISIBLE);
//            ll_order_details_jiacai_info.setVisibility(View.GONE);
            tv_order_details_type.setText("待使用");
            tv_order_details_type2.setText("已支付");
            tv_order_details_type.setCompoundDrawablesRelativeWithIntrinsicBounds(null,
                    null,
                    mContext.getResources().getDrawable(R.drawable.daishiyong_c),
                    null);
            tv_order_details_lijifukuan.setVisibility(View.GONE);
            tv_order_details_tuikuan.setVisibility(View.VISIBLE);
            tv_order_details_quxiao.setVisibility(View.GONE);
            tv_order_details_pingjia.setVisibility(View.GONE);
            tv_order_details_chakan.setVisibility(View.GONE);
            tv_order_details_jiacai.setVisibility(View.GONE);

        } else if (obj.getOrder_status() == 3) {
            if (obj.getIs_jiacai()==0) {
                tv_order_details_jiacai.setVisibility(View.VISIBLE);
            }else {
                tv_order_details_jiacai.setVisibility(View.GONE);
            }

            ll_order_details_hexiaoma.setVisibility(View.GONE);
//            ll_order_details_jiacai_info.setVisibility(View.GONE);
            tv_order_details_type.setText("待评价");
            tv_order_details_type2.setText("已支付");
            tv_order_details_type.setCompoundDrawablesRelativeWithIntrinsicBounds(null,
                    null,
                    mContext.getResources().getDrawable(R.drawable.daipingjia_c),
                    null);
            tv_order_details_tuikuan.setVisibility(View.GONE);
            tv_order_details_lijifukuan.setVisibility(View.GONE);
            tv_order_details_quxiao.setVisibility(View.GONE);
            tv_order_details_pingjia.setVisibility(View.VISIBLE);
            tv_order_details_chakan.setVisibility(View.GONE);


        } else if (obj.getOrder_status() == 4) {
            ll_order_details_hexiaoma.setVisibility(View.GONE);
//            ll_order_details_jiacai_info.setVisibility(View.GONE);
            tv_order_details_type.setText("已取消");
            tv_order_details_type2.setText("总价");
            tv_order_details_type.setCompoundDrawablesRelativeWithIntrinsicBounds(null,
                    null,
                    mContext.getResources().getDrawable(R.drawable.yiquxiao_c),
                    null);
            tv_order_details_tuikuan.setVisibility(View.GONE);
            tv_order_details_lijifukuan.setVisibility(View.GONE);
            tv_order_details_quxiao.setVisibility(View.GONE);
            tv_order_details_pingjia.setVisibility(View.GONE);
            tv_order_details_chakan.setVisibility(View.GONE);
            tv_order_details_jiacai.setVisibility(View.GONE);

        } else if (obj.getOrder_status() == 5) {
            ll_order_details_hexiaoma.setVisibility(View.GONE);
//            ll_order_details_jiacai_info.setVisibility(View.GONE);
            tv_order_details_type.setText("已完成");
            tv_order_details_type2.setText("总价");
            tv_order_details_type.setCompoundDrawablesRelativeWithIntrinsicBounds(null,
                    null,
                    mContext.getResources().getDrawable(R.drawable.yiquxiao_c),
                    null);
            tv_order_details_tuikuan.setVisibility(View.GONE);
            tv_order_details_lijifukuan.setVisibility(View.GONE);
            tv_order_details_quxiao.setVisibility(View.GONE);
            tv_order_details_pingjia.setVisibility(View.GONE);
            tv_order_details_chakan.setVisibility(View.GONE);
            tv_order_details_jiacai.setVisibility(View.GONE);

        }
        order_no=obj.getOrder_no();
        combined=obj.getCombined();
        avatar = obj.getMerchant_avatar();
        name = obj.getMerchant_name();
        tv_order_details_hexiaoma.setText(obj.getVerification_code());
        Glide.with(mContext).load(obj.getMerchant_avatar()).error(R.color.c_press).into(tv_order_details_icon);
        tv_order_details_name.setText(obj.getMerchant_name());
        tv_order_details_youhui.setText("-¥" + obj.getYouhui_money());
        tv_order_details_price.setText("¥" + obj.getCombined());
        tv_order_details_name2.setText(obj.getMerchant_list().get(0).getMerchant_name());
        tv_order_details_address_tv.setText(obj.getMerchant_list().get(0).getMerchant_address());
        tv_order_details_num.setText(obj.getMerchant_list().get(0).getScoring() + "");
     if (obj.getMerchant_list().get(0).getScoring() ==1) {
            iv_order_details_star1.setVisibility(View.VISIBLE);
            iv_order_details_star2.setVisibility(View.GONE);
            iv_order_details_star3.setVisibility(View.GONE);
            iv_order_details_star4.setVisibility(View.GONE);
            iv_order_details_star5.setVisibility(View.GONE);
        } else if (obj.getMerchant_list().get(0).getScoring()==2) {
            iv_order_details_star1.setVisibility(View.VISIBLE);
            iv_order_details_star2.setVisibility(View.VISIBLE);
            iv_order_details_star3.setVisibility(View.GONE);
            iv_order_details_star4.setVisibility(View.GONE);
            iv_order_details_star5.setVisibility(View.GONE);
        } else if (obj.getMerchant_list().get(0).getScoring()==3) {
            iv_order_details_star1.setVisibility(View.VISIBLE);
            iv_order_details_star2.setVisibility(View.VISIBLE);
            iv_order_details_star3.setVisibility(View.VISIBLE);
            iv_order_details_star4.setVisibility(View.GONE);
            iv_order_details_star5.setVisibility(View.GONE);
        } else if (obj.getMerchant_list().get(0).getScoring()==4) {
            iv_order_details_star1.setVisibility(View.VISIBLE);
            iv_order_details_star2.setVisibility(View.VISIBLE);
            iv_order_details_star3.setVisibility(View.VISIBLE);
            iv_order_details_star4.setVisibility(View.VISIBLE);
            iv_order_details_star5.setVisibility(View.GONE);
        } else if (obj.getMerchant_list().get(0).getScoring()==5) {
            iv_order_details_star1.setVisibility(View.VISIBLE);
            iv_order_details_star2.setVisibility(View.VISIBLE);
            iv_order_details_star3.setVisibility(View.VISIBLE);
            iv_order_details_star4.setVisibility(View.VISIBLE);
            iv_order_details_star5.setVisibility(View.VISIBLE);
        }else {
         iv_order_details_star1.setVisibility(View.GONE);
         iv_order_details_star2.setVisibility(View.GONE);
         iv_order_details_star3.setVisibility(View.GONE);
         iv_order_details_star4.setVisibility(View.GONE);
         iv_order_details_star5.setVisibility(View.GONE);
     }
        tv_order_details_dinner_time_tv.setText(obj.getDine_time());
        tv_order_details_dinner_num_tv.setText(obj.getDine_num_people()+"");
        if (obj.getIs_require_rooms() == 0) {
            tv_order_details_baojian_tv.setText("不需要");
        } else {
            tv_order_details_baojian_tv.setText("需要");
        }
        tv_order_details_invoice_info_tv.setText(obj.getInvoice());
        if (obj.getRemark().equals("")) {
            tv_order_details_remarks_tv.setText("无");
        } else {
            tv_order_details_remarks_tv.setText(obj.getRemark());
        }
        tv_order_details_contacts_tv.setText(obj.getContact_person_recipient());
        tv_order_details_phone_tv.setText(obj.getContact_person_phone());
        tv_order_details_dingdanhao.setText("订单号：" + obj.getOrder_no());
        tv_order_details_time.setText("下单时间：" + obj.getCreate_add_time());


    }
    private void getCancelsOrder(String order_id) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_id",order_id);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getCancelsOrder(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                RxBus.getInstance().post(new OrdersEvent(Config.refresh));
               finish();
            }
        });


    }


    @OnClick({R.id.tv_order_details_map_tv, R.id.tv_order_details_lijifukuan, R.id.tv_order_details_quxiao, R.id.tv_order_details_pingjia, R.id.tv_order_details_chakan, R.id.tv_order_details_jiacai, R.id.tv_order_details_tuikuan})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_order_details_map_tv:
                Intent intent=new Intent();
                intent.putExtra(com.sk.uudc.module.near.Constant.IParam.weiDu,weiDu );
                intent.putExtra(com.sk.uudc.module.near.Constant.IParam.jingDu,jingDu);
                STActivity(intent,ShangJiaMapAddress.class);
                break;
            case R.id.tv_order_details_lijifukuan:
                        CommitOrderResultObj obj=new CommitOrderResultObj();
                        obj.setOrder_no(order_no);
                        obj.setOrder_id(Integer.parseInt(order_id));
                        obj.setCombined(combined);
                        Intent place=new Intent(mContext,OrderPayActivity.class);
                        place.putExtra(com.sk.uudc.module.near.Constant.IParam.orderPayInfo,obj);
                        startActivity(place);
                        finish();

                break;
            case R.id.tv_order_details_quxiao:


                mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("确认取消订单吗?");
                mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getCancelsOrder(order_id);
                    }
                });
                mDialog.create().show();




                break;
            case R.id.tv_order_details_pingjia:
                Intent pingjia = new Intent();

                pingjia.putExtra(Constant.IParam.orderId, order_id);
                pingjia.putExtra(Constant.IParam.shangjiaImg, avatar);
                pingjia.putExtra(Constant.IParam.shangjiaName, name);
                STActivity(pingjia, BusinessEvaluationActivity.class);
                finish();
                break;
            case R.id.tv_order_details_chakan:
                break;
            case R.id.tv_order_details_jiacai:
                Intent jiaCaiIntent=new Intent(com.sk.uudc.module.near.Constant.IParam.jiaCai);
                jiaCaiIntent.putExtra(com.sk.uudc.module.near.Constant.IParam.merchant_id,merchantId);
                jiaCaiIntent.putExtra(com.sk.uudc.module.near.Constant.IParam.orderId,order_id);
                STActivity(jiaCaiIntent,com.sk.uudc.module.near.activity.ShangJiaActivity.class);
                finish();
                break;
            case R.id.tv_order_details_tuikuan:
                Intent tuikuan = new Intent();
                tuikuan.putExtra(Constant.IParam.orderId, order_id);
                STActivity(tuikuan, CancelReasonsActivity.class);
                finish();
                break;
        }
    }


}

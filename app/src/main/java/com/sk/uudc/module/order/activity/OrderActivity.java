package com.sk.uudc.module.order.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.view.MyDialog;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.activity.*;
import com.sk.uudc.module.near.network.response.CommitOrderResultObj;
import com.sk.uudc.module.order.Constant;
import com.sk.uudc.module.order.event.OrdersEvent;
import com.sk.uudc.module.order.network.ApiRequest;
import com.sk.uudc.module.order.network.response.MyOrderObj;
import com.sk.uudc.tools.TabEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/14.
 */

public class OrderActivity extends BaseActivity {

    @BindView(R.id.ctl_order)
    CommonTabLayout ctl_order;
    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    LoadMoreAdapter adapter;
    private String[] titles = {"全部", "待付款", "待使用", "待评价", "已取消"};
    private ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
    int type = 0;

    @Override
    protected int getContentView() {
        setAppTitle("我的订单");
        setAppTitleColor(this.getResources().getColor(R.color.black_33));
//        setTitleBackgroud(R.color.white);
        return R.layout.act_order;
    }



    @Override
    protected void initView() {
        getValue();
        initTabLayout();

        //(0全部 1待付款 2待发货 3待收货 4待评价)
        adapter = new LoadMoreAdapter<MyOrderObj.MyorderBean>(mContext, R.layout.item_order, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, MyOrderObj.MyorderBean bean) {
                ImageView iv_order_item_icon = holder.getImageView(R.id.iv_order_item_icon);
                TextView tv_order_item_shangjia_name = holder.getTextView(R.id.tv_order_item_shangjia_name);
                TextView tv_order_item_type = holder.getTextView(R.id.tv_order_item_type);
                TextView tv_order_item_shangpin_name = holder.getTextView(R.id.tv_order_item_shangpin_name);
                TextView tv_order_item_price = holder.getTextView(R.id.tv_order_item_price);
                TextView tv_order_item_time = holder.getTextView(R.id.tv_order_item_time);
                TextView tv_order_item_lijifukuan = holder.getTextView(R.id.tv_order_item_lijifukuan);
                TextView tv_order_item_quxiao = holder.getTextView(R.id.tv_order_item_quxiao);
                TextView tv_order_item_pingjia = holder.getTextView(R.id.tv_order_item_pingjia);
                TextView tv_order_item_chakan = holder.getTextView(R.id.tv_order_item_chakan);
                TextView tv_order_item_jiacai = holder.getTextView(R.id.tv_order_item_jiacai);
                tv_order_item_jiacai.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent(com.sk.uudc.module.near.Constant.IParam.jiaCai);
                        intent.putExtra(com.sk.uudc.module.near.Constant.IParam.merchant_id,bean.getMerchant_id());
                        intent.putExtra(com.sk.uudc.module.near.Constant.IParam.orderId,bean.getOrder_id());
                        STActivity(intent,com.sk.uudc.module.near.activity.ShangJiaActivity.class);
                    }
                });
                TextView tv_order_item_tuikuan = holder.getTextView(R.id.tv_order_item_tuikuan);
                Glide.with(mContext).
                        load(bean.getMerchant_avatar()).
                        error(R.color.c_press).
                        into(iv_order_item_icon);
                tv_order_item_shangjia_name.setText(bean.getMerchant_name());
                tv_order_item_shangpin_name.setText(bean.getGoods_name()+"等"+bean.getGoods_count()+"件商品");
                tv_order_item_price.setText("¥" + bean.getCombined());
                tv_order_item_time.setText(bean.getDine_time() + "用餐");
                if (bean.getOrder_status() == 1) {
                    tv_order_item_type.setText("待付款");
                    tv_order_item_lijifukuan.setVisibility(View.VISIBLE);
                    tv_order_item_tuikuan.setVisibility(View.GONE);
                    tv_order_item_quxiao.setVisibility(View.VISIBLE);
                    tv_order_item_chakan.setVisibility(View.GONE);
                    tv_order_item_pingjia.setVisibility(View.GONE);
                    tv_order_item_jiacai.setVisibility(View.GONE);
                } else if (bean.getOrder_status() == 2) {
                    tv_order_item_type.setText("待使用");
                    tv_order_item_lijifukuan.setVisibility(View.GONE);
                    tv_order_item_tuikuan.setVisibility(View.VISIBLE);
                    tv_order_item_quxiao.setVisibility(View.GONE);
                    tv_order_item_chakan.setVisibility(View.VISIBLE);
                    tv_order_item_pingjia.setVisibility(View.GONE);
                    tv_order_item_jiacai.setVisibility(View.GONE);
                } else if (bean.getOrder_status() == 3) {
                    if (bean.getIs_jiacai()==0) {
                        tv_order_item_jiacai.setVisibility(View.VISIBLE);
                    }else {
                        tv_order_item_jiacai.setVisibility(View.GONE);
                    }
                    tv_order_item_type.setText("待评价");
                    tv_order_item_lijifukuan.setVisibility(View.GONE);
                    tv_order_item_tuikuan.setVisibility(View.GONE);
                    tv_order_item_quxiao.setVisibility(View.GONE);
                    tv_order_item_chakan.setVisibility(View.GONE);
                    tv_order_item_pingjia.setVisibility(View.VISIBLE);
                } else if (bean.getOrder_status() == 4) {
                    tv_order_item_type.setText("已取消");
                    tv_order_item_lijifukuan.setVisibility(View.GONE);
                    tv_order_item_tuikuan.setVisibility(View.GONE);
                    tv_order_item_quxiao.setVisibility(View.GONE);
                    tv_order_item_chakan.setVisibility(View.GONE);
                    tv_order_item_pingjia.setVisibility(View.GONE);
                    tv_order_item_jiacai.setVisibility(View.GONE);
                } else if(bean.getOrder_status() == 0){
                    tv_order_item_type.setText("等待商家确认");
                    tv_order_item_lijifukuan.setVisibility(View.GONE);
                    tv_order_item_tuikuan.setVisibility(View.GONE);
                    tv_order_item_quxiao.setVisibility(View.VISIBLE);
                    tv_order_item_chakan.setVisibility(View.GONE);
                    tv_order_item_pingjia.setVisibility(View.GONE);
                    tv_order_item_jiacai.setVisibility(View.GONE);
                }else {
                    tv_order_item_type.setText("已完成");
                    tv_order_item_lijifukuan.setVisibility(View.GONE);
                    tv_order_item_tuikuan.setVisibility(View.GONE);
                    tv_order_item_quxiao.setVisibility(View.GONE);
                    tv_order_item_chakan.setVisibility(View.GONE);
                    tv_order_item_pingjia.setVisibility(View.GONE);
                    tv_order_item_jiacai.setVisibility(View.GONE);
                }
                //待评价
                tv_order_item_pingjia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent pingjia=new Intent(OrderActivity.this,BusinessEvaluationActivity.class);
                        pingjia.putExtra(Constant.IParam.orderId,bean.getOrder_id());
                        pingjia.putExtra(Constant.IParam.shangjiaImg,bean.getMerchant_avatar());
                        pingjia.putExtra(Constant.IParam.shangjiaName,bean.getMerchant_name());
//                        STActivity(pingjia,BusinessEvaluationActivity.class);
                        startActivity(pingjia);
                    }
                });
                //申请退款
                tv_order_item_tuikuan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent quxiao=new Intent();
                        quxiao.putExtra(Constant.IParam.orderId,bean.getOrder_id());
                        STActivity(quxiao,CancelReasonsActivity.class);
                    }
                });
                //取消订单
                tv_order_item_quxiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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
                                getCancelsOrder(bean.getOrder_id());
                            }
                        });
                        mDialog.create().show();
                    }
                });

                //立即付款
                tv_order_item_lijifukuan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CommitOrderResultObj obj=new CommitOrderResultObj();
                        obj.setOrder_no(bean.getOrder_no());
                        obj.setOrder_id(Integer.parseInt(bean.getOrder_id()));
                        obj.setCombined(bean.getCombined());

                        Intent place=new Intent(mContext,OrderPayActivity.class);
                        place.putExtra(com.sk.uudc.module.near.Constant.IParam.orderPayInfo,obj);
                        startActivity(place);
//                        finish();
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.orderId,bean.getOrder_id());
                        intent.putExtra(Constant.IParam.merchant_id,bean.getMerchant_id());
                        STActivity(intent,OrderDetailsActivity.class);
                    }
                });


            }


        };
        adapter.setOnLoadMoreListener(this);
        rv_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_order.setNestedScrollingEnabled(false);
        rv_order.setAdapter(adapter);

    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getRxBusEvent(OrdersEvent.class, new MySubscriber<OrdersEvent>() {
            @Override
            public void onMyNext(OrdersEvent event) {
                if (event.type.equals(Config.refresh)) {
                   showLoading();
                    getData(1, false);
                }

            }
        });
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
                showLoading();
                getData(1, false);

            }
        });


    }


    private void getValue() {
        type = getIntent().getIntExtra("type", 0);

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);

    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("type", type + "");
        map.put("pagesize", pageSize + "");
        map.put("page", page + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getMyOrder(map, new MyCallBack<MyOrderObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(MyOrderObj obj) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(obj.getMyorder(), true);
                } else {
                    pageNum = 2;
                    adapter.setList(obj.getMyorder(), true);
                }

            }
        });


    }

    private void initTabLayout() {
        for (int i = 0; i < titles.length; i++) {
            tabEntities.add(new TabEntity(titles[i], 0, 0));
        }
        ctl_order.setTabData(tabEntities);

        ctl_order.setCurrentTab(type);
        ctl_order.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Log.d("=====", "position===onTabSelect===" + position);
                type = position;
                showLoading();
                getData(1, false);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });


    }

    @Override
    protected void onViewClick(View v) {

    }


}

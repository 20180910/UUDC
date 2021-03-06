package com.sk.uudc.module.order.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyTextView;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.activity.OrderPayActivity;
import com.sk.uudc.module.near.network.response.CommitOrderResultObj;
import com.sk.uudc.module.order.Constant;
import com.sk.uudc.module.order.activity.BusinessEvaluationActivity;
import com.sk.uudc.module.order.activity.CancelReasonsActivity;
import com.sk.uudc.module.order.activity.OrderActivity;
import com.sk.uudc.module.order.activity.OrderDetailsActivity;
import com.sk.uudc.module.order.event.OrdersEvent;
import com.sk.uudc.module.order.network.ApiRequest;
import com.sk.uudc.module.order.network.response.OrderObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.github.baseclass.rx.RxBusHelper.getRxBusEvent;

/**
 * Created by Administrator on 2017/11/4.
 */

public class OrderFragment extends BaseFragment {

    @BindView(R.id.tv_all_order)
    TextView tv_all_order;
    @BindView(R.id.tv_daifukuan)
    TextView tv_daifukuan;
    @BindView(R.id.tv_daifukuan_num)
    MyTextView tv_daifukuan_num;
    @BindView(R.id.tv_daishiyong)
    TextView tv_daishiyong;
    @BindView(R.id.tv_daishiyong_num)
    MyTextView tv_daishiyong_num;
    @BindView(R.id.tv_daipingjia)
    TextView tv_daipingjia;
    @BindView(R.id.tv_daipingjia_num)
    MyTextView tv_daipingjia_num;
    @BindView(R.id.tv_yiquxiao)
    TextView tv_yiquxiao;
    @BindView(R.id.tv_yiquxiao_num)
    MyTextView tv_yiquxiao_num;
    @BindView(R.id.rv_zuijian)
    RecyclerView rv_zuijian;

    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {

        return R.layout.frag_order;
    }

    @Override
    protected void initView() {
        rv_zuijian.setFocusable(false);
        adapter = new LoadMoreAdapter<OrderObj.OrderListBean>(mContext, R.layout.item_order, pageSize,nsv) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, OrderObj.OrderListBean bean) {
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
                tv_order_item_price.setText("¥"+bean.getCombined());
                tv_order_item_time.setText(bean.getDine_time()+"用餐");
                if (bean.getOrder_status()==1) {
                    tv_order_item_type.setText("待付款");
                   tv_order_item_lijifukuan.setVisibility(View.VISIBLE);
                    tv_order_item_quxiao.setVisibility(View.VISIBLE);
                    tv_order_item_chakan.setVisibility(View.GONE);
                   tv_order_item_pingjia.setVisibility(View.GONE);
                   tv_order_item_jiacai.setVisibility(View.GONE);
                    tv_order_item_tuikuan.setVisibility(View.GONE);

                }else if (bean.getOrder_status()==2){
                    tv_order_item_type.setText("待使用");
                    tv_order_item_lijifukuan.setVisibility(View.GONE);
                    tv_order_item_quxiao.setVisibility(View.GONE);
                    tv_order_item_chakan.setVisibility(View.VISIBLE);
                    tv_order_item_pingjia.setVisibility(View.GONE);
                    tv_order_item_jiacai.setVisibility(View.GONE);
                    tv_order_item_tuikuan.setVisibility(View.VISIBLE);
                }else if (bean.getOrder_status()==3){
                    if (bean.getIs_jiacai()==0) {
                        tv_order_item_jiacai.setVisibility(View.VISIBLE);
                    }else {
                        tv_order_item_jiacai.setVisibility(View.GONE);
                    }

                    tv_order_item_type.setText("待评价");
                    tv_order_item_lijifukuan.setVisibility(View.GONE);
                    tv_order_item_quxiao.setVisibility(View.GONE);
                    tv_order_item_chakan.setVisibility(View.GONE);
                    tv_order_item_tuikuan.setVisibility(View.GONE);
                    tv_order_item_pingjia.setVisibility(View.VISIBLE);

                }else if (bean.getOrder_status()==4){
                    tv_order_item_type.setText("已取消");
                    tv_order_item_tuikuan.setVisibility(View.GONE);
                    tv_order_item_lijifukuan.setVisibility(View.GONE);
                    tv_order_item_quxiao.setVisibility(View.GONE);
                    tv_order_item_chakan.setVisibility(View.GONE);
                    tv_order_item_pingjia.setVisibility(View.GONE);
                    tv_order_item_jiacai.setVisibility(View.GONE);
                }else if (bean.getOrder_status()==0){//0等待商家确认
                    tv_order_item_type.setText("等待商家确认");
                    tv_order_item_tuikuan.setVisibility(View.GONE);
                    tv_order_item_lijifukuan.setVisibility(View.GONE);
                    tv_order_item_quxiao.setVisibility(View.VISIBLE);
                    tv_order_item_chakan.setVisibility(View.GONE);
                    tv_order_item_pingjia.setVisibility(View.GONE);
                    tv_order_item_jiacai.setVisibility(View.GONE);
                }else{
                    tv_order_item_type.setText("已完成");
                    tv_order_item_tuikuan.setVisibility(View.GONE);
                    tv_order_item_lijifukuan.setVisibility(View.GONE);
                    tv_order_item_quxiao.setVisibility(View.GONE);
                    tv_order_item_chakan.setVisibility(View.GONE);
                    tv_order_item_pingjia.setVisibility(View.GONE);
                    tv_order_item_jiacai.setVisibility(View.GONE);
                }
                //待评价
                tv_order_item_pingjia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent pingjia=new Intent();
                        pingjia.putExtra(Constant.IParam.orderId,bean.getOrder_id());
                        pingjia.putExtra(Constant.IParam.shangjiaImg, bean.getMerchant_avatar());
                        pingjia.putExtra(Constant.IParam.shangjiaName, bean.getMerchant_name());
                        STActivity(pingjia,BusinessEvaluationActivity.class);
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
                    }
                });
              holder.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent=new Intent();
                      intent.putExtra(Constant.IParam.orderId,bean.getOrder_id());
                      intent.putExtra(Constant.IParam.merchant_id,bean.getMerchant_id());
                      Log.d("====","order_id==="+bean.getOrder_id());
                      STActivity(intent,OrderDetailsActivity.class);
                  }
              });




            }


        };
        adapter.setOnLoadMoreListener(this);
        rv_zuijian.setLayoutManager(new LinearLayoutManager(mContext));
        rv_zuijian.setNestedScrollingEnabled(false);
        rv_zuijian.setAdapter(adapter);

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
               getData(1,false);
           }
       });


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
        map.put("pagesize", pageSize + "");
        map.put("page", page + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getOrder(map, new MyCallBack<OrderObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(OrderObj obj) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(obj.getOrder_list(), true);
                } else {

                    setValue(obj);
                    pageNum = 2;
                    adapter.setList(obj.getOrder_list(), true);
                }

            }
        });

    }

    private void setValue(OrderObj obj) {
        tv_daifukuan_num.setText(obj.getDaifukuan() + "");
        tv_daishiyong_num.setText(obj.getDaishiyong() + "");
        tv_daipingjia_num.setText(obj.getDaipingjia() + "");
        tv_yiquxiao_num.setText(obj.getYiquxiao() + "");

        if (obj.getDaifukuan() == 0) {
            tv_daifukuan_num.setVisibility(View.GONE);
        } else {
            tv_daifukuan_num.setVisibility(View.VISIBLE);
        }
        if (obj.getDaishiyong() == 0) {
            tv_daishiyong_num.setVisibility(View.GONE);
        } else {
            tv_daishiyong_num.setVisibility(View.VISIBLE);
        }
        if (obj.getDaipingjia() == 0) {
            tv_daipingjia_num.setVisibility(View.GONE);
        } else {
            tv_daipingjia_num.setVisibility(View.VISIBLE);
        }
        if (obj.getYiquxiao() == 0) {
            tv_yiquxiao_num.setVisibility(View.GONE);
        } else {
            tv_yiquxiao_num.setVisibility(View.VISIBLE);
        }


    }

    @OnClick({R.id.tv_all_order, R.id.tv_daifukuan, R.id.tv_daishiyong, R.id.tv_daipingjia, R.id.tv_yiquxiao})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all_order:
                Intent all = new Intent(getActivity(), OrderActivity.class);
                all.putExtra("type", 0);
                startActivity(all);
                break;
            case R.id.tv_daifukuan:
                Intent daifukuan = new Intent(getActivity(), OrderActivity.class);
                daifukuan.putExtra("type", 1);
                startActivity(daifukuan);
                break;
            case R.id.tv_daishiyong:
                Intent daishiyong = new Intent(getActivity(), OrderActivity.class);
                daishiyong.putExtra("type", 2);
                startActivity(daishiyong);
                break;
            case R.id.tv_daipingjia:
                Intent daipingjia = new Intent(getActivity(), OrderActivity.class);
                daipingjia.putExtra("type", 3);
                startActivity(daipingjia);
                break;
            case R.id.tv_yiquxiao:
                Intent yiquxiao = new Intent(getActivity(), OrderActivity.class);
                yiquxiao.putExtra("type", 4);
                startActivity(yiquxiao);
                break;
        }
    }

}

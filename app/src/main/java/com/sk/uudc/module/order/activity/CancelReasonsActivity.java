package com.sk.uudc.module.order.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.rx.RxBus;
import com.github.customview.MyTextView;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.order.Constant;
import com.sk.uudc.module.order.event.OrdersEvent;
import com.sk.uudc.module.order.network.ApiRequest;
import com.sk.uudc.module.order.network.response.OrderCancelReasonObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/14.
 */

public class CancelReasonsActivity extends BaseActivity {
    @BindView(R.id.rv_cancel_reason)
    RecyclerView rv_cancel_reason;
    @BindView(R.id.et_cancel_reason_content)
    EditText et_cancel_reason_content;
    @BindView(R.id.tv_cancel_reason_submit)
    MyTextView tv_cancel_reason_submit;
    @BindView(R.id.tv_cancel_reason)
    TextView tv_cancel_reason;
    BaseRecyclerAdapter adapter;
    String etResout="";
    String resout="";
    String content="";
    String order_id;
    int index = -1;


    @Override
    protected int getContentView() {
        setAppTitle("退款原因");
//        setAppTitleColor(R.color.black_33);
//        setTitleBackgroud(R.color.white);
        return R.layout.act_cancel_reason;
    }

    @Override
    protected void initView() {
        getValue();

        adapter = new BaseRecyclerAdapter<OrderCancelReasonObj.CancelReasonListBean>(mContext, R.layout.item_cancel_reason) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, OrderCancelReasonObj.CancelReasonListBean bean) {
                TextView title_tv = holder.getTextView(R.id.title_tv);
                CheckBox checkbox = (CheckBox) holder.getView(R.id.checkbox);
                title_tv.setText(bean.getContent());
                if (index == i) {
                    checkbox.setChecked(true);
                }else {
                    checkbox.setChecked(false);
                }


                checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkbox.setChecked(true);
                        index = i;
                        notifyDataSetChanged();

                        if (bean.getId().equals("0")) {
                            resout="";
                        }else {
                            resout=bean.getContent();
                        }





                    }

                });


            }


        };
        rv_cancel_reason.setLayoutManager(new LinearLayoutManager(mContext));
        rv_cancel_reason.setNestedScrollingEnabled(false);
        rv_cancel_reason.setAdapter(adapter);


    }

    private void getValue() {
        order_id = getIntent().getStringExtra(Constant.IParam.orderId);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();

    }

    private void getData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getCancelReason(map, new MyCallBack<OrderCancelReasonObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(OrderCancelReasonObj obj) {
                tv_cancel_reason.setText(obj.getTitle());
                adapter.setList(obj.getCancel_reason_list(), true);
            }
        });

    }


    @Override
    protected void onViewClick(View v) {

    }


    @OnClick(R.id.tv_cancel_reason_submit)
    public void onClick() {
        etResout = getSStr(et_cancel_reason_content);
        if (resout.equals("")&&etResout.equals("")) {
            showMsg("请选择或者输入取消原因！");
            return;
        }
        content=resout+etResout;
        getCancelOrder();

        Log.d("=======", "content===" + content);


    }

    private void getCancelOrder() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("order_id", order_id);
        map.put("content", content);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getCancelOrder(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                RxBus.getInstance().post(new OrdersEvent(Config.refresh));
                finish();

            }
        });

    }
}

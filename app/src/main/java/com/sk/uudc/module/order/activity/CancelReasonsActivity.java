package com.sk.uudc.module.order.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.order.Constant;
import com.sk.uudc.module.order.network.ApiRequest;
import com.sk.uudc.module.order.network.response.OrderCancelReasonObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    BaseRecyclerAdapter adapter;
    String etResout;
    String content;
    String order_id;
    @Override
    protected int getContentView() {
        setAppTitle("取消原因");
//        setAppTitleColor(R.color.black_33);
//        setTitleBackgroud(R.color.white);
        return R.layout.act_cancel_reason;
    }

    @Override
    protected void initView() {
        getValue();

        adapter=new BaseRecyclerAdapter<OrderCancelReasonObj>(mContext,R.layout.item_cancel_reason) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, OrderCancelReasonObj bean) {
                TextView title_tv = holder.getTextView(R.id.title_tv);
                CheckBox checkbox = (CheckBox) holder.getView(R.id.checkbox);
                title_tv.setText(bean.getContent());
                checkbox.setChecked(bean.isSelect());
               checkbox.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
//                       ((OrderCancelReasonObj)adapter.getList().get(i)).setSelect(checkbox.isChecked());
                       bean.setSelect(checkbox.isChecked());
                   }

               });


            }


        };
        rv_cancel_reason.setLayoutManager(new LinearLayoutManager(mContext));
        rv_cancel_reason.setNestedScrollingEnabled(false);
        rv_cancel_reason.setAdapter(adapter);


    }

    private void getValue() {
        order_id=getIntent().getStringExtra(Constant.IParam.orderId);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();

    }

    private void getData() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getCancelReason(map, new MyCallBack<List<OrderCancelReasonObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<OrderCancelReasonObj> obj) {
                adapter.setList(obj,true);
            }
        });

    }


    @Override
    protected void onViewClick(View v) {

    }




    @OnClick(R.id.tv_cancel_reason_submit)
    public void onClick() {
        etResout=getSStr(et_cancel_reason_content);
        ArrayList<String>resoutList=new ArrayList<>();
        ArrayList<OrderCancelReasonObj> list = (ArrayList<OrderCancelReasonObj>) adapter.getList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelect()) {
                resoutList.add(list.get(i).getContent());
            }
        }
        String resoutStr="";
        String resout="";
        for (int i = 0; i < resoutList.size(); i++) {
            resout=resoutList.get(i);
            resoutStr=resoutStr+resout+",";
        }
        if (resoutList.size()==0&& TextUtils.isEmpty(etResout)) {
            showMsg("请选择或者输入取消原因！");
            return;
        }
        if (TextUtils.isEmpty(etResout)) {
            content=resoutStr.substring(0,resoutStr.length()-1);
        }else {
            if (resoutList.size()==0) {
                content=etResout;
            }else {
                content=etResout+","+resoutStr.substring(0,resoutStr.length()-1);
            }

        }
        showProgress();
        getCancelOrder();

        Log.d("=======","content==="+content);


    }

    private void getCancelOrder() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_id",order_id);
        map.put("content",content);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getCancelOrder(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                finish();

            }
        });

    }
}

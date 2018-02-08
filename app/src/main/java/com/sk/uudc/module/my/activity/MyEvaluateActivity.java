package com.sk.uudc.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyLinearLayout;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.adapter.MyEvaluateImgAdapter;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.MyEvaluateObj;
import com.sk.uudc.module.order.Constant;
import com.sk.uudc.module.order.activity.BusinessEvaluationActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MyEvaluateActivity extends BaseActivity {
    @BindView(R.id.rv_dai_evaluate)
    RecyclerView rv_dai_evaluate;
    @BindView(R.id.tv_my_evaluate_num)
    MyTextView tv_my_evaluate_num;
    @BindView(R.id.ll_my_evaluate_num)
    MyLinearLayout ll_my_evaluate_num;
    @BindView(R.id.iv_my_evaluate_icon)
    ImageView iv_my_evaluate_icon;
    @BindView(R.id.tv_my_evaluate_name)
    TextView tv_my_evaluate_name;
    @BindView(R.id.tv_my_evaluate_time)
    TextView tv_my_evaluate_time;
    @BindView(R.id.tv_my_evaluate_qushaidan)
    MyTextView tv_my_evaluate_qushaidan;
    @BindView(R.id.ll_my_evaluate_dai)
    LinearLayout ll_my_evaluate_dai;
    private LoadMoreAdapter adapter;
    MyEvaluateImgAdapter mAdapter;

    String order_id,avatar,name;

    @Override
    protected int getContentView() {
        setAppTitle("我的评价");
        return R.layout.act_dai_evaluate;
    }

    @Override
    protected void initView() {
        adapter = new LoadMoreAdapter<MyEvaluateObj.ListBean>(mContext, R.layout.item_my_evaluate, pageSize,nsv) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, MyEvaluateObj.ListBean bean) {
                TextView tv_item_my_evaluate_content = holder.getTextView(R.id.tv_item_my_evaluate_content);
                TextView tv_item_my_evaluate_num = holder.getTextView(R.id.tv_item_my_evaluate_num);
                TextView tv_item_my_evaluate_time = holder.getTextView(R.id.tv_item_my_evaluate_time);
                TextView tv_item_my_evaluate_name = holder.getTextView(R.id.tv_item_my_evaluate_name);
                ImageView iv_item_my_evaluate_star1 = holder.getImageView(R.id.iv_item_my_evaluate_star1);
                ImageView iv_item_my_evaluate_star2 = holder.getImageView(R.id.iv_item_my_evaluate_star2);
                ImageView iv_item_my_evaluate_star3 = holder.getImageView(R.id.iv_item_my_evaluate_star3);
                ImageView iv_item_my_evaluate_star4 = holder.getImageView(R.id.iv_item_my_evaluate_star4);
                ImageView iv_item_my_evaluate_star5 = holder.getImageView(R.id.iv_item_my_evaluate_star5);
                ImageView iv_item_my_evaluate_icon = holder.getImageView(R.id.iv_item_my_evaluate_icon);
                RecyclerView rv_item_my_evaluate = (RecyclerView) holder.getView(R.id.rv_item_my_evaluate);
                List<String>img=new ArrayList<>();
                if (bean.getMerchant_appraise_image().size()>0) {
                    rv_item_my_evaluate.setVisibility(View.VISIBLE);
                    for (int i = 0; i < bean.getMerchant_appraise_image().size(); i++) {
                        img.add(bean.getMerchant_appraise_image().get(i));
                    }
                    rv_item_my_evaluate.setLayoutManager(new GridLayoutManager(mContext,3));
                    rv_item_my_evaluate.setNestedScrollingEnabled(false);
                    mAdapter=new MyEvaluateImgAdapter(mContext,img);
                    rv_item_my_evaluate.setAdapter(mAdapter);



                }else {
                    rv_item_my_evaluate.setVisibility(View.GONE);

                }

                tv_item_my_evaluate_content.setText(bean.getContent());
                tv_item_my_evaluate_num.setText(bean.getScoring()+"");
                tv_item_my_evaluate_time.setText(bean.getAdd_time());
                tv_item_my_evaluate_name.setText(bean.getMerchant_name());
                Glide.with(mContext).
                        load(bean.getMerchant_avatar()).
                        error(R.color.c_press).
                        into(iv_item_my_evaluate_icon);
                if (bean.getScoring()==1) {
                    iv_item_my_evaluate_star1.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star2.setVisibility(View.GONE);
                    iv_item_my_evaluate_star3.setVisibility(View.GONE);
                    iv_item_my_evaluate_star4.setVisibility(View.GONE);
                    iv_item_my_evaluate_star5.setVisibility(View.GONE);
                }else if (bean.getScoring()==2){
                    iv_item_my_evaluate_star1.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star2.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star3.setVisibility(View.GONE);
                    iv_item_my_evaluate_star4.setVisibility(View.GONE);
                    iv_item_my_evaluate_star5.setVisibility(View.GONE);
                }else if (bean.getScoring()==3){
                    iv_item_my_evaluate_star1.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star2.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star3.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star4.setVisibility(View.GONE);
                    iv_item_my_evaluate_star5.setVisibility(View.GONE);
                }else if (bean.getScoring()==4){
                    iv_item_my_evaluate_star1.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star2.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star3.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star4.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star5.setVisibility(View.GONE);
                }else if (bean.getScoring()==5){
                    iv_item_my_evaluate_star1.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star2.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star3.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star4.setVisibility(View.VISIBLE);
                    iv_item_my_evaluate_star5.setVisibility(View.VISIBLE);
                }
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_dai_evaluate.setLayoutManager(new LinearLayoutManager(mContext));
        rv_dai_evaluate.setNestedScrollingEnabled(false);
        rv_dai_evaluate.addItemDecoration(getItemDivider());
        rv_dai_evaluate.setAdapter(adapter);


    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void myReStart() {
        super.myReStart();
        showLoading();
        getData(1,false);

    }


    @Override
    protected void getData(int page, boolean isLoad) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("pagesize", pageSize + "");
        map.put("page", page + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getMyAppraise(map, new MyCallBack<MyEvaluateObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(MyEvaluateObj obj) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getList(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getList(),true);
                    setValue(obj);
                }



            }
        });

    }

    private void setValue(MyEvaluateObj obj) {
        if (obj.getDaipingjia()>0) {
            ll_my_evaluate_dai.setVisibility(View.VISIBLE);
            tv_my_evaluate_num.setText(obj.getDaipingjia()+"");
            tv_my_evaluate_name.setText(obj.getDpj_list().get(0).getMerchant_name());
            tv_my_evaluate_time.setText(obj.getDpj_list().get(0).getDine_time()+"消费未评价");
            Glide.with(mContext).
                    load(obj.getDpj_list().get(0).getMerchant_avatar()).
                    error(R.color.c_press).
                    into(iv_my_evaluate_icon);
            order_id=obj.getDpj_list().get(0).getOrder_id();
            avatar=obj.getDpj_list().get(0).getMerchant_avatar();
            name=obj.getDpj_list().get(0).getMerchant_name();
        }else {
            ll_my_evaluate_dai.setVisibility(View.GONE);

        }


    }



    @OnClick({R.id.ll_my_evaluate_num, R.id.tv_my_evaluate_qushaidan})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_my_evaluate_num:
                STActivity(DaiEvaluateActivity.class);
                break;
            case R.id.tv_my_evaluate_qushaidan:
                Intent pingjia=new Intent();
                pingjia.putExtra(Constant.IParam.orderId,order_id);
                pingjia.putExtra(Constant.IParam.shangjiaImg,avatar);
                pingjia.putExtra(Constant.IParam.shangjiaName,name);
                STActivity(pingjia,BusinessEvaluationActivity.class);
                break;
        }
    }
}

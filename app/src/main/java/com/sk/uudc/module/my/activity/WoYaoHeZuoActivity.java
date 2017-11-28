package com.sk.uudc.module.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.ComperationObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/6.
 */

public class WoYaoHeZuoActivity extends BaseActivity {
    @BindView(R.id.rv_woyaohezuo_changjianwenti)
    RecyclerView rv_woyaohezuo_changjianwenti;
    @BindView(R.id.iv_woyaohezuo_icon1)
    ImageView iv_woyaohezuo_icon1;
    @BindView(R.id.iv_woyaohezuo_icon2)
    ImageView iv_woyaohezuo_icon2;
    @BindView(R.id.iv_woyaohezuo_icon3)
    ImageView iv_woyaohezuo_icon3;
    @BindView(R.id.iv_woyaohezuo_icon4)
    ImageView iv_woyaohezuo_icon4;
    @BindView(R.id.tv_woyaohezuo_shenqing)
    MyTextView tv_woyaohezuo_shenqing;

    BaseRecyclerAdapter adapter;


    @Override
    protected int getContentView() {
        setAppTitle("我要合作");
        setAppTitleColor(this.getResources().getColor(R.color.black_33));
        setTitleBackgroud(R.color.white);
        return R.layout.act_wo_yao_he_zuo;
    }

    @Override
    protected void initView() {
        adapter = new BaseRecyclerAdapter<ComperationObj.RoastingListBean>(mContext, R.layout.item_woyaohezuo) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, ComperationObj.RoastingListBean bean) {
                TextView tv_item_woyaohezuo_question = holder.getTextView(R.id.tv_item_woyaohezuo_question);
                TextView tv_item_woyaohezuo_answer = holder.getTextView(R.id.tv_item_woyaohezuo_answer);
                int num = i + 1;
                tv_item_woyaohezuo_question.setText("Q" + num + " " + bean.getQuestion());
                tv_item_woyaohezuo_answer.setText("答：" + bean.getContent());

            }


        };
        rv_woyaohezuo_changjianwenti.setLayoutManager(new LinearLayoutManager(mContext));
        rv_woyaohezuo_changjianwenti.setNestedScrollingEnabled(false);
        rv_woyaohezuo_changjianwenti.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getComperation();


    }

    private void getComperation() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getComperation(map, new MyCallBack<ComperationObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(ComperationObj obj) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = (int) (PhoneUtils.getScreenWidth(mContext) / 2.1);
                layoutParams.width = PhoneUtils.getScreenWidth(mContext);
                iv_woyaohezuo_icon1.setLayoutParams(layoutParams);
                Glide.with(mContext).
                        load(obj.getCooperation_image().get(0).getImg_url()).
                        error(R.color.c_press).
                        into(iv_woyaohezuo_icon1);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams2.width = PhoneUtils.getScreenWidth(mContext);
                layoutParams2.height = PhoneUtils.getScreenWidth(mContext);
                iv_woyaohezuo_icon2.setLayoutParams(layoutParams2);
                Glide.with(mContext).
                        load(obj.getPlatform_advantages().get(0).getImg_url()).
                        error(R.color.c_press).
                        into(iv_woyaohezuo_icon2);
                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams3.width = PhoneUtils.getScreenWidth(mContext);
                layoutParams3.height = PhoneUtils.getScreenWidth(mContext) * 2;
                iv_woyaohezuo_icon3.setLayoutParams(layoutParams3);
                Glide.with(mContext).
                        load(obj.getRecruitment_progress().get(0).getImg_url()).
                        error(R.color.c_press).
                        into(iv_woyaohezuo_icon3);
                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams4.width = PhoneUtils.getScreenWidth(mContext);
                layoutParams4.height = PhoneUtils.getScreenWidth(mContext);
                iv_woyaohezuo_icon4.setLayoutParams(layoutParams4);
                Glide.with(mContext).
                        load(obj.getRegistration_process().get(0).getImg_url()).
                        error(R.color.c_press).
                        into(iv_woyaohezuo_icon4);
                adapter.setList(obj.getRoasting_list(), true);
            }
        });
    }

    @Override
    protected void onViewClick(View v) {

    }

    @OnClick(R.id.tv_woyaohezuo_shenqing)
    public void onClick() {
        STActivity(ShenQingHeHuoActivity.class);
    }
}

package com.sk.uudc.module.near.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.response.HongBaoObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/5.
 */

public class ShiXiaoHongBaoActivity extends BaseActivity {
    @BindView(R.id.rv_hongbao)
    RecyclerView rv_hongbao;

    LoadMoreAdapter adapter;

    private int youHuiType;
    private String title;

    @Override
    protected int getContentView() {
        return R.layout.act_shi_xiao_hongbao;
    }

    @Override
    protected void initView() {
        youHuiType=getIntent().getIntExtra(Constant.IParam.youHuiType,Constant.IParam.youHuiType_1);
        if (youHuiType == Constant.IParam.youHuiType_1) {//红包
            title="红包";
        } else {//优惠券
            title="优惠券";
        }
        setAppTitle("失效"+ title);
        adapter = new LoadMoreAdapter<HongBaoObj>(mContext, R.layout.item_shixiao_hongbao, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, HongBaoObj bean) {
                holder.setText(R.id.tv_hongbao_title, bean.getTitle())
                        .setText(R.id.tv_hongbao_jine, bean.getFace_value() + "")
                        .setText(R.id.tv_hongbao_content, "满" + bean.getAvailable() + "使用")
                        .setText(R.id.tv_hongbao_time, "有效期至" + bean.getEnd_time());

                View ll_hongbao = holder.getView(R.id.ll_hongbao);
                ImageView iv_youhuiquan = holder.getImageView(R.id.iv_youhuiquan);
                if (youHuiType == Constant.IParam.youHuiType_1) {//红包
                    ll_hongbao.setVisibility(View.VISIBLE);
                    iv_youhuiquan.setVisibility(View.GONE);
                } else {//优惠券
                    ll_hongbao.setVisibility(View.GONE);
                    iv_youhuiquan.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(bean.getPhoto()).error(R.color.c_press).into(iv_youhuiquan);
                }
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_hongbao.setLayoutManager(new LinearLayoutManager(mContext));
        rv_hongbao.addItemDecoration(getItemDivider());
        rv_hongbao.setAdapter(adapter);
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
        map.put("type", youHuiType + "");
        map.put("status", "2");
        map.put("pagesize", pageSize + "");
        map.put("page", page + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getHongBaoYouHuiQuan(map, new MyCallBack<List<HongBaoObj>>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(List<HongBaoObj> list) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(list, true);
                } else {
                    pageNum = 2;
                    adapter.setList(list, true);
                }
            }
        });
    }

    @Override
    protected void onViewClick(View v) {

    }
}

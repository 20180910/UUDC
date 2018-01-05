package com.sk.uudc.module.home.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.home.Constant;
import com.sk.uudc.module.home.network.ApiRequest;
import com.sk.uudc.module.home.network.response.GongGaoObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/5.
 */

public class GongGaoActivity extends BaseActivity {
    @BindView(R.id.rv_gonggao)
    RecyclerView rv_gonggao;
    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("公告");
        return R.layout.act_gong_gao;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<GongGaoObj>(mContext,R.layout.item_gonggao,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, GongGaoObj bean) {
                holder.setText(R.id.tv_gonggao_title,bean.getTitle());
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.gongGao,bean);
                        STActivity(intent,GongGaoDetailActivity.class);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_gonggao.setLayoutManager(new LinearLayoutManager(mContext));
        rv_gonggao.addItemDecoration(getItemDivider());
        rv_gonggao.setAdapter(adapter);



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
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getGongGao(map, new MyCallBack<List<GongGaoObj>>(mContext) {
            @Override
            public void onSuccess(List<GongGaoObj> list) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(list,true);
                }else{
                    pageNum=2;
                    adapter.setList(list,true);
                }
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
}

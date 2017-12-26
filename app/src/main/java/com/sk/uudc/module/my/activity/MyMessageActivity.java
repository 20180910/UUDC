package com.sk.uudc.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.Constant;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.MessageListObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/6.
 */

public class MyMessageActivity extends BaseActivity {
    @BindView(R.id.rv_my_message)
    RecyclerView rv_my_message;
    LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("我的消息");
//        setAppTitleColor(this.getResources().getColor(R.color.gray_33));
        setTitleBackgroud(R.color.white);
        return R.layout.act_my_message;
    }

    @Override
    protected void initView() {

        adapter = new LoadMoreAdapter<MessageListObj>(mContext, R.layout.item_my_message, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, MessageListObj bean) {
                TextView tv_item_my_message_title=holder.getTextView(R.id.tv_item_my_message_title);
                TextView tv_item_my_message_type=holder.getTextView(R.id.tv_item_my_message_type);
                TextView tv_item_my_message_time=holder.getTextView(R.id.tv_item_my_message_time);
                TextView tv_item_my_message_zhaiyao=holder.getTextView(R.id.tv_item_my_message_zhaiyao);
                tv_item_my_message_title.setText(bean.getTitle());
                tv_item_my_message_time.setText(bean.getAdd_time());
                tv_item_my_message_zhaiyao.setText(bean.getContent());
                if (bean.getIs_check()==0) {
                    tv_item_my_message_type.setVisibility(View.INVISIBLE);
                }else {
                    tv_item_my_message_type.setVisibility(View.VISIBLE);
                }
            }
        };
        adapter.setOnLoadMoreListener(this);
        adapter.setClickListener(new LoadMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                MessageListObj obj = (MessageListObj) adapter.getList().get(i);
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.msgId,obj.getId()+"");
                STActivity(intent,MessageDetailActivity.class);

            }
        });

        rv_my_message.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_message.addItemDecoration(getItemDivider());
        rv_my_message.setAdapter(adapter);



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

    //
    protected void getData(int page, boolean isLoad) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("page", page + "");
        map.put("pagesize", pageSize + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getNewsList(map, new MyCallBack<List<MessageListObj>>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(List<MessageListObj> list) {
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

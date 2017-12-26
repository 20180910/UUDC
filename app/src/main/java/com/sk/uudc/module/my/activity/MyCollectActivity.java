package com.sk.uudc.module.my.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.request.DelMyCollectionBody;
import com.sk.uudc.module.my.network.response.CollectObj;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.activity.ShangJiaActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MyCollectActivity extends BaseActivity {
    @BindView(R.id.rv_my_collect)
    RecyclerView rv_my_collect;
    @BindView(R.id.tv_my_collect_delete)
    MyTextView tv_my_collect_delete;

    private boolean isEdit;

    private LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("我的收藏");
        setAppRightTitle("编辑");
        setAppTitleColor(this.getResources().getColor(R.color.gray_33));
        setTitleBackgroud(R.color.white);
        setAppRightTitleColor(ContextCompat.getColor(mContext, R.color.home_green));
        return R.layout.act_my_collect;
    }

    @Override
    protected void initView() {
        adapter = new LoadMoreAdapter<CollectObj.MyCollectionBean>(mContext, R.layout.item_my_collect, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, CollectObj.MyCollectionBean bean) {

                TextView tv_item_my_collect_name = holder.getTextView(R.id.tv_item_my_collect_name);
                TextView tv_item_my_collect_address = holder.getTextView(R.id.tv_item_my_collect_address);
                TextView tv_item_my_collect_num = holder.getTextView(R.id.tv_item_my_collect_num);
                TextView tv_item_my_collect_juli = holder.getTextView(R.id.tv_item_my_collect_juli);
                ImageView iv_item_my_collect_icon = holder.getImageView(R.id.iv_item_my_collect_icon);
                ImageView iv_item_my_collect_star1 = holder.getImageView(R.id.iv_item_my_collect_star1);
                ImageView iv_item_my_collect_star2 = holder.getImageView(R.id.iv_item_my_collect_star2);
                ImageView iv_item_my_collect_star3 = holder.getImageView(R.id.iv_item_my_collect_star3);
                ImageView iv_item_my_collect_star4 = holder.getImageView(R.id.iv_item_my_collect_star4);
                ImageView iv_item_my_collect_star5 = holder.getImageView(R.id.iv_item_my_collect_star5);
                tv_item_my_collect_name.setText(bean.getMerchant_name());
                tv_item_my_collect_address.setText(bean.getMerchant_address());
                tv_item_my_collect_num.setText(bean.getScoring());
                tv_item_my_collect_juli.setText(bean.getDistance());

                Glide.with(mContext).
                        load(bean.getMerchant_avatar()).
                        error(R.color.gray).
                        into(iv_item_my_collect_icon);
                if (bean.getScoring().equals("1")) {
                    iv_item_my_collect_star1.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star2.setVisibility(View.INVISIBLE);
                    iv_item_my_collect_star3.setVisibility(View.INVISIBLE);
                    iv_item_my_collect_star4.setVisibility(View.INVISIBLE);
                    iv_item_my_collect_star5.setVisibility(View.INVISIBLE);
                } else if (bean.getScoring().equals("2")) {
                    iv_item_my_collect_star1.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star2.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star3.setVisibility(View.INVISIBLE);
                    iv_item_my_collect_star4.setVisibility(View.INVISIBLE);
                    iv_item_my_collect_star5.setVisibility(View.INVISIBLE);


                } else if (bean.getScoring().equals("3")) {
                    iv_item_my_collect_star1.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star2.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star3.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star4.setVisibility(View.INVISIBLE);
                    iv_item_my_collect_star5.setVisibility(View.INVISIBLE);

                } else if (bean.getScoring().equals("4")) {
                    iv_item_my_collect_star1.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star2.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star3.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star4.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star5.setVisibility(View.INVISIBLE);

                } else if (bean.getScoring().equals("5")) {
                    iv_item_my_collect_star1.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star2.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star3.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star4.setVisibility(View.VISIBLE);
                    iv_item_my_collect_star5.setVisibility(View.VISIBLE);

                } else {
                    iv_item_my_collect_star1.setVisibility(View.INVISIBLE);
                    iv_item_my_collect_star2.setVisibility(View.INVISIBLE);
                    iv_item_my_collect_star3.setVisibility(View.INVISIBLE);
                    iv_item_my_collect_star4.setVisibility(View.INVISIBLE);
                    iv_item_my_collect_star5.setVisibility(View.INVISIBLE);
                }

                CheckBox ch_item_my_collect = (CheckBox) holder.getView(R.id.ch_item_my_collect);
                if (isEdit) {
                    ch_item_my_collect.setVisibility(View.VISIBLE);
                } else {
                    ch_item_my_collect.setVisibility(View.GONE);
                }
                ch_item_my_collect.setChecked(bean.isSelect());

                ch_item_my_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((CollectObj.MyCollectionBean) adapter.getList().get(position)).setSelect(ch_item_my_collect.isChecked());
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra(Constant.IParam.merchant_id, bean.getMerchant_id() + "");
                        STActivity(intent, ShangJiaActivity.class);


                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_my_collect.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_collect.addItemDecoration(getItemDivider());
        rv_my_collect.setAdapter(adapter);


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
        map.put("page", page + "");
        map.put("pagesize", pageSize + "");
        map.put("sign", GetSign.getSign(map));

        ApiRequest.getMyCollection(map, new MyCallBack<CollectObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(CollectObj obj) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(obj.getMyCollection(), true);
                } else {
                    pageNum = 2;
                    adapter.setList(obj.getMyCollection(), true);
                }
            }
        });
    }

    @OnClick({R.id.app_right_tv, R.id.tv_my_collect_delete})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.app_right_tv:
                isEdit = !isEdit;
                if (isEdit) {
                    setAppRightTitle("完成");
                    tv_my_collect_delete.setVisibility(View.VISIBLE);
                } else {
                    setAppRightTitle("编辑");
                    tv_my_collect_delete.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_my_collect_delete:
                ArrayList<CollectObj.MyCollectionBean> list = (ArrayList<CollectObj.MyCollectionBean>) adapter.getList();
                ArrayList<String> merchant_id_list = new ArrayList<>();
                DelMyCollectionBody body;
                List<DelMyCollectionBody>bodyList=new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelect() == true) {
                        body=new DelMyCollectionBody();
                        merchant_id_list.add(list.get(i).getMerchant_id());
                        body.setMerchant_id(list.get(i).getMerchant_id());
                        bodyList.add(body);
                    }
                }
                Log.d("=======", "merchant_id_list==" + merchant_id_list.size());
                if (bodyList.size() == 0) {
                    showMsg("请选择商品再删除!");
                    return;
                }
                delMyCollect(bodyList);


                break;
        }
    }

    private void delMyCollect( List<DelMyCollectionBody> body) {
        showLoading();

        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.delMyCollection(map, body, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                getData(1, false);
            }
        });







    }


}

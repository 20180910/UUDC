package com.sk.uudc.module.near.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyEditText;
import com.sk.uudc.GetSign;
import com.sk.uudc.MyApplication;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.request.NearShangJiaBody;
import com.sk.uudc.module.near.network.response.NearListObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/29.
 */

public class SearchShangJiaActivity extends BaseActivity {
    @BindView(R.id.et_search_shangjia)
    MyEditText et_search_shangjia;
    @BindView(R.id.rv_search_shangjia)
    RecyclerView rv_search_shangjia;

    LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("商家列表");
        return R.layout.act_search_shangjia;
    }

    @Override
    protected void initView() {
        et_search_shangjia.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    PhoneUtils.hiddenKeyBoard(mContext,et_search_shangjia);
                    getData(1,false);
                }
                return false;
            }
        });

        adapter = new LoadMoreAdapter<NearListObj.MerchantListBean>(mContext, R.layout.item_near, pageSize) {
            public String listToString(List list) {
                if(isEmpty(list)){
                    return "";
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i)).append(",");
                }
                return sb.toString().substring(0, sb.toString().length() - 1);
            }

            @Override
            public void bindData(LoadMoreViewHolder holder, int position, NearListObj.MerchantListBean bean) {
                ImageView iv_near_icon = holder.getImageView(R.id.iv_near_icon);
                Glide.with(mContext).load(bean.getMerchant_avatar()).error(R.color.c_press).into(iv_near_icon);

                holder.setText(R.id.tv_near_name, bean.getMerchant_name())
                        .setText(R.id.tv_near_star_num, bean.getScoring() + "")
                        .setText(R.id.tv_near_price, "¥ " + bean.getMoney_people() + "/人")
                        .setText(R.id.tv_near_address, bean.getMerchant_address())
                        .setText(R.id.tv_near_type, bean.getCuisine())
                        .setText(R.id.tv_near_type2, listToString(bean.getLable()))
                        .setText(R.id.tv_near_huodong2, listObjToString(bean.getActivity()))
                        .setText(R.id.tv_near_distance, bean.getDistance());

                LinearLayout ll_near_list_huodong = (LinearLayout) holder.getView(R.id.ll_near_list_huodong);
                if (notEmpty(bean.getActivity())) {
                    ll_near_list_huodong.setVisibility(View.VISIBLE);
                } else {
                    ll_near_list_huodong.setVisibility(View.GONE);
                }
                if(bean.getScoring()>=5){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.VISIBLE);
                }else if(bean.getScoring()==4){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }else if(bean.getScoring()==3){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }else if(bean.getScoring()==2){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }else if(bean.getScoring()==1){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }else if(bean.getScoring()==0){
                    holder.getImageView(R.id.iv_near_star1).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star2).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star3).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star4).setVisibility(View.INVISIBLE);
                    holder.getImageView(R.id.iv_near_star5).setVisibility(View.INVISIBLE);
                }

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.merchant_id,bean.getMerchant_id()+"");
                        STActivity(intent,ShangJiaActivity.class);
                    }
                });

            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_search_shangjia.setLayoutManager(new LinearLayoutManager(mContext));
        rv_search_shangjia.addItemDecoration(getItemDivider());
        rv_search_shangjia.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }
    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", page + "");
        map.put("pagesize", pageSize + "");
        map.put("sign", GetSign.getSign(map));
        NearShangJiaBody body = new NearShangJiaBody();
        body.setSearch_term(getSStr(et_search_shangjia));
        body.setType_id("0");
        body.setLat(MyApplication.getWeiDu(mContext));
        body.setLng(MyApplication.getJingDu(mContext));
        ApiRequest.getNearShangJiaList(map, body, new MyCallBack<NearListObj>(mContext) {
            @Override
            public void onSuccess(NearListObj obj) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(obj.getMerchantList(), true);
                } else {
                    pageNum = 2;
                    adapter.setList(obj.getMerchantList(), true);
                }
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
    public String listObjToString(List<NearListObj.MerchantListBean.ActivityBean> list) {
        if(isEmpty(list)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append("满"+list.get(i).getFull_amount()+"减"+list.get(i).getSubtract_amount()).append(",");
            if(i%2!=0){
                sb.append("\n");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}

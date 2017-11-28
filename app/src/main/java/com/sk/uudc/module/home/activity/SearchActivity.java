package com.sk.uudc.module.home.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.home.Constant;
import com.sk.uudc.module.home.network.ApiRequest;
import com.sk.uudc.module.home.network.response.SearchObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/27.
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.iv_search_icon)
    ImageView iv_search_icon;
    @BindView(R.id.et_search_guanjianzi)
    EditText et_search_guanjianzi;
    @BindView(R.id.tv_search_cancel)
    TextView tv_search_cancel;
    @BindView(R.id.iv_search_delete)
    ImageView iv_search_delete;
    @BindView(R.id.rv_search_history)
    RecyclerView rv_search_history;
    @BindView(R.id.rv_search_hot)
    RecyclerView rv_search_hot;
    @BindView(R.id.ll_search_history)
    LinearLayout ll_search_history;
    BaseRecyclerAdapter historyAdapter,hotAdapter;
    String search_term;
//    int  historyInt=-1,hotInt=-1;

    @Override
    protected int getContentView() {
        return R.layout.act_search;
    }

    @Override
    protected void initView() {
        et_search_guanjianzi.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(et_search_guanjianzi.getText().toString())) {
                        showMsg("请输入搜索内容");
                    } else {
                        search_term=getSStr(et_search_guanjianzi);
                        Intent intent = new Intent();
                        intent.putExtra(Constant.IParam.searchTerm, search_term);
                        STActivity(intent, SearchResultActivity.class);
                    }
                }
                return false;
            }
        });
        historyAdapter=new BaseRecyclerAdapter<SearchObj.RecentlyListBean>(mContext,R.layout.item_search) {
            @Override
            public void bindData(RecyclerViewHolder holder, final int i, SearchObj.RecentlyListBean bean) {
                TextView tv_item_search =  holder.getTextView(R.id.tv_item_search);
                tv_item_search.setText(bean.getSearch_term());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        et_search_guanjianzi.setText(bean.getSearch_term());
                        search_term=bean.getSearch_term();
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.searchTerm, search_term);
                        STActivity(intent,SearchResultActivity.class);

                    }
                });



            }
        };
        rv_search_history.setLayoutManager(new GridLayoutManager(mContext,3));
        rv_search_history.setNestedScrollingEnabled(false);
        rv_search_history.setAdapter(historyAdapter);
        hotAdapter=new BaseRecyclerAdapter<SearchObj.HottestListBean>(mContext,R.layout.item_search) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, SearchObj.HottestListBean bean) {
                TextView tv_item_search = holder.getTextView(R.id.tv_item_search);
                tv_item_search.setText(bean.getSearch_term());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        search_term=bean.getSearch_term();
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.searchTerm, search_term);
                        STActivity(intent,SearchResultActivity.class);
                    }
                });



            }


        };
        rv_search_hot.setLayoutManager(new GridLayoutManager(mContext,3));
        rv_search_hot.setNestedScrollingEnabled(false);
        rv_search_hot.setAdapter(hotAdapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getHottestSearch();

    }

    private void getHottestSearch() {
        Map<String,String> map=new HashMap<String,String>();
        String user_id=getUserId();
        if (TextUtils.isEmpty(user_id)) {
            user_id="0";
        }
        map.put("user_id",user_id);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getHottestSearch(map, new MyCallBack<SearchObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(SearchObj obj) {

                if (obj.getRecently_list().size()==0) {
                    ll_search_history.setVisibility(View.GONE);
                }else {
                    ll_search_history.setVisibility(View.VISIBLE);
                    historyAdapter.setList(obj.getRecently_list(),true);
                }
                hotAdapter.setList(obj.getHottest_list(),true);

            }
        });
    }


    @OnClick({R.id.iv_search_icon, R.id.tv_search_cancel, R.id.iv_search_delete})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_icon:

                break;
            case R.id.tv_search_cancel:
                finish();
                break;
            case R.id.iv_search_delete:
                getDelRecentlySearch();
                break;
        }
    }

    private void getDelRecentlySearch() {
        Map<String,String>map=new HashMap<String,String>();
        String user_id=getUserId();
        if (TextUtils.isEmpty(user_id)) {
            user_id="0";
        }

        map.put("user_id",user_id);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getDelRecentlySearch(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                ll_search_history.setVisibility(View.GONE);

            }
        });
    }
}

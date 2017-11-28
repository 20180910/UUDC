package com.sk.uudc.module.my.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.adapter.BankManageAdapter;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.response.AccountObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/7.
 */

public class BankManageActivity extends BaseActivity {
    @BindView(R.id.tv_bank_manage_bangding)
    MyTextView tv_bank_manage_bangding;
    @BindView(R.id.ll_bank_manage_no)
    LinearLayout ll_bank_manage_no;
    @BindView(R.id.tv_bank_manage_bianji)
    TextView tv_bank_manage_bianji;
    @BindView(R.id.ll_bank_manage)
    LinearLayout ll_bank_manage;
    BankManageAdapter adapter;
    @BindView(R.id.swlv_bank_manage)
    SwipeMenuListView swlv_bank_manage;
    int index = 0;

    String type = "1", account_id;
    List<AccountObj>beanList=new ArrayList<>();

    @Override
    protected int getContentView() {
        setAppTitle("银行卡管理");
        return R.layout.act_bank_manage;
    }

    @Override
    protected void initView() {
        getValue();

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setTitleSize(16);
                deleteItem.setWidth(200);
                deleteItem.setTitle("删除");

                menu.addMenuItem(deleteItem);
            }
        };

        swlv_bank_manage.setMenuCreator(creator);
        swlv_bank_manage.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        getDelAccount(position);

                        break;
                    case 1:

                        break;
                }
                return true;
            }
        });
        swlv_bank_manage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type.equals("2")) {
                    mDialog = new MyDialog.Builder(mContext);
                    mDialog.setMessage("是否设置为默认银行卡?")
                            .setNegativeButton((dialog, which) -> dialog.dismiss())
                            .setPositiveButton((dialog, which) -> {
                                account_id = beanList.get(position).getId();
                                showProgress();
                                getEditDefault();
                                dialog.dismiss();

                            });
                    mDialog.create().show();
                } else {


                }
            }
        });


        // 设置左滑
        swlv_bank_manage.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);


    }

    private void getDelAccount(int position) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("account_id",beanList.get(position).getId());
        map.put("user_id",getUserId());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getDelAccount(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                beanList.remove(position);
                adapter.notifyDataSetChanged();

            }
        });

    }

    private void getEditDefault() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("account_id", account_id);
        map.put("user_id", getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getEditDefault(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                finish();

            }
        });
    }

    private void getValue() {
        type = getIntent().getStringExtra("type");
    }

    @Override
    protected void initData() {
        showProgress();
        getAccount();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showProgress();
        initData();
    }

    private void getAccount() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAccount(map, new MyCallBack<List<AccountObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<AccountObj> obj) {
                beanList.clear();
                if (obj.size() == 0) {
                    ll_bank_manage.setVisibility(View.GONE);
                    ll_bank_manage_no.setVisibility(View.VISIBLE);

                } else {

                    ll_bank_manage.setVisibility(View.VISIBLE);
                    ll_bank_manage_no.setVisibility(View.GONE);
                    beanList.addAll(obj);
                    adapter = new BankManageAdapter(mContext, beanList);
                    swlv_bank_manage.setAdapter(adapter);
                }


            }
        });
    }


    @OnClick({R.id.tv_bank_manage_bangding, R.id.tv_bank_manage_bianji})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bank_manage_bangding:
                Intent intent = new Intent();
                intent.putExtra("leibie", "2");
                STActivity(intent, BangDingBankActivity.class);
                break;
            case R.id.tv_bank_manage_bianji:
                Intent intent2 = new Intent();
                intent2.putExtra("leibie", "2");
                STActivity(intent2, BangDingBankActivity.class);
                break;
        }
    }
}

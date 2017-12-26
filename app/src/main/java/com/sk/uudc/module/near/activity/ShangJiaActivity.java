package com.sk.uudc.module.near.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.androidtools.PhoneUtils;
import com.github.baseclass.rx.MySubscriber;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseBody;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.home.activity.SearchActivity;
import com.sk.uudc.module.my.activity.LoginActivity;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.event.JieSuanEvent;
import com.sk.uudc.module.near.event.YuYueEvent;
import com.sk.uudc.module.near.fragment.ShangJiaEvaluateFragment;
import com.sk.uudc.module.near.fragment.ShangJiaJieShaoFragment;
import com.sk.uudc.module.near.fragment.ShangJiaShangPinFragment;
import com.sk.uudc.module.near.fragment.ShangjiaYuyueFragment;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.request.ShowOrderBody;
import com.sk.uudc.module.near.network.response.ShangJiaObj;
import com.sk.uudc.tools.TabEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/8.
 */

public class ShangJiaActivity extends BaseActivity {

    @BindView(R.id.v_shang_jia_top)
    View v_shang_jia_top;
    @BindView(R.id.iv_shangjia_icon)
    ImageView iv_shangjia_icon;
    @BindView(R.id.tv_shangjia_name)
    TextView tv_shangjia_name;
    @BindView(R.id.iv_shangjia_star1)
    ImageView iv_shangjia_star1;
    @BindView(R.id.iv_shangjia_star2)
    ImageView iv_shangjia_star2;
    @BindView(R.id.iv_shangjia_star3)
    ImageView iv_shangjia_star3;
    @BindView(R.id.iv_shangjia_star4)
    ImageView iv_shangjia_star4;
    @BindView(R.id.iv_shangjia_star5)
    ImageView iv_shangjia_star5;
    @BindView(R.id.tv_shangjia_num)
    TextView tv_shangjia_num;
    @BindView(R.id.ll_shangjia_jieshao)
    LinearLayout ll_shangjia_jieshao;
    @BindView(R.id.v_shang_jia)
    View v_shang_jia;
    @BindView(R.id.tv_shangjia_huodong)
    MyTextView tv_shangjia_huodong;
    @BindView(R.id.tv_shangjia_huodong_content)
    TextView tv_shangjia_huodong_content;
    @BindView(R.id.tv_shangjia_huaodong_num)
    TextView tv_shangjia_huaodong_num;
    @BindView(R.id.ll_shangjia_manjian)
    LinearLayout ll_shangjia_manjian;
    //    @BindView(R.id.stl_shang_jia)
//    SlidingTabLayout stl_shang_jia;
    @BindView(R.id.iv_shangjia_back)
    ImageView iv_shangjia_back;
    @BindView(R.id.tv_shangjia_search)
    MyTextView tv_shangjia_search;
    @BindView(R.id.iv_shangjia_collection)
    ImageView iv_shangjia_collection;
    @BindView(R.id.iv_shangjia_share)
    ImageView iv_shangjia_share;
    @BindView(R.id.ll_shangjia_top)
    LinearLayout ll_shangjia_top;
    @BindView(R.id.fl_shangjia)
    FrameLayout fl_shangjia;
    @BindView(R.id.ctl_shang_jia)
    CommonTabLayout ctl_shang_jia;
    @BindView(R.id.tv_yuyue_commit)
    TextView tv_yuyue_commit;
    private String[] titles = new String[]{"预约", "商品", "评价", "商家"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String merchantId;
    private YuYueEvent yuYueEvent;

    private double minMoney;
    private ShangjiaYuyueFragment shangjiaYuyueFragment;
    private boolean isCollect;
    private String actionType;
    private String orderId;
    private ShangJiaShangPinFragment jiaShangPinFragment;
    private ShangJiaEvaluateFragment evaluateFragment;
    private ShangJiaJieShaoFragment jiaJieShaoFragment;
    private int fragmentHeight;
    private ArrayList<Integer> manList;
    private ArrayList<Integer> jianList;

    @Override
    protected int getContentView() {
        return R.layout.act_shang_jia;
    }

    @Override
    protected void initView() {
        actionType=getIntent().getAction();
        orderId = getIntent().getStringExtra(Constant.IParam.orderId);
        int screenWidth = PhoneUtils.getScreenWidth(mContext);
        ll_shangjia_top.getBackground().mutate().setAlpha(0);
        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= 0 && scrollY <= screenWidth/3) {
                    double alpha = (double) scrollY / screenWidth*3;
                    ll_shangjia_top.getBackground().mutate().setAlpha((int) (alpha * 255));
                    v_shang_jia_top.setVisibility(View.INVISIBLE);
                    if(isCollect){
                        iv_shangjia_collection.setImageResource(R.drawable.collection_select);
                    }else{
                        iv_shangjia_collection.setImageResource(R.drawable.shangjia_xing);
                    }
                    iv_shangjia_share.setImageResource(R.drawable.shangjia_share);
                } else {
                    v_shang_jia_top.setVisibility(View.VISIBLE);
                    if(isCollect){
                        iv_shangjia_collection.setImageResource(R.drawable.collection_select);
                    }else{
                        iv_shangjia_collection.setImageResource(R.drawable.collection_normal);
                    }
                    iv_shangjia_share.setImageResource(R.drawable.shangjia_share1);
                    ll_shangjia_top.getBackground().mutate().setAlpha(255);
                }
            }
        });
        //
        merchantId = getIntent().getStringExtra(Constant.IParam.merchant_id);


        initTabLayout();
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getRxBusEvent(YuYueEvent.class, new MySubscriber<YuYueEvent>() {
            @Override
            public void onMyNext(YuYueEvent event) {
                yuYueEvent=event;
                tv_yuyue_commit.setVisibility(View.GONE);
                ctl_shang_jia.setCurrentTab(1);
                selectYuYue();
            }
        });
        getRxBusEvent(JieSuanEvent.class, new MySubscriber<JieSuanEvent>() {
            @Override
            public void onMyNext(JieSuanEvent event) {
                event.body.setUser_id(getUserId());
                event.body.setMerchant_id(merchantId);
                if(!TextUtils.isEmpty(actionType)){
                    jiaCai(event.body);
                    return;
                }
                if(yuYueEvent==null){
                    showMsg("请先选择预约时间");
                }else{
                    event.body.setDine_time(yuYueEvent.yuYueDate);
                    event.body.setTime_id(yuYueEvent.timeId);
                    event.body.setDine_num_people(yuYueEvent.renShu);
                    event.body.setIs_require_rooms(yuYueEvent.needBaoJian?1:0);
                    Intent jiesuan = new Intent(com.sk.uudc.module.near.Constant.IParam.jiaCai);
                    jiesuan.putExtra(Constant.IParam.orderBody,event.body);
                    jiesuan.putExtra(Constant.IParam.orderId,orderId);
                    STActivity(jiesuan,TiJiaoOrderActivity.class);
                }

            }
        });
    }

    private void jiaCai(ShowOrderBody body) {
        Intent place=new Intent(mContext,OrderPayActivity.class);
        body.setOrder_id(orderId);
        place.putExtra(Constant.IParam.jiaCaiBody,body);
        startActivityForResult(place,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 200:
                    finish();
                break;
            }
        }
    }

    private void initTabLayout() {
        mTabEntities.add(new TabEntity(titles[0], 0, 0));
        mTabEntities.add(new TabEntity(titles[1], 0, 0));
        mTabEntities.add(new TabEntity(titles[2], 0, 0));
        mTabEntities.add(new TabEntity(titles[3], 0, 0));

//        ctl_shang_jia.setTabData();

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
        map.put("merchant_id", merchantId);
        map.put("sign", GetSign.getSign(map));
        BaseBody body = new BaseBody();
        body.setUser_id(getUserId() == null ? "0" : getUserId());
        ApiRequest.getShangJiaDetail(map, body, new MyCallBack<ShangJiaObj>(mContext,pl_load,pcfl) {

            @Override
            public void onSuccess(ShangJiaObj obj) {
                isCollect = obj.getIs_collect()==1;
                if(isCollect){
                    iv_shangjia_collection.setImageResource(R.drawable.collection_select);
                }else{
                    iv_shangjia_collection.setImageResource(R.drawable.shangjia_xing);
                }
                manList = new ArrayList<Integer>();
                jianList = new ArrayList<Integer>();
                if (notEmpty(obj.getActivity())) {
                    ll_shangjia_manjian.setVisibility(View.VISIBLE);
                    tv_shangjia_huodong_content.setText(listObjToString(obj.getActivity()));
                    Log.i(TAG+"===","==="+listObjToString(obj.getActivity()));
//                    tv_shangjia_huaodong_num.setText(obj.getActivity().size()+"个活动");
                    for (int i = 0; i < obj.getActivity().size(); i++) {
                        manList.add(obj.getActivity().get(i).getFull_amount());
                        jianList.add(obj.getActivity().get(i).getSubtract_amount());
                    }
                } else {
                    ll_shangjia_manjian.setVisibility(View.INVISIBLE);
                }
                Glide.with(mContext).load(obj.getMerchant_avatar()).error(R.color.c_press).into(iv_shangjia_icon);
                tv_shangjia_name.setText(obj.getMerchant_name());
                tv_shangjia_num.setText(obj.getScoring()+"");
                minMoney = obj.getMin_money();

                shangjiaYuyueFragment = ShangjiaYuyueFragment.newInstance(merchantId,obj.getIs_provide_rooms());
                addFragment(R.id.fl_shangjia,shangjiaYuyueFragment);


                ctl_shang_jia.setTabData(mTabEntities);
//                ctl_shang_jia.setTabData(mTabEntities, ShangJiaActivity.this, R.id.fl_shangjia, fragments);
                if(!TextUtils.isEmpty(actionType)){//加菜
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            tv_yuyue_commit.setVisibility(View.GONE);
                            ctl_shang_jia.setCurrentTab(1);
                            selectYuYue();
                        }
                    });
                }
                ctl_shang_jia.setOnTabSelectListener(new OnTabSelectListener() {
                    @Override
                    public void onTabSelect(int position) {
                        if(position==0){
                            tv_yuyue_commit.setVisibility(View.VISIBLE);
                        }else{
                            tv_yuyue_commit.setVisibility(View.GONE);
                        }
                        switch (position){
                            case 0:
                                showFragment(shangjiaYuyueFragment);
                                hideFragment(jiaShangPinFragment);
                                hideFragment(evaluateFragment);
                                hideFragment(jiaJieShaoFragment);
                            break;
                            case 1:
                                selectYuYue();
                                break;
                            case 2:
                                if (evaluateFragment == null) {
                                    evaluateFragment = ShangJiaEvaluateFragment.newInstance(merchantId);
                                    addFragment(R.id.fl_shangjia,evaluateFragment);
                                }else{
                                    showFragment(evaluateFragment);
                                }
                                hideFragment(jiaShangPinFragment);
                                hideFragment(shangjiaYuyueFragment);
                                hideFragment(jiaJieShaoFragment);
                                break;
                            case 3:
                                if (jiaJieShaoFragment == null) {
                                    jiaJieShaoFragment = ShangJiaJieShaoFragment.newInstance(merchantId);
                                    addFragment(R.id.fl_shangjia,jiaJieShaoFragment);
                                }else{
                                    showFragment(jiaJieShaoFragment);
                                }
                                hideFragment(shangjiaYuyueFragment);
                                hideFragment(jiaShangPinFragment);
                                hideFragment(evaluateFragment);
                                break;
                        }
                    }
                    @Override
                    public void onTabReselect(int position) {
                    }
                });

                if(obj.getScoring()>=5){
                    iv_shangjia_star1.setVisibility(View.VISIBLE);
                    iv_shangjia_star2.setVisibility(View.VISIBLE);
                    iv_shangjia_star3.setVisibility(View.VISIBLE);
                    iv_shangjia_star4.setVisibility(View.VISIBLE);
                    iv_shangjia_star5.setVisibility(View.VISIBLE);
                }else if(obj.getScoring()==4){
                    iv_shangjia_star1.setVisibility(View.VISIBLE);
                    iv_shangjia_star2.setVisibility(View.VISIBLE);
                    iv_shangjia_star3.setVisibility(View.VISIBLE);
                    iv_shangjia_star4.setVisibility(View.VISIBLE);
                    iv_shangjia_star5.setVisibility(View.INVISIBLE);
                }else if(obj.getScoring()==3){
                    iv_shangjia_star1.setVisibility(View.VISIBLE);
                    iv_shangjia_star2.setVisibility(View.VISIBLE);
                    iv_shangjia_star3.setVisibility(View.VISIBLE);
                    iv_shangjia_star4.setVisibility(View.INVISIBLE);
                    iv_shangjia_star5.setVisibility(View.INVISIBLE);
                }else if(obj.getScoring()==2){
                    iv_shangjia_star1.setVisibility(View.VISIBLE);
                    iv_shangjia_star2.setVisibility(View.VISIBLE);
                    iv_shangjia_star3.setVisibility(View.INVISIBLE);
                    iv_shangjia_star4.setVisibility(View.INVISIBLE);
                    iv_shangjia_star5.setVisibility(View.INVISIBLE);
                }else if(obj.getScoring()==1){
                    iv_shangjia_star1.setVisibility(View.VISIBLE);
                    iv_shangjia_star2.setVisibility(View.INVISIBLE);
                    iv_shangjia_star3.setVisibility(View.INVISIBLE);
                    iv_shangjia_star4.setVisibility(View.INVISIBLE);
                    iv_shangjia_star5.setVisibility(View.INVISIBLE);
                }else if(obj.getScoring()==0){
                    iv_shangjia_star1.setVisibility(View.INVISIBLE);
                    iv_shangjia_star2.setVisibility(View.INVISIBLE);
                    iv_shangjia_star3.setVisibility(View.INVISIBLE);
                    iv_shangjia_star4.setVisibility(View.INVISIBLE);
                    iv_shangjia_star5.setVisibility(View.INVISIBLE);
                }
            }
            public String listObjToString(List<ShangJiaObj.ActivityBean> list) {
                if(isEmpty(list)){
                    return "";
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append("满"+list.get(i).getFull_amount()+"减"+list.get(i).getSubtract_amount()).append(",");
                }
                return sb.toString().substring(0, sb.toString().length() - 1);
            }
        });

    }

    private void selectYuYue() {
        Log.i(TAG+"===","fragmentHeight==="+fragmentHeight);
        if (jiaShangPinFragment == null) {
            jiaShangPinFragment = ShangJiaShangPinFragment.newInstance(fragmentHeight,merchantId, minMoney, manList, jianList, actionType);
            addFragment(R.id.fl_shangjia,jiaShangPinFragment);
        }else{
            showFragment(jiaShangPinFragment);
        }
        hideFragment(shangjiaYuyueFragment);
        hideFragment(evaluateFragment);
        hideFragment(jiaJieShaoFragment);
    }

    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        fragmentHeight = fl_shangjia.getHeight();
    }

    @OnClick({R.id.iv_shangjia_back,
            R.id.iv_shangjia_collection,
            R.id.iv_shangjia_share,
            R.id.tv_yuyue_commit,
            R.id. tv_shangjia_search})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shangjia_back:
                finish();
                break;
            case R.id.tv_yuyue_commit:
                if(shangjiaYuyueFragment!=null){
                    shangjiaYuyueFragment.startYuYue();
                }
                break;
            case R.id.iv_shangjia_collection:
                if(noLogin()){
                    STActivity(LoginActivity.class);
                }else{
                    collectShangJia();
                }
                break;
            case R.id.iv_shangjia_share:
                showFenXiang();
                break;
            case R.id.tv_shangjia_search:
                STActivity(SearchActivity.class);
                break;
        }
    }

    private void collectShangJia() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("merchant_id",merchantId);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.collectShangJia(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                isCollect=obj.getIs_collect()==1;
                if(isCollect){
                    iv_shangjia_collection.setImageResource(R.drawable.collection_select);
                }else{
                    iv_shangjia_collection.setImageResource(R.drawable.shangjia_xing);
                }
            }
        });

    }


}

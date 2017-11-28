package com.sk.uudc.module.near.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.github.baseclass.rx.MySubscriber;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseBody;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.event.JieSuanEvent;
import com.sk.uudc.module.near.event.YuYueEvent;
import com.sk.uudc.module.near.fragment.ShangJiaEvaluateFragment;
import com.sk.uudc.module.near.fragment.ShangJiaJieShaoFragment;
import com.sk.uudc.module.near.fragment.ShangJiaShangPinFragment;
import com.sk.uudc.module.near.fragment.ShangjiaYuyueFragment;
import com.sk.uudc.module.near.network.ApiRequest;
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
    private String[] titles = new String[]{"预约", "商品", "评价", "商家"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String merchantId;
    private YuYueEvent yuYueEvent;

    private double minMoney;
    @Override
    protected int getContentView() {
        return R.layout.act_shang_jia;
    }

    @Override
    protected void initView() {
        //
        merchantId = getIntent().getStringExtra(Constant.IParam.merchant_id);

//        ctl_shang_jia.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//            }
//
//            @Override
//            public void onTabReselect(int position) {
//            }
//        });
        initTabLayout();
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getRxBusEvent(YuYueEvent.class, new MySubscriber<YuYueEvent>() {
            @Override
            public void onMyNext(YuYueEvent event) {
                yuYueEvent=event;
                ctl_shang_jia.setCurrentTab(1);
            }
        });
        getRxBusEvent(JieSuanEvent.class, new MySubscriber<JieSuanEvent>() {
            @Override
            public void onMyNext(JieSuanEvent event) {
                if(yuYueEvent==null){
                    showMsg("请先选择预约时间");
                }else{
                    event.body.setUser_id(getUserId());
                    event.body.setMerchant_id(merchantId);
                    event.body.setDine_time(yuYueEvent.yuYueDate);
                    event.body.setTime_id(yuYueEvent.timeId);
                    event.body.setDine_num_people(yuYueEvent.renShu);
                    event.body.setIs_require_rooms(yuYueEvent.needBaoJian?1:0);
                    Intent jiesuan = new Intent();
                    jiesuan.putExtra(Constant.IParam.orderBody,event.body);
                    STActivity(jiesuan,TiJiaoOrderActivity.class);
                }

            }
        });

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
                ArrayList<Integer> manList=new ArrayList<Integer>();
                ArrayList<Integer> jianList=new ArrayList<Integer>();
                if (notEmpty(obj.getActivity())) {
                    ll_shangjia_manjian.setVisibility(View.VISIBLE);
                    tv_shangjia_huodong_content.setText(listObjToString(obj.getActivity()));
                    tv_shangjia_huaodong_num.setText(obj.getActivity().size()+"个活动");
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
                fragments.add(ShangjiaYuyueFragment.newInstance(merchantId));
                fragments.add(ShangJiaShangPinFragment.newInstance(merchantId,minMoney,manList,jianList));
                fragments.add(ShangJiaEvaluateFragment.newInstance(merchantId));
                fragments.add(ShangJiaJieShaoFragment.newInstance(merchantId));
                ctl_shang_jia.setTabData(mTabEntities, ShangJiaActivity.this, R.id.fl_shangjia, fragments);


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
                    if(i%2!=0){
                        sb.append("\n");
                    }
                }
                return sb.toString().substring(0, sb.toString().length() - 1);
            }
        });

    }



    @OnClick({R.id.iv_shangjia_back, R.id.iv_shangjia_collection, R.id.iv_shangjia_share})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shangjia_back:
                finish();
                break;
            case R.id.iv_shangjia_collection:
                break;
            case R.id.iv_shangjia_share:
                break;
        }
    }


}

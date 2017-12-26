package com.sk.uudc.module.near.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.github.androidtools.DateUtils;
import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.rx.RxBus;
import com.github.customview.MyEditText;
import com.github.customview.MyRadioButton;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.event.YuYueEvent;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.request.YuYueBody;
import com.sk.uudc.module.near.network.response.YuYueTimeObj;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/16.
 */

public class ShangjiaYuyueFragment extends BaseFragment {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_yuyue_date)
    TextView tv_yuyue_date;
    @BindView(R.id.rvShangjiaYuyue)
    RecyclerView rvShangjiaYuyue;
    @BindView(R.id.etShangjiaYuyueNum)
    MyEditText etShangjiaYuyueNum;
    @BindView(R.id.rbShangjiaYuyueNeed)
    MyRadioButton rbShangjiaYuyueNeed;
    @BindView(R.id.rbShangjiaYuyueNo)
    MyRadioButton rbShangjiaYuyueNo;
    @BindView(R.id.rgShangjiaYuyue)
    RadioGroup rgShangjiaYuyue;
    @BindView(R.id.ll_shangjia_baojian)
    LinearLayout ll_shangjia_baojian;
    BaseRecyclerAdapter yuyuemAdapter;
    private String merchantId;
    private int yuYuePosition=-1;

    public static ShangjiaYuyueFragment newInstance(String merchantId, int is_provide_rooms) {
        ShangjiaYuyueFragment newFragment = new ShangjiaYuyueFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.merchantId, merchantId);
        bundle.putInt(Constant.isProvideRooms, is_provide_rooms);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_shang_jia_yuyue;
    }

    @Override
    protected void initView() {
        if(getArguments().getInt(Constant.isProvideRooms,0)==1){
            ll_shangjia_baojian.setVisibility(View.VISIBLE);
        }else{
            ll_shangjia_baojian.setVisibility(View.GONE);
        }
        tv_yuyue_date.setText(DateUtils.dateToString(new Date()));
        merchantId = getArguments().getString(Constant.merchantId);
        yuyuemAdapter=new BaseRecyclerAdapter<YuYueTimeObj>(mContext,R.layout.item_shangjia_yuyue) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, YuYueTimeObj bean) {
               String beginTimeStr= bean.getBegin_time();
               String endTimeStr  = bean.getEnd_time();
                holder.setText(R.id.tv_yuyue_time,beginTimeStr+"-"+endTimeStr);
                ImageView iv_yuyue_img = holder.getImageView(R.id.iv_yuyue_img);
                if(yuYuePosition>=getList().size()){
                    yuYuePosition=-1;
                }
                if(bean.getIs_gouxuan()==1){
                    iv_yuyue_img.setImageResource(R.drawable.time_full);
                    holder.itemView.setEnabled(false);
                }else if(yuYuePosition==i){
                    iv_yuyue_img.setImageResource(R.drawable.time_select);
                    holder.itemView.setEnabled(false);
                }else{
                    iv_yuyue_img.setImageResource(R.drawable.time_empty);
                    holder.itemView.setEnabled(true);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int is_gouxuan = bean.getIs_gouxuan();
                        if(yuYuePosition!=i&&is_gouxuan==0){
                            yuYuePosition=i;
                            notifyDataSetChanged();
                        }else if(is_gouxuan==1){
                            showMsg("当前时间段已满无法预约");
                        }
                    }
                });
            }
        };
        rvShangjiaYuyue.setLayoutManager(new GridLayoutManager(mContext,3));
//        rvShangjiaYuyue.setNestedScrollingEnabled(false);
        rvShangjiaYuyue.setAdapter(yuyuemAdapter);

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
        map.put("merchant_id", merchantId);
        map.put("time", DateUtils.dateToString(new Date()));
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getShangJiaYuYue(map, new MyCallBack<List<YuYueTimeObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<YuYueTimeObj> list) {
                yuyuemAdapter.setList(list,true);
            }
        });

    }

    @OnClick({R.id.tv_yuyue_date })
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_yuyue_date:
                Calendar instance = Calendar.getInstance();
                instance.add(Calendar.DAY_OF_MONTH,4);
                TimePickerView pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        String dateToString = DateUtils.dateToString(date);
                        if(!getSStr(tv_yuyue_date).equals(dateToString)){
                            tv_yuyue_date.setText(dateToString);
                            setYuYueTime(dateToString);
                        }
                    }
                }).setRangDate(Calendar.getInstance(),instance).setType(new boolean[]{true, true, true, false, false, false}).build();
                pvTime.show();
                break;
        }
    }
    public void startYuYue(){
        if(TextUtils.isEmpty(getSStr(etShangjiaYuyueNum))){
            showMsg("请输入用餐人数");
            return;
        }else if(yuYuePosition==-1){
            showMsg("请选择预约时间");
            return;
        }

        PhoneUtils.hiddenKeyBoard(mContext,etShangjiaYuyueNum);
        yuYue();
    }
    private void yuYue() {
        showLoading();
        YuYueTimeObj yueTimeObj = (YuYueTimeObj) yuyuemAdapter.getList().get(yuYuePosition);
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",GetSign.getSign(map));
        YuYueBody body=new YuYueBody();
        body.setMerchant_id(merchantId);
        body.setDine_time(getSStr(tv_yuyue_date));
        body.setTime_id( yueTimeObj.getTime_id());
        body.setDine_num_people(getSStr(etShangjiaYuyueNum));
        body.setIs_require_rooms(rbShangjiaYuyueNeed.isChecked()?1:0);
        ApiRequest.startYuYue(map,body,new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {

                YuYueEvent yuYueEvent = new YuYueEvent();
                yuYueEvent.yuYueDate=getSStr(tv_yuyue_date);
                yuYueEvent.timeId=yueTimeObj.getTime_id();
                yuYueEvent.renShu=getSStr(etShangjiaYuyueNum);
                yuYueEvent.needBaoJian=rbShangjiaYuyueNeed.isChecked();
                RxBus.getInstance().post(yuYueEvent);
            }
        });


    }

    private void setYuYueTime(String date) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("merchant_id", merchantId);
        map.put("time", date);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getShangJiaYuYue(map, new MyCallBack<List<YuYueTimeObj>>(mContext) {
            @Override
            public void onSuccess(List<YuYueTimeObj> list) {
                yuyuemAdapter.setList(list,true);
            }
        });
    }
}

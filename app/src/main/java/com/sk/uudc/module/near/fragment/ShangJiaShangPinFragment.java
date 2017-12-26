package com.sk.uudc.module.near.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.rx.RxBus;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.activity.NewImageDetailActivity;
import com.sk.uudc.module.near.event.JieSuanEvent;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.request.ShowOrderBody;
import com.sk.uudc.module.near.network.response.ShangJiaShangPingObj;
import com.sk.uudc.tools.AndroidUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * Created by Administrator on 2017/11/9.
 */

public class ShangJiaShangPinFragment extends BaseFragment {
    @BindView(R.id.rv_shang_pin_type)
    RecyclerView rv_shang_pin_type;

    @BindView(R.id.rv_shang_pin_cai)
    RecyclerView rv_shang_pin_cai;
    @BindView(R.id.rv_shopping_cart)
    RecyclerView rv_shopping_cart;
    String merchantId;
    @BindView(R.id.iv_shangpin_shopping_car)
    ImageView iv_shangpin_shopping_car;
    @BindView(R.id.tv_shangpin_car_num)
    MyTextView tv_shangpin_car_num;
    @BindView(R.id.tv_shangpin_price)
    TextView tv_shangpin_price;
    @BindView(R.id.tv_shangpin_youhui)
    TextView tv_shangpin_youhui;
    @BindView(R.id.tv_shangpin_jiesuan)
    TextView tv_shangpin_jiesuan;
    @BindView(R.id.tv_goods_xuanfu_title)
    TextView tv_goods_xuanfu_title;
    @BindView(R.id.ll_shangpin_bottom)
    LinearLayout ll_shangpin_bottom;
    @BindView(R.id.ll_shopping_bottom)
    LinearLayout ll_shopping_bottom;

    BaseRecyclerAdapter typeAdapter, goodsAdapter;
    private SparseIntArray sparseType, sparseIntArray;
    private SparseArray<String> sparseTypeTitle;
    private int xuanFuHeight;
    private int clickTypePosition = 0;
    private BaseRecyclerAdapter shoppingAdapter;
    private int totalNum=0;
    private double totalPrice=0;
    private double minMoney;
    public static ShangJiaShangPinFragment newInstance(int fragmentHeight,String merchantId, double minMoney, ArrayList<Integer> manList, ArrayList<Integer> jianList,String actionType) {
        ShangJiaShangPinFragment newFragment = new ShangJiaShangPinFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.fragmentHeight, fragmentHeight);
        bundle.putString(Constant.merchantId, merchantId);
        bundle.putString(Constant.actionType, actionType);
        bundle.putDouble(Constant.minMoney, minMoney);
        bundle.putIntegerArrayList(Constant.actManList,manList);
        bundle.putIntegerArrayList(Constant.actJianList,jianList);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_shang_jia_shang_pin;
    }

    @Override
    protected void initView() {
        if(getArguments().getInt(Constant.fragmentHeight,0)!=0){
            rv_shang_pin_cai.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getArguments().getInt(Constant.fragmentHeight,0)));
            Log.i(TAG+"===","=11=="+getArguments().getInt(Constant.fragmentHeight,0));
        }
        xuanFuHeight = PhoneUtils.dip2px(mContext, 30);
        merchantId = getArguments().getString(Constant.merchantId);
        minMoney = getArguments().getDouble(Constant.minMoney);

        if(TextUtils.isEmpty( getArguments().getString(Constant.actionType))){//如果有加菜的标识就不显示满减
            tv_shangpin_jiesuan.setText("¥"+minMoney+"起订");
            ArrayList<Integer> manList = getArguments().getIntegerArrayList(Constant.actManList);
            if(notEmpty(manList)){
                ArrayList<Integer> jianList = getArguments().getIntegerArrayList(Constant.actJianList);
                tv_shangpin_youhui.setText("满"+manList.get(0)+"减"+jianList.get(0));
            }
        }else{
            tv_shangpin_jiesuan.setText("去加菜");
        }



        typeAdapter = new BaseRecyclerAdapter<ShangJiaShangPingObj.MerchantClassListBean>(mContext, R.layout.item_shang_jia_goods_type) {
            @Override
            public void bindData(RecyclerViewHolder holder, int position, ShangJiaShangPingObj.MerchantClassListBean bean) {

                MyTextView tv_shangjia_goods_type = (MyTextView) holder.getView(R.id.tv_shangjia_goods_type);
                tv_shangjia_goods_type.setText(bean.getNavigation_name());
                tv_shangjia_goods_type.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        if (clickTypePosition != position) {
                            rv_shang_pin_cai.scrollToPosition(sparseIntArray.get(bean.getNavigation_id(), 0));
                            clickTypePosition = position;
                            notifyDataSetChanged();
                        }
                    }

                });
                setClickView(tv_shangjia_goods_type, position);
            }

            private void setClickView(MyTextView view, int index) {
                if (clickTypePosition == index) {
                    view.setSolidColor(ContextCompat.getColor(mContext, R.color.background_f2));
                    view.setLeftLine(true);
                    view.setBorderColor(ContextCompat.getColor(mContext, R.color.home_green));
                    view.setBorderWidth(PhoneUtils.dip2px(mContext, 3));
                    view.setTextColor(ContextCompat.getColor(mContext, R.color.gray_33));
                } else {
                    view.setSolidColor(ContextCompat.getColor(mContext, R.color.white));
                    view.setLeftLine(false);
                    view.setBorderColor(ContextCompat.getColor(mContext, R.color.white));
                    view.setTextColor(ContextCompat.getColor(mContext, R.color.gray_66));
                    view.setBorderWidth(0);
                }
                view.complete();
            }
        };
        goodsAdapter = new BaseRecyclerAdapter<ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean>(mContext, R.layout.item_shang_jia_goods) {
            @Override
            public void bindData(RecyclerViewHolder holder, int position, ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean bean) {
                ImageView imageView = holder.getImageView(R.id.iv_goods_img);
                Log.i(TAG+"===","==="+position);
                imageView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        ArrayList<String> imgList = new ArrayList<>();
                        imgList.add(bean.getGoods_image());
                        intent.putStringArrayListExtra(Constant.IParam.imgList,imgList);
                        intent.putExtra(Constant.IParam.imgIndex,position);
                        STActivity(intent,NewImageDetailActivity.class);
                    }
                });
                Glide.with(mContext).load(bean.getGoods_image()).skipMemoryCache(false).override(PhoneUtils.dip2px(mContext,70), PhoneUtils.dip2px(mContext,70)) .error(R.color.c_press).into(imageView);

                holder.setText(R.id.tv_goods_name, bean.getGoods_name())
                        .setText(R.id.tv_goods_xiaoliang, "月销量" + bean.getSales_volume() + "份")
                        .setText(R.id.tv_goods_price, bean.getGoods_price() + "");
                TextView tv_goods_title = holder.getTextView(R.id.tv_goods_title);
                if (sparseIntArray.get(bean.getNavigation_id()) == position) {
                    tv_goods_title.setVisibility(View.VISIBLE);
                    tv_goods_title.setText(sparseTypeTitle.get(bean.getNavigation_id(), ""));
                } else {
                    tv_goods_title.setVisibility(View.GONE);
                }

                TextView tv_goods_num = holder.getTextView(R.id.tv_goods_num);
                tv_goods_num.setText(bean.getNum()+"");
                ImageView iv_goods_jian = holder.getImageView(R.id.iv_goods_jian);
                ImageView iv_goods_add = holder.getImageView(R.id.iv_goods_add);
                if(bean.getNum()==0){
                    iv_goods_jian.setVisibility(View.INVISIBLE);
                    tv_goods_num.setVisibility(View.INVISIBLE);
                }else{
                    iv_goods_jian.setVisibility(View.VISIBLE);
                    tv_goods_num.setVisibility(View.VISIBLE);
                }
                iv_goods_jian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        totalPrice=AndroidUtils.jianFa(totalPrice,bean.getGoods_price());
                        int num = bean.getNum();
                        num--;
                        bean.setNum(num);
                        if(bean.getNum()==0){
                            iv_goods_jian.setVisibility(View.INVISIBLE);
                            tv_goods_num.setVisibility(View.INVISIBLE);
                        }else{
                            iv_goods_jian.setVisibility(View.VISIBLE);
                            tv_goods_num.setVisibility(View.VISIBLE);
                        }
                        tv_goods_num.setText(num+"");
                        totalNum--;
                        jiSuanNum();
                    }
                });
                iv_goods_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        totalPrice=AndroidUtils.jiaFa(totalPrice,bean.getGoods_price());
                        int num = bean.getNum();
                        num++;
                        bean.setNum(num);
                        tv_goods_num.setText(num+"");
                        iv_goods_jian.setVisibility(View.VISIBLE);
                        tv_goods_num.setVisibility(View.VISIBLE);
                        totalNum++;
                        jiSuanNum();
                    }
                });
            }

        };

        rv_shang_pin_type.setLayoutManager(new LinearLayoutManager(mContext));
        rv_shang_pin_type.addItemDecoration(getItemDivider());
        rv_shang_pin_type.setAdapter(typeAdapter);

        rv_shang_pin_cai.setLayoutManager(new LinearLayoutManager(mContext));
        rv_shang_pin_cai.addItemDecoration(getItemDivider());
        rv_shang_pin_cai.setAdapter(goodsAdapter);

        rv_shang_pin_cai.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean bean = (ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean) goodsAdapter.getList().get(firstVisibleItemPosition);
                //改变左边选择项
                clickTypePosition = sparseType.get(bean.getNavigation_id(), 0);
                rv_shang_pin_type.scrollToPosition(clickTypePosition);
                typeAdapter.notifyDataSetChanged();
                bean = null;
                switch (newState){
                    case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
                        //对于滚动不加载图片的尝试
                        Glide.with(mContext).resumeRequests();
                        break;
                    case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
                        Glide.with(mContext).resumeRequests();
                        break;
                    case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under
                        Glide.with(mContext).pauseRequests();
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if(goodsAdapter.getList().size()<=1){
                    return;
                }
                ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean bean = (ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean) goodsAdapter.getList().get(firstVisibleItemPosition);
                ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean nextBean = (ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean) goodsAdapter.getList().get(firstVisibleItemPosition + 1);
                View view = layoutManager.findViewByPosition(firstVisibleItemPosition + 1);
                if (view != null && sparseIntArray.get(nextBean.getNavigation_id()) == (firstVisibleItemPosition + 1)) {
                    if (view.getTop() <= xuanFuHeight) {
                        tv_goods_xuanfu_title.setY(-(xuanFuHeight - view.getTop()));
                    } else {
                        tv_goods_xuanfu_title.setY(0);
                    }
                } else {
                    tv_goods_xuanfu_title.setY(0);
                }
                tv_goods_xuanfu_title.setText(sparseTypeTitle.get(bean.getNavigation_id(), ""));
                bean = null;
                nextBean = null;
            }
        });

    }

    private void jiSuanNum() {
        if(totalNum==0){
            tv_shangpin_car_num.setText(totalNum+"");
            tv_shangpin_car_num.setVisibility(View.INVISIBLE);
            ll_shopping_bottom.setVisibility(View.GONE);

        }else{
            tv_shangpin_car_num.setText(totalNum+"");
            tv_shangpin_car_num.setVisibility(View.VISIBLE);

        }
        tv_shangpin_price.setText("¥"+totalPrice);
        if(totalPrice<=0){
            tv_shangpin_price.setText("购物车为空");
        }
        if(isJiaCai()){
            if(totalPrice<=0){
                tv_shangpin_jiesuan.setText("加菜");
                tv_shangpin_jiesuan.setBackgroundColor(ContextCompat.getColor(mContext,R.color.gray_99));
                tv_shangpin_jiesuan.setEnabled(false);
            }else{
                tv_shangpin_jiesuan.setText("加菜");
                tv_shangpin_jiesuan.setBackgroundColor(ContextCompat.getColor(mContext,R.color.orange));
                tv_shangpin_jiesuan.setEnabled(true);
            }
        }else{
            if(totalPrice<minMoney){
                if(totalPrice<=0){
                    tv_shangpin_jiesuan.setText("¥"+minMoney+"起订");
                }else{
                    tv_shangpin_jiesuan.setText("还差¥"+AndroidUtils.jianFa(minMoney,totalPrice));
                }
                tv_shangpin_jiesuan.setBackgroundColor(ContextCompat.getColor(mContext,R.color.gray_99));
                tv_shangpin_jiesuan.setEnabled(false);
            }else{
                tv_shangpin_jiesuan.setText("结算");
                tv_shangpin_jiesuan.setBackgroundColor(ContextCompat.getColor(mContext,R.color.orange));
                tv_shangpin_jiesuan.setEnabled(true);
            }
        }

        if(TextUtils.isEmpty( getArguments().getString(Constant.actionType))) {//如果有加菜的标识就不显示满减
            ArrayList<Integer> manList = getArguments().getIntegerArrayList(Constant.actManList);
            ArrayList<Integer> jianList = getArguments().getIntegerArrayList(Constant.actJianList);
            if(isEmpty(manList)){
                return;
            }
            int index=-1;
            double v=0;
            for (int i = 0; i < manList.size(); i++) {
                Integer integer = manList.get(i);
                v = AndroidUtils.jianFa(integer, totalPrice);
                if(v>0){
                    index=i;
                    break;
                }
            }
            if(totalPrice>=manList.get(manList.size()-1)){
                tv_shangpin_youhui.setText("满"+manList.get(manList.size()-1)+"减"+jianList.get(manList.size()-1));
            }else{
                if(index!=-1){
                    tv_shangpin_youhui.setText("满"+manList.get(index)+"减"+jianList.get(index)+","+"还差"+v+"元可减"+jianList.get(index));
                }
            }
        }

    }
    public boolean isJiaCai(){
        return !TextUtils.isEmpty(getArguments().getString(Constant.actionType));
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
        ApiRequest.getShangJiaGoods(map, new MyCallBack<ShangJiaShangPingObj>(mContext, pl_load) {
            @Override
            public void onSuccess(ShangJiaShangPingObj obj) {
                if (isEmpty(obj.getMerchant_class_list())) {
                    return;
                }
                List<ShangJiaShangPingObj.MerchantClassListBean> classList = obj.getMerchant_class_list();
                sparseType = new SparseIntArray();//记录type的position
                sparseIntArray = new SparseIntArray();//记录title的position
                sparseTypeTitle = new SparseArray<String>();//记录type的title
                List<ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean> list = new ArrayList<>();
                for (int i = 0; i < classList.size(); i++) {
                    ShangJiaShangPingObj.MerchantClassListBean typeBean = classList.get(i);
                    list.addAll(classList.get(i).getGoods_list());
                    sparseType.put(typeBean.getNavigation_id(), i);
                    sparseTypeTitle.put(typeBean.getNavigation_id(), typeBean.getNavigation_name());
                }
                if(notEmpty(classList)){
                    tv_goods_xuanfu_title.setText(classList.get(0).getNavigation_name());
                }
                typeAdapter.setList(classList, true);

                for (int i = 0; i < list.size(); i++) {
                    ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean bean = list.get(i);
                    int flag = sparseIntArray.get(bean.getNavigation_id(), -1);
                    if (flag == -1) {
                        sparseIntArray.put(bean.getNavigation_id(), i);
                    }
                }
                goodsAdapter.setList(list, true);
            }
        });

    }

    @OnClick({R.id.iv_shangpin_shopping_car, R.id.tv_shangpin_jiesuan,R.id.ll_shopping_bottom})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_shopping_bottom:
            case R.id.iv_shangpin_shopping_car:
                if(totalNum==0){
                    return;
                }
                if(ll_shopping_bottom.getVisibility()==View.VISIBLE){
                    ll_shopping_bottom.setVisibility(View.GONE);
                }else{
                    ll_shopping_bottom.setVisibility(View.VISIBLE);
                    showShoppingCart();
                }
                break;
            case R.id.tv_shangpin_jiesuan:
                ShowOrderBody body=new ShowOrderBody();
                List<ShowOrderBody.ShowOrderBean> showOrder=new ArrayList<>();
                ShowOrderBody.ShowOrderBean bean;
                for (int i = 0; i < goodsAdapter.getList().size(); i++) {
                    ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean goodsListBean = (ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean) goodsAdapter.getList().get(i);
                    if(goodsListBean.getNum()>0){
                        bean=new ShowOrderBody.ShowOrderBean();
                        bean.setGoods_id(goodsListBean.getGoods_id());
                        bean.setNumber(goodsListBean.getNum());
                        showOrder.add(bean);
                    }
                }
                body.setShowOrder(showOrder);
                RxBus.getInstance().post(new JieSuanEvent(body));
                break;
        }
    }

    private void showShoppingCart() {
        if(shoppingAdapter==null){
            shoppingAdapter=new BaseRecyclerAdapter<ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean>(mContext,R.layout.item_shopping_cart_goods) {
                @Override
                public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    RecyclerViewHolder holder;
                    if(viewType==1){
                        holder=new RecyclerViewHolder(mContext,LayoutInflater.from(mContext).inflate(R.layout.item_shopping_cart_goods,parent,false));
                    }else{
                        holder=new RecyclerViewHolder(mContext,LayoutInflater.from(mContext).inflate(R.layout.item_shopping_empty,parent,false));
                    }
                    return holder;
                }
                @Override
                public void bindData(RecyclerViewHolder holder,final int i, ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean bean) {
                    if(getItemViewType(i)==0){
                        return;
                    }
                    holder.setText(R.id.tv_shopping_name,bean.getGoods_name())
                            .setText(R.id.tv_shopping_price,"¥"+bean.getGoods_price())
                            .setText(R.id.tv_shopping_num,bean.getNum()+"");
                    TextView  tv_shopping_num = holder.getTextView(R.id.tv_shopping_num);
                    ImageView iv_shopping_jian = holder.getImageView(R.id.iv_shopping_jian);
                    ImageView iv_shopping_add = holder.getImageView(R.id.iv_shopping_add);

                    iv_shopping_jian.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            totalPrice=AndroidUtils.jianFa(totalPrice,bean.getGoods_price());
                            int num = bean.getNum();
                            num--;
                            bean.setNum(num);
                            if(bean.getNum()==0){
                                notifyDataSetChanged();
                            }
                            changeShangPinGoodsNum(num, i);//点击购物车加减，改变商品里面相应的数据
                            tv_shopping_num.setText(num+"");
                            totalNum--;
                            jiSuanNum();
                        }
                    });
                    iv_shopping_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            totalPrice=AndroidUtils.jiaFa(totalPrice,bean.getGoods_price());
                            int num = bean.getNum();
                            num++;
                            bean.setNum(num);
                            changeShangPinGoodsNum(num, i);//点击购物车加减，改变商品里面相应的数据
                            tv_shopping_num.setText(num+"");
                            totalNum++;
                            jiSuanNum();
                        }
                    });

                }
                @Override
                public int getItemViewType(int position) {
                    ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean bean = (ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean) goodsAdapter.getList().get(position);
                    if(bean.getNum()>0){
                        return 1;
                    }else{
                        return 0;
                    }
                }
            };
            rv_shopping_cart.setLayoutManager(new LinearLayoutManager(mContext));
//            rv_shopping_cart.addItemDecoration(getItemDivider());
            shoppingAdapter.setList(goodsAdapter.getList());
            rv_shopping_cart.setAdapter(shoppingAdapter);
        }else{
            shoppingAdapter.notifyDataSetChanged();
        }
    }

    private void changeShangPinGoodsNum(int num, int i) {
        View itemView = rv_shang_pin_cai.getChildAt(i);
        TextView tv_goods_num = itemView.findViewById(R.id.tv_goods_num);
        if(tv_goods_num==null){
            return;
        }
        ImageView iv_goods_jian = itemView.findViewById(R.id.iv_goods_jian);
        if(num==0){
            iv_goods_jian.setVisibility(View.INVISIBLE);
            tv_goods_num.setVisibility(View.INVISIBLE);
        }
        tv_goods_num.setText(num+"");
    }
}

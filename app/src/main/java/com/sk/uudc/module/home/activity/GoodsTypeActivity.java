package com.sk.uudc.module.home.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.androidtools.SPUtils;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.customview.MyRadioButton;
import com.github.customview.MyTextView;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.MyApplication;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.home.network.ApiRequest;
import com.sk.uudc.module.home.network.request.HomeTypeMerchantListBody;
import com.sk.uudc.module.home.network.response.AreaBusinessCircleObj;
import com.sk.uudc.module.home.network.response.HomeTypeAssemblageObj;
import com.sk.uudc.module.home.network.response.HomeTypeMerchantListObj;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.activity.ShangJiaActivity;
import com.sk.uudc.tools.TabEntity;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/11/15.
 */

public class GoodsTypeActivity extends BaseActivity {


    @BindView(R.id.ddm_goods_type)
    DropDownMenu ddm_goods_type;
    LoadMoreAdapter adapter;
    //    private String headers[] = {"全部分类", "附近", "智能排序", "筛选"};
    private List<View> popupViews = new ArrayList<>();
    private String zhineng[] = {"智能排序", "离我最近", "人气最高"};
    private String screen[] = {"不限", "早餐", "午餐", "晚餐", "下午茶", "夜宵"};
    String[] titles = {"商圈", "地铁"};
    ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
    int paixuCheck = 0, screenCheck = -1, nearYijiCheck = -1, nearErjiCheck = -1;
    BaseRecyclerAdapter allmAdapter, zhinengmAdapter, screenmAdapter, nearYijimAdapter, nearerjimAdapter;
    String name,
            type_id,
            city = "上海市",
            city_id,//城市ID
            city_id_xuanze,//选择城市id
            distance = "0",//附近距离(传0是默认距离,)
            parentId = "1",
            area_id = "0",//商圈ID(没选传0,)
            sequencing = "0",//排序(0智能排序,1离我最近,2人气最高,,,,)
            is_provide_rooms = "0",//是否需要包间(0默认 1需要 2不需要)
            dinner_time = "0";//用餐时段(0不限,1早餐,2午餐,3晚餐,4下午茶,5夜宵)
    List<AreaBusinessCircleObj.PcaListBean> childList = new ArrayList<>();
    List<AreaBusinessCircleObj> parentList = new ArrayList<>();
    List<String> headers = new ArrayList<>();
    private View mainView;


    @Override
    protected int getContentView() {
//        setAppTitle("家常菜");
        setTitleBackgroud(R.color.white);
        setAppTitleColor(this.getResources().getColor(R.color.gray_33));
        setAppRightImg(R.drawable.search);
        return R.layout.act_goods_type;
    }

    @Override
    protected void initView() {
        mainView = getLayoutInflater().inflate(R.layout.item_shangjia_list, null);
        pl_load = mainView.findViewById(R.id.pl_load);
        pl_load.setInter(this);
        pcfl = mainView.findViewById(R.id.pcfl_refresh);
        pcfl.setLastUpdateTimeRelateObject(this);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                showLoading();
                getData(1, false);
            }
        });
        getValue();
        intiRcv();


    }

    private void getValue() {

        name = getIntent().getStringExtra("name");
        type_id = getIntent().getStringExtra("typeId");
        city_id = getIntent().getStringExtra("city_id");
        city_id_xuanze = getIntent().getStringExtra("city_id_xuanze");
        headers.add(name);
        headers.add("全城");
        headers.add("智能排序");
        headers.add("筛选");
        setAppTitle(name);


    }

    @Override
    protected void initData() {
        showProgress();
        getTypeAssemblage();
        //获得商圈
        getAreaBusinessCircle();
        getData(1, false);


    }

//    private void getCityId() {
//        Map<String,String>map=new HashMap<String,String>();
//        map.put("city",city);
//        map.put("sign",GetSign.getSign(map));
//        ApiRequest.getCityId(map, new MyCallBack<CityIdObj>(mContext) {
//            @Override
//            public void onSuccess(CityIdObj obj) {
//                city_id=obj.getCity_id();
//
//
//
//            }
//        });
//    }

    private void getAreaBusinessCircle() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("city_id", city_id_xuanze);
        map.put("city_type", SPUtils.getPrefInt(mContext,Config.city_level,3)+"");
        map.put("localize_city_id", city_id);
        map.put("localize_city_type","4");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAreaBusinessCircle(map, new MyCallBack<List<AreaBusinessCircleObj>>(mContext) {
            @Override
            public void onSuccess(List<AreaBusinessCircleObj> obj) {

                parentList = obj;
                nearYijimAdapter.setList(obj, true);

//               List<AreaBusinessCircleObj.PcaListBean>pca_list=new ArrayList<AreaBusinessCircleObj.PcaListBean>();

            }
        });
    }

    private void getTypeAssemblage() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getTypeAssemblage(map, new MyCallBack<HomeTypeAssemblageObj>(mContext) {
            @Override
            public void onSuccess(HomeTypeAssemblageObj obj) {
                allmAdapter.setList(obj.getType_list(), true);
            }
        });

    }

    private void intiRcv() {


//        rcAll.setLayoutManager(new LinearLayoutManager(mContext));
//        rcAll.setNestedScrollingEnabled(false);
//        rcAll.setAdapter(allmAdapter);


        //全部
        final RecyclerView rcAll = new RecyclerView(this);
        allmAdapter = new BaseRecyclerAdapter<HomeTypeAssemblageObj.TypeListBean>(mContext, R.layout.item_goods_type_all) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, HomeTypeAssemblageObj.TypeListBean bean) {
                TextView tvItemGoodsType = holder.getTextView(R.id.tvItemGoodsType);
                tvItemGoodsType.setText(bean.getType_name());
                if (type_id.equals(bean.getType_id())) {
                    tvItemGoodsType.setTextColor(mContext.getResources().getColor(R.color.home_green));
                } else {
                    tvItemGoodsType.setTextColor(mContext.getResources().getColor(R.color.gray_33));
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        type_id = bean.getType_id();
                        tvItemGoodsType.setTextColor(mContext.getResources().getColor(R.color.home_green));
                        allmAdapter.notifyDataSetChanged();
                        ddm_goods_type.setTabText(bean.getType_name());
                        ddm_goods_type.closeMenu();
                        setAppTitle(bean.getType_name());
                        showLoading();
                        getData(1, false);
                    }
                });
            }
        };

        rcAll.setLayoutManager(new LinearLayoutManager(mContext));
        rcAll.setNestedScrollingEnabled(false);
        rcAll.setAdapter(allmAdapter);


        //附近
        final View nearView = getLayoutInflater().inflate(R.layout.act_goods_type_near, null);
        CommonTabLayout ctlGoodsTypeNear = ButterKnife.findById(nearView, R.id.ctlGoodsTypeNear);
        RecyclerView rvGoodsTypeYiji = ButterKnife.findById(nearView, R.id.rvGoodsTypeYiji);
        RecyclerView rvGoodsTypeErji = ButterKnife.findById(nearView, R.id.rvGoodsTypeErji);
        initTabLayout(ctlGoodsTypeNear);
        nearYijimAdapter = new BaseRecyclerAdapter<AreaBusinessCircleObj>(mContext, R.layout.item_goods_type_all) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, AreaBusinessCircleObj list) {
                TextView tvItemGoodsType = holder.getTextView(R.id.tvItemGoodsType);
                ImageView ivItemGoodsType = holder.getImageView(R.id.ivItemGoodsType);
                tvItemGoodsType.setText(list.getTitle());

                if (nearYijiCheck == i) {
                    tvItemGoodsType.setTextColor(mContext.getResources().getColor(R.color.home_green));
                    ivItemGoodsType.setVisibility(View.VISIBLE);

                } else {
                    tvItemGoodsType.setTextColor(mContext.getResources().getColor(R.color.gray_33));
                    ivItemGoodsType.setVisibility(View.GONE);
                }
                if (parentId.equals("1")) {
                    nearYijiCheck = i;
                    nearerjimAdapter.setList(parentList.get(0).getPca_list(), true);
                } else {

                }


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nearYijiCheck = i;
                        tvItemGoodsType.setTextColor(mContext.getResources().getColor(R.color.home_green));
                        ivItemGoodsType.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                        nearerjimAdapter.setList(list.getPca_list(), true);
                        nearErjiCheck = -1;
                        parentId = list.getId();


                    }
                });
            }
        };
        rvGoodsTypeYiji.setLayoutManager(new LinearLayoutManager(mContext));
        rvGoodsTypeYiji.setNestedScrollingEnabled(false);
        rvGoodsTypeYiji.setAdapter(nearYijimAdapter);

        nearerjimAdapter = new BaseRecyclerAdapter<AreaBusinessCircleObj.PcaListBean>(mContext, R.layout.item_goods_type_all) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, AreaBusinessCircleObj.PcaListBean childList) {
                TextView tvItemGoodsType = holder.getTextView(R.id.tvItemGoodsType);
                ImageView ivItemGoodsType = holder.getImageView(R.id.ivItemGoodsType);
                tvItemGoodsType.setText(childList.getTitle());
                if (nearErjiCheck == i) {
                    tvItemGoodsType.setTextColor(mContext.getResources().getColor(R.color.home_green));
                    ivItemGoodsType.setVisibility(View.VISIBLE);
                } else {
                    tvItemGoodsType.setTextColor(mContext.getResources().getColor(R.color.gray_33));
                    ivItemGoodsType.setVisibility(View.GONE);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nearErjiCheck = i;
                        if (parentId.equals("1")) {
                            distance = childList.getId();
                            area_id = "0";
                        } else {
                            area_id = childList.getId();
                            distance = "0";
                        }

                        tvItemGoodsType.setTextColor(mContext.getResources().getColor(R.color.home_green));
                        ivItemGoodsType.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                        ddm_goods_type.setTabText(childList.getTitle());
                        ddm_goods_type.closeMenu();
                        showLoading();
                        getData(1, false);
                    }
                });
            }
        };
        rvGoodsTypeErji.setLayoutManager(new LinearLayoutManager(mContext));
        rvGoodsTypeErji.setNestedScrollingEnabled(false);
        rvGoodsTypeErji.setAdapter(nearerjimAdapter);


        // 智能排序
        final RecyclerView rczhineng = new RecyclerView(this);
        zhinengmAdapter = new BaseRecyclerAdapter<String>(mContext, R.layout.item_goods_type_all) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, String zhineng) {
                TextView tvItemGoodsType = holder.getTextView(R.id.tvItemGoodsType);
                tvItemGoodsType.setText(zhineng);
                if (paixuCheck == i) {
                    tvItemGoodsType.setTextColor(mContext.getResources().getColor(R.color.home_green));
                } else {
                    tvItemGoodsType.setTextColor(mContext.getResources().getColor(R.color.gray_33));
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        paixuCheck = i;
                        sequencing = i + "";
                        tvItemGoodsType.setTextColor(mContext.getResources().getColor(R.color.home_green));
                        notifyDataSetChanged();
                        ddm_goods_type.setTabText(zhineng);
                        ddm_goods_type.closeMenu();
                        showLoading();
                        getData(1, false);
                    }
                });
            }
        };
        zhinengmAdapter.setList(Arrays.asList(zhineng));
        rczhineng.setLayoutManager(new LinearLayoutManager(mContext));
        rczhineng.setNestedScrollingEnabled(false);
        rczhineng.setAdapter(zhinengmAdapter);
        //筛选
        final View screenView = getLayoutInflater().inflate(R.layout.act_goods_type_screen, null);
        RecyclerView rvGoodsTypeScreen = ButterKnife.findById(screenView, R.id.rvGoodsTypeScreen);
        MyTextView tvItemGoodsTypeReset = ButterKnife.findById(screenView, R.id.tvItemGoodsTypeReset);
        MyTextView tvItemGoodsTypeComplete = ButterKnife.findById(screenView, R.id.tvItemGoodsTypeComplete);
        RadioGroup rgGoodsType = ButterKnife.findById(screenView, R.id.rgGoodsType);
        MyRadioButton rbGoodsTypeNeed = ButterKnife.findById(screenView, R.id.rbGoodsTypeNeed);
        MyRadioButton rbGoodsTypeNo = ButterKnife.findById(screenView, R.id.rbGoodsTypeNo);

        screenmAdapter = new BaseRecyclerAdapter<String>(mContext, R.layout.item_goods_type_screen) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, String screen) {
                TextView tvItemGoodsTypeScreen = holder.getTextView(R.id.tvItemGoodsTypeScreen);
                tvItemGoodsTypeScreen.setText(screen);

                if (screenCheck == i) {
                    tvItemGoodsTypeScreen.setTextColor(mContext.getResources().getColor(R.color.home_green));
                    tvItemGoodsTypeScreen.setBackground(mContext.getResources().getDrawable(R.drawable.shape_green_stoke));
                } else {
                    tvItemGoodsTypeScreen.setTextColor(mContext.getResources().getColor(R.color.gray_66));
                    tvItemGoodsTypeScreen.setBackground(mContext.getResources().getDrawable(R.drawable.shape_white_stoke));
                }


                tvItemGoodsTypeScreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        screenCheck = i;
                        dinner_time = i + "";
                        tvItemGoodsTypeScreen.setTextColor(mContext.getResources().getColor(R.color.home_green));
                        tvItemGoodsTypeScreen.setBackground(mContext.getResources().getDrawable(R.drawable.shape_green_stoke));
                        notifyDataSetChanged();
                    }
                });

            }


        };

        screenmAdapter.setList(Arrays.asList(screen));
        rvGoodsTypeScreen.setLayoutManager(new GridLayoutManager(mContext, 4));
        rvGoodsTypeScreen.setNestedScrollingEnabled(false);
        rvGoodsTypeScreen.setAdapter(screenmAdapter);
        tvItemGoodsTypeComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ddm_goods_type.setTabText(screen[screenCheck]);
                ddm_goods_type.closeMenu();
                showLoading();
                getData(1, false);
            }
        });
        tvItemGoodsTypeReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenCheck = -1;
                screenmAdapter.notifyDataSetChanged();
                rbGoodsTypeNeed.setChecked(false);
                rbGoodsTypeNo.setChecked(false);
                is_provide_rooms = "0";
                dinner_time = "0";


            }
        });
        tvItemGoodsTypeComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbGoodsTypeNeed.isChecked()) {
                    is_provide_rooms = "1";
                } else if (rbGoodsTypeNo.isChecked()) {
                    is_provide_rooms = "2";
                } else {
                    is_provide_rooms = "0";
                }
                ddm_goods_type.closeMenu();
                showLoading();
                getData(1, false);


            }

        });

        popupViews.add(rcAll);
        popupViews.add(nearView);
        popupViews.add(rczhineng);
        popupViews.add(screenView);


        RecyclerView rv_goods_type = mainView.findViewById(R.id.rv_goods_type);
        adapter = new LoadMoreAdapter<HomeTypeMerchantListObj.MerchantListBean>(mContext, R.layout.item_near_type, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, HomeTypeMerchantListObj.MerchantListBean bean) {
                ImageView iv_item_near_type_icon = holder.getImageView(R.id.iv_item_near_type_icon);
                TextView tv_item_near_type_name = holder.getTextView(R.id.tv_item_near_type_name);
                ImageView iv_item_near_type_star1 = holder.getImageView(R.id.iv_item_near_type_star1);
                ImageView iv_item_near_type_star2 = holder.getImageView(R.id.iv_item_near_type_star2);
                ImageView iv_item_near_type_star3 = holder.getImageView(R.id.iv_item_near_type_star3);
                ImageView iv_item_near_type_star4 = holder.getImageView(R.id.iv_item_near_type_star4);
                ImageView iv_item_near_type_star5 = holder.getImageView(R.id.iv_item_near_type_star5);
                TextView tv_item_near_type_star_num = holder.getTextView(R.id.tv_item_near_type_star_num);
                TextView tv_item_near_type_price = holder.getTextView(R.id.tv_item_near_type_price);
                TextView tv_item_near_type_address = holder.getTextView(R.id.tv_item_near_type_address);
                TextView tv_item_near_type_type2 = holder.getTextView(R.id.tv_item_near_type_type2);
                TextView tv_item_near_type_type = holder.getTextView(R.id.tv_item_near_type_type);
                TextView tv_item_near_type_distance = holder.getTextView(R.id.tv_item_near_type_distance);
                TextView tv_item_near_type_huodong2 = holder.getTextView(R.id.tv_item_near_type_huodong2);
                TextView tv_item_near_type_huodong = holder.getTextView(R.id.tv_item_near_type_huodong);
                Glide.with(mContext).
                        load(bean.getMerchant_avatar()).
                        error(R.color.c_press).
                        into(iv_item_near_type_icon);
                tv_item_near_type_name.setText(bean.getMerchant_name());
                tv_item_near_type_star_num.setText(bean.getScoring() + "");
                tv_item_near_type_price.setText("¥" + bean.getMoney_people() + "/人");
                tv_item_near_type_address.setText(bean.getMerchant_address());
                tv_item_near_type_type.setText(bean.getCuisine());
                tv_item_near_type_distance.setText(bean.getDistance());
                if (bean.getScoring() == 1) {
                    iv_item_near_type_star1.setVisibility(View.VISIBLE);
                    iv_item_near_type_star2.setVisibility(View.GONE);
                    iv_item_near_type_star3.setVisibility(View.GONE);
                    iv_item_near_type_star4.setVisibility(View.GONE);
                    iv_item_near_type_star5.setVisibility(View.GONE);
                } else if (bean.getScoring() == 2) {
                    iv_item_near_type_star1.setVisibility(View.VISIBLE);
                    iv_item_near_type_star2.setVisibility(View.VISIBLE);
                    iv_item_near_type_star3.setVisibility(View.GONE);
                    iv_item_near_type_star4.setVisibility(View.GONE);
                    iv_item_near_type_star5.setVisibility(View.GONE);
                } else if (bean.getScoring() == 3) {
                    iv_item_near_type_star1.setVisibility(View.VISIBLE);
                    iv_item_near_type_star2.setVisibility(View.VISIBLE);
                    iv_item_near_type_star3.setVisibility(View.VISIBLE);
                    iv_item_near_type_star4.setVisibility(View.GONE);
                    iv_item_near_type_star5.setVisibility(View.GONE);
                } else if (bean.getScoring() == 4) {
                    iv_item_near_type_star1.setVisibility(View.VISIBLE);
                    iv_item_near_type_star2.setVisibility(View.VISIBLE);
                    iv_item_near_type_star3.setVisibility(View.VISIBLE);
                    iv_item_near_type_star4.setVisibility(View.VISIBLE);
                    iv_item_near_type_star5.setVisibility(View.GONE);
                } else if (bean.getScoring() == 5) {
                    iv_item_near_type_star1.setVisibility(View.VISIBLE);
                    iv_item_near_type_star2.setVisibility(View.VISIBLE);
                    iv_item_near_type_star3.setVisibility(View.VISIBLE);
                    iv_item_near_type_star4.setVisibility(View.VISIBLE);
                    iv_item_near_type_star5.setVisibility(View.VISIBLE);
                } else {
                    iv_item_near_type_star1.setVisibility(View.GONE);
                    iv_item_near_type_star2.setVisibility(View.GONE);
                    iv_item_near_type_star3.setVisibility(View.GONE);
                    iv_item_near_type_star4.setVisibility(View.GONE);
                    iv_item_near_type_star5.setVisibility(View.GONE);
                }
                String type = "";
                if (bean.getLable().size() == 0) {
                    tv_item_near_type_type2.setVisibility(View.GONE);
                } else {
                    tv_item_near_type_type2.setVisibility(View.VISIBLE);
                    for (int i = 0; i < bean.getLable().size(); i++) {
                        String type2 = bean.getLable().get(i);
                        type = type + "," + type2;
                    }
                    tv_item_near_type_type2.setText(type.substring(1, type.length()));
                }
                if (bean.getActivity().size() == 0) {
                    tv_item_near_type_huodong.setVisibility(View.GONE);
                    tv_item_near_type_huodong2.setVisibility(View.GONE);
                } else {
                    tv_item_near_type_huodong.setVisibility(View.VISIBLE);
                    tv_item_near_type_huodong2.setVisibility(View.VISIBLE);
                    String man = "满";
                    String jian = "减";
                    String yuan = "元";
                    String youhui = "";
                    for (int i = 0; i < bean.getActivity().size(); i++) {
                        String youhui2 = man + bean.getActivity().get(i).getFull_amount() + jian + bean.getActivity().get(i).getSubtract_amount() + yuan;
                        youhui = youhui + "," + youhui2;
                    }
                    tv_item_near_type_huodong2.setText(youhui.substring(1, youhui.length()));
                }


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
        rv_goods_type.setNestedScrollingEnabled(false);
        rv_goods_type.setLayoutManager(new LinearLayoutManager(mContext));
        rv_goods_type.addItemDecoration(getItemDivider());
        rv_goods_type.setAdapter(adapter);


        ddm_goods_type.setDropDownMenu(headers, popupViews, mainView);


    }


    private void initTabLayout(CommonTabLayout ctl) {
        for (int i = 0; i < titles.length; i++) {
            tabEntities.add(new TabEntity(titles[i], 0, 0));
        }
        ctl.setTabData(tabEntities);

        ctl.setCurrentTab(0);
        ctl.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                //重新刷新RecyclerView
//                initRcv();

            }

            @Override
            public void onTabReselect(int position) {

            }
        });


    }



    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String, String> map = new HashMap<String, String>();
        map.put("pagesize", pageSize + "");
        map.put("page", page + "");
        map.put("sign", GetSign.getSign(map));
        HomeTypeMerchantListBody body = new HomeTypeMerchantListBody();
        body.setType_id(type_id);
        body.setLat(MyApplication.getWeiDu(mContext) + "");
        body.setLng(MyApplication.getJingDu(mContext) + "");
        body.setCity_id(city_id_xuanze);
        body.setArea_id(area_id);
        body.setIs_provide_rooms(is_provide_rooms);
        body.setDistance(distance);
        body.setSequencing(sequencing);
        body.setDinner_time(dinner_time);
        body.setCity_type(SPUtils.getPrefInt(mContext, Config.city_level,3)+"");
        ApiRequest.postMerchantList(map, body, new MyCallBack<HomeTypeMerchantListObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(HomeTypeMerchantListObj obj) {
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
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (ddm_goods_type.isShowing()) {
            ddm_goods_type.closeMenu();
        } else {
            super.onBackPressed();
        }
    }


    @OnClick({R.id.app_right_iv})
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_iv:

                STActivity(SearchActivity.class);
            break;
        }
    }

}

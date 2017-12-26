package com.sk.uudc.module.near.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.github.customview.MyLinearLayout;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseFragment;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.home.activity.SearchActivity;
import com.sk.uudc.module.near.activity.NearMapActivity;
import com.sk.uudc.module.near.adapter.NearFragmentAdapter;
import com.sk.uudc.module.near.network.ApiRequest;
import com.sk.uudc.module.near.network.response.NearShangJiaObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/4.
 */

public class NearFragment extends BaseFragment {
    @BindView(R.id.tv_near_search)
    TextView tv_near_search;
    @BindView(R.id.ll_near_search)
    MyLinearLayout ll_near_search;
    @BindView(R.id.iv_near_search)
    ImageView iv_near_search;
    @BindView(R.id.tv_near_map)
    TextView tv_near_map;
    @BindView(R.id.ll_near_top)
    MyLinearLayout ll_near_top;
    @BindView(R.id.stl_near)
    SlidingTabLayout stl_near;
//    @BindView(R.id.rv_near)
//    RecyclerView rv_near;
//    @BindView(R.id.pcf_near)
//    PtrClassicFrameLayout pcf_near;
    @BindView(R.id.vp_near)
    ViewPager vp_near;

    private List<Fragment> fragments = new ArrayList<>();
    private NearFragmentAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.frag_near;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getNearShangJiaType(map, new MyCallBack<NearShangJiaObj>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(NearShangJiaObj obj) {
                initTabLayout(obj.getType_list());
            }
        });
    }
    private void initTabLayout(List<NearShangJiaObj.TypeListBean> list) {
        for (int i = 0; i < list.size(); i++) {
            fragments.add(NearFramentVP.newInstance(list.get(i).getType_id()+""));
        }
        adapter = new NearFragmentAdapter(getChildFragmentManager());
        adapter.setList(fragments);
        adapter.setListBeen(list);
        vp_near.setOffscreenPageLimit(fragments.size()-1);
        vp_near.setAdapter(adapter);

        stl_near.setViewPager(vp_near);

    }


    @OnClick({R.id.ll_near_search, R.id.tv_near_map})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_near_search:
                STActivity(SearchActivity.class);
//                STActivity(SearchShangJiaActivity.class);
                break;
            case R.id.tv_near_map:
                STActivity(NearMapActivity.class);
                break;
        }
    }


}

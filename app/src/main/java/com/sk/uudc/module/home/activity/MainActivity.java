package com.sk.uudc.module.home.activity;

import android.view.View;
import android.widget.FrameLayout;

import com.github.customview.MyRadioButton;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.module.home.fragment.HomeFragment;
import com.sk.uudc.module.my.fragment.MyFragment;
import com.sk.uudc.module.near.fragment.NearFragment;
import com.sk.uudc.module.order.fragment.OrderFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MainActivity extends BaseActivity {

    HomeFragment homeFragment;
    NearFragment nearFragment;
    OrderFragment orderFragment;
    MyFragment myFragment;

    @BindView(R.id.layout_main_content)
    FrameLayout layout_main_content;
    @BindView(R.id.rb_home)
    MyRadioButton rb_home;

    @BindView(R.id.rb_home_near)
    MyRadioButton rb_home_near;

    @BindView(R.id.rb_home_order)
    MyRadioButton rb_home_order;

    @BindView(R.id.rb_home_my)
    MyRadioButton rb_home_my;

    private MyRadioButton selectButton;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        selectButton = rb_home;
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, homeFragment).commitAllowingStateLoss();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.rb_home, R.id.rb_home_near, R.id.rb_home_order, R.id.rb_home_my})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.rb_home:
                selectHome();
                break;
            case R.id.rb_home_near:
                selectNear();
                break;
            case R.id.rb_home_order:
                selectOrder();
                break;
            case R.id.rb_home_my:
                /*if (TextUtils.isEmpty(getUserId())) {
                    selectButton.setChecked(true);
                    STActivity(LoginActivity.class);
                    return;
                }*/
                selectMy();
                break;
        }
    }

    private void selectMy() {
        selectButton = rb_home_my;
        if (myFragment == null) {
            myFragment = new MyFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, myFragment).commitAllowingStateLoss();
        } else {
            showFragment(myFragment);
        }
        hideFragment(homeFragment);
        hideFragment(nearFragment);
        hideFragment(orderFragment);
    }

    private void selectOrder() {
        selectButton = rb_home_order;
        if (orderFragment == null) {
            orderFragment = new OrderFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, orderFragment).commitAllowingStateLoss();
        } else {
            showFragment(orderFragment);
        }
        hideFragment(myFragment);
        hideFragment(nearFragment);
        hideFragment(homeFragment);
    }

    private void selectNear() {
        selectButton = rb_home_near;
        if (nearFragment == null) {
            nearFragment = new NearFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, nearFragment).commitAllowingStateLoss();
        } else {
            showFragment(nearFragment);
        }
        hideFragment(homeFragment);
        hideFragment(myFragment);
        hideFragment(orderFragment);
    }

    private void selectHome() {
        selectButton = rb_home;
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, homeFragment).commitAllowingStateLoss();
        } else {
            showFragment(homeFragment);
        }
        hideFragment(nearFragment);
        hideFragment(orderFragment);
        hideFragment(myFragment);
    }
}

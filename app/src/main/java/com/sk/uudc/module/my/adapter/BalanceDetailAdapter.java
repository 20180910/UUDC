package com.sk.uudc.module.my.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/7.
 */

public class BalanceDetailAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list;
    private String[]title={"全部","收入","支出"};
    public void setList(List<Fragment> list) {
        this.list = list;
    }

    public BalanceDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return title[position];
    }
}

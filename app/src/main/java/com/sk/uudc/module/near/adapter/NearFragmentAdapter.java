package com.sk.uudc.module.near.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sk.uudc.module.near.network.response.NearShangJiaObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class NearFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list;
    private List<NearShangJiaObj.TypeListBean> listBeen;
    public void setList(List<Fragment> list) {
        this.list = list;
    }

    public NearFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public List<NearShangJiaObj.TypeListBean> getListBeen() {
        return listBeen;
    }

    public void setListBeen(List<NearShangJiaObj.TypeListBean> listBeen) {
        this.listBeen = listBeen;
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
        return listBeen.get(position).getType_name();
    }
}

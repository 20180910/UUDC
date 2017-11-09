package com.sk.uudc.module.my.bean;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by Administrator on 2017/11/7.
 */

public class UpdateBean implements CustomTabEntity {
    private String title;
    public UpdateBean(String title) {
        this.title=title;
    }

    @Override
    public String getTabTitle() {
        return this.title;
    }

    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }
}

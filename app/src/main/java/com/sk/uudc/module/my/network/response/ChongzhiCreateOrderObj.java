package com.sk.uudc.module.my.network.response;

import com.sk.uudc.base.BaseObj;

/**
 * Created by Administrator on 2017/11/27.
 */

public class ChongzhiCreateOrderObj extends BaseObj {

    /**
     * order_no : C201711271554408663
     * money : 10
     */

    private String order_no;
    private double money;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}

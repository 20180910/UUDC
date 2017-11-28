package com.sk.uudc.module.near.network.response;

import com.sk.uudc.base.BaseObj;

/**
 * Created by Administrator on 2017/11/27.
 */

public class CommitOrderResultObj extends BaseObj {
    /**
     * order_id : 13
     * order_no : Y201711271131491647
     * combined : 50.11
     */

    private int order_id;
    private String order_no;
    private double combined;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public double getCombined() {
        return combined;
    }

    public void setCombined(double combined) {
        this.combined = combined;
    }
}

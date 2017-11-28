package com.sk.uudc.module.my.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class MyShouyiObj extends BaseObj {


    /**
     * commission : 0
     * history_commission : 0
     * zuori_commission : 0
     * commissiondetail : [{"value":50,"remark":"收益","add_time":"2017-11-21 11:48"},{"value":50,"remark":"收益","add_time":"2017-11-21 11:48"},{"value":50,"remark":"收益","add_time":"2017-11-21 11:48"}]
     */

    private double commission;
    private double history_commission;
    private double zuori_commission;
    private List<CommissiondetailBean> commissiondetail;

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getHistory_commission() {
        return history_commission;
    }

    public void setHistory_commission(double history_commission) {
        this.history_commission = history_commission;
    }

    public double getZuori_commission() {
        return zuori_commission;
    }

    public void setZuori_commission(double zuori_commission) {
        this.zuori_commission = zuori_commission;
    }

    public List<CommissiondetailBean> getCommissiondetail() {
        return commissiondetail;
    }

    public void setCommissiondetail(List<CommissiondetailBean> commissiondetail) {
        this.commissiondetail = commissiondetail;
    }

    public static class CommissiondetailBean {
        /**
         * value : 50
         * remark : 收益
         * add_time : 2017-11-21 11:48
         */

        private double value;
        private String remark;
        private String add_time;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}

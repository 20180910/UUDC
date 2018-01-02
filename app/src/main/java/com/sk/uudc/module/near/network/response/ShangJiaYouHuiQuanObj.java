package com.sk.uudc.module.near.network.response;

import com.sk.uudc.base.BaseObj;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ShangJiaYouHuiQuanObj extends BaseObj {
    /**
     * total_amount : 100
     * total_number : 1
     * coupon_redemption_list : [{"coupon_redemption_id":8,"title":"满500减100","face_value":100,"available":500,"end_time":"2018-1-9","is_status":0}]
     */

    private String merchant_id;
    private double total_amount;
    private int total_number;
    private List<CouponRedemptionListBean> coupon_redemption_list;

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public int getTotal_number() {
        return total_number;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public List<CouponRedemptionListBean> getCoupon_redemption_list() {
        return coupon_redemption_list;
    }

    public void setCoupon_redemption_list(List<CouponRedemptionListBean> coupon_redemption_list) {
        this.coupon_redemption_list = coupon_redemption_list;
    }

    public static class CouponRedemptionListBean implements Serializable {
        /**
         * coupon_redemption_id : 8
         * title : 满500减100
         * face_value : 100
         * available : 500
         * end_time : 2018-1-9
         * is_status : 0
         */

        private int coupon_redemption_id;
        private String title;
        private double face_value;
        private double available;
        private String end_time;
        private int is_status;

        public int getCoupon_redemption_id() {
            return coupon_redemption_id;
        }

        public void setCoupon_redemption_id(int coupon_redemption_id) {
            this.coupon_redemption_id = coupon_redemption_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getFace_value() {
            return face_value;
        }

        public void setFace_value(double face_value) {
            this.face_value = face_value;
        }

        public double getAvailable() {
            return available;
        }

        public void setAvailable(double available) {
            this.available = available;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getIs_status() {
            return is_status;
        }

        public void setIs_status(int is_status) {
            this.is_status = is_status;
        }
    }
}

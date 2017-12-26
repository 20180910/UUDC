package com.sk.uudc.module.order.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class MyOrderObj extends BaseObj {

    private List<MyorderBean> myorder;

    public List<MyorderBean> getMyorder() {
        return myorder;
    }

    public void setMyorder(List<MyorderBean> myorder) {
        this.myorder = myorder;
    }

    public static class MyorderBean {
        /**
         * order_id : 5
         * order_no : Y201711231711550457
         * order_status : 4
         * merchant_avatar : http://121.40.186.118:5019/upload/sjtx.png
         * merchant_name : 金拱门
         * goods_name : 草莓新地
         * goods_count : 2
         * combined : 28.89
         * dine_time : 2017年11月23日12:00
         * is_jiacai :0
         */

        private String merchant_id;
        private String order_id;
        private String order_no;
        private int order_status;
        private int is_jiacai;
        private String merchant_avatar;
        private String merchant_name;
        private String goods_name;
        private String goods_count;
        private double combined;
        private String dine_time;
        public int getIs_jiacai() {
            return is_jiacai;
        }

        public void setIs_jiacai(int is_jiacai) {
            this.is_jiacai = is_jiacai;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getMerchant_avatar() {
            return merchant_avatar;
        }

        public void setMerchant_avatar(String merchant_avatar) {
            this.merchant_avatar = merchant_avatar;
        }

        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_count() {
            return goods_count;
        }

        public void setGoods_count(String goods_count) {
            this.goods_count = goods_count;
        }

        public double getCombined() {
            return combined;
        }

        public void setCombined(double combined) {
            this.combined = combined;
        }

        public String getDine_time() {
            return dine_time;
        }

        public void setDine_time(String dine_time) {
            this.dine_time = dine_time;
        }
    }
}

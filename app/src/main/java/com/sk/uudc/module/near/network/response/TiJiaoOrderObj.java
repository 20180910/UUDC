package com.sk.uudc.module.near.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class TiJiaoOrderObj extends BaseObj {
    /**
     * merchant_id : 2
     * merchant_name : 香锅冒菜
     * merchant_avatar : http://121.40.186.118:5019/upload/sjtx.png
     * goods_list : [{"goods_id":12,"goods_name":"白米饭一盒","goods_price":3.01,"number":4},{"goods_id":13,"goods_name":"娃娃菜","goods_price":4.08,"number":10}]
     * merchants_preferential : 10
     * to_pay : 42.84
     * dine_time : 2017-11-27 11:00
     * dine_num_people : 2
     * is_require_rooms : 0
     */

    private int merchant_id;
    private String merchant_name;
    private String merchant_avatar;
    private double merchants_preferential;
    private double to_pay;
    private String dine_time;
    private int time_id;
    private int dine_num_people;
    private int is_require_rooms;
    private List<GoodsListBean> goods_list;

    public int getMerchant_id() {
        return merchant_id;
    }

    public int getTime_id() {
        return time_id;
    }

    public void setTime_id(int time_id) {
        this.time_id = time_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_avatar() {
        return merchant_avatar;
    }

    public void setMerchant_avatar(String merchant_avatar) {
        this.merchant_avatar = merchant_avatar;
    }

    public double getMerchants_preferential() {
        return merchants_preferential;
    }

    public void setMerchants_preferential(double merchants_preferential) {
        this.merchants_preferential = merchants_preferential;
    }

    public double getTo_pay() {
        return to_pay;
    }

    public void setTo_pay(double to_pay) {
        this.to_pay = to_pay;
    }

    public String getDine_time() {
        return dine_time;
    }

    public void setDine_time(String dine_time) {
        this.dine_time = dine_time;
    }

    public int getDine_num_people() {
        return dine_num_people;
    }

    public void setDine_num_people(int dine_num_people) {
        this.dine_num_people = dine_num_people;
    }

    public int getIs_require_rooms() {
        return is_require_rooms;
    }

    public void setIs_require_rooms(int is_require_rooms) {
        this.is_require_rooms = is_require_rooms;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {
        /**
         * goods_id : 12
         * goods_name : 白米饭一盒
         * goods_price : 3.01
         * number : 4
         */

        private int goods_id;
        private String goods_name;
        private double goods_price;
        private int number;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public double getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(double goods_price) {
            this.goods_price = goods_price;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}

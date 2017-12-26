package com.sk.uudc.module.order.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class JiaCaiOrderObj extends BaseObj {
    /**
     * addfood_order_no : J201711301443062194
     * goods_list : [{"goods_name":"黄焖鸡米饭小份+米饭","goods_price":0.01,"goods_number":1}]
     * youhui_money : 0
     * combined : 0.01
     * pay_status : 未支付
     */

    private String addfood_order_no;
    private double youhui_money;
    private double combined;
    private String pay_status;
    private List<GoodsListBean> goods_list;

    public String getAddfood_order_no() {
        return addfood_order_no;
    }

    public void setAddfood_order_no(String addfood_order_no) {
        this.addfood_order_no = addfood_order_no;
    }

    public double getYouhui_money() {
        return youhui_money;
    }

    public void setYouhui_money(double youhui_money) {
        this.youhui_money = youhui_money;
    }

    public double getCombined() {
        return combined;
    }

    public void setCombined(double combined) {
        this.combined = combined;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {
        /**
         * goods_name : 黄焖鸡米饭小份+米饭
         * goods_price : 0.01
         * goods_number : 1
         */

        private String goods_name;
        private double goods_price;
        private int goods_number;

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

        public int getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(int goods_number) {
            this.goods_number = goods_number;
        }
    }
}

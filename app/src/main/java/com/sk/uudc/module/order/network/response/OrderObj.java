package com.sk.uudc.module.order.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */

public class OrderObj extends BaseObj {

    /**
     * daifukuan : 1
     * daishiyong : 1
     * daipingjia : 1
     * yiquxiao : 1
     * order_list : [{"order_id":5,"order_no":"Y201711231711550457","order_status":4,"merchant_avatar":"http://121.40.186.118:5019/upload/sjtx.png","merchant_name":"金拱门","goods_name":"草莓新地","goods_count":2,"combined":28.89,"dine_time":"2017年11月23日12:00"},{"order_id":4,"order_no":"Y201711231711223714","order_status":3,"merchant_avatar":"http://121.40.186.118:5019/upload/sjtx.png","merchant_name":"金拱门","goods_name":"麦辣鸡翅2块","goods_count":2,"combined":36.02,"dine_time":"2017年11月23日12:00"},{"order_id":3,"order_no":"Y201711231710478776","order_status":2,"merchant_avatar":"http://121.40.186.118:5019/upload/sjtx.png","merchant_name":"金拱门","goods_name":"经典麦辣鸡腿汉堡","goods_count":2,"combined":27.23,"dine_time":"2017年11月23日12:00"},{"order_id":2,"order_no":"Y201711231708366153","order_status":1,"merchant_avatar":"http://121.40.186.118:5019/upload/sjtx.png","merchant_name":"香锅冒菜","goods_name":"澳洲肥牛卷","goods_count":2,"combined":19.28,"dine_time":"2017年11月23日11:00"}]
     */

    private int daifukuan;
    private int daishiyong;
    private int daipingjia;
    private int yiquxiao;
    private List<OrderListBean> order_list;

    public int getDaifukuan() {
        return daifukuan;
    }

    public void setDaifukuan(int daifukuan) {
        this.daifukuan = daifukuan;
    }

    public int getDaishiyong() {
        return daishiyong;
    }

    public void setDaishiyong(int daishiyong) {
        this.daishiyong = daishiyong;
    }

    public int getDaipingjia() {
        return daipingjia;
    }

    public void setDaipingjia(int daipingjia) {
        this.daipingjia = daipingjia;
    }

    public int getYiquxiao() {
        return yiquxiao;
    }

    public void setYiquxiao(int yiquxiao) {
        this.yiquxiao = yiquxiao;
    }

    public List<OrderListBean> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<OrderListBean> order_list) {
        this.order_list = order_list;
    }

    public static class OrderListBean {
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
         */

        private String order_id;
        private String order_no;
        private int order_status;
        private String merchant_avatar;
        private String merchant_name;
        private String goods_name;
        private int goods_count;
        private double combined;
        private String dine_time;

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

        public int getGoods_count() {
            return goods_count;
        }

        public void setGoods_count(int goods_count) {
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

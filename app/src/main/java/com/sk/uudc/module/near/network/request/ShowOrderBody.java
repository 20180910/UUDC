package com.sk.uudc.module.near.network.request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/25.
 */

public class ShowOrderBody implements Serializable{
    /**
     * ShowOrder : [{"goods_id":1,"number":2},{"goods_id":1,"number":2}]
     * user_id : 1
     * merchant_id : 2
     * dine_time : 2017-11-25T15:00:26.0041762+08:00
     * time_id : 4
     * dine_num_people : 5
     * is_require_rooms : 6
     */

    private String user_id;
    private String order_id;
    private String merchant_id;
    private String dine_time;
    private int time_id;
    private String dine_num_people;
    private int is_require_rooms;
    private List<ShowOrderBean> ShowOrder;

    public String getUser_id() {
        return user_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getDine_time() {
        return dine_time;
    }

    public void setDine_time(String dine_time) {
        this.dine_time = dine_time;
    }

    public int getTime_id() {
        return time_id;
    }

    public void setTime_id(int time_id) {
        this.time_id = time_id;
    }

    public String getDine_num_people() {
        return dine_num_people;
    }

    public void setDine_num_people(String dine_num_people) {
        this.dine_num_people = dine_num_people;
    }

    public int getIs_require_rooms() {
        return is_require_rooms;
    }

    public void setIs_require_rooms(int is_require_rooms) {
        this.is_require_rooms = is_require_rooms;
    }

    public List<ShowOrderBean> getShowOrder() {
        return ShowOrder;
    }

    public void setShowOrder(List<ShowOrderBean> ShowOrder) {
        this.ShowOrder = ShowOrder;
    }

    public static class ShowOrderBean implements Serializable{
        /**
         * goods_id : 1
         * number : 2
         */

        private int goods_id;
        private int number;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}

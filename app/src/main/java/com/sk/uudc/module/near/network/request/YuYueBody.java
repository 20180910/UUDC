package com.sk.uudc.module.near.network.request;

/**
 * Created by Administrator on 2017/11/25.
 */

public class YuYueBody {

    /**
     * merchant_id : 1
     * dine_time : 2017-11-28T09:17:20.7849891+08:00
     * time_id : 3
     * dine_num_people : 4
     * is_require_rooms : 5
     */

    private String merchant_id;
    private String dine_time;
    private int time_id;
    private String dine_num_people;
    private int is_require_rooms;

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
}

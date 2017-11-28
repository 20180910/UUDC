package com.sk.uudc.module.near.network.response;

/**
 * Created by Administrator on 2017/11/24.
 */

public class YuYueTimeObj  {
    /**
     * time_id : 1
     * begin_time : 10
     * end_time : 11
     * is_gouxuan : 0
     */

    private int time_id;
    private String begin_time;
    private String end_time;
    private int is_gouxuan;

    public int getTime_id() {
        return time_id;
    }

    public void setTime_id(int time_id) {
        this.time_id = time_id;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getIs_gouxuan() {
        return is_gouxuan;
    }

    public void setIs_gouxuan(int is_gouxuan) {
        this.is_gouxuan = is_gouxuan;
    }
}

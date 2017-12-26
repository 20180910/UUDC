package com.sk.uudc.module.near.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ShangJiaInfoObj extends BaseObj {

    /**
     * merchant_id : 1
     * merchant_address : 长宁区兆丰大厦N楼
     * merchant_photo_list : ["http://121.40.186.118:5019/upload/jjt.jpg","http://121.40.186.118:5019/upload/jjt.jpg","http://121.40.186.118:5019/upload/jjt.jpg"]
     * scheduled_time : 10:00~23:00
     * merchant_remark :
     * "merchant_tel": "13845673210"
     * activity : [{"full_amount":50,"subtract_amount":20},{"full_amount":100,"subtract_amount":50},{"full_amount":200,"subtract_amount":100}]
     */

    private int merchant_id;
    private String merchant_address;
    private String scheduled_time;
    private String merchant_remark;
    private String merchant_tel;
    private List<String> merchant_photo_list;
    private List<ActivityBean> activity;

    public String getMerchant_tel() {
        return merchant_tel;
    }

    public void setMerchant_tel(String merchant_tel) {
        this.merchant_tel = merchant_tel;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_address() {
        return merchant_address;
    }

    public void setMerchant_address(String merchant_address) {
        this.merchant_address = merchant_address;
    }

    public String getScheduled_time() {
        return scheduled_time;
    }

    public void setScheduled_time(String scheduled_time) {
        this.scheduled_time = scheduled_time;
    }

    public String getMerchant_remark() {
        return merchant_remark;
    }

    public void setMerchant_remark(String merchant_remark) {
        this.merchant_remark = merchant_remark;
    }

    public List<String> getMerchant_photo_list() {
        return merchant_photo_list;
    }

    public void setMerchant_photo_list(List<String> merchant_photo_list) {
        this.merchant_photo_list = merchant_photo_list;
    }

    public List<ActivityBean> getActivity() {
        return activity;
    }

    public void setActivity(List<ActivityBean> activity) {
        this.activity = activity;
    }

    public static class ActivityBean {
        /**
         * full_amount : 50
         * subtract_amount : 20
         */

        private int full_amount;
        private int subtract_amount;

        public int getFull_amount() {
            return full_amount;
        }

        public void setFull_amount(int full_amount) {
            this.full_amount = full_amount;
        }

        public int getSubtract_amount() {
            return subtract_amount;
        }

        public void setSubtract_amount(int subtract_amount) {
            this.subtract_amount = subtract_amount;
        }
    }
}

package com.sk.uudc.module.my.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/25.
 */

public class MyEvaluateObj extends BaseObj {

    /**
     * daipingjia : 2
     * dpj_list : [{"order_id":11,"merchant_avatar":"http://121.40.186.118:5019/upload/sjtx.png","merchant_name":"金拱门","dine_time":"2017-11-24"}]
     * list : [{"appraise_id":2,"content":"恭喜您你明明","scoring":4,"add_time":"2017-11-25 11:59","merchant_id":4,"merchant_avatar":"http://121.40.186.118:5019/upload/sjtx.png","merchant_name":"金拱门","merchant_appraise_image":["http://121.40.186.118:5019/upload/201711/25/171125115849697351.jpg","http://121.40.186.118:5019/upload/201711/25/171125115859531728.jpg","http://121.40.186.118:5019/upload/201711/25/171125115907128522.jpg"]}]
     */

    private int daipingjia;
    private List<DpjListBean> dpj_list;
    private List<ListBean> list;

    public int getDaipingjia() {
        return daipingjia;
    }

    public void setDaipingjia(int daipingjia) {
        this.daipingjia = daipingjia;
    }

    public List<DpjListBean> getDpj_list() {
        return dpj_list;
    }

    public void setDpj_list(List<DpjListBean> dpj_list) {
        this.dpj_list = dpj_list;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class DpjListBean {
        /**
         * order_id : 11
         * merchant_avatar : http://121.40.186.118:5019/upload/sjtx.png
         * merchant_name : 金拱门
         * dine_time : 2017-11-24
         */

        private String order_id;
        private String merchant_avatar;
        private String merchant_name;
        private String dine_time;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
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

        public String getDine_time() {
            return dine_time;
        }

        public void setDine_time(String dine_time) {
            this.dine_time = dine_time;
        }
    }

    public static class ListBean {
        /**
         * appraise_id : 2
         * content : 恭喜您你明明
         * scoring : 4
         * add_time : 2017-11-25 11:59
         * merchant_id : 4
         * merchant_avatar : http://121.40.186.118:5019/upload/sjtx.png
         * merchant_name : 金拱门
         * merchant_appraise_image : ["http://121.40.186.118:5019/upload/201711/25/171125115849697351.jpg","http://121.40.186.118:5019/upload/201711/25/171125115859531728.jpg","http://121.40.186.118:5019/upload/201711/25/171125115907128522.jpg"]
         */

        private String appraise_id;
        private String content;
        private double scoring;
        private String add_time;
        private String merchant_id;
        private String merchant_avatar;
        private String merchant_name;
        private List<String> merchant_appraise_image;

        public String getAppraise_id() {
            return appraise_id;
        }

        public void setAppraise_id(String appraise_id) {
            this.appraise_id = appraise_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public double getScoring() {
            return scoring;
        }

        public void setScoring(double scoring) {
            this.scoring = scoring;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
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

        public List<String> getMerchant_appraise_image() {
            return merchant_appraise_image;
        }

        public void setMerchant_appraise_image(List<String> merchant_appraise_image) {
            this.merchant_appraise_image = merchant_appraise_image;
        }
    }
}

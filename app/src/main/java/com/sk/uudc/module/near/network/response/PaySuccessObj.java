package com.sk.uudc.module.near.network.response;

import com.sk.uudc.base.BaseObj;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class PaySuccessObj extends BaseObj {
    /**
     * verification_code : 32596193
     * list : [{"merchant_id":2,"merchant_name":"香锅冒菜","merchant_avatar":"http://localhost:8020/upload/sjtx.png","distance":"592M"},{"merchant_id":1,"merchant_name":"黄焖鸡米饭","merchant_avatar":"http://localhost:8020/upload/sjtx.png","distance":"328M"}]
     */

    private String verification_code;
    private List<ListBean> list;

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * merchant_id : 2
         * merchant_name : 香锅冒菜
         * merchant_avatar : http://localhost:8020/upload/sjtx.png
         * distance : 592M
         */

        private int merchant_id;
        private String merchant_name;
        private String merchant_avatar;
        private String distance;

        public int getMerchant_id() {
            return merchant_id;
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

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}

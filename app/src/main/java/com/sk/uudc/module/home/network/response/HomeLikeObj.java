package com.sk.uudc.module.home.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public class HomeLikeObj extends BaseObj {

    private List<MerchantListBean> MerchantList;

    public List<MerchantListBean> getMerchantList() {
        return MerchantList;
    }

    public void setMerchantList(List<MerchantListBean> MerchantList) {
        this.MerchantList = MerchantList;
    }

    public static class MerchantListBean {
        /**
         * merchant_id : 1
         * merchant_avatar : http://121.40.186.118:5019/upload/sjtx.png
         * merchant_name : 黄焖鸡米饭
         * money_people : 30
         * merchant_address : 兆丰大厦N楼
         * scoring : 5
         * Distance : -1
         * lat : 31.226764
         * lng : 121.431836
         * lable : ["酒水饮料"]
         */

        private String merchant_id;
        private String merchant_avatar;
        private String merchant_name;
        private double money_people;
        private String merchant_address;
        private int scoring;
        private String Distance;
        private String lat;
        private String lng;
        private List<String> lable;

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

        public double getMoney_people() {
            return money_people;
        }

        public void setMoney_people(double money_people) {
            this.money_people = money_people;
        }

        public String getMerchant_address() {
            return merchant_address;
        }

        public void setMerchant_address(String merchant_address) {
            this.merchant_address = merchant_address;
        }

        public int getScoring() {
            return scoring;
        }

        public void setScoring(int scoring) {
            this.scoring = scoring;
        }

        public String getDistance() {
            return Distance;
        }

        public void setDistance(String Distance) {
            this.Distance = Distance;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public List<String> getLable() {
            return lable;
        }

        public void setLable(List<String> lable) {
            this.lable = lable;
        }
    }
}

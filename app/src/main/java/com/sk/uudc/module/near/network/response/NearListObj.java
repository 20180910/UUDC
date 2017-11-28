package com.sk.uudc.module.near.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class NearListObj extends BaseObj {
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
         * merchant_avatar :
         * merchant_name : 黄焖鸡米饭(兆丰大厦店)
         * money_people : 30
         * merchant_address : 兆丰大厦N楼
         * scoring : 5
         * Distance : 0M
         * lat : 31.564211
         * lng : 121.455215
         * cuisine : 湘菜
         * lable : ["炒菜","火锅","酒水饮料"]
         * activity : [{"full_amount":50,"subtract_amount":20},{"full_amount":100,"subtract_amount":50},{"full_amount":200,"subtract_amount":100}]
         */

        private int merchant_id;
        private String merchant_avatar;
        private String merchant_name;
        private double money_people;
        private String merchant_address;
        private double scoring;
        private String Distance;
        private double lat;
        private double lng;
        private String cuisine;
        private List<String> lable;
        private List<ActivityBean> activity;

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
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

        public double getScoring() {
            return scoring;
        }

        public void setScoring(double scoring) {
            this.scoring = scoring;
        }

        public String getDistance() {
            return Distance;
        }

        public void setDistance(String Distance) {
            this.Distance = Distance;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getCuisine() {
            return cuisine;
        }

        public void setCuisine(String cuisine) {
            this.cuisine = cuisine;
        }

        public List<String> getLable() {
            return lable;
        }

        public void setLable(List<String> lable) {
            this.lable = lable;
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
}

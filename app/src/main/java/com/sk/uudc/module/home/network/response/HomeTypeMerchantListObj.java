package com.sk.uudc.module.home.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class HomeTypeMerchantListObj extends BaseObj {

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
         * merchant_name : 黄焖鸡米饭 (兆丰大厦店)
         * money_people : 30
         * merchant_address : 兆丰大厦N楼
         * scoring : 5
         * Distance : 4KM
         * lat : 31.224988
         * lng : 121.425943
         * cuisine : 湘菜
         * lable : ["炒菜","火锅","酒水饮料"]
         * activity : [{"full_amount":50,"subtract_amount":20},{"full_amount":100,"subtract_amount":50},{"full_amount":200,"subtract_amount":100}]
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
        private String cuisine;
        private List<String> lable;
        private List<ActivityBean> activity;

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

            private double full_amount;
            private double subtract_amount;

            public double getFull_amount() {
                return full_amount;
            }

            public void setFull_amount(double full_amount) {
                this.full_amount = full_amount;
            }

            public double getSubtract_amount() {
                return subtract_amount;
            }

            public void setSubtract_amount(double subtract_amount) {
                this.subtract_amount = subtract_amount;
            }
        }
    }
}

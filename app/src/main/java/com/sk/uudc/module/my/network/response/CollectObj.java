package com.sk.uudc.module.my.network.response;

import java.util.List;

/**
 * Created by Administrator on 2017/11/20.
 */

public class CollectObj {

    private List<MyCollectionBean> MyCollection;

    public List<MyCollectionBean> getMyCollection() {
        return MyCollection;
    }

    public void setMyCollection(List<MyCollectionBean> MyCollection) {
        this.MyCollection = MyCollection;
    }

    public static class MyCollectionBean {
        /**
         * merchant_id : 1
         * merchant_name : 黄焖鸡米饭(兆丰大厦店)
         * merchant_avatar :
         * scoring : 5
         * merchant_address : 兆丰大厦N楼
         * distance : 12954KM
         */

        private String merchant_id;
        private String merchant_name;
        private String merchant_avatar;
        private String scoring;
        private String merchant_address;
        private String distance;

        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
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

        public String getScoring() {
            return scoring;
        }

        public void setScoring(String scoring) {
            this.scoring = scoring;
        }

        public String getMerchant_address() {
            return merchant_address;
        }

        public void setMerchant_address(String merchant_address) {
            this.merchant_address = merchant_address;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}

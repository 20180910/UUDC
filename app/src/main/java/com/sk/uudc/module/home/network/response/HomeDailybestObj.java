package com.sk.uudc.module.home.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class HomeDailybestObj extends BaseObj {


    private List<DailybestListBean> dailybest_list;

    public List<DailybestListBean> getDailybest_list() {
        return dailybest_list;
    }

    public void setDailybest_list(List<DailybestListBean> dailybest_list) {
        this.dailybest_list = dailybest_list;
    }

    public static class DailybestListBean {
        /**
         * merchant_id : 5
         * merchant_avatar : http://121.40.186.118:5019/upload/sjtx.png
         * merchant_name : 啥都有面馆
         * scoring : 5
         */

        private String merchant_id;
        private String merchant_avatar;
        private String merchant_name;
        private String scoring;

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

        public String getScoring() {
            return scoring;
        }

        public void setScoring(String scoring) {
            this.scoring = scoring;
        }
    }
}

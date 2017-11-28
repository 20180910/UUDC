package com.sk.uudc.module.my.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/25.
 */

public class DaiEvaluateObj extends BaseObj {

    private List<DaiPingJiaBean> DaiPingJia;

    public List<DaiPingJiaBean> getDaiPingJia() {
        return DaiPingJia;
    }

    public void setDaiPingJia(List<DaiPingJiaBean> DaiPingJia) {
        this.DaiPingJia = DaiPingJia;
    }

    public static class DaiPingJiaBean {
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
}

package com.sk.uudc.module.home.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

public class ShowHongBaoObj extends BaseObj {
    /**
     * img_url :
     * red_envelope_list : [{"coupons_id":159,"title":"满100减10","face_value":10,"available":100}]
     */

    private String img_url;
    private List<RedEnvelopeListBean> red_envelope_list;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public List<RedEnvelopeListBean> getRed_envelope_list() {
        return red_envelope_list;
    }

    public void setRed_envelope_list(List<RedEnvelopeListBean> red_envelope_list) {
        this.red_envelope_list = red_envelope_list;
    }

    public static class RedEnvelopeListBean {
        /**
         * coupons_id : 159
         * title : 满100减10
         * face_value : 10.0
         * available : 100.0
         */

        private int coupons_id;
        private String title;
        private double face_value;
        private double available;

        public int getCoupons_id() {
            return coupons_id;
        }

        public void setCoupons_id(int coupons_id) {
            this.coupons_id = coupons_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getFace_value() {
            return face_value;
        }

        public void setFace_value(double face_value) {
            this.face_value = face_value;
        }

        public double getAvailable() {
            return available;
        }

        public void setAvailable(double available) {
            this.available = available;
        }
    }
}

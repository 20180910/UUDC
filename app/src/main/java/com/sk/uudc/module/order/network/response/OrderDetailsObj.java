package com.sk.uudc.module.order.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class OrderDetailsObj extends BaseObj {

    /**
     * userid : 4
     * order_no : Y201711231711550457
     * order_status : 4
     * verification_code :
     * merchant_avatar : http://121.40.186.118:5019/upload/sjtx.png
     * merchant_name : 金拱门
     * goods_list : [{"goods_name":"草莓新地","goods_price":10,"goods_number":1},{"goods_name":"双层吉士汉堡","goods_price":18.8,"goods_number":1}]
     * youhui_money : 0
     * combined : 28.89
     * merchant_list : [{"merchant_id":4,"merchant_name":"金拱门","merchant_address":"兆丰大厦H楼","scoring":5,"lat":31.22173,"lng":121.428782}]
     * dine_time : 2017-11-23 12:00:00
     * dine_num_people : 1
     * is_require_rooms : 0
     * invoice : 需要发票,小魏
     * remark :
     * contact_person_recipient : 小魏
     * contact_person_phone : 13872228829
     * create_add_time : 2017.11.23 17:11
     */

    private String userid;
    private String order_no;
    private int order_status;
    private String verification_code;
    private String merchant_avatar;
    private String merchant_name;
    private double youhui_money;
    private double combined;
    private String dine_time;
    private String dine_num_people;
    private int is_require_rooms;
    private String invoice;
    private String remark;
    private String contact_person_recipient;
    private String contact_person_phone;
    private String create_add_time;
    private List<GoodsListBean> goods_list;
    private List<MerchantListBean> merchant_list;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
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

    public double getYouhui_money() {
        return youhui_money;
    }

    public void setYouhui_money(double youhui_money) {
        this.youhui_money = youhui_money;
    }

    public double getCombined() {
        return combined;
    }

    public void setCombined(double combined) {
        this.combined = combined;
    }

    public String getDine_time() {
        return dine_time;
    }

    public void setDine_time(String dine_time) {
        this.dine_time = dine_time;
    }

    public String getDine_num_people() {
        return dine_num_people;
    }

    public void setDine_num_people(String dine_num_people) {
        this.dine_num_people = dine_num_people;
    }

    public int getIs_require_rooms() {
        return is_require_rooms;
    }

    public void setIs_require_rooms(int is_require_rooms) {
        this.is_require_rooms = is_require_rooms;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContact_person_recipient() {
        return contact_person_recipient;
    }

    public void setContact_person_recipient(String contact_person_recipient) {
        this.contact_person_recipient = contact_person_recipient;
    }

    public String getContact_person_phone() {
        return contact_person_phone;
    }

    public void setContact_person_phone(String contact_person_phone) {
        this.contact_person_phone = contact_person_phone;
    }

    public String getCreate_add_time() {
        return create_add_time;
    }

    public void setCreate_add_time(String create_add_time) {
        this.create_add_time = create_add_time;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public List<MerchantListBean> getMerchant_list() {
        return merchant_list;
    }

    public void setMerchant_list(List<MerchantListBean> merchant_list) {
        this.merchant_list = merchant_list;
    }

    public static class GoodsListBean {
        /**
         * goods_name : 草莓新地
         * goods_price : 10
         * goods_number : 1
         */

        private String goods_name;
        private double goods_price;
        private int goods_number;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public double getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(double goods_price) {
            this.goods_price = goods_price;
        }

        public int getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(int goods_number) {
            this.goods_number = goods_number;
        }
    }

    public static class MerchantListBean {
        /**
         * merchant_id : 4
         * merchant_name : 金拱门
         * merchant_address : 兆丰大厦H楼
         * scoring : 5
         * lat : 31.22173
         * lng : 121.428782
         */

        private String merchant_id;
        private String merchant_name;
        private String merchant_address;
        private double scoring;
        private double lat;
        private double lng;

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
    }
}

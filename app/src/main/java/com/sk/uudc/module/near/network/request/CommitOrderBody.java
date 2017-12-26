package com.sk.uudc.module.near.network.request;

import com.sk.uudc.module.near.network.response.TiJiaoOrderObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class CommitOrderBody  {
    /**
     * ShowOrder : [{"goods_id":1,"number":2},{"goods_id":1,"number":2}]
     * user_id : 1
     * merchant_id : 2
     * dine_time : 2017-11-27T10:36:05.1942147+08:00
     * time_id : 4
     * dine_num_people : 5
     * is_require_rooms : 6
     * invoice_type : sample string 7
     * invoice_name : sample string 8
     * invoice_tax_number : sample string 9
     * remark : sample string 10
     * contact_person_recipient : sample string 11
     * contact_person_phone : sample string 12
     */

    private String user_id;
    private String order_id;
    private String merchant_id;
    private String dine_time;
    private int time_id;
    private int dine_num_people;
    private int is_require_rooms;
    private String invoice_type;
    private String invoice_name;
    private String invoice_tax_number;
    private String remark;
    private String contact_person_recipient;
    private String contact_person_phone;
    private List<TiJiaoOrderObj.GoodsListBean> ShowOrder;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getDine_time() {
        return dine_time;
    }

    public void setDine_time(String dine_time) {
        this.dine_time = dine_time;
    }

    public int getTime_id() {
        return time_id;
    }

    public void setTime_id(int time_id) {
        this.time_id = time_id;
    }

    public int getDine_num_people() {
        return dine_num_people;
    }

    public void setDine_num_people(int dine_num_people) {
        this.dine_num_people = dine_num_people;
    }

    public int getIs_require_rooms() {
        return is_require_rooms;
    }

    public void setIs_require_rooms(int is_require_rooms) {
        this.is_require_rooms = is_require_rooms;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getInvoice_name() {
        return invoice_name;
    }

    public void setInvoice_name(String invoice_name) {
        this.invoice_name = invoice_name;
    }

    public String getInvoice_tax_number() {
        return invoice_tax_number;
    }

    public void setInvoice_tax_number(String invoice_tax_number) {
        this.invoice_tax_number = invoice_tax_number;
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

    public List<TiJiaoOrderObj.GoodsListBean> getShowOrder() {
        return ShowOrder;
    }

    public void setShowOrder(List<TiJiaoOrderObj.GoodsListBean> ShowOrder) {
        this.ShowOrder = ShowOrder;
    }

    public static class ShowOrderBean {
        /**
         * goods_id : 1
         * number : 2
         */

        private int goods_id;
        private int number;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}

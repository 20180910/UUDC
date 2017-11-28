package com.sk.uudc.module.my.network.response;

/**
 * Created by Administrator on 2017/11/23.
 */

public class AccountObj {


    /**
     * userid : 5
     * id : 1
     * bank_image : http://121.40.186.118:5019/upload/201709/18/201709181443427904.png
     * bank_name : 中信银行
     * card_type : 储蓄卡
     * bank_card : 尾号9702
     * is_default : 1
     */

    private String userid;
    private String id;
    private String bank_image;
    private String bank_name;
    private String card_type;
    private String bank_card;
    private String is_default;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_image() {
        return bank_image;
    }

    public void setBank_image(String bank_image) {
        this.bank_image = bank_image;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }
}

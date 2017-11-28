package com.sk.uudc.module.my.network.response;

/**
 * Created by Administrator on 2017/11/23.
 */

public class AccountDefaultObj {

    /**
     * id : 3
     * bank_name : 中国工商银行   2227
     * bank_card : ***2227
     * card_type : 储蓄卡
     * bank_image : http://121.40.186.118:5019/upload/201709/18/201709181440263746.png
     */

    private String id;
    private String bank_name;
    private String bank_card;
    private String card_type;
    private String bank_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getBank_image() {
        return bank_image;
    }

    public void setBank_image(String bank_image) {
        this.bank_image = bank_image;
    }
}

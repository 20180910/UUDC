package com.sk.uudc.module.my.network.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/23.
 */

public class AccountObj implements Serializable{


    /**
     * userid : 36
     * id : 38
     * bank_image : http://121.40.186.118:5119/upload/201709/18/201709181440263746.png
     * bank_name : 中国工商银行
     * real_name : sasss
     * id_number : 411282199209204017
     * card_type : 储蓄卡
     * bank_card_number : 6222021702046025771
     * bank_card : 尾号5771
     * opening_bank : fheifhewohgewi
     * is_default : 1
     */

    private int userid;
    private String id;
    private String bank_image;
    private String bank_name;
    private String real_name;
    private String id_number;
    private String card_type;
    private String bank_card_number;
    private String bank_card;
    private String opening_bank;
    private int is_default;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
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

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getBank_card_number() {
        return bank_card_number;
    }

    public void setBank_card_number(String bank_card_number) {
        this.bank_card_number = bank_card_number;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getOpening_bank() {
        return opening_bank;
    }

    public void setOpening_bank(String opening_bank) {
        this.opening_bank = opening_bank;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }
}

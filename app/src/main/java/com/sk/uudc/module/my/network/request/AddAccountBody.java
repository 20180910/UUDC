package com.sk.uudc.module.my.network.request;

/**
 * Created by Administrator on 2017/11/23.
 */

public class AddAccountBody {

    /**
     * realname : sample string 1
     * bank_card_num : sample string 2
     * id_number : sample string 3
     * card_type : 4
     * opening_bank : sample string 5
     */

    private String realname;
    private String bank_card_num;
    private String id_number;
    private String card_type;
    private String opening_bank;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getBank_card_num() {
        return bank_card_num;
    }

    public void setBank_card_num(String bank_card_num) {
        this.bank_card_num = bank_card_num;
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

    public String getOpening_bank() {
        return opening_bank;
    }

    public void setOpening_bank(String opening_bank) {
        this.opening_bank = opening_bank;
    }
}

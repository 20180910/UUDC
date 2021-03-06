package com.sk.uudc.base;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/20.
 */

public class BaseObj implements Serializable {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    private String SMSCode;
    private String img;
    private int payment_type;
    private String payment_url;
    private int is_collect;//1收藏，0没收藏
    private double account_balance;//账户余额
    public String getImg() {
        return img;
    }

    public String getPayment_url() {
        return payment_url;
    }

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public void setPayment_url(String payment_url) {
        this.payment_url = payment_url;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public String getSMSCode() {
        return SMSCode;
    }

    public void setSMSCode(String SMSCode) {
        this.SMSCode = SMSCode;
    }

    private double allmoney;//全部金额
    private String agreement;//提现协议
//    private String promoters_agreement;//推广员注册协议
//    private String farmer_agreement;//农户注册协议
    private String realname_authentication;//实名认证协议

    public String getRealname_authentication() {
        return realname_authentication;
    }

    public void setRealname_authentication(String realname_authentication) {
        this.realname_authentication = realname_authentication;
    }

    public String getAgreement() {
        return agreement;
    }

    public double getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(double account_balance) {
        this.account_balance = account_balance;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public double getAllmoney() {
        return allmoney;
    }

    public void setAllmoney(double allmoney) {
        this.allmoney = allmoney;
    }
}

package com.sk.uudc.module.my.network.response;

import com.sk.uudc.base.BaseObj;

/**
 * Created by Administrator on 2017/11/29.
 */

public class ChongzhiSuccessObj extends BaseObj {

    /**
     * money : 0.01
     * balance : 600
     * content : 使用支付宝充值
     */

    private String money;
    private String balance;
    private String content;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

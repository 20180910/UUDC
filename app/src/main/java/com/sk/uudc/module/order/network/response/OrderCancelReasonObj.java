package com.sk.uudc.module.order.network.response;

/**
 * Created by Administrator on 2017/11/24.
 */

public class OrderCancelReasonObj {

    /**
     * title : 取消订单原因
     */

    private String content;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

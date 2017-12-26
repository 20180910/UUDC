package com.sk.uudc.module.order.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class OrderCancelReasonObj extends BaseObj {


    /**
     * cancel_reason_list : [{"id":7,"content":"不想要了"},{"id":6,"content":"看上了别家"},{"id":5,"content":"点错了,重新点"},{"id":4,"content":"临时有事,不吃了"},{"id":0,"content":"其他"}]
     * title : 再下单后的3小时内可以免手续费取消，如果超出时间,则扣除20%的违约金,距离预定饭前2小时取消不退款
     */

    private String title;
    private List<CancelReasonListBean> cancel_reason_list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CancelReasonListBean> getCancel_reason_list() {
        return cancel_reason_list;
    }

    public void setCancel_reason_list(List<CancelReasonListBean> cancel_reason_list) {
        this.cancel_reason_list = cancel_reason_list;
    }

    public static class CancelReasonListBean {
        /**
         * id : 7
         * content : 不想要了
         */

        private String id;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

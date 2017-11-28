package com.sk.uudc.module.my.network.response;

import com.sk.uudc.base.BaseObj;

/**
 * Created by Administrator on 2017/11/20.
 */

public class MessageDetailObj extends BaseObj {


    /**
     * id : 1
     * title : 系统消息
     * content : "您已成功注册,可以享受我们的服务了,快来找找你喜欢的餐厅开始预约服务吧,现在有几千家美味美食等你来选!";
     */

    private int id;
    private String title;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package com.sk.uudc.module.home.network.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/5.
 */

public class GongGaoObj implements Serializable{
    /**
     * id : 16
     * title : 热烈祝贺首批商家成功入驻优优点餐平台！
     * content : 热烈祝贺首批商家成功入驻优优点餐平台！
     * add_time : 2017-12-22 17:01
     */

    private int id;
    private String title;
    private String content;
    private String add_time;

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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}

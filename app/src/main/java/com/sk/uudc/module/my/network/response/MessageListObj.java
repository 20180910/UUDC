package com.sk.uudc.module.my.network.response;

/**
 * Created by Administrator on 2017/11/20.
 */

public class MessageListObj  {

    /**
     * id : 1
     * title : 系统消息
     * content : "您已成功注册,可以享受我们的服务了,快来找找你喜欢的餐厅开始预约服务吧,现在有几千家美味美食等你来选!";
     * add_time : 2017/11/20
     * is_check : 1
     */

    private int id;
    private String title;
    private String content;
    private String add_time;
    private int is_check;

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

    public int getIs_check() {
        return is_check;
    }

    public void setIs_check(int is_check) {
        this.is_check = is_check;
    }
}

package com.sk.uudc.network.response;

import com.sk.uudc.base.BaseObj;

/**
 * Created by Administrator on 2017/11/30.
 */

public class FenXiangObj extends BaseObj {
    /**
     * title : 优优点餐
     * content : 优优点餐
     * share_link : https://www.baidu.com/
     */

    private String title;
    private String content;
    private String share_link;

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

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }
}

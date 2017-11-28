package com.sk.uudc.module.my.network.response;

/**
 * Created by Administrator on 2017/11/25.
 */

public class ProvinceObj {

    /**
     * id : 1770
     * title : 北京市
     * sort_id : 99
     * parent_id : 99
     */

    private String id;
    private String title;
    private String sort_id;
    private String parent_id;

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSort_id() {
        return sort_id;
    }

    public void setSort_id(String sort_id) {
        this.sort_id = sort_id;
    }
}

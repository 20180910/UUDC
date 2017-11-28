package com.sk.uudc.module.home.network.request;

/**
 * Created by Administrator on 2017/11/27.
 */

public class SearchResultBody {

    /**
     * search_term : sample string 1
     * lat : 2.1
     * lng : 3.1
     * user_id : 4
     */

    private String search_term;
    private String lat;
    private String lng;
    private String user_id;

    public String getSearch_term() {
        return search_term;
    }

    public void setSearch_term(String search_term) {
        this.search_term = search_term;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

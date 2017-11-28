package com.sk.uudc.module.near.network.request;

/**
 * Created by Administrator on 2017/11/21.
 */

public class NearShangJiaBody {
    /**
     * type_id : 2
     * lat : 31.564211
     * lng : 121.455215
     */
    private String type_id;
    private String search_term;
    private double lat;
    private double lng;

    public String getSearch_term() {
        return search_term;
    }

    public void setSearch_term(String search_term) {
        this.search_term = search_term;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}

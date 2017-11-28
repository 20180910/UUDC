package com.sk.uudc.module.home.network.request;

/**
 * Created by Administrator on 2017/11/22.
 */

public class HomeTypeMerchantListBody {

    /**
     * type_id : 1
     * lat : 2.1
     * lng : 3.1
     * city_id : 4
     * area_id : 5
     * is_provide_rooms : 6
     * distance : 7
     * sequencing : 8
     * dinner_time : 9
     */

    private String type_id;
    private String lat;
    private String lng;
    private String city_id;
    private String area_id;
    private String is_provide_rooms;
    private String distance;
    private String sequencing;
    private String dinner_time;

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
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

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getIs_provide_rooms() {
        return is_provide_rooms;
    }

    public void setIs_provide_rooms(String is_provide_rooms) {
        this.is_provide_rooms = is_provide_rooms;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSequencing() {
        return sequencing;
    }

    public void setSequencing(String sequencing) {
        this.sequencing = sequencing;
    }

    public String getDinner_time() {
        return dinner_time;
    }

    public void setDinner_time(String dinner_time) {
        this.dinner_time = dinner_time;
    }
}

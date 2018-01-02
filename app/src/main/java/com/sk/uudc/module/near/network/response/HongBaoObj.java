package com.sk.uudc.module.near.network.response;

/**
 * Created by Administrator on 2017/12/29.
 */

public class HongBaoObj {
    /**coupons_id 红包/优惠券ID,
     * title标题,photo 图标,
     * face_value价值多少,
     * available满多少,
     * end_time有效期至
     * coupons_id : 81
     * title : 满200减10
     * photo :
     * face_value : 10
     * available : 200
     * end_time : 2017.12.31
     */

    private int coupons_id;
    private String title;
    private String photo;
    private double face_value;
    private double available;
    private String end_time;

    public int getCoupons_id() {
        return coupons_id;
    }

    public void setCoupons_id(int coupons_id) {
        this.coupons_id = coupons_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getFace_value() {
        return face_value;
    }

    public void setFace_value(double face_value) {
        this.face_value = face_value;
    }

    public double getAvailable() {
        return available;
    }

    public void setAvailable(double available) {
        this.available = available;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}

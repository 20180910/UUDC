package com.sk.uudc.module.near.network.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ShangJiaObj extends BaseObj {

    /**
     * merchant_id : 1
     * merchant_name : 黄焖鸡米饭 (兆丰大厦店)
     * merchant_avatar :
     * scoring : 5
     * is_collect : 0
     * min_money : 50
     * activity : [{"full_amount":50,"subtract_amount":20},{"full_amount":100,"subtract_amount":50},{"full_amount":200,"subtract_amount":100}]
     */

    private int merchant_id;
    private String merchant_name;
    private String merchant_avatar;
    private double scoring;
    private int is_collect;
    private double min_money;
    private List<ActivityBean> activity;

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_avatar() {
        return merchant_avatar;
    }

    public void setMerchant_avatar(String merchant_avatar) {
        this.merchant_avatar = merchant_avatar;
    }

    public double getScoring() {
        return scoring;
    }

    public void setScoring(double scoring) {
        this.scoring = scoring;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public double getMin_money() {
        return min_money;
    }

    public void setMin_money(double min_money) {
        this.min_money = min_money;
    }

    public List<ActivityBean> getActivity() {
        return activity;
    }

    public void setActivity(List<ActivityBean> activity) {
        this.activity = activity;
    }

    public static class ActivityBean implements Parcelable{
        /**
         * full_amount : 50
         * subtract_amount : 20
         */

        private int full_amount;
        private int subtract_amount;

        protected ActivityBean( ) {

        }
        protected ActivityBean(Parcel in) {
            full_amount = in.readInt();
            subtract_amount = in.readInt();
        }

        public static final Creator<ActivityBean> CREATOR = new Creator<ActivityBean>() {
            @Override
            public ActivityBean createFromParcel(Parcel in) {
                return new ActivityBean(in);
            }

            @Override
            public ActivityBean[] newArray(int size) {
                return new ActivityBean[size];
            }
        };

        public int getFull_amount() {
            return full_amount;
        }

        public void setFull_amount(int full_amount) {
            this.full_amount = full_amount;
        }

        public int getSubtract_amount() {
            return subtract_amount;
        }

        public void setSubtract_amount(int subtract_amount) {
            this.subtract_amount = subtract_amount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(full_amount);
            parcel.writeInt(subtract_amount);
        }
    }
}

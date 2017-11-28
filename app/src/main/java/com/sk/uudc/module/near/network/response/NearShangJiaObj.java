package com.sk.uudc.module.near.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class NearShangJiaObj extends BaseObj {
    private List<TypeListBean> type_list;

    public List<TypeListBean> getType_list() {
        return type_list;
    }

    public void setType_list(List<TypeListBean> type_list) {
        this.type_list = type_list;
    }

    public static class TypeListBean {
        /**
         * type_image : http://121.40.186.118:5019/upload/1.png
         * type_id : 1
         * type_name : 小吃快餐
         */

        private String type_image;
        private int type_id;
        private String type_name;

        public String getType_image() {
            return type_image;
        }

        public void setType_image(String type_image) {
            this.type_image = type_image;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }
}

package com.sk.uudc.module.near.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class ShangJiaShangPingObj extends BaseObj {

    private List<MerchantClassListBean> merchant_class_list;

    public List<MerchantClassListBean> getMerchant_class_list() {
        return merchant_class_list;
    }

    public void setMerchant_class_list(List<MerchantClassListBean> merchant_class_list) {
        this.merchant_class_list = merchant_class_list;
    }

    public static class MerchantClassListBean {
        /**
         * navigation_id : 9
         * navigation_name : 锅底
         * goods_list : [{"goods_id":28,"goods_image":"http://121.40.186.118:5019/upload/yuanyang.png","goods_price":68.01,"goods_name":"鸳鸯锅底","navigation_id":9,"sales_volume":"0"},{"goods_id":29,"goods_image":"http://121.40.186.118:5019/upload/sanxian.png","goods_price":64.08,"goods_name":"全锅-三鲜","navigation_id":9,"sales_volume":"0"},{"goods_id":30,"goods_image":"http://121.40.186.118:5019/upload/qingyou.png","goods_price":54.06,"goods_name":"全锅-清油","navigation_id":9,"sales_volume":"0"},{"goods_id":31,"goods_image":"http://121.40.186.118:5019/upload/niuyou.png","goods_price":44.07,"goods_name":"全锅-牛油","navigation_id":9,"sales_volume":"0"},{"goods_id":32,"goods_image":"http://121.40.186.118:5019/upload/fanqie.png","goods_price":34.05,"goods_name":"全锅-番茄","navigation_id":9,"sales_volume":"0"}]
         */

        private int navigation_id;
        private String navigation_name;
        private List<GoodsListBean> goods_list;

        public int getNavigation_id() {
            return navigation_id;
        }

        public void setNavigation_id(int navigation_id) {
            this.navigation_id = navigation_id;
        }

        public String getNavigation_name() {
            return navigation_name;
        }

        public void setNavigation_name(String navigation_name) {
            this.navigation_name = navigation_name;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean {
            /**
             * goods_id : 28
             * goods_image : http://121.40.186.118:5019/upload/yuanyang.png
             * goods_price : 68.01
             * goods_name : 鸳鸯锅底
             * navigation_id : 9
             * sales_volume : 0
             */

            private int goods_id;
            private String goods_image;
            private double goods_price;
            private String goods_name;
            private int navigation_id;
            private String sales_volume;
            private int num;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_image() {
                return goods_image;
            }

            public void setGoods_image(String goods_image) {
                this.goods_image = goods_image;
            }

            public double getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(double goods_price) {
                this.goods_price = goods_price;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getNavigation_id() {
                return navigation_id;
            }

            public void setNavigation_id(int navigation_id) {
                this.navigation_id = navigation_id;
            }

            public String getSales_volume() {
                return sales_volume;
            }

            public void setSales_volume(String sales_volume) {
                this.sales_volume = sales_volume;
            }
        }
    }
}

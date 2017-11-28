package com.sk.uudc.module.home.network.response;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class AreaBusinessCircleObj {

    /**
     * id : 2876
     * title : 黄浦区
     * parent_id : 1875
     * pca_list : [{"id":4992,"title":"南京东路","parent_id":2876}]
     */

    private String id;
    private String title;
    private String parent_id;
    private List<PcaListBean> pca_list;

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

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public List<PcaListBean> getPca_list() {
        return pca_list;
    }

    public void setPca_list(List<PcaListBean> pca_list) {
        this.pca_list = pca_list;
    }

    public static class PcaListBean {
        /**
         * id : 4992
         * title : 南京东路
         * parent_id : 2876
         */

        private String id;
        private String title;
        private String parent_id;

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

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }
}

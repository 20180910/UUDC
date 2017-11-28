package com.sk.uudc.module.home.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class SearchObj extends BaseObj {

    private List<RecentlyListBean> recently_list;
    private List<HottestListBean> hottest_list;

    public List<RecentlyListBean> getRecently_list() {
        return recently_list;
    }

    public void setRecently_list(List<RecentlyListBean> recently_list) {
        this.recently_list = recently_list;
    }

    public List<HottestListBean> getHottest_list() {
        return hottest_list;
    }

    public void setHottest_list(List<HottestListBean> hottest_list) {
        this.hottest_list = hottest_list;
    }

    public static class RecentlyListBean {
        /**
         * search_term : 黄焖鸡米饭
         */

        private String search_term;

        public String getSearch_term() {
            return search_term;
        }

        public void setSearch_term(String search_term) {
            this.search_term = search_term;
        }
    }

    public static class HottestListBean {
        /**
         * search_term : 黄焖鸡
         */

        private String search_term;

        public String getSearch_term() {
            return search_term;
        }

        public void setSearch_term(String search_term) {
            this.search_term = search_term;
        }
    }
}

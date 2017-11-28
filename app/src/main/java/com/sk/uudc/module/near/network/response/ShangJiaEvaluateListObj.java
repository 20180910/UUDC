package com.sk.uudc.module.near.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ShangJiaEvaluateListObj extends BaseObj {

    private List<ScoringListBean> ScoringList;

    public List<ScoringListBean> getScoringList() {
        return ScoringList;
    }

    public void setScoringList(List<ScoringListBean> ScoringList) {
        this.ScoringList = ScoringList;
    }

    public static class ScoringListBean {
        /**
         * appraise_id : 1
         * nickname : 蛋黄
         * userimg : http://121.40.186.118:5019/upload/201711/18/171118172801348584.jpg
         * content : 店铺挺不错,饭菜很好吃
         * scoring : 5
         * deadline : 2017-11-22 11:35
         * merchant_appraise_image_list : []
         */

        private int appraise_id;
        private String nickname;
        private String userimg;
        private String content;
        private double scoring;
        private String deadline;
        private List<String> merchant_appraise_image_list;

        public int getAppraise_id() {
            return appraise_id;
        }

        public void setAppraise_id(int appraise_id) {
            this.appraise_id = appraise_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUserimg() {
            return userimg;
        }

        public void setUserimg(String userimg) {
            this.userimg = userimg;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public double getScoring() {
            return scoring;
        }

        public void setScoring(double scoring) {
            this.scoring = scoring;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public List<String> getMerchant_appraise_image_list() {
            return merchant_appraise_image_list;
        }

        public void setMerchant_appraise_image_list(List<String> merchant_appraise_image_list) {
            this.merchant_appraise_image_list = merchant_appraise_image_list;
        }
    }
}

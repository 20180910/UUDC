package com.sk.uudc.module.near.network.response;

import com.sk.uudc.base.BaseObj;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ShangJiaEvaluateNumObj extends BaseObj {

    /**
     * reputation_count : 0
     * good_reputation : 0
     * review_reputation : 0
     * scoring : 5
     */

    private int reputation_count;
    private int good_reputation;
    private int review_reputation;
    private double scoring;

    public int getReputation_count() {
        return reputation_count;
    }

    public void setReputation_count(int reputation_count) {
        this.reputation_count = reputation_count;
    }

    public int getGood_reputation() {
        return good_reputation;
    }

    public void setGood_reputation(int good_reputation) {
        this.good_reputation = good_reputation;
    }

    public int getReview_reputation() {
        return review_reputation;
    }

    public void setReview_reputation(int review_reputation) {
        this.review_reputation = review_reputation;
    }

    public double getScoring() {
        return scoring;
    }

    public void setScoring(double scoring) {
        this.scoring = scoring;
    }
}

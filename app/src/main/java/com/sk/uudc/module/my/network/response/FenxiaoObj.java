package com.sk.uudc.module.my.network.response;

import com.sk.uudc.base.BaseObj;

/**
 * Created by Administrator on 2017/11/20.
 */

public class FenxiaoObj extends BaseObj {

    /**
     * content : 将次分销码分享给好友，好友注册时输入他将成为你的下级好友，你可以赚取利益。
     * distribution_yard : T0BH0F0F
     */

    private String content;
    private String distribution_yard;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDistribution_yard() {
        return distribution_yard;
    }

    public void setDistribution_yard(String distribution_yard) {
        this.distribution_yard = distribution_yard;
    }
}

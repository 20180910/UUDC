package com.sk.uudc.module.home.network.response;

import com.sk.uudc.base.BaseObj;

/**
 * Created by Administrator on 2017/11/21.
 */

public class HomePageImageObj extends BaseObj {

    /**
     * img_url : http://121.40.186.118:5019/upload/201711/21/201711211521225223.png
     */

    private String img_url;
    private String merchant_id;

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}

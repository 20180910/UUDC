package com.sk.uudc.module.my.network.response;

import com.sk.uudc.base.BaseObj;

/**
 * Created by Administrator on 2017/11/21.
 */

public class AboutPlatformObj extends BaseObj {

    /**
     * image : http://121.40.186.118:5019/upload/tubiao.png
     * edition : 成立于2008年,我公司为餐饮提供很好的平台。
     */

    private String image;
    private String edition;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
}

package com.sk.uudc.module.order.network.request;

import java.util.List;

/**
 * Created by Administrator on 2017/11/25.
 */

public class BusinessEvaluationBody {

    /**
     * img : [{"img":"sample string 1"},{"img":"sample string 1"}]
     * order_id : 1
     * scoring : 2.0
     * content : sample string 3
     */

    private String order_id;
    private String scoring;
    private String content;
    private List<ImgBean> img;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getScoring() {
        return scoring;
    }

    public void setScoring(String scoring) {
        this.scoring = scoring;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ImgBean> getImg() {
        return img;
    }

    public void setImg(List<ImgBean> img) {
        this.img = img;
    }

    public static class ImgBean {
        /**
         * img : sample string 1
         */

        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}

package com.sk.uudc.module.my.network.response;

import com.sk.uudc.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */

public class ComperationObj extends BaseObj {

    private List<CooperationImageBean> cooperation_image;
    private List<PlatformAdvantagesBean> platform_advantages;
    private List<RecruitmentProgressBean> recruitment_progress;
    private List<RegistrationProcessBean> registration_process;
    private List<RoastingListBean> roasting_list;

    public List<CooperationImageBean> getCooperation_image() {
        return cooperation_image;
    }

    public void setCooperation_image(List<CooperationImageBean> cooperation_image) {
        this.cooperation_image = cooperation_image;
    }

    public List<PlatformAdvantagesBean> getPlatform_advantages() {
        return platform_advantages;
    }

    public void setPlatform_advantages(List<PlatformAdvantagesBean> platform_advantages) {
        this.platform_advantages = platform_advantages;
    }

    public List<RecruitmentProgressBean> getRecruitment_progress() {
        return recruitment_progress;
    }

    public void setRecruitment_progress(List<RecruitmentProgressBean> recruitment_progress) {
        this.recruitment_progress = recruitment_progress;
    }

    public List<RegistrationProcessBean> getRegistration_process() {
        return registration_process;
    }

    public void setRegistration_process(List<RegistrationProcessBean> registration_process) {
        this.registration_process = registration_process;
    }

    public List<RoastingListBean> getRoasting_list() {
        return roasting_list;
    }

    public void setRoasting_list(List<RoastingListBean> roasting_list) {
        this.roasting_list = roasting_list;
    }

    public static class CooperationImageBean {
        /**
         * img_url : http://121.40.186.118:5019/upload/201711/21/201711211711370208.png
         */

        private String img_url;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }

    public static class PlatformAdvantagesBean {
        /**
         * img_url : http://121.40.186.118:5019/upload/201711/21/201711211711468200.png
         */

        private String img_url;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }

    public static class RecruitmentProgressBean {
        /**
         * img_url : http://121.40.186.118:5019/upload/201711/21/201711211711166353.png
         */

        private String img_url;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }

    public static class RegistrationProcessBean {
        /**
         * img_url : http://121.40.186.118:5019/upload/201711/21/201711211711286269.png
         */

        private String img_url;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }

    public static class RoastingListBean {
        /**
         * question : 什么是合伙人计划?
         * content : 就是合伙人计划呗
         */

        private String question;
        private String content;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

package com.sk.uudc.module.my.network.request;

import com.sk.uudc.base.BaseObj;

/**
 * Created by Administrator on 2017/11/18.
 */

public class EditUserInfoBody extends BaseObj {


    /**
     * user_id : 1
     * avatar : sample string 2
     * name : sample string 3
     * nickname : sample string 4
     * birthday : sample string 5
     * sex : sample string 6
     */

    private String user_id;
    private String avatar;
    private String name;
    private String nickname;
    private String birthday;
    private String sex;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

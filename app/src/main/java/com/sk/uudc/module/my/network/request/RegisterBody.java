package com.sk.uudc.module.my.network.request;

/**
 * Created by Administrator on 2017/11/18.
 */

public class RegisterBody {

    /**
     * username : sample string 1
     * password : sample string 2
     * distribution_yard : sample string 3
     */

    private String username;
    private String password;
    private String distribution_yard;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDistribution_yard() {
        return distribution_yard;
    }

    public void setDistribution_yard(String distribution_yard) {
        this.distribution_yard = distribution_yard;
    }
}

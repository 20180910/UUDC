package com.sk.uudc.module.home.event;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/4.
 */

public class HomePageEvent implements Serializable{
    public  String type;
    public  HomePageEvent(String type){
        this.type=type;
    }
}

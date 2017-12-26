package com.sk.uudc.module.order.event;

import java.io.Serializable;

public class OrdersEvent implements Serializable {
    public String type;

    public OrdersEvent(String type) {
        this.type = type;
    }


}

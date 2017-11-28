package com.sk.uudc.module.near.event;

import com.sk.uudc.module.near.network.request.ShowOrderBody;

/**
 * Created by Administrator on 2017/11/25.
 */

public class JieSuanEvent {
    public ShowOrderBody body;

    public JieSuanEvent(ShowOrderBody body) {
        this.body = body;
    }
}

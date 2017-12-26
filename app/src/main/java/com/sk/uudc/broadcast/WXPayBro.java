package com.sk.uudc.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sk.uudc.Config;

/**
 * Created by Administrator on 2017/12/26.
 */

public class WXPayBro extends BroadcastReceiver {
    public interface WXPayInter{
        void payResult(boolean isJiaCai);
    }
    private WXPayInter inter;
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isJiaCai = intent.getBooleanExtra(Config.isJiaCai, false);
        inter.payResult(isJiaCai);
    }

    public WXPayBro(WXPayInter inter) {
        this.inter=inter;
    }
}

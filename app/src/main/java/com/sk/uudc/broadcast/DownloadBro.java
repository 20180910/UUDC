package com.sk.uudc.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sk.uudc.Constant;
import com.sk.uudc.tools.FileUtils;

import java.io.File;

/**
 * Created by Administrator on 2017/12/23.
 */

public class DownloadBro extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction(); //动作
        Log.i("===","action==="+action);
        if (action.equals("download")) {
            Log.i("===","action===download");
            String path = intent.getStringExtra(Constant.IParam.path);
            FileUtils.installApp(context,new File(path));
//            IntentUtils.openFileIntent(context,path);

        }
    }

}

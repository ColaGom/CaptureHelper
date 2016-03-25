package com.aviv.capturehelper.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

/**
 * Created by Counter on 2016-03-11.
 */
public class CaptureReceiver extends BroadcastReceiver {

    public static final String ACTION_RESTART_PERSISTENTSERVICE = "Action.Restart.Persistent";


    public CaptureReceiver() {
        Logger.d(Const.TAG, "[RestartService]Constructor_Zero Argument");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_RESTART_PERSISTENTSERVICE))
        {
            Intent i = new Intent(context, CaptureService.class);
            context.startService(i);
        }
        else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            Intent i = new Intent(context, CaptureService.class);
            context.startService(i);
        }
    }
}

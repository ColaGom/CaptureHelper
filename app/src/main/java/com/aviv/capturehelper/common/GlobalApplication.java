package com.aviv.capturehelper.common;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;

import com.aviv.capturehelper.controller.Master;
import com.orhanobut.logger.Logger;

/**
 * Created by Counter on 2016-03-11.
 */
public class GlobalApplication extends Application {

    private CaptureReceiver mReceiver;
    private CaptureService mService;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(Const.TAG);
        Master.getInstance().init(this);

        mReceiver = new CaptureReceiver();

        try{
            IntentFilter mainFilter = new IntentFilter("com.aviv.capturehelper.capture");
            registerReceiver(mReceiver, mainFilter);
        } catch (Exception e)
        {
            Logger.d(e.getMessage());
            e.printStackTrace();
        }

        Intent intent = new Intent(this, CaptureService.class);
        startService(intent);
    }

    @Override
    public void onTerminate() {
        unregisterReceiver(mReceiver);

        Intent intent = new Intent(this, CaptureService.class);
        startService(intent);

        super.onTerminate();
    }

    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);
    }
}

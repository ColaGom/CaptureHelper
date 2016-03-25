package com.aviv.capturehelper.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.aviv.capturehelper.view.activity.AlbumPopupActivity;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Counter on 2016-03-11.
 */
public class CaptureService extends Service implements ICaptureListener {
    private final long TIME_PREIOD = 5000;
    private CaptureObserver mObserver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        unregisterRestartAlarm();
        monitorAllFiles(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(mObserver == null)
                {
                    Logger.e("Observer is null");
                    monitorAllFiles(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
                }
            }
        }, 1000, TIME_PREIOD);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registerRestartAlarm();
    }

    @Override
    public void onCapture(String path) {
        Logger.i("[onCapture]" + path);
        Intent intent = new Intent(this, AlbumPopupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Const.EXTRA_FILE_PATH, path);
        startActivity(intent);
    }

    public void registerRestartAlarm() {
        Logger.d(Const.TAG, "registerRestartAlarm");
        Intent intent = new Intent(CaptureService.this, CaptureReceiver.class);
        intent.setAction(CaptureReceiver.ACTION_RESTART_PERSISTENTSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 10 * 1000; // 10초 후에 알람이벤트 발생
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 10 * 1000, sender);
    }

    public void unregisterRestartAlarm() {
        Logger.d(Const.TAG, "unregisterRestartAlarm");
        Intent intent = new Intent(CaptureService.this, CaptureReceiver.class);
        intent.setAction(CaptureReceiver.ACTION_RESTART_PERSISTENTSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }

    private void monitorAllFiles(File root) {
        File[] files = root.listFiles();
        for(File file : files) {
            if(file.isDirectory()){
                monitorAllFiles(file);
                mObserver = new CaptureObserver(file.getAbsolutePath(), this);
                mObserver.startWatching();
            }
        }
    }
}

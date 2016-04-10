package com.aviv.capturehelper.common;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.controller.Master;
import com.aviv.capturehelper.view.activity.AlbumPopupActivity;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Counter on 2016-03-11.
 */
public class CaptureService extends Service implements ICaptureListener {
    private final long TIME_PREIOD = 3000;
    private List<File> mListOriginFile;

    final String[] filters = new String[] {"Screenshots"};

    final File[] roots = new File[] {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.d("onCreate");

        mListOriginFile = new ArrayList<>();
        unregisterRestartAlarm();

        for(File root:roots)
            addOriginFiles(root);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(File root:roots)
                    checkNewPicture(root);
            }
        }, 1000, TIME_PREIOD);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        registerRestartAlarm();
    }

    private void checkNewPicture(File root){
        File[] files = root.listFiles();

        for(File file: files)
        {
            if(file.isDirectory()){
                checkNewPicture(file);
            }else if(containsFilter(file.getParentFile().getName()) && !mListOriginFile.contains(file)){
                Logger.d("New Picture : " + file.getPath());
                mListOriginFile.add(file);
                onCapture(file.getPath());
            }
        }
    }

    private void addOriginFiles(File root)
    {
        File[] files = root.listFiles();
        for(File file: files)
        {
            if(file.isDirectory()){
                addOriginFiles(file);
            }else if(containsFilter(file.getParentFile().getName())){
                Logger.d("Add Origin : " + file.getPath());
                mListOriginFile.add(file);
            }
        }
    }

    @Override
    public void onCapture(String path) {
        if(!Master.getInstance().getUserPrefLoader().getEnableHelper()) return;
        Intent intent = new Intent(this, AlbumPopupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(Const.EXTRA_FILE_PATH, path);
        startActivity(intent);

    }

    public void registerRestartAlarm() {
        Intent intent = new Intent(CaptureService.this, CaptureReceiver.class);
        intent.setAction(CaptureReceiver.ACTION_RESTART_PERSISTENTSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 10 * 1000; // 10초 후에 알람이벤트 발생
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 10 * 1000, sender);
    }

    public void unregisterRestartAlarm() {
        Intent intent = new Intent(CaptureService.this, CaptureReceiver.class);
        intent.setAction(CaptureReceiver.ACTION_RESTART_PERSISTENTSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }


    /**
     * monitoring all file in root directory
     * @param root root file
     */
    private void monitorAllFiles(File root) {
        File[] files = root.listFiles();
        for(File file : files) {
            if(file.isDirectory()){
                monitorAllFiles(file);
                if(containsFilter(file.getName())) {
                    Logger.d("startWatching : " + file.getName());
//                    CaptureObserver observer = new CaptureObserver(file.getAbsolutePath(), this);
//                    observer.startWatching();
//                    mListObserver.add(observer);
                     //File [] origin = file.listFiles();
                }
            }
        }
    }

    /**
     * check file name in filter
     * @param name file name
     * @return
     */
    private boolean containsFilter(String name)
    {
        for(String filter:filters)
        {
            if(filter.equals(name)) return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void createNotification(){
        Intent intent = new Intent(this, CaptureReceiver.class);
        intent.setAction(CaptureReceiver.ACTION_ON_FLOATING);

        PendingIntent pIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification n = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(false)
                .build();

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.noti_controller);
        remoteViews.setOnClickPendingIntent(R.id.btn_on, pIntent);

        n.contentView = remoteViews;

        n.flags |= Notification.FLAG_NO_CLEAR;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);
    }
}

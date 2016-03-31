package com.aviv.capturehelper.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.view.activity.ImageViewActivity;
import com.aviv.capturehelper.view.activity.LockActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colabear on 2016-03-31.
 */
public class ActivityStarter {

    public static void startImageViewActivity(Context context, List<File> imageList,int startIdx)
    {
        Intent intent = new Intent(context, ImageViewActivity.class);
        ArrayList<String> arrStr = new ArrayList<String>();

        for(File f:imageList)
        {
            arrStr.add(f.getPath());
        }

        intent.putExtra(Const.EXTRA_ARRAY_STRING, arrStr);
        intent.putExtra(Const.EXTRA_START_INDEX, startIdx);
        context.startActivity(intent);
    }

    public static void startStoreIntent(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Const.APP_URL_FOR_MARKET));
        context.startActivity(intent);
    }

    public static void startAppShareIntent(Context context) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getString(R.string.label_app_share_title));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, context.getString(R.string.label_app_share_desc) + Const.APP_URL_FOR_INVITE);
        context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.share_using)));
    }

    public static  void startLockActivity(Activity activity, int type){
        Intent intent = new Intent(activity, LockActivity.class);
        intent.putExtra(Const.EXTRA_TYPE, type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, Const.REQUEST_SETTING_LOCK);
    }
}

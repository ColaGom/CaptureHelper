package com.aviv.capturehelper.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.view.activity.LockActivity;

/**
 * Created by Colabear on 2016-03-31.
 */
public class ActivityStarter {

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

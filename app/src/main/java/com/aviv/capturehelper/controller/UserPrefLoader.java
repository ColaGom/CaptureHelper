package com.aviv.capturehelper.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.common.Util;

import java.text.SimpleDateFormat;

/**
 * Created by Colabear on 2016-03-24.
 */
public class UserPrefLoader {

    private SharedPreferences mPrefer = null;
    private String KEY_PREF = "aviv.pref.capturehelper";


    public void init(Context context)
    {
        mPrefer = context.getSharedPreferences(KEY_PREF, 0);
    }

    private final String KEY_SAVE_QUALITY = "key.save.quality";

    public int getSaveQuality()
    {
        return mPrefer.getInt(KEY_SAVE_QUALITY, Const.QUALITY_ORIGIN);
    }

    public void putSaveQuality(int quality)
    {
        SharedPreferences.Editor editor = mPrefer.edit();
        editor.putInt(KEY_SAVE_QUALITY, quality);
        editor.commit();
    }

    private final  String KEY_CLOUD_TOKEN = "key.cloud.token";

    public void setCloudToken(String value){
        SharedPreferences.Editor editor = mPrefer.edit();
        editor.putString(KEY_CLOUD_TOKEN, value);
        editor.commit();
    }

    public String getCloudToken(){
        return mPrefer.getString(KEY_CLOUD_TOKEN, "");
    }

    private final  String KEY_SAVE_FOLDER_PATH = "key.save.folder";

    public String getFolderPath()
    {
        String path = mPrefer.getString(KEY_SAVE_FOLDER_PATH, Const.DEFULAT_SAVE_FOLDER);
        return mPrefer.getString(KEY_SAVE_FOLDER_PATH, Const.DEFULAT_SAVE_FOLDER);
    }

    public  void putFolderPath(String path)
    {
        SharedPreferences.Editor editor = mPrefer.edit();
        editor.putString(KEY_SAVE_FOLDER_PATH, path);
        editor.commit();
    }

    private final  String KEY_SAVE_PREFIX = "key.save.prefix";
    public String getSavePrefix()
    {
        return mPrefer.getString(KEY_SAVE_PREFIX, Const.DEFULAT_SAVE_PREFIX);
    }


    public void setSavePrefix(String prefix)
    {
        SharedPreferences.Editor editor = mPrefer.edit();
        editor.putString(KEY_SAVE_PREFIX, prefix);
        editor.commit();
    }


    private final  String KEY_SAVE_SUFFIX = "key.save.suffix";
    public String getSaveSuffix()
    {
        return mPrefer.getString(KEY_SAVE_SUFFIX, Const.DEFULAT_SAVE_SUFFIX);
    }

    public void putSaveSuffix(String suffix)
    {
        SharedPreferences.Editor editor = mPrefer.edit();
        editor.putString(KEY_SAVE_SUFFIX, suffix);
        editor.commit();
    }

    public String getSaveFileName()
    {
        return String.format("%s_%s.jpg",getSavePrefix(), Util.getNowDateString(new SimpleDateFormat(getSaveSuffix())));
    }

    private  final String KEY_ENABLE_DROPBOX = "key.enable.dropbox";

    public boolean getEnableDropbox(){ return mPrefer.getBoolean(KEY_ENABLE_DROPBOX, false); }

    public void setEnableDropbox(boolean enable){
        SharedPreferences.Editor editor = mPrefer.edit();
        editor.putBoolean(KEY_ENABLE_DROPBOX, enable);
        editor.commit();
    }

    private  final String KEY_ENABLE_HELPER = "key.enable.helper";

    public boolean getEnableHelper()
    {
        return mPrefer.getBoolean(KEY_ENABLE_HELPER, true);
    }

    public void putEnableHelper(boolean enable)
    {
        SharedPreferences.Editor editor = mPrefer.edit();
        editor.putBoolean(KEY_ENABLE_HELPER, enable);
        editor.commit();
    }

    private final String KEY_LOCK_PATTERN = "key.lock.pattern";
    public String getLockPattern()
    {
        return mPrefer.getString(KEY_LOCK_PATTERN, "");
    }

    public void putLockPattern(String pattern)
    {
        SharedPreferences.Editor editor = mPrefer.edit();
        editor.putString(KEY_LOCK_PATTERN, pattern);
        editor.commit();
    }
}

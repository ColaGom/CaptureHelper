package com.aviv.capturehelper.common;

import android.content.Context;
import android.content.SharedPreferences;

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

    private final  String KEY_SAVE_SUFFIX = "key.save.suffix";
    public String getSaveSuffix()
    {
        return mPrefer.getString(KEY_SAVE_SUFFIX, Const.DEFULAT_SAVE_SUFFIX);
    }
}

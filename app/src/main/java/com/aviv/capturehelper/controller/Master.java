package com.aviv.capturehelper.controller;

import android.content.Context;

/**
 * Created by Colabear on 2016-03-22.
 */
public class Master {
    private static Master sInstance;

    public static Master getInstance()
    {
        if(sInstance == null) sInstance = new Master();
        return sInstance;
    }

    private AlbumDataLoader mAlbumDataLoader;
    private UserPrefLoader mUserPrefLoader;

    public void init(Context context)
    {
        mAlbumDataLoader = new AlbumDataLoader();
        mAlbumDataLoader.init(context);
        mUserPrefLoader = new UserPrefLoader();
        mUserPrefLoader.init(context);
    }

    public AlbumDataLoader getAlbumDataLoader()
    {
        return mAlbumDataLoader;
    }

    public UserPrefLoader getUserPrefLoader()
    {
        return mUserPrefLoader;
    }
}

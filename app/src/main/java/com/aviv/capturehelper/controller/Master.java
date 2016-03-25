package com.aviv.capturehelper.controller;

import android.content.Context;

import com.aviv.capturehelper.common.UserPrefLoader;

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
    private ImageDataLoader mImageDataLoader;
    private UserPrefLoader mUserPrefLoader;

    public void init(Context context)
    {
        mAlbumDataLoader = new AlbumDataLoader();
        mAlbumDataLoader.init(context);
        mImageDataLoader = new ImageDataLoader();
        mImageDataLoader.init(context);
        mUserPrefLoader = new UserPrefLoader();
        mUserPrefLoader.init(context);
    }

    public AlbumDataLoader getAlbumDataLoader()
    {
        return mAlbumDataLoader;
    }

    public ImageDataLoader getImageDataLoader()
    {
        return mImageDataLoader;
    }
    public UserPrefLoader getUserPrefLoader()
    {
        return mUserPrefLoader;
    }
}

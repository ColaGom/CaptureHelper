package com.aviv.capturehelper.controller;


import android.util.Log;

import com.aviv.capturehelper.common.Const;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Counter on 2016-01-17.
 */
public class DropBoxer {
    final static private String APP_KEY = "vsns9ctie46227p";
    final static private String APP_SECRET = "qhu7h5f4gut9i47";

    private DropboxAPI<AndroidAuthSession> mDBApi;
    AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
    AndroidAuthSession mSession = new AndroidAuthSession(appKeyPair);

    public DropBoxer(){
        mDBApi = new DropboxAPI<AndroidAuthSession>(mSession);
    }

    public DropboxAPI<AndroidAuthSession> getDBApi(){ return mDBApi; }

    public void uploadFile(final File file){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(!mDBApi.getSession().isLinked()){
                    mDBApi.getSession().setOAuth2AccessToken(Master.getInstance().getUserPrefLoader().getCloudToken());
                }
                try
                {
                    FileInputStream inputStream = new FileInputStream(file);

                    DropboxAPI.Entry response = mDBApi.putFile(file.getParentFile().getName() + File.separator + file.getName(), inputStream,
                            file.length(), null, null);
                    Log.d(Const.TAG, "[DropBoxer]The uploaded file's rev is: " + response.rev);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}

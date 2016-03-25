package com.aviv.capturehelper.common;

import android.os.FileObserver;

import com.orhanobut.logger.Logger;

import java.util.Arrays;

/**
 * Created by Counter on 2016-03-11.
 */
public class CaptureObserver extends FileObserver {

    private String mPath;

    final int[] eventValue = new int[] {FileObserver.ACCESS, FileObserver.ALL_EVENTS, FileObserver.ATTRIB, FileObserver.CLOSE_NOWRITE,FileObserver.CLOSE_WRITE, FileObserver.CREATE,
            FileObserver.DELETE, FileObserver.DELETE_SELF,FileObserver.MODIFY,FileObserver.MOVED_FROM,FileObserver.MOVED_TO, FileObserver.MOVE_SELF,FileObserver.OPEN};
    final String[] eventName = new String[] {"ACCESS", "ALL_EVENTS", "ATTRIB", "CLOSE_NOWRITE", "CLOSE_WRITE", "CREATE",
            "DELETE", "DELETE_SELF" , "MODIFY" , "MOVED_FROM" ,"MOVED_TO", "MOVE_SELF","OPEN"};
    final String[] filters = new String[] {"Screenshots"};

    ICaptureListener mListener;

    public CaptureObserver(String path, ICaptureListener listener) {
        super(path);
        mPath = path;
        mListener = listener;
    }

    public CaptureObserver(String path, int mask) {
        super(path, mask);
        mPath = path;
    }

    @Override
    public void onEvent(int event, String path) {
        StringBuilder sbEvent = new StringBuilder();
        sbEvent.append("Event : ").append('(').append(event).append(')');
        for(int i = 0; i < eventValue.length; ++i) {
            if((eventValue[i] & event) == eventValue[i]) {
                sbEvent.append(eventName[i]);
                sbEvent.append(',');
            }
        }
        sbEvent.append("\tPath : ").append(path).append('(').append(mPath).append(')');

        if(event == FileObserver.CLOSE_WRITE) {
            Logger.i(sbEvent.toString());
            if(mPath.contains("Screenshots")){
                mListener.onCapture(mPath + "/" + path);
            }
        }

        if(event == FileObserver.CREATE) {
            Logger.i(sbEvent.toString());
        }
    }
}

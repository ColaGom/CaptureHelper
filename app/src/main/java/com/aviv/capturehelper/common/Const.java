package com.aviv.capturehelper.common;

import com.aviv.capturehelper.model.dao.AlbumData;

import java.util.Comparator;

/**
 * Created by Counter on 2016-03-11.
 */
public class Const {
    public static final String TAG = "CaptureHelper";

    public static  final int REQUEST_SETTING_LOCK = 1;

    public static final String EXTRA_FILE_PATH = "extra.file.path";
    public static final String EXTRA_SERIALIZE_ALBUM = "extra.file.path";
    public static final String EXTRA_TYPE = "extra.type";
    public static final String EXTRA_ARRAY_STRING = "extra.array.string";
    public static final String EXTRA_START_INDEX = "extra.start.index";
    public static final String EXTRA_NAME = "extra.name";

    public static final String DEFULAT_SAVE_FOLDER = "aviv_capture";
    public static final String DEFULAT_SAVE_PREFIX = "Screenshot";
    public static final String DEFULAT_SAVE_SUFFIX = "yyyy-MM-dd-hh:mm:ss";

    public static  final int QUALITY_50 = 0;
    public static  final int QUALITY_ORIGIN = 1;

    public static final  String APP_URL_FOR_MARKET = "market://details?id=com.aviv.capturehelper";
    public static final  String APP_URL_FOR_INVITE = "https://play.google.com/store/apps/details?id=com.aviv.autocallrecorder";

    public static final int MINIMUN_PATTERN = 4;

    public static final Comparator<AlbumData> albumComparator = new Comparator<AlbumData>() {
        @Override
        public int compare(AlbumData lhs, AlbumData rhs) {


            if(lhs.getIsnew())
            {
                if(rhs.getIsnew())
                {
                    return lhs.getName().compareTo(rhs.getName());
                }
                else return 1;
            }
            return 0;
           // return lhs.getName().compareTo(rhs.getName());
        }
    };
}

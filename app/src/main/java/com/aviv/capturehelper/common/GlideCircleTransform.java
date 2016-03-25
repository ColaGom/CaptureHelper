package com.aviv.capturehelper.common;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Counter on 2016-03-14.
 */
public class GlideCircleTransform extends BitmapTransformation {
    private static final String ID = "glide.circle.transform";

    public GlideCircleTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return Util.getCircleBitmapImage(toTransform);
    }

    @Override
    public String getId() {
        return ID;
    }
}

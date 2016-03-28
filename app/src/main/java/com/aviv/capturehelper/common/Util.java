package com.aviv.capturehelper.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.aviv.capturehelper.model.wrapper.WrapAlbumData;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;

/**
 * Created by Counter on 2016-03-11.
 */
public class Util {

    public static String getNowDateString(SimpleDateFormat format){
        return format.format(new java.util.Date());
    }

    public static float pxToDp(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dpToPx(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    private static final String[] okFileExtensions = new String[] {"jpg", "jpeg","png"};

    public static File[] getListOfImageFiles(String dir)
    {
        return getListOfImageFiles(new File(dir));
    }

    public static File[] getListOfImageFiles(File dir)
    {
        return dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                for (String ext : okFileExtensions) {
                    if (pathname.getName().toLowerCase().endsWith(ext)) return true;
                }
                return false;
            }
        });
    }

    public static Bitmap getCircleBitmapImage(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);

        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        squaredBitmap.recycle();
        return bitmap;
    }

    public static void moveFile(WrapAlbumData albumData, String path){
        File from = new File(path);
        File to = new File(albumData.getValue().getPath(), from.getName());
        from.renameTo(to);
    }
}

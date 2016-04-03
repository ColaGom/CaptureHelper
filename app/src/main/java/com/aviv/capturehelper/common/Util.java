package com.aviv.capturehelper.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.aviv.capturehelper.controller.Master;
import com.aviv.capturehelper.model.wrapper.WrapAlbumData;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

/**
 * Created by Counter on 2016-03-11.
 */
public class Util {

    /**
     * Hides the soft keyboard
     */
    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
    public static void hideSoftKeyboard(Context context, EditText et) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    public static String getNowDateString(SimpleDateFormat format){
        return format.format(new java.util.Date());
    }

    public static int pxToDp(final Context context, final int px) {
        return (int)(px / context.getResources().getDisplayMetrics().density);
    }

    public static int dpToPx(final Context context, final int dp) {
        return (int)(dp * context.getResources().getDisplayMetrics().density);
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

    public static void moveFile(WrapAlbumData albumData, String path,boolean resize){
        File from = new File(path);
        File to = new File(albumData.getValue().getPath(), from.getName());
        from.renameTo(to);

        if(resize && Master.getInstance().getUserPrefLoader().getSaveQuality() == Const.QUALITY_50)
        {
            resizedFile(to);
        }
    }

    private static void resizedFile(File file){
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());

        try {
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, true);
            FileOutputStream fos = new FileOutputStream(file.getPath());
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

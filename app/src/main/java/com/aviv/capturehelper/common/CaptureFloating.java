package com.aviv.capturehelper.common;

import android.app.Service;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.aigestudio.wheelpicker.core.AbstractWheelDecor;
import com.aigestudio.wheelpicker.view.WheelCurvedPicker;
import com.aviv.capturehelper.R;
import com.aviv.capturehelper.controller.AlbumDataLoader;
import com.aviv.capturehelper.controller.Master;
import com.aviv.capturehelper.model.dao.AlbumData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colabear on 2016-03-27.
 */
public class CaptureFloating extends Service {

    private WindowManager mWindowManager;
    private View mFloatingVIew;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }  

    @Override
    public void onCreate() {
        super.onCreate();

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        setFloatingView();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams((int)Util.dpToPx(this, 180), (int)Util.dpToPx(this, 120),
                WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        params.x = 0;
        params.y = 0;
        params.gravity = Gravity.CENTER | Gravity.CENTER;

        mWindowManager.addView(mFloatingVIew, params);
    }

    private void setFloatingView()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        mFloatingVIew = inflater.inflate(R.layout.floating_capture, null);
        WheelCurvedPicker wheelCurvedPicker = (WheelCurvedPicker) mFloatingVIew.findViewById(R.id.wcp_album);
        wheelCurvedPicker.setWheelDecor(true, new AbstractWheelDecor() {
            @Override
            public void drawDecor(Canvas canvas, Rect rectLast, Rect rectNext, Paint paint) {
                canvas.drawColor(0x8847A1D9);
            }
        });
        AlbumDataLoader loader = Master.getInstance().getAlbumDataLoader();
        wheelCurvedPicker.setData(extractAlbumName(loader.getAll()));
    }

    private List<String> extractAlbumName(List<AlbumData> list)
    {
        List<String> result = new ArrayList<>();

        for(AlbumData album:list)
        {
            result.add(album.getName());
        }

        return result;
    }
}

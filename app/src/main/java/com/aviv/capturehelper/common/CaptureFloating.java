package com.aviv.capturehelper.common;

import android.app.Service;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.aigestudio.wheelpicker.core.AbstractWheelDecor;
import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
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
    private WindowManager.LayoutParams mCurrentParams;
    private int mCurrentIdx;

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

        int width = (int)Util.dpToPx(this, 180);
        int height = (int)Util.dpToPx(this, 120);

        mCurrentParams = new WindowManager.LayoutParams(width, height,
                WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(displaymetrics);

        mCurrentParams.x = displaymetrics.widthPixels / 2 - width / 2;
        mCurrentParams.y = 0;
        mCurrentParams.gravity = Gravity.CENTER | Gravity.CENTER;

        mWindowManager.addView(mFloatingVIew, mCurrentParams);
    }

    private void setFloatingView()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        mFloatingVIew = inflater.inflate(R.layout.floating_capture, null);

        mFloatingVIew.setOnTouchListener(new View.OnTouchListener() {

            int x;
            int y;
            float touchedX;
            float touchedY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        x = mCurrentParams.x;
                        y = mCurrentParams.y;
                        touchedX = event.getRawX();
                        touchedY = event.getRawY();
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_MOVE:
                        mCurrentParams.x = (int) (x + (event.getRawX() - touchedX));
                        mCurrentParams.y = (int) (y + (event.getRawY() - touchedY));

                        mWindowManager.updateViewLayout(mFloatingVIew, mCurrentParams);
                        break;
                }
                return false;
            }
        });

        mFloatingVIew.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopFloating();
            }
        });

        mFloatingVIew.findViewById(R.id.btn_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        WheelCurvedPicker wheelCurvedPicker  = (WheelCurvedPicker) mFloatingVIew.findViewById(R.id.wcp_album);
        wheelCurvedPicker.setWheelDecor(true, new AbstractWheelDecor() {
            @Override
            public void drawDecor(Canvas canvas, Rect rectLast, Rect rectNext, Paint paint) {
                canvas.drawColor(0x8847A1D9);
            }
        });
        AlbumDataLoader loader = Master.getInstance().getAlbumDataLoader();
        List<String> datas = extractAlbumName(loader.getAll());
        if(datas.size() > 0) {
            wheelCurvedPicker.setData(extractAlbumName(loader.getAll()));
            wheelCurvedPicker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
                @Override
                public void onWheelScrolling(float deltaX, float deltaY) {

                }

                @Override
                public void onWheelSelected(int index, String data) {
                    mCurrentIdx = index;
                }

                @Override
                public void onWheelScrollStateChanged(int state) {

                }
            });
        }
        else{
            Toast.makeText(this, getString(R.string.msg_empty_album), Toast.LENGTH_SHORT).show();
            stopFloating();
        }
    }

    private AlbumData getSelectedAlbum()
    {
        return Master.getInstance().getAlbumDataLoader().getAll().get(mCurrentIdx);
    }

    private void capture()
    {

    }

    private void stopFloating() {
        mWindowManager.removeView(mFloatingVIew);
        stopSelf();
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

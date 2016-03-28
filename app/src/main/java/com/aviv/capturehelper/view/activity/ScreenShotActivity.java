package com.aviv.capturehelper.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.common.ScreenShotBinder;

import java.io.File;
import java.io.FileOutputStream;

public class ScreenShotActivity extends AppCompatActivity {

    //TODO: 여기서부터
    public static ScreenShotBinder binder;

    private String mPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);

        Bundle b = getIntent().getExtras();
        mPath = b.getString(Const.EXTRA_FILE_PATH);

        View screenView = getWindow().getDecorView().getRootView();
        screenView.setDrawingCacheEnabled(true);
        screenView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);


        try {
            File imageFIle = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFIle);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

//       screenView.setDrawingCacheEnabled(true);
//            screenView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY),
//                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY));
//            screenView.layout(0, 0, screenView.getMeasuredWidth(), screenView.getMeasuredHeight());
//            screenView.buildDrawingCache();
//
//            Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
//            screenView.setDrawingCacheEnabled(false);
//            mFloatingVIew.setVisibility(View.VISIBLE);
//
//
//            File imageFile = new File(albumData.getPath(), fileName);
//            FileOutputStream outputStream = new FileOutputStream(imageFile);
//            int quality = 100;
//            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
//            outputStream.flush();
//            outputStream.close();

    }
}

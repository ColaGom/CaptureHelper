package com.aviv.capturehelper.view.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.common.ScreenShotBinder;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;

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

        ButterKnife.bind(this);

        //    finish();

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

    @OnClick(R.id.btn_test)
    public void capture(){
        View screenView = getWindow().getDecorView();
        screenView.setDrawingCacheEnabled(true);
        //screenView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        //        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //screenView.layout(0, 0, screenView.getWidth(), screenView.getHeight());
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this, getString(R.string.msg_success_save), Toast.LENGTH_SHORT).show();
    }

    public static Bitmap loadBitmapFromView(View v) {
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }
}

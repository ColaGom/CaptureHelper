package com.aviv.capturehelper.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aviv.capturehelper.R;
import com.orhanobut.logger.Logger;

/**
 * Created by Colabear on 2016-03-23.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected final int REQUEST_CREATE_ALBUM = 1;


    protected void onAlbumCreated() {
        Logger.d("onAlbumCreated");
    }

    protected  void onClickBackBtn() {
        finish();
    }

    protected  void startCreateAlbumActivity()
    {
        Intent i = new Intent(this, CreateAlbumActivity.class);
        startActivityForResult(i, REQUEST_CREATE_ALBUM);
    }

    protected  void setActionBar(String title,String left,String right)
    {
//        ActionBar actionBar = getActionBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.actionbar_custom, null);

        TextView tvTitle = (TextView)mCustomView.findViewById(R.id.tv_title);
        TextView tvLeft = (TextView)mCustomView.findViewById(R.id.tv_left);
        TextView tvRight = (TextView)mCustomView.findViewById(R.id.tv_right);

        tvTitle.setText(title);
        tvLeft.setText(left);
        tvRight.setText(right);

        mCustomView.findViewById(R.id.btn_left).setOnClickListener(this);
        mCustomView.findViewById(R.id.btn_right).setOnClickListener(this);

        //actionBar.setCustomView(mCustomView, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode)
            {
                case REQUEST_CREATE_ALBUM:
                    onAlbumCreated();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
    }
}

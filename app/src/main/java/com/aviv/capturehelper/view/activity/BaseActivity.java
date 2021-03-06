package com.aviv.capturehelper.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.controller.ActivityStarter;
import com.aviv.capturehelper.model.dao.AlbumData;
import com.orhanobut.logger.Logger;

/**
 * Created by Colabear on 2016-03-23.
 */
public class BaseActivity extends AppCompatActivity {

    protected final int REQUEST_CREATE_ALBUM = 1;
    protected final int REQUEST_ALBUM = 2;
    protected final int REQUEST_MODIFY_ALBUM = 3;

    protected void finishedAlbumCreate(int resultCode) {
        Logger.d("finishedAlbumCreate");
    }

    protected void finishedAlbum(int resultCode) {

    }

    protected  void startSettingActivity()
    {
        Intent i = new Intent(this, SettingActivity.class);
        startActivity(i);
    }

    protected  void startAlbumActivity(AlbumData albumData)
    {
        Intent i = new Intent(this, AlbumActivity.class);
        i.putExtra(Const.EXTRA_SERIALIZE_ALBUM, albumData);
        startActivityForResult(i, REQUEST_ALBUM);
    }

    protected  void setToolbar(String title)
    {
        setToolbar(title, true);
    }

    protected void setToolbar(int titleRes)
    {
        setToolbar(getString(titleRes), true);
    }

    protected void setToolbar(String title, boolean displayHome)
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHome);
    }

    protected void setToolbar(int titleRes, boolean displayHome)
    {
        setToolbar(getString(titleRes), displayHome);
    }

    protected  void startCreateAlbumActivity()
    {
        Intent i = new Intent(this, CreateAlbumActivity.class);
        i.putExtra(Const.EXTRA_TYPE, CreateAlbumActivity.TYPE_CREATE);
        startActivityForResult(i, REQUEST_CREATE_ALBUM);
    }

    protected  void startModifyAlbumActivity(AlbumData albumData)
    {
        Intent i = new Intent(this, CreateAlbumActivity.class);
        i.putExtra(Const.EXTRA_TYPE, CreateAlbumActivity.TYPE_MODIFY);
        i.putExtra(Const.EXTRA_SERIALIZE_ALBUM, albumData);
        startActivityForResult(i, REQUEST_MODIFY_ALBUM);
    }

    protected  void startLockValidActivity()
    {
        ActivityStarter.startLockActivity(this, LockActivity.TYPE_VALID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode)
            {
                case REQUEST_CREATE_ALBUM:
                    finishedAlbumCreate(resultCode);
                    break;

                case REQUEST_ALBUM:
                    finishedAlbum(resultCode);
                    break;
            }
        }
    }
}

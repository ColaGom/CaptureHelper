package com.aviv.capturehelper.view.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.controller.Master;
import com.aviv.capturehelper.model.dao.AlbumData;
import com.orhanobut.logger.Logger;

import java.io.File;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateAlbumActivity extends BaseActivity {

    public static final int TYPE_CREATE = 1;
    public static final int TYPE_MODIFY = 2;

    int mType;
    AlbumData mAlbum;

    @BindString(R.string.title_activity_create_album)
    String mStrTitle;

    @BindString(R.string.title_activity_modify_album)
    String mStrModifyTitle;

    @Bind(R.id.et_input)
    EditText mEtInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_album);

        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        mType =  b.getInt(Const.EXTRA_TYPE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(mType == TYPE_CREATE)
            toolbar.setTitle(mStrTitle);
        else {
            toolbar.setTitle(mStrModifyTitle);
            mAlbum = (AlbumData)b.getSerializable(Const.EXTRA_SERIALIZE_ALBUM);
            mEtInput.setText(mAlbum.getName());
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btn_create)
    void onClickCreateAlbum(View view)
    {
        if(mEtInput.length() == 0) {
            Snackbar.make(view, getString(R.string.msg_input_name), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else if(mType == TYPE_CREATE){
            createAlbum();

        }else if(mType == TYPE_MODIFY){
            modifyAlbum();
        }
    }

    private void modifyAlbum()
    {
        String name = mEtInput.getText().toString();

        if(Master.getInstance().getAlbumDataLoader().containsAlbum(name)){
            Toast.makeText(this, getString(R.string.msg_contains_album), Toast.LENGTH_SHORT).show();
            return;
        }

        mAlbum.setName(name);
        Master.getInstance().getAlbumDataLoader().update(mAlbum);

        setResult(RESULT_OK);
        finish();
    }

    private void createAlbum(){
        String name = mEtInput.getText().toString();

        if(Master.getInstance().getAlbumDataLoader().containsAlbum(name)){
            Toast.makeText(this, getString(R.string.msg_contains_album), Toast.LENGTH_SHORT).show();
            return;
        }
        
        String path = Environment.getExternalStorageDirectory()
                + File.separator + Master.getInstance().getUserPrefLoader().getFolderPath()
                + File.separator + name;

        File directory = new File(path);

        if(!directory.exists()) {
            Logger.d("make directory : " + path);
            directory.mkdirs();
        }

        AlbumData albumData = new AlbumData();
        albumData.setDate(new java.util.Date());
        albumData.setName(mEtInput.getText().toString());
        albumData.setPath(path);
        albumData.setIsnew(false);
        albumData.setIsfavorite(false);

        Master.getInstance().getAlbumDataLoader().insert(albumData);

        setResult(RESULT_OK);
        finish();
    }
}

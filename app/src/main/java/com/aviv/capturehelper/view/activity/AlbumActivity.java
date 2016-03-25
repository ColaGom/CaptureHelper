package com.aviv.capturehelper.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.model.dao.AlbumData;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlbumActivity extends BaseActivity {

    AlbumData mAlbum;

    @Bind(R.id.gv_album)
    GridView mGvAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_album);

        Bundle b = getIntent().getExtras();
        mAlbum = (AlbumData)b.getSerializable(Const.EXTRA_SERIALIZE_ALBUM);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mAlbum.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btn_modify)
    void onClickModify(View view)
    {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}

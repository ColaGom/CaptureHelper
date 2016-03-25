package com.aviv.capturehelper.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.common.Util;
import com.aviv.capturehelper.model.dao.AlbumData;
import com.aviv.capturehelper.view.adapter.AdapterImage;

import java.io.File;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlbumActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    @Bind(R.id.gv_album)
    GridView mGvAlbum;

    @Bind(R.id.btn_modify)
    View mBtnModify;

    AlbumData mAlbum;
    AdapterImage mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Bundle b = getIntent().getExtras();
        mAlbum = (AlbumData)b.getSerializable(Const.EXTRA_SERIALIZE_ALBUM);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mAlbum.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        File[] images = Util.getListOfImageFiles(mAlbum.getPath());
        mAdapter = new AdapterImage(this, R.layout.row_image, Arrays.asList(images));
        mGvAlbum.setAdapter(mAdapter);
        mGvAlbum.setOnItemClickListener(this);
        mGvAlbum.setOnItemLongClickListener(this);

        setComponents();
    }

    private void setComponents()
    {
        if(mAdapter.getSelectedCount() > 0)
        {
            mBtnModify.setVisibility(View.VISIBLE);
        }
        else{
            mBtnModify.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_modify)
    void onClickModify(View view)
    {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        File item = (File)parent.getItemAtPosition(position);
        mAdapter.select(item);
        setComponents();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}

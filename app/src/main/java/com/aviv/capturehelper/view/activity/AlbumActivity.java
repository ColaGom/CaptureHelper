package com.aviv.capturehelper.view.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.common.Dialoger;
import com.aviv.capturehelper.common.Util;
import com.aviv.capturehelper.controller.ActivityStarter;
import com.aviv.capturehelper.controller.Master;
import com.aviv.capturehelper.model.dao.AlbumData;
import com.aviv.capturehelper.model.wrapper.WrapAlbumData;
import com.aviv.capturehelper.view.adapter.AdapterImage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlbumActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    @Bind(R.id.gv_album)
    GridView mGvAlbum;

    @Bind(R.id.btn_delete)
    View mBtnDelete;

    @Bind(R.id.btn_move)
    View mBtnMove;

    AlertDialog mDialog;
    AlbumData mAlbum;
    AdapterImage mAdapter;
    List<File> mListFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Bundle b = getIntent().getExtras();
        mAlbum = (AlbumData) b.getSerializable(Const.EXTRA_SERIALIZE_ALBUM);

        ButterKnife.bind(this);

        setToolbar(mAlbum.getName());

        mListFile = Arrays.asList(Util.getListOfImageFiles(mAlbum.getPath()));
        mAdapter = new AdapterImage(this, R.layout.row_image, mListFile);
        mGvAlbum.setAdapter(mAdapter);
        mGvAlbum.setOnItemClickListener(this);
        mGvAlbum.setOnItemLongClickListener(this);

        setComponents();
    }

    private void setComponents() {
        if (mAdapter.getSelectedCount() > 0) {
            mBtnDelete.setVisibility(View.VISIBLE);
            mBtnMove.setVisibility(View.VISIBLE);
        } else {
            mBtnDelete.setVisibility(View.GONE);
            mBtnMove.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn_select_all, R.id.btn_deselect_all, R.id.btn_delete, R.id.btn_move})
    void onClick(View view) {
        int id = view.getId();

        switch (id)
        {
            case R.id.btn_select_all:
                mAdapter.selectAll();
                setComponents();
                break;
            case R.id.btn_deselect_all:
                mAdapter.deselectAll();
                setComponents();
                break;

            case R.id.btn_delete:
                Dialoger.showAlertDialog(this, getString(R.string.title_image_delete), getString(R.string.msg_album_delete), new Dialoger.AlertListener() {
                    @Override
                    public void onClickYes() {
                        List<File> selectedItem = mAdapter.getSelectedItem();

                        for(File f:selectedItem)
                            f.delete();

                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onClickNo() {

                    }
                });
                break;

            case R.id.btn_move:
                showMoveDialog();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mAdapter.getSelectedCount() == 0){
            ActivityStarter.startImageViewActivity(this, mListFile, position);
        }else{
            File item = (File) parent.getItemAtPosition(position);
            mAdapter.select(item);
            setComponents();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        File item = (File) parent.getItemAtPosition(position);
        mAdapter.select(item);
        setComponents();
        return true;
    }

    private void refreshAdapter()
    {
        mListFile = Arrays.asList(Util.getListOfImageFiles(mAlbum.getPath()));
        mAdapter = new AdapterImage(AlbumActivity.this, R.layout.row_image, mListFile);
        mGvAlbum.setAdapter(mAdapter);
    }

    //TODO : complete moved Image notify change data in MainActivity(Album list)
    private  void showMoveDialog()
    {
        Dialoger.showSelectAlbumDialog(this, new Dialoger.SelectAlbumListener() {
            @Override
            public void onSelectedAlbum(int idx) {
                for(File f:mAdapter.getSelectedItem()){
                    Util.moveFile(new WrapAlbumData(Master.getInstance().getAlbumDataLoader().get(idx)), f.getPath());
                }

                refreshAdapter();
            }
        });
    }
}

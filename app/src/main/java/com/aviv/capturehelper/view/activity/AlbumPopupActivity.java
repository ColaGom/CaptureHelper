package com.aviv.capturehelper.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.common.Dialoger;
import com.aviv.capturehelper.common.Util;
import com.aviv.capturehelper.controller.Master;
import com.aviv.capturehelper.model.wrapper.WrapAlbumData;
import com.aviv.capturehelper.model.wrapper.WrapHelper;
import com.aviv.capturehelper.view.adapter.AdapterAlbum;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Counter on 2016-03-11.
 */
public class AlbumPopupActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.btn_cancel)
    View mBtnCancel;

    @Bind(R.id.btn_plus)
    View mBtnPlus;

    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Bind(R.id.tv_preview)
    TextView mTvPreview;

    @Bind(R.id.iv_preview)
    ImageView mIvPreview;

    @Bind(R.id.lv_album)
    ListView mLvAlbum;

    private String mPath;
    private AdapterAlbum mAdapter;
    private List<WrapAlbumData> mAlbumDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_popup);

        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();

        mPath = b.getString(Const.EXTRA_FILE_PATH);

        Logger.i("file Exist : " + new File(mPath).exists());
        Glide.with(this).load(mPath).listener(mListener).into(mIvPreview);
        mTvPreview.setText(mPath);

        setAlbumListView();
    }

    private void setAlbumListView() {
        mAlbumDataList = WrapHelper.convertAlbumData(Master.getInstance().getAlbumDataLoader().getAll());

        if(mAlbumDataList.size() == 0){
            mAlbumDataList.add(new WrapAlbumData());
        }

        mAdapter = new AdapterAlbum(this, R.layout.row_album, mAlbumDataList);
        mLvAlbum.setAdapter(mAdapter);
        mLvAlbum.setOnItemClickListener(this);
    }

    private RequestListener<String, GlideDrawable> mListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            e.printStackTrace();
            Logger.e("Exception" + e.toString());
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            Logger.e("onResourceReady");
            return false;
        }
    };

    @OnClick(R.id.root)
    void onClickBack()
    {
        finish();
    }

    @OnClick(R.id.btn_cancel)
    void onClickCancel()
    {
        finish();
    }

    @OnClick(R.id.btn_plus)
    void onClickPlus()
    {
        startCreateAlbumActivity();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final WrapAlbumData albumData = mAdapter.getItem(position);

        if(albumData.isEmpty()){

        }else{
            String message = getString(R.string.msg_selected_prefix) + " " + albumData.getValue().getName();
            Dialoger.showAlertDialog(this, getString(R.string.title_selected_album), message, new Dialoger.AlertListener() {
                @Override
                public void onClickYes() {
                    albumData.getValue().setIsnew(true);
                    Master.getInstance().getAlbumDataLoader().insert(albumData.getValue());
                    File movedFile = Util.moveFile(albumData, mPath, true);
                    Toast.makeText(AlbumPopupActivity.this, getString(R.string.msg_saved), Toast.LENGTH_SHORT).show();
                    if(Master.getInstance().getUserPrefLoader().getEnableDropbox())
                       Master.getInstance().getDropBoxer().uploadFile(movedFile);
                    finish();
                }

                @Override
                public void onClickNo() {

                }
            });
        }
    }
}

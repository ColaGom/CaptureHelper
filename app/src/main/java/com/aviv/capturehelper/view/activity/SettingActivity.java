package com.aviv.capturehelper.view.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.controller.ActivityStarter;
import com.aviv.capturehelper.controller.Master;
import com.aviv.capturehelper.controller.UserPrefLoader;
import com.aviv.capturehelper.view.dialog.DirectoryChooserDialog;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements DirectoryChooserDialog.ChosenDirectoryListener {

    @Bind(R.id.rg_quality)
    RadioGroup mRgQuality;

    @Bind(R.id.tv_save_folder)
    TextView mTvSaveFolder;

    @Bind(R.id.sc_dropbox)
    SwitchCompat mScDropbox;

    @Bind(R.id.sc_lock)
    SwitchCompat mScLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        setToolbar(R.string.title_activity_setting);

        setView();
        setScLock();
    }

    private void setView()
    {
        int quality = Master.getInstance().getUserPrefLoader().getSaveQuality();
        RadioButton rb = (RadioButton)mRgQuality.getChildAt(quality);
        rb.setChecked(true);

        mTvSaveFolder.setText(Master.getInstance().getUserPrefLoader().getFolderPath());

        mRgQuality.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View v = group.findViewById(checkedId);
                int selectedIdx = group.indexOfChild(v);
                Master.getInstance().getUserPrefLoader().putSaveQuality(selectedIdx);
            }
        });
    }

    @OnClick({R.id.btn_app_share, R.id.sc_dropbox, R.id.sc_lock, R.id.btn_save_folder, R.id.btn_version})
    void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_version:
                ActivityStarter.startStoreIntent(this);
                break;
            case R.id.btn_app_share:
                ActivityStarter.startAppShareIntent(this);
                break;
            case R.id.sc_dropbox:
                if(mScDropbox.isChecked()){
                    DropboxAPI<AndroidAuthSession> api = Master.getInstance().getDropBoxer().getDBApi();
                    api.getSession().startOAuth2Authentication(this);
                }else{
                    Master.getInstance().getUserPrefLoader().setEnableDropbox(false);
                }
                break;
            case R.id.sc_lock:
                if(mScLock.isChecked()) {
                    ActivityStarter.startLockActivity(this, LockActivity.TYPE_SETTING);
                }
                else{
                    Master.getInstance().getUserPrefLoader().putLockPattern("");
                    Toast.makeText(this,getString(R.string.label_clear_lock),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_save_folder:
                DirectoryChooserDialog dialog = new DirectoryChooserDialog(this, this);
                dialog.setNewFolderEnabled(true);
                dialog.chooseDirectory(Environment.getExternalStorageDirectory().getAbsolutePath());
                break;
        }
    }

    private void setScLock()
    {
        mScLock.setChecked(!TextUtils.isEmpty(Master.getInstance().getUserPrefLoader().getLockPattern()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setScLock();

        DropboxAPI<AndroidAuthSession> api = Master.getInstance().getDropBoxer().getDBApi();

        if(api.getSession().authenticationSuccessful()){
            try {
                api.getSession().finishAuthentication();
                String accessToken = api.getSession().getOAuth2AccessToken();
                Log.d(Const.TAG, "[SettingActivity]authenticationSuccessful" + accessToken);
                UserPrefLoader loader = Master.getInstance().getUserPrefLoader();
                loader.setEnableDropbox(true);
                loader.setCloudToken(accessToken);
                mScDropbox.setChecked(true);
            }catch (IllegalStateException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onChosenDir(String chosenDir) {
        String savedPath = chosenDir.replace(Environment.getExternalStorageDirectory().getAbsolutePath() + "/", "");
        Logger.d(Const.TAG, "[SettingActivity]onChosenDir : " + savedPath);

        UserPrefLoader userPrefLoader = Master.getInstance().getUserPrefLoader();
        userPrefLoader.putFolderPath(savedPath);
        mTvSaveFolder.setText(savedPath);
    }
}

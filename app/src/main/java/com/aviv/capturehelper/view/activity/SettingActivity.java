package com.aviv.capturehelper.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.controller.Master;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.rg_quality)
    RadioGroup mRgQuality;

    @Bind(R.id.tv_save_folder)
    TextView mTvSaveFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        setToolbar(R.string.title_activity_setting);

        setView();
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

    @OnClick({R.id.btn_app_share, R.id.btn_lock, R.id.btn_save_folder})
    void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_app_share:
                break;
            case R.id.btn_lock:
                break;
            case R.id.btn_save_folder:
                break;
        }
    }
}

package com.aviv.capturehelper.view.activity;

import android.os.Bundle;

import com.aviv.capturehelper.R;

import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        setToolbar(R.string.title_activity_setting);
    }
}

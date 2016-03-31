package com.aviv.capturehelper.view.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.controller.Master;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by Counter on 2016-02-03.
 */
public class LockActivity extends BaseActivity {

    public static final int TYPE_SETTING = 1;
    public static final int TYPE_VALID = 2;
    private PatternView mPatternView;
    private int mCurrentType;
    private Button mBtnComplete;
    private TextView mTvWarnning;
    private List<PatternView.Cell> mCurrentPattern;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        mCurrentType = getIntent().getIntExtra(Const.EXTRA_TYPE, TYPE_SETTING);
        mPatternView = (PatternView)findViewById(R.id.pv_password);
        mBtnComplete = (Button)findViewById(R.id.btn_lock_pattern);
        mTvWarnning = (TextView) findViewById(R.id.tv_warnning);

        if(mCurrentType == TYPE_VALID) {
            setToolbar(R.string.title_valid_lock, false);
            mTvWarnning.setVisibility(View.GONE);
        }else{
            setToolbar(R.string.title_setting_lock, true);
            mTvWarnning.setVisibility(View.VISIBLE);
        }

        mPatternView.setOnPatternListener(new PatternView.OnPatternListener() {
            @Override
            public void onPatternStart() {

            }

            @Override
            public void onPatternCleared() {

            }

            @Override
            public void onPatternCellAdded(List<PatternView.Cell> pattern) {
            }

            @Override
            public void onPatternDetected(List<PatternView.Cell> pattern) {
                mCurrentPattern = pattern;
                if(mCurrentType == TYPE_SETTING) {
                    if (pattern.size() < Const.MINIMUN_PATTERN) {
                        Toast.makeText(LockActivity.this, getString(R.string.label_invalid_pattern), Toast.LENGTH_SHORT).show();
                        mBtnComplete.setVisibility(View.GONE);
                    }else {
                        mBtnComplete.setVisibility(View.VISIBLE);
                        mBtnComplete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Master.getInstance().getUserPrefLoader().putLockPattern(PatternUtils.patternToSha1String(mCurrentPattern));
                                setResult(RESULT_OK);
                                finish();
                            }
                        });
                    }
                }else if(mCurrentType == TYPE_VALID){
                    if(Master.getInstance().getUserPrefLoader().getLockPattern().equals(PatternUtils.patternToSha1String(mCurrentPattern)))
                        finish();
                    else {
                        Toast.makeText(LockActivity.this, getString(R.string.label_missmacth_pattern), Toast.LENGTH_SHORT).show();
                        mPatternView.setDisplayMode(PatternView.DisplayMode.Wrong);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(mCurrentType == TYPE_SETTING)
            super.onBackPressed();
    }
}

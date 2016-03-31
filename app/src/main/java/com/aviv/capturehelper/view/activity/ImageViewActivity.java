package com.aviv.capturehelper.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Const;
import com.aviv.capturehelper.view.fragment.ImageViewFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Colabear on 2016-03-31.
 */
public class ImageViewActivity extends BaseActivity {

    @Bind(R.id.pager)
    ViewPager mPager;

    ArrayList<String> mListFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_imageview);
        ButterKnife.bind(this);

        setToolbar(R.string.title_activity_imageview);

        Bundle b = getIntent().getExtras();
        mListFile = b.getStringArrayList(Const.EXTRA_ARRAY_STRING);

        int startIdx = b.getInt(Const.EXTRA_START_INDEX);

        ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(startIdx);
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            ImageViewFragment fragment = new ImageViewFragment();
            fragment.setImageFile(mListFile.get(position));
            return fragment;
        }

        @Override
        public int getCount() {
            return mListFile.size();
        }
    }
}

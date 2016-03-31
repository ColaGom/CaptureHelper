package com.aviv.capturehelper.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aviv.capturehelper.R;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Colabear on 2016-03-31.
 */
public class ImageViewFragment extends Fragment {

    private String mImagePath;
    public ImageViewFragment(){

    }

    public void setImageFile(String path)
    {
        mImagePath = path;
    }

    @Bind(R.id.ssv_image)
    SubsamplingScaleImageView mSsvImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_imageview, container, false);

        ButterKnife.bind(this, rootView);

        if(!TextUtils.isEmpty(mImagePath)) {
            mSsvImage.setImage(ImageSource.uri(Uri.fromFile(new File(mImagePath))));
        }
        return rootView;
    }
}

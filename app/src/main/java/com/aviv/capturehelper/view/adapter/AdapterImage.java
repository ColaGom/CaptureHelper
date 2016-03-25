package com.aviv.capturehelper.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.io.File;
import java.util.List;

/**
 * Created by Colabear on 2016-03-24.
 */
public class AdapterImage extends ArrayAdapter<File> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<File> mSelectedList;
    private int mRes;

    public AdapterImage(Context context, int resource, List<File> objects) {
        super(context, resource, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void selectAll()
    {

    }

    public void deselectAll()
    {

    }

    
}

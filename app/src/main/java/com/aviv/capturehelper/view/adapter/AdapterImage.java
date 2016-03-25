package com.aviv.capturehelper.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.aviv.capturehelper.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Colabear on 2016-03-24.
 */
public class AdapterImage extends ArrayAdapter<File> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<File> mSelectedList;
    private List<File> mObjects;
    private int mRes;

    public AdapterImage(Context context, int resource, List<File> objects) {
        super(context, resource, objects);

        mObjects = objects;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mSelectedList = new ArrayList<>();
        mRes = resource;
    }

    public int getSelectedCount()
    {
        return mSelectedList.size();
    }

    public List<File> getSelectedItem()
    {
        return mSelectedList;
    }

    public void selectAll()
    {
        mSelectedList.addAll(mObjects);
        notifyDataSetChanged();
    }

    public void deselectAll()
    {
        mSelectedList.clear();
        notifyDataSetChanged();
    }

    public void select(File file){
        if(mSelectedList.contains(file)){
            mSelectedList.remove(file);
        }else{
            mSelectedList.add(file);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(mRes, parent, false);
            holder.bind(convertView);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.setView(getItem(position));

        return convertView;
    }

    class ViewHolder implements IHolder<File>
    {
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.frame)
        View vFrame;

        @Override
        public void bind(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }

        @Override
        public void setView(File data) {
            Glide.with(mContext).load(data).centerCrop().crossFade().into(ivImage);

            if(mSelectedList.contains(data))
            {
                vFrame.setVisibility(View.VISIBLE);
            }else
            {
                vFrame.setVisibility(View.GONE);
            }
        }
    }

}

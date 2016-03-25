package com.aviv.capturehelper.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Util;
import com.aviv.capturehelper.model.dao.AlbumData;
import com.aviv.capturehelper.model.wrapper.WrapAlbumData;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by Colabear on 2016-03-23.
 */
public class AdapterGridAlbum extends ArrayAdapter<WrapAlbumData> {

    private Context mContext;
    private LayoutInflater mInflater;
    private int mRes;

    public AdapterGridAlbum(Context context, int resource, List<WrapAlbumData> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mRes = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(mRes, parent, false);
            holder.bind(convertView);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        WrapAlbumData data = getItem(position);
        holder.setView(data);

        return convertView;
    }

    class ViewHolder implements IHolder<WrapAlbumData>
    {
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_count)
        TextView tvCount;

        @BindString(R.string.label_prefix_count)
        String mPrefixCount;

        @BindString(R.string.label_suffix_count)
        String mSuffixCount;

        @BindString(R.string.label_empty_album)
        String mEmptyAlbum;

        public void bind(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }


        public void setView(WrapAlbumData data)
        {
            if(!data.isEmpty())
            {
                AlbumData albumData = data.getValue();

                tvTitle.setText(albumData.getName());

                File directory = new File(albumData.getPath());
                File[] files = Util.getListOfImageFiles(albumData.getPath());

                if(files.length > 0) {
                    Glide.with(mContext).load(directory.listFiles()[0]).centerCrop().crossFade().into(ivImage);
                    tvCount.setText(mPrefixCount + " " + files.length + mSuffixCount);
                }else{
                    tvCount.setText(mEmptyAlbum);
                }
            }
        }
    }
}

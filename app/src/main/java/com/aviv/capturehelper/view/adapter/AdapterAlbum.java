package com.aviv.capturehelper.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.model.wrapper.WrapAlbumData;

import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by Colabear on 2016-03-22.
 */
public class AdapterAlbum extends ArrayAdapter<WrapAlbumData> {

    private Context mContext;
    private LayoutInflater mInflater;
    private int mRes;
    private WrapAlbumData mSelectedAlbum;

    public WrapAlbumData getSelectedAlbum()
    {
        return mSelectedAlbum;
    }

    public AdapterAlbum(Context context, int resource, List<WrapAlbumData> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mRes = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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

    public void onSelected(int position){
        WrapAlbumData selectAlbum = getItem(position);

        if(mSelectedAlbum != null && mSelectedAlbum.equals(selectAlbum)){
            mSelectedAlbum = null;
        }else{
            mSelectedAlbum = selectAlbum;
        }
        notifyDataSetChanged();
    }

    class ViewHolder
    {
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_name)
        TextView tvName;
        @BindString(R.string.empty_album)
        String emptyMsg;
        @Bind(R.id.frame)
        View vFrame;


        void bind(View view){
            ButterKnife.bind(this, view);
            view.setTag(this);
        }

        void setView(WrapAlbumData data)
        {
            if(data.isEmpty()){
                tvName.setText(emptyMsg);
            }
            else {
                tvName.setText(data.getValue().getName());
            }

            if(mSelectedAlbum != null && mSelectedAlbum.equals(data))
            {
                vFrame.setVisibility(View.VISIBLE);
            }else{
                vFrame.setVisibility(View.GONE);
            }
        }
    }
}

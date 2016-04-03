package com.aviv.capturehelper.common;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.aigestudio.wheelpicker.core.AbstractWheelDecor;
import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.aigestudio.wheelpicker.view.WheelCurvedPicker;
import com.aviv.capturehelper.R;
import com.aviv.capturehelper.controller.Master;
import com.aviv.capturehelper.model.dao.AlbumData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Counter on 2016-03-11.
 */
public class Dialoger {

    public interface  AlertListener
    {
        void onClickYes();
        void onClickNo();
    }
    private static AlertDialog mAlertDialog;
    public static void showAlertDialog(final Context context, String title,String message, final AlertListener listener){
        if(mAlertDialog != null && mAlertDialog.isShowing())
            mAlertDialog.dismiss();

        mAlertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onClickYes();
                        mAlertDialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onClickNo();
                        mAlertDialog.dismiss();
                    }
                })
                .show();
    }

    public static  AlertDialog createModifyAlertDialog(Context context, String title)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_custom, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        return builder.create();
    }


    public interface SelectAlbumListener
    {
        void onSelectedAlbum(AlbumData albumData);
    }
    public static class ViewHolderAlbumDialog
    {
        @Bind(R.id.wcp_album)
        WheelCurvedPicker mWcpAlbum;

        AlertDialog mDialog;
        SelectAlbumListener mListener;
        String mCurrentAlbum;

        public ViewHolderAlbumDialog(AlertDialog dialog, SelectAlbumListener listener)
        {
            mDialog = dialog;
            mListener = listener;
        }

        public void bind(View view, String filter)
        {
            ButterKnife.bind(this, view);

            mWcpAlbum.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
                @Override
                public void onWheelScrolling(float deltaX, float deltaY) {

                }

                @Override
                public void onWheelSelected(int index, String data) {
                    mCurrentAlbum = data;
                }

                @Override
                public void onWheelScrollStateChanged(int state) {

                }
            });

            mWcpAlbum.setData(extractAlbumName(Master.getInstance().getAlbumDataLoader().getAll(), filter));

            mWcpAlbum.setWheelDecor(true, new AbstractWheelDecor() {
                @Override
                public void drawDecor(Canvas canvas, Rect rectLast, Rect rectNext, Paint paint) {
                    canvas.drawColor(0x8847A1D9);
                }
            });
        }

        @OnClick({R.id.btn_complete, R.id.btn_cancel})
        void onClick(View view)
        {
            int id = view.getId();
            switch (id)
            {
                case R.id.btn_complete:
                    mListener.onSelectedAlbum(Master.getInstance().getAlbumDataLoader().get(mCurrentAlbum));
                    if(mDialog.isShowing())
                        mDialog.dismiss();
                    break;
                case R.id.btn_cancel:
                    if(mDialog.isShowing())
                        mDialog.dismiss();
                    break;
            }
        }
    }
    public static void showSelectAlbumDialog(Context context, String filter,SelectAlbumListener listener)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_select_album, null);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.title_select_album))
                .setView(view)
                .create();

        ViewHolderAlbumDialog holder = new ViewHolderAlbumDialog(dialog, listener);
        holder.bind(view, filter);

        dialog.show();
        dialog.getWindow().setLayout(Util.dpToPx(context, 300), Util.dpToPx(context,200));
    }

    private static List<String> extractAlbumName(List<AlbumData> list,String filter)
    {
        List<String> result = new ArrayList<>();
        for(AlbumData album:list)
        {
            if(!album.getName().equals(filter))
                result.add(album.getName());
        }
        return result;
    }
}

package com.aviv.capturehelper.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.aviv.capturehelper.R;


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

}

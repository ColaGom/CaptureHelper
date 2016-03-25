package com.aviv.capturehelper.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aviv.capturehelper.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Colabear on 2016-03-25.
 */
public class CustomDialog extends Dialog {

    @Bind(R.id.root)
    LinearLayout mRoot;

    public CustomDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_custom);
        ButterKnife.bind(this);
    }

    public void addButton(String text, View.OnClickListener onClickListener)
    {
        Button button = new Button(getContext());
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setBackgroundResource(R.drawable.selector_main_blue100);
        button.setOnClickListener(onClickListener);

        mRoot.addView(button, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }
}

package com.aviv.capturehelper.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Util;

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
        button.setTextColor(getContext().getResources().getColor(R.color.main_color));
        button.setBackgroundResource(R.drawable.selector_white_blue100);
        button.setOnClickListener(onClickListener);

        mRoot.addView(button, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ViewGroup.LayoutParams params = button.getLayoutParams();
        params.width = Util.dpToPx(getContext(), 200);
        button.setLayoutParams(params);
    }
}

package com.aviv.capturehelper.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Colabear on 2016-03-23.
 */
public class SquaredFrameLayout extends FrameLayout {
    public SquaredFrameLayout(Context context) {
        super(context);
    }

    public SquaredFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
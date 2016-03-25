package com.aviv.capturehelper.view.adapter;

import android.view.View;

/**
 * Created by Colabear on 2016-03-23.
 */
public interface IHolder<T> {
    void bind(View view);
    void setView(T data);
}

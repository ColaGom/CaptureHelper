package com.aviv.capturehelper.common;

import java.io.File;

/**
 * Created by Colabear on 2016-03-28.
 */
public interface ScreenShotBinder {
    void onCapture(boolean success);
    File getImageFile();
}

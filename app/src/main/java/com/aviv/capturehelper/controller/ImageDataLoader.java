package com.aviv.capturehelper.controller;

import com.aviv.capturehelper.model.dao.ImageData;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;

/**
 * Created by Colabear on 2016-03-22.
 */
public class ImageDataLoader extends Loader<ImageData> {

    @Override
    AbstractDao<ImageData, Long> getDao() {
        return mSession.getImageDataDao();
    }

    @Override
    public ImageData get(long key) {
        return getDao().load(key);
    }

    @Override
    List<ImageData> getRange(int start, int count) {
        return null;
    }

    @Override
    public List<ImageData> getAll() {
        return getDao().loadAll();
    }

    @Override
    public void insert(ImageData data) {

    }

    @Override
    public void insert(ArrayList<ImageData> arr) {

    }
}
